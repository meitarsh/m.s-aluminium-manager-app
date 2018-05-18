@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.chaosruler.msa_manager.BuildConfig
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_projects_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.local_SQL_Helper
import com.example.chaosruler.msa_manager.object_types.project_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper
import java.util.*
import kotlin.collections.HashMap

/**
 * implenting the SQL helper on projects database (SQLITE)
 * @author Chaosruler972
 * @constructor a context to work with, the rest comes from strings.xml
 */
class local_projects_table_helper(
        /**
         * The context we are working with
         * @author Chaosruler972
         */
        private var context: Context
) : local_SQL_Helper(context,context.getString(R.string.LOCAL_SYNC_DATABASE_NAME),null,context.resources.getInteger(R.integer.LOCAL_PROJECTS_TABLE_VERSION),context.getString(R.string.LOCAL_PROJECTS_TABLE_NAME))
{
    /**
     * the project id field name
     * @author Chaosruler972
     */
    private var ID = context.getString(R.string.LOCAL_PROJECTS_COLUMN_ID)
    /**
     * the project name field name
     * @author Chaosruler972
     */
    private var NAME = context.getString(R.string.LOCAL_PROJECTS_COLUMN_NAME)
    /**
     * the dataaraeid field name
     * @author Chaosruler972
     */
    private var DATAAREAID = context.getString(R.string.LOCAL_PROJECTS_COLUMN_DATAARAEID)
    /**
     * the username field name
     * @author Chaosruler972
     */
    private var USERNAME = context.getString(R.string.LOCAL_PROJECTS_COLUMN_USERNAME)

    /**
     *    MUST BE CALLED, it reports to the database about the table schema, is used by the abstracted
     * SQL class
     * @author Chaosruler972
     */
    init
    {
        val vector: Vector<String> = Vector()
        vector.add(ID)
        vector.add(NAME)
        vector.add(DATAAREAID)
        vector.add(USERNAME)
        init_vector_of_variables(vector)


    }

    /**
     * provides info for the abstracted SQL class
     * on what the table schema is for creation
     * @author Chaosruler972
     * @param db an instance of database
     */
    override fun onCreate(db: SQLiteDatabase)
    {
        val map: HashMap<String, String> = HashMap()
        map[ID] = "TEXT "
        map[NAME] = "TEXT"
        map[DATAAREAID] = "TEXT"
        map[USERNAME] = "TEXT"
        createDB(db,map)
    }

    /**
     * adds all big, updates, inserts... whatever
     * @author Chaosruler972
     */
    fun sync_db()
    {
        if(!global_variables_dataclass.isLocal)
            return
        val server_vec = server_data_to_vector()
        for(item in server_vec)
        {
            add_project(item)
        }
    }

    /**
     *  converts DB to vector of projects data
     *  @author Chaosruler972
     *  @return a vector of projects table from local DB
     */
    fun get_local_DB():Vector<project_data>
    {
        Log.d("DB OF: ","projects")
        val vector: Vector<project_data> = Vector()
        val all_db: Vector<HashMap<String, String>> = get_db()
        all_db
                .filter {
                    @Suppress("USELESS_ELVIS_RIGHT_IS_NULL")
                    (it[USERNAME]?:null) != null && it[USERNAME] == remote_SQL_Helper.getusername()
                }
                .forEach { vector.addElement(project_data((it[ID]?:"").trim(), (it[NAME]?:"").trim(), (it[DATAAREAID]?:"").trim(), (it[USERNAME]?:"").trim())) }
        return vector
    }


    /**
     * get local DB by project name
     * @author Chaosruler972
     * @return a vector of projects table filtered by project name
     * @param projid the project id that represents the name of the project we want to filter
     */
    fun get_local_DB_by_projname(projid:String): Vector<project_data>
    {
        val vector: Vector<project_data> = Vector()

        val projdb: Vector<project_data> = global_variables_dataclass.db_project_vec

        projdb
                .filter { (it.getProjID()?:"")==projid }
                .forEach { vector.addElement(it) }
        return vector
    }

    /**
     *     subroutine to convert server data to vector of project data
     *  @author Chaosruler972
     *  @return a vector of project table from server DB
     */
    fun server_data_to_vector():Vector<project_data>
    {
        val typemap: java.util.HashMap<String, String> = remote_projects_table_helper.define_type_map()
        val server_data: Vector<java.util.HashMap<String, String>> =
        if(BuildConfig.DEBUG)
        {
            remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_PROJECTS),typemap,context.getString(R.string.PROJECTS_DATAAREAID),context.getString(R.string.DATAAREAID_DEVELOP))
        }
        else
        {
            remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_PROJECTS),typemap,null, null)
        }
        Log.d("projects","Synced ${server_data.count()} elements")
        val result_vector: Vector<project_data> = Vector()
        server_data.mapTo(result_vector) {
            project_data((it[remote_projects_table_helper.ID]?: "").trim(),
                    (it[remote_projects_table_helper.NAME]?: "").trim(), (it[remote_projects_table_helper.DATAAREAID]?: "").trim(),
                    remote_SQL_Helper.getusername().trim())
        }
        return result_vector
    }

    /**
     * server data to vector... by projid
     * @author Chaosruler972
     * @return a vector of projects table filtered by project name
     * @param projid the project id that represents the name of the project we want to filter
     */
    fun server_data_to_vector_by_projid(projid:String):Vector<project_data>
    {
        val typemap: java.util.HashMap<String, String> = remote_projects_table_helper.define_type_map()
        val server_data: Vector<java.util.HashMap<String, String>> =
                if(BuildConfig.DEBUG)
                {
                    remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_PROJECTS),typemap,context.getString(R.string.PROJECTS_DATAAREAID),context.getString(R.string.DATAAREAID_DEVELOP))
                }
                else
                {
                    remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_PROJECTS),typemap,null, null)
                }
        val result_vector: Vector<project_data> = Vector()
        server_data
                .filter { (it[remote_projects_table_helper.ID] ?: "") == projid }
                .forEach {
                    result_vector.addElement(project_data((it[remote_projects_table_helper.ID] ?: "").trim(),
                            (it[remote_projects_table_helper.NAME] ?: "").trim(), (it[remote_projects_table_helper.DATAAREAID] ?: "").trim(),
                            remote_SQL_Helper.getusername().trim()))
                }
        return result_vector
    }

    /**
     *   subroutine that is in charge of getting the project class
     * by query
     * @author Chaosruler972
     * @param proj the project data to filter by
     * @return the project data from the server, null if not found
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun get_project_by_project(proj: project_data) // subroutine to get a project object
            : project_data?
    {
        val input_map = HashMap<String, String>()
        input_map[ID] = "'${proj.getProjID()}'"
        val vector = get_rows(input_map)
        if(vector.size > 0)
        {
            return project_data((vector.firstElement()[ID]?:"").trim(), (vector.firstElement()[NAME]?:"").trim(), (vector.firstElement()[DATAAREAID]?:"").trim(), (vector.firstElement()[USERNAME]?:"").trim())
        }


        return null
    }


    /**
     * add project mechanism
     * if project is invalid, forget about it
     * if project is valid, and it exists, update it
     * if its a new project, add a new project to table
     * @author Chaosruler972
     * @param project the project data object to add
     * @return if add was successfull true, else false
     */
    fun add_project(project: project_data) // subroutine that manages the project adding operation to the database
            : Boolean
    {
        val res =  (remote_SQL_Helper.get_latest_sync_time().time > 0.toLong() &&
                update_project(project,project.copy())) // checks if project exists in database
             // if it does, lets update
        if(!res) // if it doesn't lets create a new entry for the project
            return insert_project(project)
        return res
    }

    /**
     * checks if project exists, query is not that smart, gets an ENTIRE table and than checks
     * if the opr is there
     *
     * // on update
     * will select USERNAME only
     * @param proj the project to check if exists or not
     * @return if the project data was found or not
     * @author Chaosruler972
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun check_project(proj: project_data) // subroutine to check if project exists on the database
            : Boolean {
        val project:project_data? = get_project_by_project( proj)
        return project != null
    }

    /**
     *  subroutine in charge of feeding schema and database information to SQL
     * abstract implentation on insert queries
     * @author Chaosruler972
     * @param proj the project table data that we want to insert
     * @return if insertion was successfull or not
     */
    private fun insert_project(proj: project_data):Boolean // subroutine to insert a project to the database
    {

        val everything_to_add: Vector<HashMap<String, String>> = Vector()

        val data: HashMap<String, String> = HashMap()
        data[ID] = (proj.getProjID() ?: "").trim()
        data[NAME] = (proj.get_project_name() ?: "").trim()
        data[DATAAREAID] = (proj.get_DATAREAID() ?: "").trim()
        data[USERNAME] = (proj.get_USERNAME() ?: "").trim()
        everything_to_add.addElement(data)
        return add_data(everything_to_add, false)
    }

    /**
     *  subroutine in charge of feeding information and database information to
     * SQL abstraction on update queries
     * @author Chaosruler972
     * @param from the source that we want to update
     * @param to what we want to update it to
     * @return if update was successfull or not
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun update_project(from: project_data, to: project_data) // subroutine to update data of a project that exists on the database
            : Boolean {

        val change_to: HashMap<String, String> = HashMap()
        change_to[NAME] = (to.get_project_name() ?: "").trim()
        change_to[DATAAREAID] = (to.get_DATAREAID() ?: "").trim()
        change_to[USERNAME] = (to.get_USERNAME() ?: "").trim()
        return update_data(ID, arrayOf(from.getProjID()!!),change_to)
    }

    /**
     * subroutine in charge of feeding information and database information to
     * SQL abstraction on delete queries
     * @author Chaosruler972
     * @param project the source that we want to remove
     * @return if removal was successfull or not
     */
    @Suppress("unused")
    fun delete_project( project: project_data):Boolean // subroutine to delete a project from the database (local)
    {
        if ( get_project_by_project(project)==null )
            return false
        return remove_from_db(ID, arrayOf((project.getProjID()?:"").trim()))

    }

    /**
     * gets an project by ID
     * @author Chaosruler972
     * @param id the id that we want to filter by
     * @return the project itself if found, null otheerwise
     */
    fun get_project_by_id(id:String):project_data?
    {
        return get_project_by_project(project_data(id, null, null, remote_SQL_Helper.getusername()))
    }


}