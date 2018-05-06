@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.MSSQL_helpers

import android.content.Context
import android.widget.Toast
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.remote_helper
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass
import com.example.chaosruler.msa_manager.object_types.big_table_data
import com.example.chaosruler.msa_manager.services.offline_mode_service
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper


@Suppress("MemberVisibilityCanPrivate")
/**
 * a representation of the big table
 * @author Chaosruler972
 * @constructor empty, this class can be used from mulitple contexts and activities
 */
class remote_big_table_helper
{

    companion object : remote_helper()
    {
        /**
         * The Database name
         * @author Chaosruler972
         */
        var DATABASE_NAME: String = ""
        /**
         * The Table name
         * @author Chaosruler972
         */
        var TABLE_NAME: String = ""

        /**
         * Vendor ID field name
         * @author Chaosruler972
         */
        var VENDOR_ID: String = ""
        /**
         * Vendor ID field type
         * @author Chaosruler972
         */
        var VENDOR_ID_TYPE: String = ""

        /**
         * Dataraeid, for testing, field name
         * @author Chaosruler972
         */
        var DATAREAID: String = ""
        /**
         * Dataraid, for testing, field type
         * @author Chaosruler972
         */
        var DATAAREAID_TYPE: String = ""

        /**
         * Recversion field name
         * @author Chaosruler972
         */
        var RECVERSION: String = ""
        /**
         * Recversion field type
         * @author Chaosruler972
         */
        var RECVERSION_TYPE: String = ""

        /**
         * RecID field name
         * @author Chaosruler972
         */
        var RECID: String = ""
        /**
         * RecID field type
         * @author Chaosruler972
         */
        var RECID_TYPE: String = ""

        /**
         * Project Id field name
         * @author Chaosruler972
         */
        var PROJECTS_ID: String = ""
        /**
         * Project ID field type
         * @author Chaosruler972
         */
        var PROJECTS_ID_TYPE: String = ""

        /**
         * Inventory ID field name
         * @author Chaosruler972
         */
        var INVENTORY_ID: String = ""
        /**
         * Inventory ID field type
         * @author Chaosruler972
         */
        var INVENTORY_ID_TYPE: String = ""

        /**
         * Flat field name
         * @author Chaosruler972
         */
        var FLAT: String = ""
        /**
         * Flat field type
         * @author Chaosruler972
         */
        var FLAT_TYPE: String = ""

        /**
         * Floor field name
         * @author Chaosruler972
         */
        var FLOOR: String = ""
        /**
         * Floor field type
         * @author Chaosruler972
         */
        var FLOOR_TYPE: String = ""

        /**
         * quanity field name
         * @author Chaosruler972
         */
        var QTY: String = ""
        /**
         * quanity field type
         * @author Chaosruler972
         */
        var QTY_TYPE: String = ""

        /**
         * QTYINPARTIALACC field name
         * @author Chaosruler972
         */
        var QTYINPARTIALACC: String = ""
        /**
         * QTYINPARTIALACC field type
         * @author Chaosruler972
         */
        var QTYINPARTIALACC_TYPE: String = ""

        /**
         * Sales price field name
         * @author Chaosruler972
         */
        var SALESPRICE: String = ""
        /**
         * Sales price field type
         * @author Chaosruler972
         */
        var SALESPRICE_TYPE: String = ""

        /**
         * Operation ID field name
         * @author Chaosruler972
         */
        var OPR_ID: String = ""
        /**
         * Operation ID field type
         * @author Chaosruler972
         */
        var OPR_ID_TYPE: String = ""

        /**
         * Miles to percent convertion, field name
         * @author Chaosruler972
         */
        var MILESTONEPERCENT: String = ""
        /**
         * Miles to percent field type
         * @author Chaosruler972
         */
        var MILESTONEPERCENT_TYPE: String = ""


        /**
         * Quanity for account field name
         * @author Chaosruler972
         */
        var QTYFORACCOUNT: String = ""
        /**
         * Quanity for account field type
         * @author Chaosruler972
         */
        var QTYFORACCOUNT_TYPE: String = ""
        /**
         * Percent for account field name
         * @author Chaosruler972
         */
        var PERCENTFORACCOUNT: String = ""
        /**
         * Percent for account field type
         * @author Chaosruler972
         */
        var PERCENTFORACCOUNT_TYPE: String = ""

        /**
         * Total sum field name
         * @author Chaosruler972
         */
        var TOTALSUM: String = ""
        /**
         * Total sum field type
         * @author Chaosruler972
         */
        var TOTALSUM_TYPE: String = ""

        /**
         * Sales progress field name
         *@author Chaosruler972
         */
        var SALPROG: String = ""
        /**
         * Sales progress field type
         * @author Chaosruler972
         */
        var SALPROG_TYPE: String = ""

        /**
         * Print order field name
         * @author Chaosruler972
         */
        var PRINTORDER: String = ""
        /**
         * print order field type
         * @author Chaosruler972
         */
        var PRINTORDER_TYPE: String = ""

        /**
         * Item number field name
         * @author Chaosruler972
         */
        var ITEMNUMBER: String = ""
        /**
         * Item number field type
         * @author Chaosruler972
         */
        var ITEMNUMBER_TYPE: String = ""
        /**
         * Koma num field name
         * @author Chaosruler972
         */
        var KOMANUM: String = ""
        /**
         * Koma num field type
         * @author Chaosruler972
         */
        var KOMANUM_TYPE: String = ""

        /**
         * Dira num field name
         * @author Chaosruler972
         */
        var DIRANUM: String = ""
        /**
         * Dira num field type
         * @author Chaosruler972
         */
        var DIRANUM_TYPE: String = ""


        /**
         * Syncs to this table too...
         * @author Chaosruler972
         */
       var TABLE_SECOND_SYNC_NAME: String = ""


        /**
         * Inits all the variables with the data from strings.xml holding right for big database remote metadata
         * @author Chaosruler972
         * @param context a baseContext to work with
         */
        override fun extract_variables(context: Context) {
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

            QTYINPARTIALACC = context.getString(R.string.TABLE_BIG_QTYINPARTIALACC)
            QTYINPARTIALACC_TYPE = context.getString(R.string.TABLE_BIG_QTYFORACCOUNT_TYPE)

            TABLE_SECOND_SYNC_NAME = context.getString(R.string.TABLE_BIG_SYNC)
        }

        /**
         * defines a type map as a hashmap that each key is the variable name, and value is is type
         * @author Chaosruler972
         * @return the typemap in hashmap format
         */
        override fun define_type_map():HashMap<String,String> {
            val map: HashMap<String, String> = HashMap()
            map[VENDOR_ID] = VENDOR_ID_TYPE
            map[DATAREAID] = DATAAREAID_TYPE
            map[RECVERSION] = RECVERSION_TYPE
            map[RECID] = RECID_TYPE
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
            map[QTYINPARTIALACC] = QTYINPARTIALACC_TYPE
            // n = 18
            return map
        }

        /**
         * pushes an update to the database on remote call
         * @author Chaosruler972
         * @param obj a representation of the object data class we want to push an update to
         * @param context a baseContext to work with
         * @param map a map of the identifying traits of what we should update on the object in the remote database
         */
        override fun push_update(obj: table_dataclass, map: HashMap<String, String>, context: Context) {
            if(obj is big_table_data)
                push_update(obj,map,context)
        }

        /**
         * a function to take the object dataclass and initate and take the identifying traits and he updated traits and create an update query matching that
         * @author Chaosruler972
         * @param context a baseContext to work with
         * @param map a map of the variables we want to identify the object with
         * @param obj the data-object we want to update and take the data from
         */
        fun push_update(obj: big_table_data, map: HashMap<String, String>, context: Context) {
            val typemap = define_type_map()
            for(item in map) {
                if((typemap[item.key] ?: "") == "text" || (typemap[item.key] ?: "") != "varchar" || (typemap[item.key] ?: "") != "nvarchar" )
                    item.setValue("N"+remote_SQL_Helper.add_quotes(item.value))
            }
            val where_clause: HashMap<String, String> = HashMap()
            where_clause[remote_big_table_helper.VENDOR_ID] = obj.get_VENDOR_ID() ?: ""
            where_clause[remote_big_table_helper.INVENTORY_ID] = obj.get_INVENTORY_ID() ?: ""
            where_clause[remote_big_table_helper.PROJECTS_ID] = obj.get_PROJECT_ID() ?: ""
            where_clause[remote_big_table_helper.OPR_ID] = obj.get_OPRID() ?: ""
            var query = remote_SQL_Helper.construct_update_str_multiwhere_text(remote_big_table_helper.DATABASE_NAME,remote_big_table_helper.TABLE_NAME,where_clause,"varchar",map)
            query = query.replace("'","&quote;")
            val str = offline_mode_service.general_push_command(query, remote_SQL_Helper.getusername())

            /*
                Input hack in order to sync between two tables, not tested and not necceserily
                stable at the very idea of it
             */
            val query_second = remote_SQL_Helper.construct_update_str_multiwhere_text(remote_big_table_helper.DATABASE_NAME,remote_big_table_helper.TABLE_SECOND_SYNC_NAME,where_clause,"varchar",map)
            offline_mode_service.general_push_command(query_second, remote_SQL_Helper.getusername())
            Toast.makeText(context,str,Toast.LENGTH_SHORT).show()
        }
    } // companion end
}