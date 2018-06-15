@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_big_table_helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_big_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.*
import com.example.chaosruler.msa_manager.object_types.big_table.big_builder
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

) : syncable, local_SQL_Helper(context, context.getString(R.string.LOCAL_SYNC_BIG_DB__NAME)
,null,context.resources.getInteger(R.integer.LOCAL_BIG_TABLE_VERSION),context.getString(R.string.LOCAL_BIG_TABLE_NAME), create_vector_of_variables(context)) {


    override var filtering_mz11_enabled: Boolean = context.resources.getBoolean(R.bool.filtering_mz11_enabled)
    /**
     * the username that synced this data
     * @author Chaosruler972
     */
    override var USER: String = context.getString(R.string.LOCAL_BIG_COLUMN_USERNAME)

    override fun SPECIAL_SEARCH_COLUMN(): String = context.getString(R.string.TABLE_BIG_PROJECTS_ID)

    override var REMOTE_DATABASE_NAME: String = context.getString(R.string.DATABASE_NAME)
    override var REMOTE_TABLE_NAME: String = context.getString(R.string.TABLE_BIG)
    override var REMOTE_DATAARAEID_KEY: String = context.getString(R.string.TABLE_BIG_DATAAREAID)
    override var REMOTE_DATAARAEID_VAL: String = context.getString(R.string.DATAAREAID_DEVELOP)

    override var remote_sql_helper: remote_helper = remote_big_table_helper

    override var builder: table_dataclass_hashmap_createable = big_builder


    /**
     *    MUST BE CALLED, it reports to the database about the table schema, is used by the abstracted
     * SQL class
     * @author Chaosruler972
     */


    /**
     * provides info for the abstracted SQL class
     * on what the table schema is for creation
     * @author Chaosruler972
     * @param db an instance of database
     */
    override fun onCreate(db: SQLiteDatabase)
    {
        val map: HashMap<String, String> = HashMap()
        val type = context.getString(R.string.SQLITE_VAL_TYPE)
        map[hashmap_of_variables[local_big_enum.ACCOUNT_NUM]!!] = type
        map[hashmap_of_variables[local_big_enum.DATAARAEID]!!] = type
        map[hashmap_of_variables[local_big_enum.RECVERSION]!!] = type
        map[hashmap_of_variables[local_big_enum.RECID]!!] = "$type PRIMARY KEY"
        map[hashmap_of_variables[local_big_enum.PROJID]!!] = type
        map[hashmap_of_variables[local_big_enum.ITEMID]!!] = type
        map[hashmap_of_variables[local_big_enum.FLAT]!!] = type
        map[hashmap_of_variables[local_big_enum.FLOOR]!!] = type
        map[hashmap_of_variables[local_big_enum.QTY]!!] = type
        map[hashmap_of_variables[local_big_enum.SALESPRICE]!!] = type
        map[hashmap_of_variables[local_big_enum.OPR_ID]!!] = type
        map[hashmap_of_variables[local_big_enum.MILESTONEPERCENTAGE]!!] = type
        map[hashmap_of_variables[local_big_enum.QTYFORACCOUNT]!!] = type
        map[hashmap_of_variables[local_big_enum.PERCENTFORACCOUNT]!!] = type
        map[hashmap_of_variables[local_big_enum.TOTAL_SUM]!!] = type
        map[hashmap_of_variables[local_big_enum.SALPROG]!!] = type
        map[hashmap_of_variables[local_big_enum.PRINTORDER]!!] = type
        map[hashmap_of_variables[local_big_enum.ITEMNUMBER]!!] = type
        map[hashmap_of_variables[local_big_enum.KOMANUM]!!] = type
        map[hashmap_of_variables[local_big_enum.DIRANUM]!!] = type
        map[hashmap_of_variables[local_big_enum.USER]!!] = type

//        map[QTYINPARTIALACC] = type
//        val foreign: HashMap<String, String> = HashMap()
//        foreign[ACCOUNT_NUM] = context.getString(R.string.LOCAL_VENDORS_TABLE_NAME) + "(" + context.getString(R.string.LOCAL_VENDORS_COLUMN_ID) + ")"
//        foreign[ITEMID] = context.getString(R.string.LOCAL_INVENTORY_TABLE_NAME) + "(" + context.getString(R.string.LOCAL_INVENTORY_COLUMN_ID) + ")"
//        foreign[OPR_ID] = context.getString(R.string.LOCAL_OPR_TABLE_NAME) + "(" + context.getString(R.string.LOCAL_OPR_COLUMN_ID) + ")"
//        foreign[ID] = context.getString(R.string.LOCAL_PROJECTS_TABLE_NAME) + "(" + context.getString(R.string.LOCAL_PROJECTS_COLUMN_ID) + ")"
//        val extra = " PRIMARY KEY(${hashmap_of_variables[local_big_enum.RECID]}, ${hashmap_of_variables[local_big_enum.USER]!!}) "
//        createDB(db, map, foreign, extra)
        createDB(db, map)
    }

    companion object vector_of_variables_maker{
        fun create_vector_of_variables(context: Context): HashMap<Int, String> {
            /**
             * Account number field name
             * @author Chaosruler972TABLE_BIG_SYNC
             */
            val ACCOUNT_NUM: String = context.getString(R.string.LOCAL_BIG_COLUMN_ACCOUNTNUM)
            /**
             * Dataaraeid field name
             * @author Chaosruler972
             */
            val DATAARAEID: String = context.getString(R.string.LOCAL_BIG_COLUMN_DATAARAEID)
            /**
             * rec version field name
             * @author Chaosruler972
             */
            val RECVERSION: String = context.getString(R.string.LOCAL_BIG_COLUMN_RECVERSION)
            /**
             * rec id field name
             * @author Chaosruler972
             */
            val RECID: String = context.getString(R.string.LOCAL_BIG_COLUMN_RECID)
            /**
             * the project id field name
             * @author Chaosruler972
             */
            val PROJID: String = context.getString(R.string.LOCAL_BIG_COLUMN_PROJID)
            /**
             * the item id field name
             * @author Chaosruler972
             */
            val ITEMID: String = context.getString(R.string.LOCAL_BIG_COLUMN_ITEMID)
            /**
             * the flat field name
             * @author Chaosruler972
             */
            val FLAT: String = context.getString(R.string.LOCAL_BIG_COLUMN_FLAT)
            /**
             * flat field name
             * @author Chaosruler972
             */
            val FLOOR: String = context.getString(R.string.LOCAL_BIG_COLUMN_FLOOR)
            /**
             * quanity field name
             * @author Chaosruler972
             */
            val QTY: String = context.getString(R.string.LOCAL_BIG_COLUMN_QTY)
            /**
             * the sales price field name
             * @author Chaosruler972
             */
            val SALESPRICE = context.getString(R.string.LOCAL_BIG_COLUMN_SALESPRICE)
            /**
             * the operation id field name
             * @author Chaosruler972
             */
            val OPR_ID: String = context.getString(R.string.LOCAL_BIG_COLUMN_OPRID)
            /**
             * the miestone to percent field name
             * @author Chaosruler972
             */
            val MILESTONEPERCENTAGE: String = context.getString(R.string.LOCAL_BIG_COLUMN_MILESTONEPRECENT)
            /**
             * the quanity for account field name
             * @author Chaosruler972
             */
            val QTYFORACCOUNT = context.getString(R.string.LOCAL_BIG_COLUMN_QTYFORACCOUNT)
            /**
             * the percent for account field name
             * @author Chaosruler972
             */
            val PERCENTFORACCOUNT: String = context.getString(R.string.LOCAL_BIG_COLUMN_PERCENTFORACCOUNT)
            /**
             * the total sum field name
             * @author Chaosruler972
             */
            val TOTAL_SUM: String = context.getString(R.string.LOCAL_BIG_COLUMN_TOTALSUM)
            /**
             * the sale progress field name
             * @author Chaosruler972
             */
            val SALPROG: String = context.getString(R.string.LOCAL_BIG_COLUMN_SALPROG)
            /**
             * the print order field name
             * @author Chaosruler972
             */
            val PRINTORDER: String = context.getString(R.string.LOCAL_BIG_COLUMN_printorder)
            /**
             * the item number field name
             * @author Chaosruler972
             */
            val ITEMNUMBER: String = context.getString(R.string.LOCAL_BIG_COLUMN_ITEMNUMBER)
            /**
             * the koma num field name
             * @author Chaosruler972
             */
            val KOMANUM: String = context.getString(R.string.LOCAL_BIG_COLUMN_KOMANUM)
            /**
             * the dira num field name
             * @author Chaosruler972
             */
            val DIRANUM: String = context.getString(R.string.LOCAL_BIG_COLUMN_DIRANUM)

            /**
             * the username that synced this data
             * @author Chaosruler972
             */
            val USER: String = context.getString(R.string.LOCAL_BIG_COLUMN_USERNAME)

            /**
             * QTY in partial
             * @author Chaosruler972
             */
            val QTYINPARTIALACC: String = context.getString(R.string.LOCAL_BIG_COLUMN_QTYINPARTIALACC)

            val variable_hashmap = HashMap<Int, String>()
            variable_hashmap[local_big_enum.ACCOUNT_NUM] = ACCOUNT_NUM
            variable_hashmap[local_big_enum.DATAARAEID] = DATAARAEID
            variable_hashmap[local_big_enum.DIRANUM] = DIRANUM
            variable_hashmap[local_big_enum.FLAT] = FLAT
            variable_hashmap[local_big_enum.FLOOR] = FLOOR
            variable_hashmap[local_big_enum.RECID] = RECID
            variable_hashmap[local_big_enum.RECVERSION] = RECVERSION
            variable_hashmap[local_big_enum.USER] = USER
            variable_hashmap[local_big_enum.QTY] = QTY
            variable_hashmap[local_big_enum.ITEMID] = ITEMID
            variable_hashmap[local_big_enum.MILESTONEPERCENTAGE] = MILESTONEPERCENTAGE
            variable_hashmap[local_big_enum.SALESPRICE] = SALESPRICE
            variable_hashmap[local_big_enum.SALPROG] = SALPROG
            variable_hashmap[local_big_enum.ITEMNUMBER] = ITEMNUMBER
            variable_hashmap[local_big_enum.PROJID] = PROJID
            variable_hashmap[local_big_enum.OPR_ID] = OPR_ID
            variable_hashmap[local_big_enum.QTYFORACCOUNT] = QTYFORACCOUNT
            variable_hashmap[local_big_enum.PERCENTFORACCOUNT] = PERCENTFORACCOUNT
            variable_hashmap[local_big_enum.QTYFORACCOUNT] = QTYFORACCOUNT
            variable_hashmap[local_big_enum.TOTAL_SUM] = TOTAL_SUM
            variable_hashmap[local_big_enum.PRINTORDER] = PRINTORDER
            variable_hashmap[local_big_enum.KOMANUM] = KOMANUM
            variable_hashmap[local_big_enum.QTYINPARTIALACC] = QTYINPARTIALACC
            return variable_hashmap
        }
    }
}