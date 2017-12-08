package com.example.chaosruler.msa_manager.MSSQL_helpers

import android.content.Context
import android.widget.Toast
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.big_table_data
import com.example.chaosruler.msa_manager.services.offline_mode_service
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper

/**
 * Created by chaosruler on 12/3/17.
 */
class remote_big_table_helper
{
    companion object
    {
        public var DATABASE_NAME:String = ""
        public var TABLE_NAME:String = ""

        public var VENDOR_ID:String = ""
        public var VENDOR_ID_TYPE:String = ""

        public var DATAREAID:String = ""
        public var DATAAREAID_TYPE:String = ""

        public var RECVERSION:String = ""
        public var RECVERSION_TYPE:String = ""

        public var RECID:String = ""
        public var RECID_TYPE:String = ""

        public var PROJECTS_ID:String = ""
        public var PROJECTS_ID_TYPE:String = ""

        public var INVENTORY_ID:String = ""
        public var INVENTORY_ID_TYPE:String = ""

        public var FLAT:String = ""
        public var FLAT_TYPE:String = ""

        public var FLOOR:String = ""
        public var FLOOR_TYPE:String = ""

        public var QTY:String = ""
        public var QTY_TYPE:String = ""

        public var SALESPRICE:String = ""
        public var SALESPRICE_TYPE:String = ""

        public var OPR_ID:String = ""
        public var OPR_ID_TYPE:String = ""

        public var MILESTONEPERCENT:String = ""
        public var MILESTONEPERCENT_TYPE:String = ""

        public var QTYFORACCOUNT:String = ""
        public var QTYFORACCOUNT_TYPE:String = ""

        public var PERCENTFORACCOUNT:String = ""
        public var PERCENTFORACCOUNT_TYPE:String = ""

        public var TOTALSUM:String = ""
        public var TOTALSUM_TYPE:String = ""

        public var SALPROG:String = ""
        public var SALPROG_TYPE:String = ""

        public var PRINTORDER:String = ""
        public var PRINTORDER_TYPE:String = ""

        public var ITEMNUMBER:String = ""
        public var ITEMNUMBER_TYPE:String = ""

        public var KOMANUM:String = ""
        public var KOMANUM_TYPE:String = ""

        public var DIRANUM:String = ""
        public var DIRANUM_TYPE:String = ""

        public fun init_variables(context: Context)
        {
            TABLE_NAME = context.getString(R.string.TABLE_BIG)
            DATABASE_NAME = context.getString(R.string.DATABASE_NAME)
            VENDOR_ID = context.getString(R.string.TABLE_BIG_VENDOR_ID)
            VENDOR_ID_TYPE = context.getString(R.string.TABLE_BIG_VENDOR_ID_TYPE)

            DATAREAID = context.getString(R.string.TABLE_BIG_DATAAREAID)
            DATAAREAID_TYPE = context.getString(R.string.TABLE_BIG_DATAAREAID_TYPE)

            RECVERSION = context.getString(R.string.TABLE_BIG_REC_VERSION)
            RECVERSION_TYPE = context.getString(R.string.TABLE_BIG_REC_VERSION_TYPE)

            RECID = context.getString(R.string.TABLE_BIG_REC_ID)
            RECID_TYPE = context.getString(R.string.TABLE_BIG_REC_ID_TYPE)

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

            OPR_ID = context.getString(R.string.TABLE_BIG_OPR_ID)
            OPR_ID_TYPE = context.getString(R.string.TABLE_BIG_OPR_ID_TYPE)

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
            map[OPR_ID] = OPR_ID_TYPE
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

        public fun push_update(big_table_data: big_table_data,map:HashMap<String,String>,context: Context)
        {
            var typemap = make_type_map()
            for(item in map)
            {
                if((typemap[item.key] ?: "") == "text" || (typemap[item.key] ?: "") != "varchar" || (typemap[item.key] ?: "") != "nvarchar" )
                    item.setValue("N"+remote_SQL_Helper.add_quotes(item.value))
            }
            var where_clause:HashMap<String,String> = HashMap()
            where_clause[remote_big_table_helper.VENDOR_ID] = big_table_data.get_VENDOR_ID()!!
            where_clause[remote_big_table_helper.INVENTORY_ID] = big_table_data.get_INVENTORY_ID()!!
            where_clause[remote_big_table_helper.PROJECTS_ID] = big_table_data.get_PROJECT_ID()!!
            where_clause[remote_big_table_helper.OPR_ID] = big_table_data.get_OPRID()!!
            var query = remote_SQL_Helper.construct_update_str_multiwhere_text(remote_big_table_helper.DATABASE_NAME,remote_big_table_helper.TABLE_NAME,where_clause,"varchar",map)
            query = query.replace("'","&quote;")
            var str = offline_mode_service.general_push_command(query,remote_SQL_Helper.getusername())
            Toast.makeText(context,str,Toast.LENGTH_SHORT).show()
        }
    } // companion end
}