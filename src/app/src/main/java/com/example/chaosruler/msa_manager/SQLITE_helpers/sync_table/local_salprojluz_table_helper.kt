@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_salprojluz_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.local_SQL_Helper
import com.example.chaosruler.msa_manager.object_types.salprojluz_data
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper
import java.util.*
import com.example.chaosruler.msa_manager.BuildConfig
import com.example.chaosruler.msa_manager.services.global_variables_dataclass


/**
 * Local salproijluz table helper
 * @author Chaosruler972
 */
class local_salprojluz_table_helper(
        /**
         * The context we are working with
         * @author Chaosruler972
         */
        private var context: Context
) : local_SQL_Helper(context,
        context.getString(R.string.LOCAL_SYNC_DATABASE_NAME),
        null,
        context.resources.getInteger(R.integer.LOCAL_SALPROJLUZ_TABLE_VERSION),
        context.getString(R.string.LOCAL_SALPROJLUZ_TABLE_NAME)
        )
{
    /**
     * ID
     * @author Chaosruler972
     */
    private val ID = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_ID)
    /**
     * startdate
     * @author Chaosruler972
     */
    private val STARTDATE = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_STARTDATE)
    /**
     * finish date
     * @author Chaosruler972
     */
    private val FINISHDATE = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_FINISHDATE)
    /**
     * sium bpoal
     * @author Chaosruler972
     */
    private val SIUMBPOAL = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_SIUMBPOAL)
    /**
     * is finished
     * @author Chaosruler972
     */
    private val IS_FINISHED = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_ISFINISHED)
    /**
     * notes
     * @author Chaosruler972
     */
    private val NOTES = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_NOTES)
    /**
     * koma
     * @author Chaosruler972
     */
    private val KOMA = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_KOMA)
    /**
     * building
     * @author Chaosruler972
     */
    private val BUILDING = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_BUILDING)

    /**
     * Percent executed
     * @author Chaosruler972
     */
    private val PERCENTEXC = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_PERCENTEXC)

    /**
     * dataaraeid
     * @author Chaosruler972
     */
    private val DATAARAEID = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_DATAARAEID)

    /**
     * recid
     * @author Chaosruler972
     */
    private val RECID = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_RECID)

    /**
     * recversion
     * @author Chaosruler972
     */
    private val RECVERSION = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_RECVERSION)

    /**
     * username
     * @author Chaosruler972
     */
    private val USERNAME = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_USERNAME)

    init {
        val vector: Vector<String> = Vector()
        vector.add(ID)
        vector.add(STARTDATE)
        vector.add(FINISHDATE)
        vector.add(SIUMBPOAL)
        vector.add(IS_FINISHED)
        vector.add(NOTES)
        vector.add(KOMA)
        vector.add(BUILDING)
        vector.add(DATAARAEID)
        vector.add(USERNAME)
        vector.add(PERCENTEXC)
        vector.add(RECID)
        vector.addElement(RECVERSION)
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
        map[ID] = "TEXT "
        map[STARTDATE] = "TEXT"
        map[FINISHDATE] = "TEXT"
        map[SIUMBPOAL] = "TEXT"
        map[IS_FINISHED] = "INTEGER"
        map[NOTES] = "TEXT"
        map[KOMA] = "TEXT"
        map[BUILDING] = "TEXT"
        map[DATAARAEID] = "TEXT"
        map[USERNAME] = "TEXT"
        map[PERCENTEXC] = "TEXT"
        map[RECID] = "TEXT"
        map[RECVERSION] = "TEXT"
        createDB(db, map)
    }

    /**
     *  converts DB to vector of salrpojluz data
     *  @author Chaosruler972
     *  @return a vector of vendor table from local DB
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun get_local_DB():Vector<salprojluz_data>
    {
        Log.d("DB OF: ","salrpojluz")
        val vector: Vector<salprojluz_data> = Vector()

        val all_db: Vector<HashMap<String, String>> = get_db()
        all_db
                .filter {
                    @Suppress("USELESS_ELVIS_RIGHT_IS_NULL")
                    (it[USERNAME]?:null) != null && it[USERNAME] == remote_SQL_Helper.getusername()
                }
                .forEach { vector.addElement(salprojluz_data(
                        it[ID]?:"",it[STARTDATE]?:"",it[FINISHDATE]?:"",
                        ((it[IS_FINISHED]?:"0") == "0"), it[SIUMBPOAL]?:"",it[NOTES]?:"",
                        it[KOMA]?:"",it[BUILDING]?:"",it[PERCENTEXC]?:"",
                        it[DATAARAEID]?:"",it[RECID]?:"", it[RECVERSION]?:"",it[USERNAME]?:""
                )) }
        return vector
    }

    /**
     *     subroutine to convert server data to vector of vendor data
     *  @author Chaosruler972
     *  @return a vector of vendor table from server DB
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun server_data_to_vector():Vector<salprojluz_data>
    {
        val typemap: HashMap<String, String> = remote_salprojluz_table_helper.define_type_map()
        val server_data: Vector<java.util.HashMap<String, String>> =
                if(BuildConfig.DEBUG)
                {
                    remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_SALPROJLUZ),typemap,context.getString(R.string.TABLE_SALPROJLUZ_DATAAREAID),context.getString(R.string.DATAAREAID_DEVELOP))
                }
                else
                {
                    remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_SALPROJLUZ),typemap,null, null)
                }

        val result_vector: Vector<salprojluz_data> = Vector()
        server_data.mapTo(result_vector) {
                salprojluz_data(
                        (it[remote_salprojluz_table_helper.ID]?:""),
                        (it[remote_salprojluz_table_helper.STARTDATE]?:""),
                        (it[remote_salprojluz_table_helper.FINISHDATE]?:""),
                        ((it[remote_salprojluz_table_helper.IS_FINISHED]?:"")=="true"),
                        (it[remote_salprojluz_table_helper.SIUMBPOAL]?:""),
                        (it[remote_salprojluz_table_helper.NOTES]?:""),
                        (it[remote_salprojluz_table_helper.KOMA]?:""),
                        (it[remote_salprojluz_table_helper.BUILDING]?:""),
                        (it[remote_salprojluz_table_helper.PERCENTEXC]?:""),
                        (it[remote_salprojluz_table_helper.DATAAREAID]?:""),
                        (it[remote_salprojluz_table_helper.RECID]?:""),
                        (it[remote_salprojluz_table_helper.RECVERION]?:""),
                        remote_SQL_Helper.getusername()
                )
        }
        return result_vector
    }

    /**
     * server data to vector... by projid
     * @author Chaosruler972
     * @return a vector of vendor table filtered by project name
     * @param projid the project id that represents the name of the project we want to filter
     */
    fun server_data_to_vector_by_projname(projid: String): Vector<salprojluz_data>
    {
        val typemap: HashMap<String, String> = remote_salprojluz_table_helper.define_type_map()
        val server_data_big: Vector<java.util.HashMap<String, String>> =
                if(BuildConfig.DEBUG)
                {
                    remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_SALPROJLUZ),typemap,context.getString(R.string.TABLE_BIG_DATAAREAID),context.getString(R.string.DATAAREAID_DEVELOP))
                }
                else
                {
                    remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_SALPROJLUZ),typemap,remote_salprojluz_table_helper.ID, projid)
                }

        val result_vector: Vector<salprojluz_data> = Vector()
        server_data_big.mapTo(result_vector) {
            salprojluz_data(
                    (it[remote_salprojluz_table_helper.ID]?:""),
                    (it[remote_salprojluz_table_helper.STARTDATE]?:""),
                    (it[remote_salprojluz_table_helper.FINISHDATE]?:""),
                    ((it[remote_salprojluz_table_helper.IS_FINISHED]?:"")=="true"),
                    (it[remote_salprojluz_table_helper.SIUMBPOAL]?:""),
                    (it[remote_salprojluz_table_helper.NOTES]?:""),
                    (it[remote_salprojluz_table_helper.KOMA]?:""),
                    (it[remote_salprojluz_table_helper.BUILDING]?:""),
                    (it[remote_salprojluz_table_helper.PERCENTEXC]?:""),
                    (it[remote_salprojluz_table_helper.RECID]?:""),
                    (it[remote_salprojluz_table_helper.RECVERION]?:""),
                    (it[remote_salprojluz_table_helper.DATAAREAID]?:""),
                    remote_SQL_Helper.getusername()
            )
        }
        return result_vector
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
            add_salprojluz(item)
        }
    }

    /**
     *   subroutine that is in charge of getting the vendor class
     * by query
     * @author Chaosruler972
     * @param salprojluz_data the vendor data to filter by
     * @return the vendor data from the server, null if not found
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun get_salproj_by_salproj(salprojluz_data: salprojluz_data) // subroutine to get a vendor object
            : salprojluz_data?
    {
        val input_map = HashMap<String, String>()
        input_map[ID] = "'${salprojluz_data.get_projid()}'"
        val vector = get_rows(input_map)
        if(vector.size > 0)
        {
            val it = vector[0]

            return  salprojluz_data(
                    (it[ID]?:""),
                    (it[STARTDATE]?:""),
                    (it[FINISHDATE]?:""),
                    ((it[IS_FINISHED]?:"")=="true"),
                    (it[SIUMBPOAL]?:""),
                    (it[NOTES]?:""),
                    (it[KOMA]?:""),
                    (it[BUILDING]?:""),
                    (it[PERCENTEXC]?:""),
                    (it[RECID]?:""),
                    (it[RECVERSION]?:""),
                    (it[DATAARAEID]?:""),
                    remote_SQL_Helper.getusername()
            )
        }


        return null
    }

    /**
     * add vendor mechanism
     *  add vendor mechanism
     * if vendor is invalid, forget about it
     * if vendor is valid, and it exists, update it
     * if its a new vendor, add a new vendor to table
     * @author Chaosruler972
     * @param salprojluz_data the vendor data object to add
     * @return if add was successfull true, else false
     */
    fun add_salprojluz(salprojluz_data: salprojluz_data) // subroutine that manages the vendor adding operation to the database
            : Boolean
    {
        val res = (remote_SQL_Helper.get_latest_sync_time().time > 0.toLong() &&
                update_salproj(salprojluz_data,salprojluz_data.copy())) // checks if vendor exists in database
           // if it does, lets update
        if(!res)
            return insert_salproj(salprojluz_data)
        return res
    }

    /**
     * checks if vendor exists, query is not that smart, gets an ENTIRE table and than checks
     * if the opr is there
     *
     * // on update
     * will select USERNAME only
     * @param salprojluz_data the vendor to check if exists or not
     * @return if the vendor data was found or not
     * @author Chaosruler972
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun check_salproj(salprojluz_data: salprojluz_data) // subroutine to check if vendor exists on the database
            : Boolean {
        val new_data:salprojluz_data? = get_salproj_by_salproj( salprojluz_data)
        return new_data != null
    }

    /**
     *  subroutine in charge of feeding schema and database information to SQL
     * abstract implentation on insert queries
     * @author Chaosruler972
     * @param salprojluz_data the vendor table data that we want to insert
     * @return if insertion was successfull or not
     */
    private fun insert_salproj(salprojluz_data: salprojluz_data):Boolean // subroutine to insert a vendor to the database
    {

        val everything_to_add: Vector<HashMap<String, String>> = Vector()

        val data: HashMap<String, String> = HashMap()
        data[ID] = (salprojluz_data.get_projid()?:"")
        data[STARTDATE] = (salprojluz_data.get_startdate()?:"")
        data[FINISHDATE] = (salprojluz_data.get_finishdate()?:"")
        data[SIUMBPOAL] = (salprojluz_data.get_siumbpoal()?:"")
        data[IS_FINISHED] = if (salprojluz_data.get_isfinished())
            "1"
        else
            "0"
        data[NOTES] = (salprojluz_data.get_notes()?:"")
        data[KOMA] = (salprojluz_data.get_koma()?:"")
        data[BUILDING] = (salprojluz_data.get_building()?:"")
        data[DATAARAEID] = (salprojluz_data.get_dataaraeid()?:"")
        data[PERCENTEXC] = (salprojluz_data.get_percentexc()?:"")
        data[RECID] = (salprojluz_data.get_recid()?:"")
        data[RECVERSION] = (salprojluz_data.get_recversion()?:"")
        data[USERNAME] = remote_SQL_Helper.getusername()
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
    fun update_salproj(from:salprojluz_data,to:salprojluz_data) // subroutine to update data of a vendor that exists on the database
            : Boolean {

        val change_to: HashMap<String, String> = HashMap()
        change_to[STARTDATE] = (to.get_startdate()?:"")
        change_to[FINISHDATE] = (to.get_finishdate()?:"")
        change_to[SIUMBPOAL] = (to.get_siumbpoal()?:"")
        change_to[IS_FINISHED] = if (to.get_isfinished())
            "1"
        else
            "0"
        change_to[NOTES] = (to.get_notes()?:"")
        change_to[KOMA] = (to.get_koma()?:"")
        change_to[BUILDING] = (to.get_building()?:"")
        change_to[DATAARAEID] = (to.get_dataaraeid()?:"")
        change_to[USERNAME] = (to.get_username()?:"")
        change_to[PERCENTEXC] = (to.get_percentexc()?:"")
        change_to[ID] = (to.get_projid()?:"")

        return update_data(RECID, arrayOf(from.get_recid()!!),change_to)
    }

    /**
     * subroutine in charge of feeding information and database information to
     * SQL abstraction on delete queries
     * @author Chaosruler972
     * @param salprojluz_data the source that we want to remove
     * @return if removal was successfull or not
     */
    @Suppress("unused")
    fun delete_salproj_data( salprojluz_data: salprojluz_data):Boolean // subroutine to delete a vendor from the database (local)
    {
        if ( get_salproj_by_salproj(salprojluz_data)==null )
            return false
        return remove_from_db(RECID, arrayOf((salprojluz_data.get_recid()?:"").trim()))

    }

    /**
     * gets an vendor by ID
     * @author Chaosruler972
     * @param id the id that we want to filter by
     * @return the vendor itself if found, null otheerwise
     */
    fun get_salproj_by_id(id:String):salprojluz_data? = get_salproj_by_salproj(salprojluz_data(id,"","",false,"","","","","","","","",""))
}