package com.example.chaosruler.msa_manager.SQLITE_helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.services.local_SQL_Helper
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by chaosruler on 12/3/17.
 */
class local_big_table_helper(private var context: Context) : local_SQL_Helper(context,context.getString(R.string.LOCAL_SYNC_DATABASE_NAME)
,null,context.getString(R.string.LOCAL_BIG_TABLE_VERSION).toInt(),context.getString(R.string.LOCAL_BIG_TABLE_NAME))
{
    private var ACCOUNT_NUM: String = context.getString(R.string.LOCAL_BIG_COLUMN_ACCOUNTNUM)
    private var DATAARAEID:String = context.getString(R.string.LOCAL_BIG_COLUMN_DATAARAEID)
    private var RECVERSION:String = context.getString(R.string.LOCAL_BIG_COLUMN_RECVERSION)
    private var RECID:String = context.getString(R.string.LOCAL_BIG_COLUMN_RECID)
    private var PROJID:String = context.getString(R.string.LOCAL_BIG_COLUMN_PROJID)
    private var ITEMID:String = context.getString(R.string.LOCAL_BIG_COLUMN_ITEMID)
    private var FLAT:String = context.getString(R.string.LOCAL_BIG_COLUMN_FLAT)
    private var FLOOR:String = context.getString(R.string.LOCAL_BIG_COLUMN_FLOOR)
    private var QTY:String = context.getString(R.string.LOCAL_BIG_COLUMN_QTY)
    private var SALESPRICE:String = context.getString(R.string.LOCAL_BIG_COLUMN_SALESPRICE)
    private var OPR_ID:String = context.getString(R.string.LOCAL_BIG_COLUMN_OPRID)
    private var MILESTONEPERCENTAGE:String = context.getString(R.string.LOCAL_BIG_COLUMN_MILESTONEPRECENT)
    private var QTYFORACCOUNT:String = context.getString(R.string.LOCAL_BIG_COLUMN_QTYFORACCOUNT)
    private var PERCENTFORACCOUNT:String = context.getString(R.string.LOCAL_BIG_COLUMN_PERCENTFORACCOUNT)
    private var TOTAL_SUM:String = context.getString(R.string.LOCAL_BIG_COLUMN_TOTALSUM)
    private var SALPROG:String = context.getString(R.string.LOCAL_BIG_COLUMN_SALPROG)
    private var PRINTORDER:String = context.getString(R.string.LOCAL_BIG_COLUMN_printorder)
    private var ITEMNUMBER:String = context.getString(R.string.LOCAL_BIG_COLUMN_ITEMNUMBER)
    private var KOMANUM:String = context.getString(R.string.LOCAL_BIG_COLUMN_KOMANUM)
    private var DIRANUM:String = context.getString(R.string.LOCAL_BIG_COLUMN_DIRANUM)

    private val USER:String = context.getString(R.string.LOCAL_BIG_COLUMN_USERNAME)
    /*
    MUST BE CALLED, it reports to the database about the table schema, is used by the abstracted
    SQL class
 */
    init
    {
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
        var map:HashMap<String,String> = HashMap()
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
        var foreign:HashMap<String,String> = HashMap()
        foreign[ACCOUNT_NUM] = context.getString(R.string.LOCAL_VENDORS_TABLE_NAME)+"("+context.getString(R.string.LOCAL_VENDORS_COLUMN_ID)+")"
        foreign[ITEMID] = context.getString(R.string.LOCAL_INVENTORY_TABLE_NAME)+"("+context.getString(R.string.LOCAL_INVENTORY_COLUMN_ID)+")"
        foreign[OPR_ID] = context.getString(R.string.LOCAL_OPR_TABLE_NAME) + "(" + context.getString(R.string.LOCAL_OPR_COLUMN_ID)+")"
        foreign[PROJID] = context.getString(R.string.LOCAL_PROJECTS_TABLE_NAME) + "(" + context.getString(R.string.LOCAL_PROJECTS_COLUMN_ID) + ")"
        createDB(db,map,foreign)
    }
}