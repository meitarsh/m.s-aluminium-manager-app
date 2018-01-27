@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.chaosruler.msa_manager.BuildConfig
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_big_table_helper
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_opr_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.local_SQL_Helper
import com.example.chaosruler.msa_manager.object_types.big_table_data
import com.example.chaosruler.msa_manager.object_types.opr_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper
import java.util.*
import kotlin.collections.HashMap

/**
 * implenting the SQL helper on operation database (SQLITE)
 * @author Chaosruler972
 * @constructor a context to work with, the rest comes from strings.xml
 */
class local_OPR_table_helper(
        /**
         * The context we are working with
         * @author Chaosruler972
         */
        private var context: Context
): local_SQL_Helper(context,context.getString(R.string.LOCAL_SYNC_DATABASE_NAME),null,context.resources.getInteger(R.integer.LOCAL_OPR_TABLE_VERSION),context.getString(R.string.LOCAL_OPR_TABLE_NAME)) {
    /**
     * The operation id field name
     * @author Chaosruler972
     */
    private val ID: String = context.getString(R.string.LOCAL_OPR_COLUMN_ID)
    /**
     * the operation name field name
     * @author Chaosruler972
     */
    private val NAME: String = context.getString(R.string.LOCAL_OPR_COLUMN_NAME)
    /**
     * the dataaraeid field name
     * @author Chaosruler972
     */
    private val DATAARAEID: String = context.getString(R.string.LOCAL_OPR_COLUMN_DATAARAEID)
    /**
     * The username that synced this data
     * @author Chaosruler972
     */
    private val USER: String = context.getString(R.string.LOCAL_OPR_COLUMN_USERNAME)

    /**
     *    MUST BE CALLED, it reports to the database about the table schema, is used by the abstracted
     * SQL class
     * @author Chaosruler972
     */
    init {
        val vector: Vector<String> = Vector()
        vector.add(ID)
        vector.add(NAME)
        vector.add(DATAARAEID)
        vector.add(USER)
        init_vector_of_variables(vector)

    }

    /**
     * provides info for the abstracted SQL class
     * on what the table schema is for creation
     * @author Chaosruler972
     * @param db an instance of database
     */
    override fun onCreate(db: SQLiteDatabase) {
        val map: HashMap<String, String> = HashMap()
        map[ID] = "BLOB primary key"
        map[NAME] = "BLOB"
        map[USER] = "BLOB"
        map[DATAARAEID] = "BLOB"
        createDB(db, map)
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
            add_opr(item)
        }
    }

    /**
     *  converts DB to vector of operation data
     *  @author Chaosruler972
     *  @return a vector of operation table from local DB
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun get_local_DB():Vector<opr_data>
    {
        Log.d("DB OF: ","OPR")
        val vector: Vector<opr_data> = Vector()

        val all_db: Vector<HashMap<String, String>> = get_db()
        all_db
                .filter {
                    @Suppress("USELESS_ELVIS_RIGHT_IS_NULL")
                    (it[USER]?:null) != null && it[USER] == remote_SQL_Helper.getusername()
                }
                .forEach { vector.addElement(opr_data((it[ID]?:"").trim(), (it[NAME]?:"").trim(), (it[DATAARAEID]?:"").trim(), (it[USER]?:"").trim())) }
        return vector
    }

    /**
     * get local DB by project name
     * @author Chaosruler972
     * @return a vector of operations table filtered by project name
     * @param projid the project id that represents the name of the project we want to filter
     */
    fun get_local_DB_by_projname(projid:String): Vector<opr_data>
    {
        val vector: Vector<opr_data> = Vector()

        val all_db: Vector<big_table_data> = global_variables_dataclass.DB_BIG!!.get_local_DB()
        val oprdb: Vector<opr_data> = global_variables_dataclass.DB_OPR!!.get_local_DB()


        for(opr in oprdb)
        {
            for (big in all_db)
            {
                if((big.get_PROJECT_ID()?:"") == projid && (big.get_OPRID()?:"") == (opr.get_oprid()?:"1")  ) {
                    vector.addElement(opr)
                    break
                }
            }
        }
        return vector
    }

    /**
     *     subroutine to convert server data to vector of operation data
     *  @author Chaosruler972
     *  @return a vector of operation table from server DB
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun server_data_to_vector():Vector<opr_data>
    {
        val server_data: Vector<java.util.HashMap<String, String>> =
        if(BuildConfig.DEBUG)
        {
            val typemap: java.util.HashMap<String, String> = remote_opr_table_helper.define_type_map()
            remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_OPR),typemap,context.getString(R.string.OPR_DATAAREAID),context.getString(R.string.DATAAREAID_DEVELOP))
        }
        else
        {
            remote_SQL_Helper.get_all_table(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_OPR))
        }
        val result_vector: Vector<opr_data> = Vector()
        server_data.mapTo(result_vector) {
            opr_data((it[remote_opr_table_helper.ID] ?: "").trim(),
                    (it[remote_opr_table_helper.NAME] ?: "").trim(), (it[remote_opr_table_helper.DATAAREAID] ?: "").trim(),
                    remote_SQL_Helper.getusername().trim())
        }
        return result_vector
    }
    /**
     * server data to vector... by projid
     * @author Chaosruler972
     * @return a vector of operations table filtered by project name
     * @param projid the project id that represents the name of the project we want to filter
     */
    fun server_data_to_vector_by_projname(projid: String): Vector<opr_data>
    {

        val server_data_big: Vector<java.util.HashMap<String, String>> =
                if(BuildConfig.DEBUG)
                {
                    val typemap: HashMap<String, String> = remote_big_table_helper.define_type_map()
                    remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_BIG),typemap,context.getString(R.string.TABLE_BIG_DATAAREAID),context.getString(R.string.DATAAREAID_DEVELOP))
                }
                else
                {
                    remote_SQL_Helper.get_all_table(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_BIG))
                }

        val server_data_opr: Vector<java.util.HashMap<String, String>> =
                if(BuildConfig.DEBUG)
                {
                    val typemap: HashMap<String, String> = remote_opr_table_helper.define_type_map()
                    remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_OPR),typemap,context.getString(R.string.OPR_DATAAREAID),context.getString(R.string.DATAAREAID_DEVELOP))
                }
                else
                {
                    remote_SQL_Helper.get_all_table(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_OPR))
                }
        val result_vector: Vector<opr_data> = Vector()
        for(opr in server_data_opr)
        {
            for (big in server_data_big)
            {
                if((big[remote_big_table_helper.PROJECTS_ID]?:"").trim() == projid && (big[remote_big_table_helper.OPR_ID]?:"").trim() == (opr[remote_opr_table_helper.ID]?:"1").trim()  ) {
                    result_vector.addElement(opr_data( (opr[remote_opr_table_helper.ID]?:"").trim(),
                            (opr[remote_opr_table_helper.NAME]?:"").trim(),(opr[remote_opr_table_helper.DATAAREAID]?:"").trim()
                    ,(remote_SQL_Helper.getusername().trim())))

                    break
                }
            }
        }

        return result_vector
    }

    /**
     *   subroutine that is in charge of getting the opr class
     * by query
     * @author Chaosruler972
     * @param opr_data the opr data to filter by
     * @return the opr data from the server, null if not found
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun get_opr_by_opr(opr_data: opr_data) // subroutine to get a opr object
            : opr_data?
    {
        val input_map = HashMap<String, String>()
        input_map[ID] = "'${opr_data.get_oprid()}'"
        val vector = get_rows(input_map)
        if(vector.size > 0)
        {
            return opr_data((vector.firstElement()[ID]?:"").trim(), (vector.firstElement()[NAME]?:"").trim(), (vector.firstElement()[DATAARAEID]?:"").trim(), (vector.firstElement()[USER]?:"").trim())
        }


        return null
    }



    /**
     * add inventory mechanism
     * add opr mechanism
     * if opr is invalid, forget about it
     * if opr is valid, and it exists, update it
     * if its a new opr, add a new opr to table
     * @author Chaosruler972
     * @param opr_data the opr data object to add
     * @return if add was successfull true, else false
     */
    fun add_opr(opr_data: opr_data) // subroutine that manages the opr adding operation to the database
            : Boolean
    {

        return if (check_opr( opr_data)) // checks if opr exists in database
            update_opr(opr_data,opr_data.copy()) // if it does, lets update
        else // if it doesn't lets create a new entry for the opr
            insert_opr(opr_data)
    }

    /**
     * checks if operation exists, query is not that smart, gets an ENTIRE table and than checks
     * if the opr is there
     *
     * // on update
     * will select USERNAME only
     * @param opr_data the opr to check if exists or not
     * @return if the inventory data was found or not
     * @author Chaosruler972
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun check_opr(opr_data: opr_data) // subroutine to check if opr exists on the database
            : Boolean {
        val opr:opr_data? = get_opr_by_opr( opr_data)
        return opr != null
    }



    /**
     *  subroutine in charge of feeding schema and database information to SQL
     * abstract implentation on insert queries
     * @author Chaosruler972
     * @param opr_data the opr table data that we want to insert
     * @return if insertion was successfull or not
     */
    private fun insert_opr(opr_data: opr_data):Boolean // subroutine to insert a opr to the database
    {

        val everything_to_add: Vector<HashMap<String, String>> = Vector()

        val data: HashMap<String, String> = HashMap()
        data[ID] = (opr_data.get_oprid() ?: "").trim()
        data[NAME] = (opr_data.get_opr_name() ?: "").trim()
        data[DATAARAEID] = (opr_data.get_DATAREAID() ?: "").trim()
        data[USER] = (opr_data.get_USERNAME() ?: "").trim()
        everything_to_add.addElement(data)
        return add_data(everything_to_add)
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
    fun update_opr(from:opr_data,to:opr_data) // subroutine to update data of a opr that exists on the database
            : Boolean {

        val change_to: HashMap<String, String> = HashMap()
        change_to[NAME] = (to.get_opr_name() ?: "").trim()
        change_to[DATAARAEID] = (to.get_DATAREAID() ?: "").trim()
        change_to[USER] = (to.get_USERNAME() ?: "").trim()
        return update_data(ID, arrayOf((from.get_oprid()?:"").trim()),change_to)
    }

    /**
     * subroutine in charge of feeding information and database information to
     * SQL abstraction on delete queries
     * @author Chaosruler972
     * @param opr_data the source that we want to remove
     * @return if removal was successfull or not
     */
    @Suppress("unused")
    fun delete_opr( opr_data: opr_data):Boolean // subroutine to delete a opr from the database (local)
    {
        if ( get_opr_by_opr(opr_data)==null )
            return false
        return remove_from_db(ID, arrayOf((opr_data.get_oprid()?:"").trim()))

    }

    /**
     * gets an opr by ID
     * @author Chaosruler972
     * @param id the id that we want to filter by
     * @return the opr itself if found, null otheerwise
     */
    fun get_opr_by_id(id: String): opr_data?
    {
        val mock_obj = opr_data(id, null, null, remote_SQL_Helper.getusername())
        return get_opr_by_opr(mock_obj)
    }
}