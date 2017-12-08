package com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.chaosruler.msa_manager.BuildConfig
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_vendors_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.vendor_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.abstraction_classes.local_SQL_Helper
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by chaosruler on 12/3/17.
 */
class local_vendor_table_helper(private var context: Context) : local_SQL_Helper(context,context.getString(R.string.LOCAL_SYNC_DATABASE_NAME),null,context.getString(R.string.LOCAL_VENDORS_TABLE_VERSION).toInt(),context.getString(R.string.LOCAL_VENDORS_TABLE_NAME)) {
    private val ID: String = context.getString(R.string.LOCAL_VENDORS_COLUMN_ID)
    private val NAME: String = context.getString(R.string.LOCAL_VENDORS_COLUMN_NAME)
    private val DATAARAEID: String = context.getString(R.string.LOCAL_VENDORS_COLUMN_DATAARAEID)
    private val USER: String = context.getString(R.string.LOCAL_VENDORS_COLUMN_USERNAME)

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
       adds all vendor, updates, inserts... whatever
    */
    fun sync_db()
    {
        if(!global_variables_dataclass.isLocal)
            return
        var server_vec = server_data_to_vector()
        for(item in server_vec)
        {
            add_vendor(item)
        }
    }

    /*
        converts DB to vector of vendor
     */
    fun get_local_DB():Vector<vendor_data>
    {
        var vector:Vector<vendor_data> = Vector()

        var all_db:Vector<HashMap<String,String>> = get_db()
        all_db
                .filter { it[USER] != null && it[USER] == remote_SQL_Helper.getusername() }
                .forEach { vector.addElement(vendor_data((it[ID]?:"").trim(), (it[NAME]?:"").trim(), (it[DATAARAEID]?:"").trim(), (it[USER]?:"").trim())) }
        return vector
    }


    /*
           subroutine to convert server data to vector of vendor
        */
    fun server_data_to_vector():Vector<vendor_data>
    {

        var server_data: Vector<java.util.HashMap<String, String>> =
        if(BuildConfig.DEBUG)
        {
            var typemap:HashMap<String,String> = remote_vendors_table_helper.make_type_map()
            remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_VENDORS),typemap,context.getString(R.string.VENDORS_DATAAREAID),context.getString(R.string.DATAAREAID_DEVELOP))
        }
        else
        {
            remote_SQL_Helper.get_all_table(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_VENDORS))
        }

        var result_vector:Vector<vendor_data> = Vector()
        server_data.mapTo(result_vector) {
            vendor_data((it[remote_vendors_table_helper.ID]?: "").trim(),
                    (it[remote_vendors_table_helper.NAME]?: "").trim(), (it[remote_vendors_table_helper.DATAAREAID]?: "").trim(),
                    remote_SQL_Helper.getusername().trim())
        }
        return result_vector
    }
    /*
           subroutine that is in charge of getting the vendor class
           by query
        */
    fun get_vendor_by_vendor(vendor_data: vendor_data) // subroutine to get a vendor object
            : vendor_data?
    {
        var input_map = HashMap<String,String>()
        input_map[ID] = "'${vendor_data.get_accountnum()}'"
        val vector = get_rows(input_map)
        if(vector.size > 0)
        {
            return vendor_data((vector.firstElement()[ID]?:"").trim(), (vector.firstElement()[NAME]?:"").trim(), (vector.firstElement()[DATAARAEID]?:"").trim(), (vector.firstElement()[USER]?:"").trim())
        }


        return null
    }


    /*
      add vendor mechanism
      if vendor is invalid, forget about it
      if vendor is valid, and it exists, update it
      if its a new vendor, add a new vendor to table
   */
    fun add_vendor(vendor_data: vendor_data) // subroutine that manages the vendor adding operation to the database
            : Boolean
    {
        /*
        var map:HashMap<String,String> = HashMap()
        map[ID] = vendor_data.get_accountnum() ?: ""
        map[NAME] = vendor_data.get_accountname() ?: ""
        map[DATAARAEID] = vendor_data.get_DATAREAID() ?: ""
        map[USER] = vendor_data.get_USERNAME() ?: ""
        return replace(map)
        */
        return if (check_vendor( vendor_data)) // checks if vendor exists in database
            update_vendor(vendor_data,vendor_data.copy()) // if it does, lets update
        else // if it doesn't lets create a new entry for the vendor
            insert_vendor(vendor_data)
    }

    /*
          checks if vendor exists, query is not that smart, gets an ENTIRE table and than checks
          if the vendor is there

          // on update
          will select USERNAME only
       */
    fun check_vendor(vendor_data: vendor_data) // subroutine to check if vendor exists on the database
            : Boolean {
        val vendor:vendor_data? = get_vendor_by_vendor( vendor_data)
        return vendor != null
    }
    /*
        subroutine in charge of feeding schema and database information to SQL
        abstract implentation on insert queries
     */
    private fun insert_vendor(vendor_data: vendor_data):Boolean // subroutine to insert a vendor to the database
    {

        var everything_to_add:Vector<HashMap<String,String>> = Vector()

        var data: HashMap<String,String> = HashMap()
        data[ID] = (vendor_data.get_accountnum() ?: "").trim()
        data[NAME] = (vendor_data.get_accountname() ?: "").trim()
        data[DATAARAEID] = (vendor_data.get_DATAREAID() ?: "").trim()
        data[USER] = (vendor_data.get_USERNAME() ?: "").trim()
        everything_to_add.addElement(data)
        return add_data(everything_to_add)
    }

    /*
      subroutine in charge of feeding information and database information to
      SQL abstraction on update queries
   */
    fun update_vendor(from:vendor_data,to:vendor_data) // subroutine to update data of a vendor that exists on the database
            : Boolean {

        var change_to:HashMap<String,String> = HashMap()
        change_to[NAME] = (to.get_accountname() ?: "").trim()
        change_to[DATAARAEID] = (to.get_DATAREAID() ?: "").trim()
        change_to[USER] = (to.get_USERNAME() ?: "").trim()
        return update_data(ID, arrayOf(from.get_accountnum()!!),change_to)
    }

    /*
        subroutine in charge of feeding information and database information to
        SQL abstraction on delete queries
     */
    fun delete_vendor_data( vendor_data: vendor_data):Boolean // subroutine to delete a vendor from the database (local)
    {
        if ( get_vendor_by_vendor(vendor_data)==null )
            return false
        return remove_from_db(ID, arrayOf((vendor_data.get_accountnum()?:"").trim()))

    }

    fun get_vendor_by_id(id:String):vendor_data? = get_vendor_by_vendor(vendor_data(id,null,null,remote_SQL_Helper.getusername()))
}