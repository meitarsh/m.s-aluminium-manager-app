package com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.chaosruler.msa_manager.BuildConfig
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_big_table_helper
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_opr_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.opr_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.abstraction_classes.local_SQL_Helper
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.big_table_data
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.project_data
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper
import java.util.*
import kotlin.collections.HashMap


class local_OPR_table_helper(private var context: Context): local_SQL_Helper(context,context.getString(R.string.LOCAL_SYNC_DATABASE_NAME),null,context.getString(R.string.LOCAL_OPR_TABLE_VERSION).toInt(),context.getString(R.string.LOCAL_OPR_TABLE_NAME)) {
    private val ID: String = context.getString(R.string.LOCAL_OPR_COLUMN_ID)
    private val NAME: String = context.getString(R.string.LOCAL_OPR_COLUMN_NAME)
    private val DATAARAEID: String = context.getString(R.string.LOCAL_OPR_COLUMN_DATAARAEID)
    private val USER: String = context.getString(R.string.LOCAL_OPR_COLUMN_USERNAME)

    /*
    MUST BE CALLED, it reports to the database about the table schema, is used by the abstracted
    SQL class
 */
    init {
        var vector: Vector<String> = Vector()
        vector.add(ID)
        vector.add(NAME)
        vector.add(DATAARAEID)
        vector.add(USER)
        init_vector_of_variables(vector)

    }

    /*
           provides info for the abstracted SQL class
           on what the table schema is for creation
        */
    override fun onCreate(db: SQLiteDatabase) {
        var map: HashMap<String, String> = HashMap()
        map[ID] = "text primary key"
        map[NAME] = "text"
        map[USER] = "text"
        map[DATAARAEID] = "text"
        createDB(db, map)
    }

    /*
       adds all opr, updates, inserts... whatever
    */
    fun sync_db()
    {
        if(!global_variables_dataclass.isLocal)
            return
        var server_vec = server_data_to_vector()
        for(item in server_vec)
        {
            add_opr(item)
        }
    }

    /*
        converts DB to vector of opr
     */
    fun get_local_DB():Vector<opr_data>
    {
        var vector:Vector<opr_data> = Vector()

        var all_db:Vector<HashMap<String,String>> = get_db()
        all_db
                .filter { (it[USER]?:null) != null && it[USER] == remote_SQL_Helper.getusername() }
                .forEach { vector.addElement(opr_data((it[ID]?:"").trim(), (it[NAME]?:"").trim(), (it[DATAARAEID]?:"").trim(), (it[USER]?:"").trim())) }
        return vector
    }
    /*
              get local DB by project name
           */
    fun get_local_DB_by_projname(projid:String): Vector<opr_data>
    {
        var vector: Vector<opr_data> = Vector()

        var all_db: Vector<big_table_data> = global_variables_dataclass.DB_BIG!!.get_local_DB()
        var oprdb : Vector<opr_data> = global_variables_dataclass.DB_OPR!!.get_local_DB()


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

    /*
           subroutine to convert server data to vector of opr
        */
    fun server_data_to_vector():Vector<opr_data>
    {
        var server_data: Vector<java.util.HashMap<String, String>> =
        if(BuildConfig.DEBUG)
        {
            var typemap: java.util.HashMap<String, String> = remote_opr_table_helper.make_type_map()
            remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_OPR),typemap,context.getString(R.string.OPR_DATAAREAID),context.getString(R.string.DATAAREAID_DEVELOP))
        }
        else
        {
            remote_SQL_Helper.get_all_table(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_OPR))
        }
        var result_vector:Vector<opr_data> = Vector()
        server_data.mapTo(result_vector) {
            opr_data((it[remote_opr_table_helper.ID] ?: "").trim(),
                    (it[remote_opr_table_helper.NAME] ?: "").trim(), (it[remote_opr_table_helper.DATAAREAID] ?: "").trim(),
                    remote_SQL_Helper.getusername().trim())
        }
        return result_vector
    }
    /*
           gets server data by projname
        */
    fun server_data_to_vector_by_projname(projid: String): Vector<opr_data>
    {

        var server_data_big: Vector<java.util.HashMap<String, String>> =
                if(BuildConfig.DEBUG)
                {
                    var typemap:HashMap<String,String> = remote_big_table_helper.make_type_map()
                    remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_BIG),typemap,context.getString(R.string.TABLE_BIG_DATAAREAID),context.getString(R.string.DATAAREAID_DEVELOP))
                }
                else
                {
                    remote_SQL_Helper.get_all_table(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_BIG))
                }

        var server_data_opr: Vector<java.util.HashMap<String, String>> =
                if(BuildConfig.DEBUG)
                {
                    var typemap:HashMap<String,String> = remote_opr_table_helper.make_type_map()
                    remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_OPR),typemap,context.getString(R.string.OPR_DATAAREAID),context.getString(R.string.DATAAREAID_DEVELOP))
                }
                else
                {
                    remote_SQL_Helper.get_all_table(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_OPR))
                }
        var result_vector: Vector<opr_data> = Vector()
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
    /*
           subroutine that is in charge of getting the opr class
           by query
        */
    fun get_opr_by_opr(opr_data: opr_data) // subroutine to get a opr object
            : opr_data?
    {
        var input_map = HashMap<String,String>()
        input_map[ID] = "'${opr_data.get_oprid()}'"
        val vector = get_rows(input_map)
        if(vector.size > 0)
        {
            return opr_data((vector.firstElement()[ID]?:"").trim(), (vector.firstElement()[NAME]?:"").trim(), (vector.firstElement()[DATAARAEID]?:"").trim(), (vector.firstElement()[USER]?:"").trim())
        }


        return null
    }


    /*
      add opr mechanism
      if opr is invalid, forget about it
      if opr is valid, and it exists, update it
      if its a new opr, add a new opr to table
   */
    fun add_opr(opr_data: opr_data) // subroutine that manages the opr adding operation to the database
            : Boolean
    {
        /*
       var map:HashMap<String,String> = HashMap()
       map[ID] = opr_data.get_oprid() ?: ""
       map[NAME] = opr_data.get_opr_name() ?: ""
       map[DATAARAEID] = opr_data.get_DATAREAID() ?: ""
       map[USER] = opr_data.get_USERNAME() ?: ""
       return replace(map)
       */
        return if (check_opr( opr_data)) // checks if opr exists in database
            update_opr(opr_data,opr_data.copy()) // if it does, lets update
        else // if it doesn't lets create a new entry for the opr
            insert_opr(opr_data)
    }

    /*
          checks if opr exists, query is not that smart, gets an ENTIRE table and than checks
          if the opr is there

          // on update
          will select USERNAME only
       */
    fun check_opr(opr_data: opr_data) // subroutine to check if opr exists on the database
            : Boolean {
        val opr:opr_data? = get_opr_by_opr( opr_data)
        return opr != null
    }
    /*
        subroutine in charge of feeding schema and database information to SQL
        abstract implentation on insert queries
     */
    private fun insert_opr(opr_data: opr_data):Boolean // subroutine to insert a opr to the database
    {

        var everything_to_add:Vector<HashMap<String,String>> = Vector()

        var data: HashMap<String,String> = HashMap()
        data[ID] = (opr_data.get_oprid() ?: "").trim()
        data[NAME] = (opr_data.get_opr_name() ?: "").trim()
        data[DATAARAEID] = (opr_data.get_DATAREAID() ?: "").trim()
        data[USER] = (opr_data.get_USERNAME() ?: "").trim()
        everything_to_add.addElement(data)
        return add_data(everything_to_add)
    }

    /*
      subroutine in charge of feeding information and database information to
      SQL abstraction on update queries
   */
    fun update_opr(from:opr_data,to:opr_data) // subroutine to update data of a opr that exists on the database
            : Boolean {

        var change_to:HashMap<String,String> = HashMap()
        change_to[NAME] = (to.get_opr_name() ?: "").trim()
        change_to[DATAARAEID] = (to.get_DATAREAID() ?: "").trim()
        change_to[USER] = (to.get_USERNAME() ?: "").trim()
        return update_data(ID, arrayOf((from.get_oprid()?:"").trim()),change_to)
    }

    /*
        subroutine in charge of feeding information and database information to
        SQL abstraction on delete queries
     */
    fun delete_opr( opr_data: opr_data):Boolean // subroutine to delete a opr from the database (local)
    {
        if ( get_opr_by_opr(opr_data)==null )
            return false
        return remove_from_db(ID, arrayOf((opr_data.get_oprid()?:"").trim()))

    }

    /*
        gets opr object by its id, else returns null
     */
    public fun get_opr_by_id(id: String):opr_data?
    {
        var mock_obj:opr_data = opr_data(id,null,null,remote_SQL_Helper.getusername())
        return get_opr_by_opr(mock_obj)
    }
}