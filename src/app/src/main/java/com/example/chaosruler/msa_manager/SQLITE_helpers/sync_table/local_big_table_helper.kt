package com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.chaosruler.msa_manager.BuildConfig
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_big_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.big_table_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.abstraction_classes.local_SQL_Helper
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper
import java.util.*
import kotlin.collections.HashMap


class local_big_table_helper(private var context: Context) : local_SQL_Helper(context,context.getString(R.string.LOCAL_SYNC_DATABASE_NAME)
,null,context.resources.getInteger(R.integer.LOCAL_BIG_TABLE_VERSION),context.getString(R.string.LOCAL_BIG_TABLE_NAME)) {
    private var ACCOUNT_NUM: String = context.getString(R.string.LOCAL_BIG_COLUMN_ACCOUNTNUM)
    private var DATAARAEID: String = context.getString(R.string.LOCAL_BIG_COLUMN_DATAARAEID)
    private var RECVERSION: String = context.getString(R.string.LOCAL_BIG_COLUMN_RECVERSION)
    private var RECID: String = context.getString(R.string.LOCAL_BIG_COLUMN_RECID)
    private var PROJID: String = context.getString(R.string.LOCAL_BIG_COLUMN_PROJID)
    private var ITEMID: String = context.getString(R.string.LOCAL_BIG_COLUMN_ITEMID)
    private var FLAT: String = context.getString(R.string.LOCAL_BIG_COLUMN_FLAT)
    private var FLOOR: String = context.getString(R.string.LOCAL_BIG_COLUMN_FLOOR)
    private var QTY: String = context.getString(R.string.LOCAL_BIG_COLUMN_QTY)
    private var SALESPRICE: String = context.getString(R.string.LOCAL_BIG_COLUMN_SALESPRICE)
    private var OPR_ID: String = context.getString(R.string.LOCAL_BIG_COLUMN_OPRID)
    private var MILESTONEPERCENTAGE: String = context.getString(R.string.LOCAL_BIG_COLUMN_MILESTONEPRECENT)
    private var QTYFORACCOUNT: String = context.getString(R.string.LOCAL_BIG_COLUMN_QTYFORACCOUNT)
    private var PERCENTFORACCOUNT: String = context.getString(R.string.LOCAL_BIG_COLUMN_PERCENTFORACCOUNT)
    private var TOTAL_SUM: String = context.getString(R.string.LOCAL_BIG_COLUMN_TOTALSUM)
    private var SALPROG: String = context.getString(R.string.LOCAL_BIG_COLUMN_SALPROG)
    private var PRINTORDER: String = context.getString(R.string.LOCAL_BIG_COLUMN_printorder)
    private var ITEMNUMBER: String = context.getString(R.string.LOCAL_BIG_COLUMN_ITEMNUMBER)
    private var KOMANUM: String = context.getString(R.string.LOCAL_BIG_COLUMN_KOMANUM)
    private var DIRANUM: String = context.getString(R.string.LOCAL_BIG_COLUMN_DIRANUM)

    private val USER: String = context.getString(R.string.LOCAL_BIG_COLUMN_USERNAME)

    /*
    MUST BE CALLED, it reports to the database about the table schema, is used by the abstracted
    SQL class
 */
    init {
        var vector: Vector<String> = Vector()
        vector.add(ACCOUNT_NUM)
        vector.add(DATAARAEID)
        vector.add(RECVERSION)
        vector.add(RECID)
        vector.add(PROJID)
        vector.add(ITEMID)
        vector.add(FLAT)
        vector.add(FLOOR)
        vector.add(QTY)
        vector.add(SALESPRICE)
        vector.add(OPR_ID)
        vector.add(MILESTONEPERCENTAGE)
        vector.add(QTYFORACCOUNT)
        vector.add(PERCENTFORACCOUNT)
        vector.add(TOTAL_SUM)
        vector.add(SALPROG)
        vector.add(PRINTORDER)
        vector.add(ITEMNUMBER)
        vector.add(KOMANUM)
        vector.add(DIRANUM)
        vector.add(USER)
        init_vector_of_variables(vector)
    }

    /*
           provides info for the abstracted SQL class
           on what the table schema is for creation
        */
    override fun onCreate(db: SQLiteDatabase)
    {
        var map: HashMap<String, String> = HashMap()
        map[ACCOUNT_NUM] = "text"
        map[DATAARAEID] = "text"
        map[RECVERSION] = "text"
        map[RECID] = "text"
        map[PROJID] = "text"
        map[ITEMID] = "text"
        map[FLAT] = "text"
        map[FLOOR] = "text"
        map[QTY] = "real"
        map[SALESPRICE] = "real"
        map[OPR_ID] = "text"
        map[MILESTONEPERCENTAGE] = "real"
        map[QTYFORACCOUNT] = "real"
        map[PERCENTFORACCOUNT] = "real"
        map[TOTAL_SUM] = "real"
        map[SALPROG] = "INTEGER"
        map[PRINTORDER] = "INTEGER"
        map[ITEMNUMBER] = "real"
        map[KOMANUM] = "real"
        map[DIRANUM] = "real"
        var foreign: HashMap<String, String> = HashMap()
        foreign[ACCOUNT_NUM] = context.getString(R.string.LOCAL_VENDORS_TABLE_NAME) + "(" + context.getString(R.string.LOCAL_VENDORS_COLUMN_ID) + ")"
        foreign[ITEMID] = context.getString(R.string.LOCAL_INVENTORY_TABLE_NAME) + "(" + context.getString(R.string.LOCAL_INVENTORY_COLUMN_ID) + ")"
        foreign[OPR_ID] = context.getString(R.string.LOCAL_OPR_TABLE_NAME) + "(" + context.getString(R.string.LOCAL_OPR_COLUMN_ID) + ")"
        foreign[PROJID] = context.getString(R.string.LOCAL_PROJECTS_TABLE_NAME) + "(" + context.getString(R.string.LOCAL_PROJECTS_COLUMN_ID) + ")"
        createDB(db, map, foreign)
    }

    /*
         adds all big, updates, inserts... whatever
      */
    fun sync_db()
    {
        if(!global_variables_dataclass.isLocal)
            return
        var server_vec = server_data_to_vector()
        for (item in server_vec)
        {
            add_big(item)
        }
    }

    /*
        converts DB to vector of big
     */
    fun get_local_DB(): Vector<big_table_data>
    {
        var vector: Vector<big_table_data> = Vector()

        var all_db: Vector<java.util.HashMap<String, String>> = get_db()
        all_db
                .filter { it[USER] != null && it[USER]?:"" == remote_SQL_Helper.getusername() }
                .map {
                    big_table_data((it[ACCOUNT_NUM]?:"").trim(),
                            (it[DATAARAEID]?:"").trim(), (it[RECVERSION]?:"").trim(),
                            (it[RECID]?:"").trim(), (it[PROJID]?:"").trim(),
                            (it[ITEMID]?:"").trim(), (it[FLAT]?:"").trim(),
                            (it[FLOOR]?:"").trim(), (it[QTY]?:"").trim(),
                            (it[SALESPRICE]?:"").trim(), (it[OPR_ID]?:"").trim(),
                            (it[MILESTONEPERCENTAGE]?:"").trim(), (it[QTYFORACCOUNT]?:"").trim(),
                            (it[PERCENTFORACCOUNT]?:"").trim(), (it[TOTAL_SUM]?:"").trim(),
                            (it[SALPROG]?:"").trim(), (it[PRINTORDER]?:"").trim(),
                            (it[ITEMNUMBER]?:"").trim(), (it[KOMANUM]?:"").trim(),
                            (it[DIRANUM]?:"").trim(), (it[USER]?:"").trim())
                }
                .forEach { vector.addElement(it) }
        return vector
    }

    /*
           get local DB by project name
        */
    fun get_local_DB_by_projname(projid:String): Vector<big_table_data>
    {
        var vector: Vector<big_table_data> = Vector()

        var all_db: Vector<java.util.HashMap<String, String>> = get_db()
        all_db
                .filter {
                    @Suppress("USELESS_ELVIS_RIGHT_IS_NULL")
                    (it[USER]?:null) != null && it[USER]?:"" == remote_SQL_Helper.getusername() && (it[PROJID]?:null)!=null && projid == (it[PROJID]?:null)
                }
                .map {
                    big_table_data((it[ACCOUNT_NUM]?:"").trim(),
                            (it[DATAARAEID]?:"").trim(), (it[RECVERSION]?:"").trim(),
                            (it[RECID]?:"").trim(), (it[PROJID]?:"").trim(),
                            (it[ITEMID]?:"").trim(), (it[FLAT]?:"").trim(),
                            (it[FLOOR]?:"").trim(), (it[QTY]?:"").trim(),
                            (it[SALESPRICE]?:"").trim(), (it[OPR_ID]?:"").trim(),
                            (it[MILESTONEPERCENTAGE]?:"").trim(), (it[QTYFORACCOUNT]?:"").trim(),
                            (it[PERCENTFORACCOUNT]?:"").trim(), (it[TOTAL_SUM]?:"").trim(),
                            (it[SALPROG]?:"").trim(), (it[PRINTORDER]?:"").trim(),
                            (it[ITEMNUMBER]?:"").trim(), (it[KOMANUM]?:"").trim(),
                            (it[DIRANUM]?:"").trim(), (it[USER]?:"").trim())
                }
                .forEach { vector.addElement(it) }
        return vector
    }

    /*
           subroutine to convert server data to vector of big
        */
    fun server_data_to_vector(): Vector<big_table_data>
    {

        var server_data: Vector<java.util.HashMap<String, String>> =
        if(BuildConfig.DEBUG)
        {
            var typemap:HashMap<String,String> = remote_big_table_helper.make_type_map()
            remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_BIG),typemap,context.getString(R.string.TABLE_BIG_DATAAREAID),context.getString(R.string.DATAAREAID_DEVELOP))
        }
        else
        {
            remote_SQL_Helper.get_all_table(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_BIG))
        }
        var result_vector: Vector<big_table_data> = Vector()
        server_data
                .map { it ->
                    big_table_data(it[remote_big_table_helper.VENDOR_ID]?: "",
                            it[remote_big_table_helper.DATAREAID]?: "", it[remote_big_table_helper.RECVERSION]?: "",
                            it[remote_big_table_helper.RECID]?: "", it[remote_big_table_helper.PROJECTS_ID]?: "",
                            it[remote_big_table_helper.INVENTORY_ID]?: "", it[remote_big_table_helper.FLAT]?: "",
                            it[remote_big_table_helper.FLOOR]?: "", it[remote_big_table_helper.QTY]?: "",
                            it[remote_big_table_helper.SALESPRICE]?: "", it[remote_big_table_helper.OPR_ID]?: "",
                            it[remote_big_table_helper.MILESTONEPERCENT]?: "", it[remote_big_table_helper.QTYFORACCOUNT]?: "",
                            it[remote_big_table_helper.PERCENTFORACCOUNT]?: "", it[remote_big_table_helper.TOTALSUM]?: "",
                            it[remote_big_table_helper.SALPROG]?: "", it[remote_big_table_helper.PRINTORDER]?: "",
                            it[remote_big_table_helper.ITEMNUMBER]?: "", it[remote_big_table_helper.KOMANUM]?: "",
                            it[remote_big_table_helper.DIRANUM]?: "", remote_SQL_Helper.getusername())
                }
                .forEach { result_vector.addElement(it) }
        return result_vector
    }


    /*
        server data to vector... by projid
     */
    fun server_data_to_vector_by_projname(projid: String): Vector<big_table_data>
    {

        var server_data: Vector<java.util.HashMap<String, String>> =
                if(BuildConfig.DEBUG)
                {
                    var typemap:HashMap<String,String> = remote_big_table_helper.make_type_map()
                    remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_BIG),typemap,context.getString(R.string.TABLE_BIG_DATAAREAID),context.getString(R.string.DATAAREAID_DEVELOP))
                }
                else
                {
                    remote_SQL_Helper.get_all_table(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_BIG))
                }
        var result_vector: Vector<big_table_data> = Vector()
        server_data
                .filter { it[PROJID]!=null && projid == it[PROJID]!!  }
                .map { it ->
                    big_table_data(it[remote_big_table_helper.VENDOR_ID]?: "",
                            it[remote_big_table_helper.DATAREAID]?: "", it[remote_big_table_helper.RECVERSION]?: "",
                            it[remote_big_table_helper.RECID]?: "", it[remote_big_table_helper.PROJECTS_ID]?: "",
                            it[remote_big_table_helper.INVENTORY_ID]?: "", it[remote_big_table_helper.FLAT]?: "",
                            it[remote_big_table_helper.FLOOR]?: "", it[remote_big_table_helper.QTY]?: "",
                            it[remote_big_table_helper.SALESPRICE]?: "", it[remote_big_table_helper.OPR_ID]?: "",
                            it[remote_big_table_helper.MILESTONEPERCENT]?: "", it[remote_big_table_helper.QTYFORACCOUNT]?: "",
                            it[remote_big_table_helper.PERCENTFORACCOUNT]?: "", it[remote_big_table_helper.TOTALSUM]?: "",
                            it[remote_big_table_helper.SALPROG]?: "", it[remote_big_table_helper.PRINTORDER]?: "",
                            it[remote_big_table_helper.ITEMNUMBER]?: "", it[remote_big_table_helper.KOMANUM]?: "",
                            it[remote_big_table_helper.DIRANUM]?: "", remote_SQL_Helper.getusername())
                }
                .forEach { result_vector.addElement(it) }
        return result_vector
    }
    /*
           subroutine that is in charge of getting the big class
           by query
        */
    fun get_big_by_big(big_table_data: big_table_data) // subroutine to get a opr object
            : big_table_data? {
        var input_map = java.util.HashMap<String, String>()
        input_map[ACCOUNT_NUM] = "'${big_table_data.get_VENDOR_ID()}'"
        input_map[PROJID] = "'${big_table_data.get_PROJECT_ID()}'"
        input_map[ITEMID] = "'${big_table_data.get_INVENTORY_ID()}'"
        input_map[OPR_ID] = "'${big_table_data.get_OPRID()}'"
        val vector = get_rows(input_map)
        if (vector.size > 0) {
                return big_table_data(vector.firstElement()[ACCOUNT_NUM]?:"",
                        vector.firstElement()[DATAARAEID]?:"", vector.firstElement()[RECVERSION]?:"",
                        vector.firstElement()[RECID]?:"", vector.firstElement()[PROJID]?:"",
                        vector.firstElement()[ITEMID]?:"", vector.firstElement()[FLAT]?:"",
                        vector.firstElement()[FLOOR]?:"", vector.firstElement()[QTY]?:"",
                        vector.firstElement()[SALESPRICE]?:"", vector.firstElement()[OPR_ID]?:"",
                        vector.firstElement()[MILESTONEPERCENTAGE]?:"", vector.firstElement()[QTYFORACCOUNT]?:"",
                        vector.firstElement()[PERCENTFORACCOUNT]?:"", vector.firstElement()[TOTAL_SUM]?:"",
                        vector.firstElement()[SALPROG]?:"", vector.firstElement()[PRINTORDER]?:"",
                        vector.firstElement()[ITEMNUMBER]?:"", vector.firstElement()[KOMANUM]?:"",
                        vector.firstElement()[DIRANUM]?:"", vector.firstElement()[USER]?:"")
        }

        return null
    }


    /*
      add big mechanism
      if big is invalid, forget about it
      if big is valid, and it exists, update it
      if its a new opr, add a new big to table
   */
    fun add_big(big_table_data: big_table_data) // subroutine that manages the big adding operation to the database
            : Boolean
    {
        /*
        var map:HashMap<String,String> = HashMap()
        map[ACCOUNT_NUM] = big_table_data.get_VENDOR_ID() ?: ""
        map[DATAARAEID] = big_table_data.get_DATAAREAID() ?: ""
        map[RECVERSION] = big_table_data.get_RECVERSION() ?: ""
        map[RECID] = big_table_data.get_RECID() ?: ""
        map[PROJID] = big_table_data.get_PROJECT_ID() ?: ""
        map[ITEMID] = big_table_data.get_INVENTORY_ID() ?: ""
        map[FLAT] = big_table_data.get_FLAT() ?: ""
        map[FLOOR] = big_table_data.get_FLOOR() ?: ""
        map[QTY] = big_table_data.get_QTY() ?: ""
        map[SALESPRICE] = big_table_data.get_SALESPRICE() ?: ""
        map[OPR_ID] = big_table_data.get_OPRID() ?: ""
        map[MILESTONEPERCENTAGE] = big_table_data.get_MILESTONEPERCENT() ?: ""
        map[QTYFORACCOUNT] = big_table_data.get_QTYFORACCOUNT() ?: ""
        map[PERCENTFORACCOUNT] = big_table_data.get_PERCENTFORACCOUNT() ?: ""
        map[TOTAL_SUM] = big_table_data.get_TOTALSUM() ?: ""
        map[SALPROG] = big_table_data.get_SALPROG() ?: ""
        map[PRINTORDER] = big_table_data.get_PRINTORDER() ?: ""
        map[ITEMNUMBER] = big_table_data.get_ITEMNUMBER() ?: ""
        map[KOMANUM] = big_table_data.get_KOMANUM() ?: ""
        map[DIRANUM] = big_table_data.get_DIRANUM() ?: ""
        map[USER] = big_table_data.get_USERNAME() ?: ""
        return replace(map)
        */
        return if (check_big(big_table_data)) // checks if big exists in database
            update_big(big_table_data, big_table_data.copy()) // if it does, lets update
        else // if it doesn't lets create a new entry for the big
            insert_big(big_table_data)
    }

    /*
          checks if big exists, query is not that smart, gets an ENTIRE table and than checks
          if the big is there

       */
    fun check_big(big_table_data: big_table_data) // subroutine to check if big exists on the database
            : Boolean {
        val big: big_table_data? = get_big_by_big(big_table_data)
        return big != null
    }

    /*
        subroutine in charge of feeding schema and database information to SQL
        abstract implentation on insert queries
     */
    private fun insert_big(big_table_data: big_table_data): Boolean // subroutine to insert a big to the database
    {

        var everything_to_add: Vector<java.util.HashMap<String, String>> = Vector()

        var data: java.util.HashMap<String, String> = java.util.HashMap()
        data[DATAARAEID] = (big_table_data.get_DATAAREAID() ?: "").trim()
        data[RECVERSION] = (big_table_data.get_RECVERSION() ?: "").trim()
        data[RECID] = (big_table_data.get_RECID() ?: "").trim()
        data[FLAT] = (big_table_data.get_FLAT() ?: "").trim()
        data[FLOOR] = (big_table_data.get_FLOOR() ?: "").trim()
        data[QTY] = (big_table_data.get_QTY() ?: "").trim()
        data[SALESPRICE] = (big_table_data.get_SALESPRICE() ?: "").trim()
        data[MILESTONEPERCENTAGE] = (big_table_data.get_MILESTONEPERCENT() ?: "").trim()
        data[QTYFORACCOUNT] = (big_table_data.get_QTYFORACCOUNT() ?: "").trim()
        data[PERCENTFORACCOUNT] = (big_table_data.get_PERCENTFORACCOUNT() ?: "").trim()
        data[TOTAL_SUM] = (big_table_data.get_TOTALSUM() ?: "").trim()
        data[SALPROG] = (big_table_data.get_SALPROG() ?: "").trim()
        data[PRINTORDER] = (big_table_data.get_PRINTORDER() ?: "").trim()
        data[ITEMNUMBER] = (big_table_data.get_ITEMNUMBER() ?: "").trim()
        data[KOMANUM] = (big_table_data.get_KOMANUM() ?: "").trim()
        data[DIRANUM] = (big_table_data.get_DIRANUM() ?: "").trim()
        data[USER] = (big_table_data.get_USERNAME() ?: "").trim()
        data[ACCOUNT_NUM] = (big_table_data.get_VENDOR_ID() ?: "").trim()
        data[PROJID] = (big_table_data.get_PROJECT_ID() ?: "").trim()
        data[ITEMID] = (big_table_data.get_INVENTORY_ID() ?: "").trim()
        data[OPR_ID] = (big_table_data.get_OPRID() ?: "").trim()
        everything_to_add.addElement(data)
        return add_data(everything_to_add)
    }

    /*
      subroutine in charge of feeding information and database information to
      SQL abstraction on update queries
   */
    fun update_big(from: big_table_data, to: big_table_data) // subroutine to update data of a big that exists on the database
            : Boolean {

        var change_to: java.util.HashMap<String, String> = java.util.HashMap()
        change_to[DATAARAEID] = (to.get_DATAAREAID() ?: "").trim()
        change_to[RECVERSION] = (to.get_RECVERSION() ?: "").trim()
        change_to[RECID] = (to.get_RECID() ?: "").trim()
        change_to[FLAT] = (to.get_FLAT() ?: "").trim()
        change_to[FLOOR] = (to.get_FLOOR() ?: "").trim()
        change_to[QTY] = (to.get_QTY() ?: "").trim()
        change_to[SALESPRICE] = (to.get_SALESPRICE() ?: "").trim()
        change_to[MILESTONEPERCENTAGE] = (to.get_MILESTONEPERCENT() ?: "").trim()
        change_to[QTYFORACCOUNT] = (to.get_QTYFORACCOUNT() ?: "").trim()
        change_to[PERCENTFORACCOUNT] = (to.get_PERCENTFORACCOUNT() ?: "").trim()
        change_to[TOTAL_SUM] = (to.get_TOTALSUM() ?: "").trim()
        change_to[SALPROG] = (to.get_SALPROG() ?: "").trim()
        change_to[PRINTORDER] = (to.get_PRINTORDER() ?: "").trim()
        change_to[ITEMNUMBER] = (to.get_ITEMNUMBER() ?: "").trim()
        change_to[KOMANUM] = (to.get_KOMANUM() ?: "").trim()
        change_to[DIRANUM] = (to.get_DIRANUM() ?: "").trim()
        change_to[USER] = (to.get_USERNAME() ?: "").trim()
        return update_data(arrayOf(ACCOUNT_NUM, PROJID, ITEMID, OPR_ID), arrayOf(from.get_VENDOR_ID()!!, from.get_PROJECT_ID()!!, from.get_INVENTORY_ID()!!, from.get_OPRID()!!), change_to)
    }

    /*
        subroutine in charge of feeding information and database information to
        SQL abstraction on delete queries
     */
    fun delete_big(big_table_data: big_table_data): Boolean // subroutine to delete a big from the database (local)
    {
        if (get_big_by_big(big_table_data) == null)
            return false
        return remove_from_db(arrayOf(ACCOUNT_NUM, PROJID, ITEMID, OPR_ID), arrayOf((big_table_data.get_VENDOR_ID()?:"").trim(), (big_table_data.get_PROJECT_ID()?:"").trim(), (big_table_data.get_INVENTORY_ID()?:"").trim(), (big_table_data.get_OPRID()?:"").trim()))

    }
}