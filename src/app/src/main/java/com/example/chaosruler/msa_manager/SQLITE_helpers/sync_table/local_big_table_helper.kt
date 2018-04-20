@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.chaosruler.msa_manager.BuildConfig
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_big_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.local_SQL_Helper
import com.example.chaosruler.msa_manager.object_types.big_table_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper
import java.util.*
import kotlin.collections.HashMap

/**
 * implenting the SQL helper on big database (SQLITE)
 * @author Chaosruler972
 * @constructor a context to work with, the rest comes from strings.xml
 */
class local_big_table_helper(
        /**
         * the context we are working with
         * @author Chaosruler972
         */
        private var context: Context
) : local_SQL_Helper(context,context.getString(R.string.LOCAL_SYNC_DATABASE_NAME)
,null,context.resources.getInteger(R.integer.LOCAL_BIG_TABLE_VERSION),context.getString(R.string.LOCAL_BIG_TABLE_NAME)) {
    /**
     * Account number field name
     * @author Chaosruler972
     */
    private var ACCOUNT_NUM: String = context.getString(R.string.LOCAL_BIG_COLUMN_ACCOUNTNUM)
    /**
     * Dataaraeid field name
     * @author Chaosruler972
     */
    private var DATAARAEID: String = context.getString(R.string.LOCAL_BIG_COLUMN_DATAARAEID)
    /**
     * rec version field name
     * @author Chaosruler972
     */
    private var RECVERSION: String = context.getString(R.string.LOCAL_BIG_COLUMN_RECVERSION)
    /**
     * rec id field name
     * @author Chaosruler972
     */
    private var RECID: String = context.getString(R.string.LOCAL_BIG_COLUMN_RECID)
    /**
     * the project id field name
     * @author Chaosruler972
     */
    private var PROJID: String = context.getString(R.string.LOCAL_BIG_COLUMN_PROJID)
    /**
     * the item id field name
     * @author Chaosruler972
     */
    private var ITEMID: String = context.getString(R.string.LOCAL_BIG_COLUMN_ITEMID)
    /**
     * the flat field name
     * @author Chaosruler972
     */
    private var FLAT: String = context.getString(R.string.LOCAL_BIG_COLUMN_FLAT)
    /**
     * flat field name
     * @author Chaosruler972
     */
    private var FLOOR: String = context.getString(R.string.LOCAL_BIG_COLUMN_FLOOR)
    /**
     * quanity field name
     * @author Chaosruler972
     */
    private var QTY: String = context.getString(R.string.LOCAL_BIG_COLUMN_QTY)
    /**
     * the sales price field name
     * @author Chaosruler972
     */
    private var SALESPRICE: String = context.getString(R.string.LOCAL_BIG_COLUMN_SALESPRICE)
    /**
     * the operation id field name
     * @author Chaosruler972
     */
    private var OPR_ID: String = context.getString(R.string.LOCAL_BIG_COLUMN_OPRID)
    /**
     * the miestone to percent field name
     * @author Chaosruler972
     */
    private var MILESTONEPERCENTAGE: String = context.getString(R.string.LOCAL_BIG_COLUMN_MILESTONEPRECENT)
    /**
     * the quanity for account field name
     * @author Chaosruler972
     */
    private var QTYFORACCOUNT: String = context.getString(R.string.LOCAL_BIG_COLUMN_QTYFORACCOUNT)
    /**
     * the percent for account field name
     * @author Chaosruler972
     */
    private var PERCENTFORACCOUNT: String = context.getString(R.string.LOCAL_BIG_COLUMN_PERCENTFORACCOUNT)
    /**
     * the total sum field name
     * @author Chaosruler972
     */
    private var TOTAL_SUM: String = context.getString(R.string.LOCAL_BIG_COLUMN_TOTALSUM)
    /**
     * the sale progress field name
     * @author Chaosruler972
     */
    private var SALPROG: String = context.getString(R.string.LOCAL_BIG_COLUMN_SALPROG)
    /**
     * the print order field name
     * @author Chaosruler972
     */
    private var PRINTORDER: String = context.getString(R.string.LOCAL_BIG_COLUMN_printorder)
    /**
     * the item number field name
     * @author Chaosruler972
     */
    private var ITEMNUMBER: String = context.getString(R.string.LOCAL_BIG_COLUMN_ITEMNUMBER)
    /**
     * the koma num field name
     * @author Chaosruler972
     */
    private var KOMANUM: String = context.getString(R.string.LOCAL_BIG_COLUMN_KOMANUM)
    /**
     * the dira num field name
     * @author Chaosruler972
     */
    private var DIRANUM: String = context.getString(R.string.LOCAL_BIG_COLUMN_DIRANUM)

    /**
     * the username that synced this data
     * @author Chaosruler972
     */
    private val USER: String = context.getString(R.string.LOCAL_BIG_COLUMN_USERNAME)

    /**
     *    MUST BE CALLED, it reports to the database about the table schema, is used by the abstracted
     * SQL class
     * @author Chaosruler972
     */
    init {
        val vector: Vector<String> = Vector()
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

    /**
     * provides info for the abstracted SQL class
     * on what the table schema is for creation
     * @author Chaosruler972
     * @param db an instance of database
     */
    override fun onCreate(db: SQLiteDatabase)
    {
        val map: HashMap<String, String> = HashMap()
        map[ACCOUNT_NUM] = "BLOB"
        map[DATAARAEID] = "BLOB"
        map[RECVERSION] = "BLOB"
        map[RECID] = "BLOB"
        map[PROJID] = "BLOB"
        map[ITEMID] = "BLOB"
        map[FLAT] = "BLOB"
        map[FLOOR] = "BLOB"
        map[QTY] = "BLOB"
        map[SALESPRICE] = "BLOB"
        map[OPR_ID] = "BLOB"
        map[MILESTONEPERCENTAGE] = "BLOB"
        map[QTYFORACCOUNT] = "BLOB"
        map[PERCENTFORACCOUNT] = "BLOB"
        map[TOTAL_SUM] = "BLOB"
        map[SALPROG] = "BLOB"
        map[PRINTORDER] = "BLOB"
        map[ITEMNUMBER] = "BLOB"
        map[KOMANUM] = "BLOB"
        map[DIRANUM] = "BLOB"
        val foreign: HashMap<String, String> = HashMap()
        foreign[ACCOUNT_NUM] = context.getString(R.string.LOCAL_VENDORS_TABLE_NAME) + "(" + context.getString(R.string.LOCAL_VENDORS_COLUMN_ID) + ")"
        foreign[ITEMID] = context.getString(R.string.LOCAL_INVENTORY_TABLE_NAME) + "(" + context.getString(R.string.LOCAL_INVENTORY_COLUMN_ID) + ")"
        foreign[OPR_ID] = context.getString(R.string.LOCAL_OPR_TABLE_NAME) + "(" + context.getString(R.string.LOCAL_OPR_COLUMN_ID) + ")"
        foreign[PROJID] = context.getString(R.string.LOCAL_PROJECTS_TABLE_NAME) + "(" + context.getString(R.string.LOCAL_PROJECTS_COLUMN_ID) + ")"
        val extra = " PRIMARY KEY($ACCOUNT_NUM, $ITEMID, $OPR_ID,$PROJID) "
        createDB(db, map, foreign,extra)
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
        for (item in server_vec)
        {
            add_big(item)
        }
    }


    /**
     *  converts DB to vector of big data
     *  @author Chaosruler972
     *  @return a vector of big table from local DB
     */
    fun get_local_DB(): Vector<big_table_data>
    {
        Log.d("DB OF: ","Big")
        val vector: Vector<big_table_data> = Vector()

        val all_db: Vector<java.util.HashMap<String, String>> = get_db()
        all_db
                .filter { it[USER] != null && it[USER]?:"" == remote_SQL_Helper.getusername() }
                .map {
                    big_table_data(
                            (it[ACCOUNT_NUM]?:"").trim(),
                            (it[DATAARAEID]?:"").trim(),
                            (it[RECVERSION]?:"").trim(),
                            (it[RECID]?:"").trim(),
                            (it[PROJID]?:"").trim(),
                            (it[ITEMID]?:"").trim(),
                            (it[FLAT]?:"").trim(),
                            (it[FLOOR]?:"").trim(),
                            (it[QTY]?:"").trim(),
                            (it[SALESPRICE]?:"").trim(),
                            (it[OPR_ID]?:"").trim(),
                            (it[MILESTONEPERCENTAGE]?:"").trim(),
                            (it[QTYFORACCOUNT]?:"").trim(),
                            (it[PERCENTFORACCOUNT]?:"").trim(),
                            (it[TOTAL_SUM]?:"").trim(),
                            (it[SALPROG]?:"").trim(),
                            (it[PRINTORDER]?:"").trim() ,
                            (it[ITEMNUMBER]?:"").trim(),
                            (it[KOMANUM]?:"").trim(),
                            (it[DIRANUM]?:"").trim(),
                            (it[USER]?:"").trim())
                }
                .forEach { vector.addElement(it) }
        return vector
    }


    /**
     * get local DB by project name
     * @author Chaosruler972
     * @return a vector of big table filtered by project name
     * @param projid the project id that represents the name of the project we want to filter
     */
    fun get_local_DB_by_projname(projid:String): Vector<big_table_data>
    {
        val vector: Vector<big_table_data> = Vector()

        val all_db: Vector<java.util.HashMap<String, String>> = get_db()
        all_db
                .filter {
                    @Suppress("USELESS_ELVIS_RIGHT_IS_NULL")
                    (it[USER]?:null) != null && it[USER]?:"" == remote_SQL_Helper.getusername() && (it[PROJID]?:null)!=null && projid == (it[PROJID]?:null)
                }
                .map {
                    big_table_data(
                            (it[ACCOUNT_NUM]?:"").trim(),
                            (it[DATAARAEID]?:"").trim(),
                            (it[RECVERSION]?:"").trim(),
                            (it[RECID]?:"").trim(),
                            (it[PROJID]?:"").trim(),
                            (it[ITEMID]?:"").trim(),
                            (it[FLAT]?:"").trim(),
                            (it[FLOOR]?:"").trim(),
                            (it[QTY]?:"").trim(),
                            (it[SALESPRICE]?:"").trim(),
                            (it[OPR_ID]?:"").trim(),
                            (it[MILESTONEPERCENTAGE]?:"").trim(),
                            (it[QTYFORACCOUNT]?:"").trim(),
                            (it[PERCENTFORACCOUNT]?:"").trim(),
                            (it[TOTAL_SUM]?:"").trim(),
                            (it[SALPROG]?:"").trim(),
                            (it[PRINTORDER]?:"").trim() ,
                            (it[ITEMNUMBER]?:"").trim(),
                            (it[KOMANUM]?:"").trim(),
                            (it[DIRANUM]?:"").trim(),
                            (it[USER]?:"").trim())
                }
                .forEach { vector.addElement(it) }
        return vector
    }

    /**
     *     subroutine to convert server data to vector of big data
     *  @author Chaosruler972
     *  @return a vector of big table from server DB
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun server_data_to_vector(): Vector<big_table_data>
    {
        val typemap: HashMap<String, String> = remote_big_table_helper.define_type_map()
        val server_data: Vector<java.util.HashMap<String, String>> =
        if(BuildConfig.DEBUG)
        {
            remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_BIG),typemap,context.getString(R.string.TABLE_BIG_DATAAREAID),context.getString(R.string.DATAAREAID_DEVELOP))
        }
        else
        {
            remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_BIG),typemap,null,null)
        }
        val result_vector: Vector<big_table_data> = Vector()
        server_data
                .map { it ->
                    big_table_data(
                            it[remote_big_table_helper.VENDOR_ID]?: "",
                            it[remote_big_table_helper.DATAREAID]?: "",
                            it[remote_big_table_helper.RECVERSION]?: "",
                            it[remote_big_table_helper.RECID]?: "",
                            it[remote_big_table_helper.PROJECTS_ID]?: "",
                            it[remote_big_table_helper.INVENTORY_ID]?: "",
                            it[remote_big_table_helper.FLAT]?: "",
                            it[remote_big_table_helper.FLOOR]?: "",
                            it[remote_big_table_helper.QTY]?: "",
                            it[remote_big_table_helper.SALESPRICE]?: "",
                            it[remote_big_table_helper.OPR_ID]?: "",
                            it[remote_big_table_helper.MILESTONEPERCENT]?: "",
                            it[remote_big_table_helper.QTYFORACCOUNT]?: "",
                            it[remote_big_table_helper.PERCENTFORACCOUNT]?: "",
                            it[remote_big_table_helper.TOTALSUM]?: "",
                            it[remote_big_table_helper.SALPROG]?: "",
                            it[remote_big_table_helper.PRINTORDER]?: "",
                            it[remote_big_table_helper.ITEMNUMBER]?: "",
                            it[remote_big_table_helper.KOMANUM]?: "",
                            it[remote_big_table_helper.DIRANUM]?: "",
                            remote_SQL_Helper.getusername())
                }
                .forEach { result_vector.addElement(it) }
        return result_vector
    }



    /**
     * server data to vector... by projid
     * @author Chaosruler972
     * @return a vector of big table filtered by project name
     * @param projid the project id that represents the name of the project we want to filter
     */
    fun server_data_to_vector_by_projname(projid: String): Vector<big_table_data>
    {

        val typemap: HashMap<String, String> = remote_big_table_helper.define_type_map()
        val server_data: Vector<java.util.HashMap<String, String>> =
                if(BuildConfig.DEBUG)
                {
                    remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_BIG),typemap,context.getString(R.string.TABLE_BIG_DATAAREAID),context.getString(R.string.DATAAREAID_DEVELOP))
                }
                else
                {
                    remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_BIG),typemap,null,null)
                }
        val result_vector: Vector<big_table_data> = Vector()
        server_data
                .filter { it[PROJID]!=null && projid == it[PROJID]!!  }
                .map { it ->
                    big_table_data(
                            it[remote_big_table_helper.VENDOR_ID]?: "",
                            it[remote_big_table_helper.DATAREAID]?: "",
                            it[remote_big_table_helper.RECVERSION]?: "",
                            it[remote_big_table_helper.RECID]?: "",
                            it[remote_big_table_helper.PROJECTS_ID]?: "",
                            it[remote_big_table_helper.INVENTORY_ID]?: "",
                            it[remote_big_table_helper.FLAT]?: "",
                            it[remote_big_table_helper.FLOOR]?: "",
                            it[remote_big_table_helper.QTY]?: "",
                            it[remote_big_table_helper.SALESPRICE]?: "",
                            it[remote_big_table_helper.OPR_ID]?: "",
                            it[remote_big_table_helper.MILESTONEPERCENT]?: "",
                            it[remote_big_table_helper.QTYFORACCOUNT]?: "",
                            it[remote_big_table_helper.PERCENTFORACCOUNT]?: "",
                            it[remote_big_table_helper.TOTALSUM]?: "",
                            it[remote_big_table_helper.SALPROG]?: "",
                            it[remote_big_table_helper.PRINTORDER]?: "",
                            it[remote_big_table_helper.ITEMNUMBER]?: "",
                            it[remote_big_table_helper.KOMANUM]?: "",
                            it[remote_big_table_helper.DIRANUM]?: "",
                            remote_SQL_Helper.getusername())
                }
                .forEach { result_vector.addElement(it) }
        return result_vector
    }


    /**
     *   subroutine that is in charge of getting the big class
     * by query
     * @author Chaosruler972
     * @param big_table_data the big data to filter by
     * @return the big data from the server, null if not found
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun get_big_by_big(big_table_data: big_table_data) // subroutine to get a opr object
            : big_table_data? {
        val input_map = java.util.HashMap<String, String>()
        input_map[ACCOUNT_NUM] = "'${big_table_data.get_VENDOR_ID()}'"
        input_map[PROJID] = "'${big_table_data.get_PROJECT_ID()}'"
        input_map[ITEMID] = "'${big_table_data.get_INVENTORY_ID()}'"
        input_map[OPR_ID] = "'${big_table_data.get_OPRID()}'"
        val vector = get_rows(input_map)
        if (vector.size > 0) {
                return big_table_data(
                        vector.firstElement()[ACCOUNT_NUM]?:"",
                        vector.firstElement()[DATAARAEID]?:"",
                        vector.firstElement()[RECVERSION]?:"",
                        vector.firstElement()[RECID]?:"",
                        vector.firstElement()[PROJID]?:"",
                        vector.firstElement()[ITEMID]?:"",
                        vector.firstElement()[FLAT]?:"",
                        vector.firstElement()[FLOOR]?:"",
                        vector.firstElement()[QTY]?:"",
                        vector.firstElement()[SALESPRICE]?:"",
                        vector.firstElement()[OPR_ID]?:"",
                        vector.firstElement()[MILESTONEPERCENTAGE]?:"",
                        vector.firstElement()[QTYFORACCOUNT]?:"",
                        vector.firstElement()[PERCENTFORACCOUNT]?:"",
                        vector.firstElement()[TOTAL_SUM]?:"",
                        vector.firstElement()[SALPROG]?:"",
                        vector.firstElement()[PRINTORDER]?:"",
                        vector.firstElement()[ITEMNUMBER]?:"",
                        vector.firstElement()[KOMANUM]?:"",
                        vector.firstElement()[DIRANUM]?:"",
                        vector.firstElement()[USER]?:""
                )
        }

        return null
    }


    /**
     * add big mechanism
     * if big is invalid, forget about it
     * if big is valid, and it exists, update it
     * if its a new opr, add a new big to table
     * @author Chaosruler972
     * @param big_table_data the big data object to add
     * @return if add was successfull true, else false
     */
    fun add_big(big_table_data: big_table_data) // subroutine that manages the big adding operation to the database
            : Boolean
    {
        Log.d("Big Check exists",check_big(big_table_data).toString())
        return if (remote_SQL_Helper.get_latest_sync_time().time > 0.toLong() &&
                check_big(big_table_data)) // checks if big exists in database
            update_big(big_table_data, big_table_data.copy()) // if it does, lets update
        else // if it doesn't lets create a new entry for the big
            insert_big(big_table_data)
    }


    /**
     * checks if big exists, query is not that smart, gets an ENTIRE table and than checks
     * if the big is there
     * @param big_table_data the big to check if exists or not
     * @return if the big data was found or not
     * @author Chaosruler972
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun check_big(big_table_data: big_table_data) // subroutine to check if big exists on the database
            : Boolean {
        val big: big_table_data? = get_big_by_big(big_table_data)
        return big != null
    }


    /**
     *  subroutine in charge of feeding schema and database information to SQL
     * abstract implentation on insert queries
     * @author Chaosruler972
     * @param big_table_data the big table data that we want to insert
     * @return if insertion was successfull or not
     */
    private fun insert_big(big_table_data: big_table_data): Boolean // subroutine to insert a big to the database
    {

        val everything_to_add: Vector<java.util.HashMap<String, String>> = Vector()

        val data: java.util.HashMap<String, String> = java.util.HashMap()
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


    /**
     *  subroutine in charge of feeding information and database information to
     * SQL abstraction on update queries
     * @author Chaosruler972
     * @param from the source that we want to update
     * @param to what we want to update it to
     * @return if update was successfull or not
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun update_big(from: big_table_data, to: big_table_data) // subroutine to update data of a big that exists on the database
            : Boolean {

        val change_to: java.util.HashMap<String, String> = java.util.HashMap()
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


    /**
     * subroutine in charge of feeding information and database information to
     * SQL abstraction on delete queries
     * @author Chaosruler972
     * @param big_table_data the source that we want to remove
     * @return if removal was successfull or not
     */
    @Suppress("unused")
    fun delete_big(big_table_data: big_table_data): Boolean // subroutine to delete a big from the database (local)
    {
        if (get_big_by_big(big_table_data) == null)
            return false
        return remove_from_db(arrayOf(ACCOUNT_NUM, PROJID, ITEMID, OPR_ID), arrayOf((big_table_data.get_VENDOR_ID()?:"").trim(), (big_table_data.get_PROJECT_ID()?:"").trim(), (big_table_data.get_INVENTORY_ID()?:"").trim(), (big_table_data.get_OPRID()?:"").trim()))

    }
}