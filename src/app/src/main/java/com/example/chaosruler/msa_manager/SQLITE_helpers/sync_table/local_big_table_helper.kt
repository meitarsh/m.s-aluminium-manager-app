@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_big_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.local_SQL_Helper
import com.example.chaosruler.msa_manager.abstraction_classes.syncable
import com.example.chaosruler.msa_manager.object_types.big_table_data
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

) : syncable, local_SQL_Helper(context, context.getString(R.string.LOCAL_SYNC_DATABASE_NAME)
,null,context.resources.getInteger(R.integer.LOCAL_BIG_TABLE_VERSION),context.getString(R.string.LOCAL_BIG_TABLE_NAME)) {


    /**
     * Account number field name
     * @author Chaosruler972TABLE_BIG_SYNC
     */
    var ACCOUNT_NUM: String = context.getString(R.string.LOCAL_BIG_COLUMN_ACCOUNTNUM)
    /**
     * Dataaraeid field name
     * @author Chaosruler972
     */
    var DATAARAEID: String = context.getString(R.string.LOCAL_BIG_COLUMN_DATAARAEID)
    /**
     * rec version field name
     * @author Chaosruler972
     */
    var RECVERSION: String = context.getString(R.string.LOCAL_BIG_COLUMN_RECVERSION)
    /**
     * rec id field name
     * @author Chaosruler972
     */
    val RECID: String = context.getString(R.string.LOCAL_BIG_COLUMN_RECID)
    /**
     * the project id field name
     * @author Chaosruler972
     */
    var PROJID: String = context.getString(R.string.LOCAL_BIG_COLUMN_PROJID)
    /**
     * the item id field name
     * @author Chaosruler972
     */
    var ITEMID: String = context.getString(R.string.LOCAL_BIG_COLUMN_ITEMID)
    /**
     * the flat field name
     * @author Chaosruler972
     */
    var FLAT: String = context.getString(R.string.LOCAL_BIG_COLUMN_FLAT)
    /**
     * flat field name
     * @author Chaosruler972
     */
    var FLOOR: String = context.getString(R.string.LOCAL_BIG_COLUMN_FLOOR)
    /**
     * quanity field name
     * @author Chaosruler972
     */
    var QTY: String = context.getString(R.string.LOCAL_BIG_COLUMN_QTY)
    /**
     * the sales price field name
     * @author Chaosruler972
     */
    var SALESPRICE: String = context.getString(R.string.LOCAL_BIG_COLUMN_SALESPRICE)
    /**
     * the operation id field name
     * @author Chaosruler972
     */
    var OPR_ID: String = context.getString(R.string.LOCAL_BIG_COLUMN_OPRID)
    /**
     * the miestone to percent field name
     * @author Chaosruler972
     */
    var MILESTONEPERCENTAGE: String = context.getString(R.string.LOCAL_BIG_COLUMN_MILESTONEPRECENT)
    /**
     * the quanity for account field name
     * @author Chaosruler972
     */
    var QTYFORACCOUNT: String = context.getString(R.string.LOCAL_BIG_COLUMN_QTYFORACCOUNT)
    /**
     * the percent for account field name
     * @author Chaosruler972
     */
    var PERCENTFORACCOUNT: String = context.getString(R.string.LOCAL_BIG_COLUMN_PERCENTFORACCOUNT)
    /**
     * the total sum field name
     * @author Chaosruler972
     */
    var TOTAL_SUM: String = context.getString(R.string.LOCAL_BIG_COLUMN_TOTALSUM)
    /**
     * the sale progress field name
     * @author Chaosruler972
     */
    var SALPROG: String = context.getString(R.string.LOCAL_BIG_COLUMN_SALPROG)
    /**
     * the print order field name
     * @author Chaosruler972
     */
    var PRINTORDER: String = context.getString(R.string.LOCAL_BIG_COLUMN_printorder)
    /**
     * the item number field name
     * @author Chaosruler972
     */
    var ITEMNUMBER: String = context.getString(R.string.LOCAL_BIG_COLUMN_ITEMNUMBER)
    /**
     * the koma num field name
     * @author Chaosruler972
     */
    var KOMANUM: String = context.getString(R.string.LOCAL_BIG_COLUMN_KOMANUM)
    /**
     * the dira num field name
     * @author Chaosruler972
     */
    var DIRANUM: String = context.getString(R.string.LOCAL_BIG_COLUMN_DIRANUM)

    override var filtering_mz11_enabled: Boolean = context.resources.getBoolean(R.bool.filtering_mz11_enabled)
    /**
     * the username that synced this data
     * @author Chaosruler972
     */
    override var USER: String = context.getString(R.string.LOCAL_BIG_COLUMN_USERNAME)

    /**
     * QTY in partial
     * @author Chaosruler972
     */
    private val QTYINPARTIALACC: String = context.getString(R.string.LOCAL_BIG_COLUMN_QTYINPARTIALACC)


    override var REMOTE_DATABASE_NAME: String = context.getString(R.string.DATABASE_NAME)
    override var REMOTE_TABLE_NAME: String = context.getString(R.string.TABLE_BIG)
    override var REMOTE_DATAARAEID_KEY: String = context.getString(R.string.TABLE_BIG_DATAAREAID)
    override var REMOTE_DATAARAEID_VAL: String = context.getString(R.string.DATAAREAID_DEVELOP)

    override fun get_remote_typemap(): HashMap<String, String> = remote_big_table_helper.define_type_map()


    override fun hashmap_to_table_dataclass_local(hashMap: HashMap<String, String>): big_table_data {
        return big_table_data(
                (hashMap[ACCOUNT_NUM] ?: "").trim(),
                (hashMap[DATAARAEID] ?: "").trim(),
                (hashMap[RECVERSION] ?: "").trim(),
                (hashMap[RECID] ?: "").trim(),
                (hashMap[PROJID] ?: "").trim(),
                (hashMap[ITEMID] ?: "").trim(),
                (hashMap[FLAT] ?: "").trim(),
                (hashMap[FLOOR] ?: "").trim(),
                (hashMap[QTY] ?: "").trim(),
                (hashMap[SALESPRICE] ?: "").trim(),
                (hashMap[OPR_ID] ?: "").trim(),
                (hashMap[MILESTONEPERCENTAGE] ?: "").trim(),
                (hashMap[QTYFORACCOUNT] ?: "").trim(),
                (hashMap[PERCENTFORACCOUNT] ?: "").trim(),
                (hashMap[TOTAL_SUM] ?: "").trim(),
                (hashMap[SALPROG] ?: "").trim(),
                (hashMap[PRINTORDER] ?: "").trim(),
                (hashMap[ITEMNUMBER] ?: "").trim(),
                (hashMap[KOMANUM] ?: "").trim(),
                (hashMap[DIRANUM] ?: "").trim(),
                (hashMap[USER] ?: "").trim(),
                (hashMap[QTYINPARTIALACC] ?: "").trim()
        )
    }

    override fun hashmap_to_table_dataclass_remote(hashMap: HashMap<String, String>): big_table_data {
        return big_table_data(
                hashMap[remote_big_table_helper.VENDOR_ID] ?: "",
                hashMap[remote_big_table_helper.DATAREAID] ?: "",
                hashMap[remote_big_table_helper.RECVERSION] ?: "",
                hashMap[remote_big_table_helper.RECID] ?: "",
                hashMap[remote_big_table_helper.PROJECTS_ID] ?: "",
                hashMap[remote_big_table_helper.INVENTORY_ID] ?: "",
                hashMap[remote_big_table_helper.FLAT] ?: "",
                hashMap[remote_big_table_helper.FLOOR] ?: "",
                hashMap[remote_big_table_helper.QTY] ?: "",
                hashMap[remote_big_table_helper.SALESPRICE] ?: "",
                hashMap[remote_big_table_helper.OPR_ID] ?: "",
                hashMap[remote_big_table_helper.MILESTONEPERCENT] ?: "",
                hashMap[remote_big_table_helper.QTYFORACCOUNT] ?: "",
                hashMap[remote_big_table_helper.PERCENTFORACCOUNT] ?: "",
                hashMap[remote_big_table_helper.TOTALSUM] ?: "",
                hashMap[remote_big_table_helper.SALPROG] ?: "",
                hashMap[remote_big_table_helper.PRINTORDER] ?: "",
                hashMap[remote_big_table_helper.ITEMNUMBER] ?: "",
                hashMap[remote_big_table_helper.KOMANUM] ?: "",
                hashMap[remote_big_table_helper.DIRANUM] ?: "",
                remote_SQL_Helper.getusername(),
                hashMap[remote_big_table_helper.QTYINPARTIALACC] ?: ""
        )
    }


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
//        vector.add(QTYINPARTIALACC)
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
        val type = "TEXT"
        map[ACCOUNT_NUM] = type
        map[DATAARAEID] = type
        map[RECVERSION] = type
        map[RECID] = type
        map[PROJID] = type
        map[ITEMID] = type
        map[FLAT] = type
        map[FLOOR] = type
        map[QTY] = type
        map[SALESPRICE] = type
        map[OPR_ID] = type
        map[MILESTONEPERCENTAGE] = type
        map[QTYFORACCOUNT] = type
        map[PERCENTFORACCOUNT] = type
        map[TOTAL_SUM] = type
        map[SALPROG] = type
        map[PRINTORDER] = type
        map[ITEMNUMBER] = type
        map[KOMANUM] = type
        map[DIRANUM] = type
        map[USER] = type

//        map[QTYINPARTIALACC] = type
        val foreign: HashMap<String, String> = HashMap()
//        foreign[ACCOUNT_NUM] = context.getString(R.string.LOCAL_VENDORS_TABLE_NAME) + "(" + context.getString(R.string.LOCAL_VENDORS_COLUMN_ID) + ")"
//        foreign[ITEMID] = context.getString(R.string.LOCAL_INVENTORY_TABLE_NAME) + "(" + context.getString(R.string.LOCAL_INVENTORY_COLUMN_ID) + ")"
//        foreign[OPR_ID] = context.getString(R.string.LOCAL_OPR_TABLE_NAME) + "(" + context.getString(R.string.LOCAL_OPR_COLUMN_ID) + ")"
//        foreign[PROJID] = context.getString(R.string.LOCAL_PROJECTS_TABLE_NAME) + "(" + context.getString(R.string.LOCAL_PROJECTS_COLUMN_ID) + ")"
        val extra = " PRIMARY KEY($RECID, $USER) "
        createDB(db, map, foreign, extra)
    }



}