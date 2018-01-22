@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.MSSQL_helpers

import android.content.Context
import android.widget.Toast
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.remote_helper
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass
import com.example.chaosruler.msa_manager.object_types.inventory_data
import com.example.chaosruler.msa_manager.services.offline_mode_service
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper
import java.util.*
import kotlin.collections.HashMap


@Suppress("MemberVisibilityCanPrivate")
/**
 * a representation of the remote table
 * @author Chaosruler972
 * @constructor empty, this class can be used from mulitple contexts and activities
 */
class remote_inventory_table_helper
{
    companion object : remote_helper() {

        /**
         * Database name
         * @author Chaosruler972
         */
        var DATABASE_NAME: String = ""
        /**
         * Table name for inventory
         * @author Chaosruler972
         */
        var TABLE_NAME: String = ""

        /**
         * Inventory ID field name
         * @author Chaosruler972
         */
        var ID: String = ""
        /**
         * Inventory ID field type
         * @author Chaosruler972
         */
        var ID_TYPE: String = ""

        /**
         * Inventory name field name
         * @author Chaosruler972
         */
        var NAME: String = ""
        /**
         * Inventory name field type
         * @author Chaosruler972
         */
        var NAME_TYPE: String = ""

        /**
         * Dataraaeid field name
         * @author Chaosruler972
         */
        var DATAAREAID: String = ""
        /**
         * Dataraaeid field type
         * @author Chaosruler972
         */
        var DATAAREAID_TYPE: String = ""

        /**
         * Inits all the variables with the data from strings.xml holding right for remote database remote metadata
         * @author Chaosruler972
         * @param context a baseContext to work with
         */
        override fun extract_variables(context: Context) {
            DATABASE_NAME = context.getString(R.string.DATABASE_NAME)
            TABLE_NAME = context.getString(R.string.TABLE_INVENTORY)

            ID = context.getString(R.string.INVENTORY_ID)
            ID_TYPE = context.getString(R.string.INVENTORY_ID_TYPE)

            NAME = context.getString(R.string.INVENTORY_NAME)
            NAME_TYPE = context.getString(R.string.INVENTORY_NAME_TYPE)

            DATAAREAID = context.getString(R.string.INVENTORY_DATAAREAID)
            DATAAREAID_TYPE = context.getString(R.string.INVENTORY_DATAAREAID_TYPE)
        }

        /**
         * defines a type map as a hashmap that each key is the variable name, and value is is type
         * @author Chaosruler972
         * @return the typemap in hashmap format
         */
        override fun define_type_map():HashMap<String,String> {
            val map: HashMap<String, String> = HashMap()
            map[ID] = ID_TYPE
            map[NAME] = NAME_TYPE
            map[DATAAREAID] = DATAAREAID_TYPE
            return map
        }

        /**
         * Select * from REMOTE_TABLE function
         * @author Chaosruler972
         * @return returns a vector of hashmap of strings, each element represents a row, hasmap items represents columns
         */
        @Suppress("unused")
        fun select_wildcard(): Vector<HashMap<String, String>> = remote_SQL_Helper.select_columns_from_db_with_where(DATABASE_NAME, TABLE_NAME, define_type_map(), null, null)


        /**
         * pushes an update to the database on remote call
         * @author Chaosruler972
         * @param obj a representation of the object data class we want to push an update to
         * @param context a baseContext to work with
         * @param map a map of the identifying traits of what we should update on the object in the remote database
         */
        override fun push_update(obj: table_dataclass, map: HashMap<String, String>, context: Context) {
            if(obj is inventory_data)
                push_update(obj,map,context)
        }



                /**
         * a function to take the object dataclass and initate and take the identifying traits and he updated traits and create an update query matching that
         * @author Chaosruler972
         * @param context a baseContext to work with
         * @param map a map of the variables we want to identify the object with
         * @param inventory the data-object we want to update and take the data from
         */
        fun push_update(inventory: inventory_data, map: HashMap<String, String>, context: Context) {
            val typemap = define_type_map()
            for(item in map) {
                if((typemap[item.key] ?: "") == "text" || (typemap[item.key] ?: "") != "varchar" || (typemap[item.key] ?: "") != "nvarchar" )
                    item.setValue("N"+remote_SQL_Helper.add_quotes(item.value))
            }
            val where_clause: HashMap<String, String> = HashMap()
            where_clause[remote_inventory_table_helper.ID] = inventory.get_itemid() ?: ""
            var query = remote_SQL_Helper.construct_update_str_multiwhere_text(remote_inventory_table_helper.DATABASE_NAME,remote_inventory_table_helper.TABLE_NAME,where_clause,"varchar",map)
            query = query.replace("'","&quote;")
            val str = offline_mode_service.general_push_command(query, remote_SQL_Helper.getusername())
            Toast.makeText(context,str, Toast.LENGTH_SHORT).show()
        }
    }
}