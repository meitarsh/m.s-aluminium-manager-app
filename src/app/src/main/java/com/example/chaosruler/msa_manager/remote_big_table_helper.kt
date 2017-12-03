package com.example.chaosruler.msa_manager

import android.content.Context

/**
 * Created by chaosruler on 12/3/17.
 */
class remote_big_table_helper
{
    companion object
    {
        private var DATABASE_NAME:String = ""
        private var TABLE_NAME:String = ""

        private var VENDOR_ID:String = ""
        private var VENDOR_ID_TYPE:String = ""

        private var DATAREAID:String = ""
        private var DATAAREAID_TYPE:String = ""

        private var RECVERSION:String = ""
        private var RECVERSION_TYPE:String = ""

        private var PROJECTS_ID:String = ""
        private var PROJECTS_ID_TYPE:String = ""

        private var INVENTORY_ID:String = ""
        private var INVENTORY_ID_TYPE:String = ""

        private var FLAT:String = ""
        private var FLAT_TYPE:String = ""

        private var FLOOR:String = ""
        private var FLOOR_TYPE:String = ""

        private var QTY:String = ""
        private var QTY_TYPE:String = ""

        private var SALESPRICE:String = ""
        private var SALESPRICE_TYPE:String = ""

        private var MYSTERY_ID:String = ""
        private var MYSTERY_ID_TYPE:String = ""

        private var MILESTONEPERCENT:String = ""
        private var MILESTONEPERCENT_TYPE:String = ""

        private var QTYFORACCOUNT:String = ""
        private var QTYFORACCOUNT_TYPE:String = ""

        private var PERCENTFORACCOUNT:String = ""
        private var PERCENTFORACCOUNT_TYPE:String = ""

        private var TOTALSUM:String = ""
        private var TOTALSUM_TYPE:String = ""

        private var SALPROG:String = ""
        private var SALPROG_TYPE:String = ""

        private var PRINTORDER:String = ""
        private var PRINTORDER_TYPE:String = ""

        private var ITEMNUMBER:String = ""
        private var ITEMNUMBER_TYPE:String = ""

        private var KOMANUM:String = ""
        private var KOMANUM_TYPE:String = ""

        private var DIRANUM:String = ""
        private var DIRANUM_TYPE:String = ""

        public fun init_variables(context: Context)
        {
            TABLE_NAME = context.getString(R.string.TABLE_INVENTORY)
            DATABASE_NAME = context.getString(R.string.DATABASE_NAME)
            VENDOR_ID = context.getString(R.string.TABLE_BIG_VENDOR_ID)
            VENDOR_ID_TYPE = context.getString(R.string.TABLE_BIG_VENDOR_ID_TYPE)

            DATAREAID = context.getString(R.string.TABLE_BIG_DATAAREAID)
            DATAAREAID_TYPE = context.getString(R.string.TABLE_BIG_DATAAREAID_TYPE)

            RECVERSION = context.getString(R.string.TABLE_BIG_REC_VERSION)
            RECVERSION_TYPE = context.getString(R.string.TABLE_BIG_REC_VERSION_TYPE)

            PROJECTS_ID = context.getString(R.string.TABLE_BIG_PROJECTS_ID)
            PROJECTS_ID_TYPE = context.getString(R.string.TABLE_BIG_PROJECTS_ID_TYPE)

            INVENTORY_ID = context.getString(R.string.TABLE_BIG_INVENTORY_ID)
            INVENTORY_ID_TYPE = context.getString(R.string.TABLE_BIG_INVENTORY_ID_TYPE)

            FLAT = context.getString(R.string.TABLE_BIG_FLAT)
            FLAT_TYPE = context.getString(R.string.TABLE_BIG_FLAT_TYPE)

            FLOOR = context.getString(R.string.TABLE_BIG_FLOOR)
            FLOOR_TYPE = context.getString(R.string.TABLE_BIG_FLOOR_TYPE)

            QTY = context.getString(R.string.TABLE_BIG_QTY)
            QTY_TYPE = context.getString(R.string.TABLE_BIG_QTY_TYPE)

            SALESPRICE = context.getString(R.string.TABLE_BIG_SALESPRICE)
            SALESPRICE_TYPE = context.getString(R.string.TABLE_BIG_SALESPRICE_TYPE)

            MYSTERY_ID = context.getString(R.string.TABLE_BIG_MYSTERY_ID)
            MYSTERY_ID_TYPE = context.getString(R.string.TABLE_BIG_MYSTERY_ID_TYPE)

            MILESTONEPERCENT = context.getString(R.string.TABLE_BIG_MILESTONEPERCENT)
            MILESTONEPERCENT_TYPE = context.getString(R.string.TABLE_BIG_MILESTONEPERCENT_TYPE)

            QTYFORACCOUNT = context.getString(R.string.TABLE_BIG_QTYFORACCOUNT)
            QTYFORACCOUNT_TYPE = context.getString(R.string.TABLE_BIG_QTYFORACCOUNT_TYPE)

            PERCENTFORACCOUNT = context.getString(R.string.TABLE_BIG_PERCENTFORACCOUNT)
            PERCENTFORACCOUNT_TYPE = context.getString(R.string.TABLE_BIG_PERCENTFORACCOUNT_TYPE)

            TOTALSUM = context.getString(R.string.TABLE_BIG_TOTALSUM)
            TOTALSUM_TYPE = context.getString(R.string.TABLE_BIG_TOTALSUM_TYPE)

            SALPROG = context.getString(R.string.TABLE_BIG_SALPROG)
            SALPROG_TYPE = context.getString(R.string.TABLE_BIG_SALPROG_TYPE)

            PRINTORDER = context.getString(R.string.TABLE_BIG_PRINTORDER)
            PRINTORDER_TYPE = context.getString(R.string.TABLE_BIG_PRINTORDER_TYPE)

            ITEMNUMBER = context.getString(R.string.TABLE_BIG_ITEMNUMBER)
            ITEMNUMBER_TYPE = context.getString(R.string.TABLE_BIG_ITEMNUMBER_TYPE)

            KOMANUM = context.getString(R.string.TABLE_BIG_KOMANUM)
            KOMANUM_TYPE = context.getString(R.string.TABLE_BIG_KOMANUM_TYPE)

            DIRANUM = context.getString(R.string.TABLE_BIG_DIRANUM)
            DIRANUM_TYPE = context.getString(R.string.TABLE_BIG_DIRANUM_TYPE)

        }

        fun make_type_map():HashMap<String,String>
        {
            var map:HashMap<String,String> = HashMap()
            map[VENDOR_ID] = VENDOR_ID_TYPE
            map[DATAREAID] = DATAAREAID_TYPE
            map[RECVERSION] = RECVERSION_TYPE
            map[PROJECTS_ID] = PROJECTS_ID_TYPE
            map[INVENTORY_ID] = INVENTORY_ID_TYPE
            map[FLAT] = FLAT_TYPE
            map[FLOOR] = FLOOR_TYPE
            map[QTY] = QTY_TYPE
            map[SALESPRICE] = SALESPRICE_TYPE
            map[MYSTERY_ID] = MYSTERY_ID_TYPE
            map[MILESTONEPERCENT] = MILESTONEPERCENT_TYPE
            map[QTYFORACCOUNT] = QTYFORACCOUNT_TYPE
            map[PERCENTFORACCOUNT] = PERCENTFORACCOUNT_TYPE
            map[TOTALSUM] = TOTALSUM_TYPE
            map[SALPROG] = SALPROG_TYPE
            map[PRINTORDER] = PRINTORDER_TYPE
            map[ITEMNUMBER] = ITEMNUMBER_TYPE
            map[KOMANUM] = KOMANUM_TYPE
            map[DIRANUM] = DIRANUM_TYPE
            // n = 18
            return map
        }
    } // companion end
}