package com.example.chaosruler.msa_manager

import android.content.Context
import java.util.*

/**
 * Created by chaosruler on 12/3/17.
 */
class remote_inventory_table_helper
{
    companion object
    {
        private var DATABASE_NAME:String = ""
        private var TABLE_NAME:String = ""

        private var ID:String = ""
        private var ID_TYPE:String = ""

        private var NAME:String = ""
        private var NAME_TYPE:String = ""

        private var DATAAREAID:String = ""
        private var DATAAREAID_TYPE:String = ""


        public fun init_variables(context: Context)
        {
            DATABASE_NAME = context.getString(R.string.DATABASE_NAME)
            TABLE_NAME = context.getString(R.string.TABLE_INVENTORY)

            ID = context.getString(R.string.INVENTORY_ID)
            ID_TYPE = context.getString(R.string.INVENTORY_ID_TYPE)

            NAME = context.getString(R.string.INVENTORY_NAME)
            NAME_TYPE = context.getString(R.string.INVENTORY_NAME_TYPE)

            DATAAREAID = context.getString(R.string.INVENTORY_DATAAREAID)
            DATAAREAID_TYPE = context.getString(R.string.INVENTORY_NAME_TYPE)
        }

        fun make_type_map():HashMap<String,String>
        {
            var map:HashMap<String,String> = HashMap()
            map[ID] = ID_TYPE
            map[NAME] = NAME_TYPE
            map[DATAAREAID] = DATAAREAID_TYPE
            return map
        }

        fun select_wildcard(): Vector<HashMap<String, String>> = remote_SQL_Helper.select_columns_from_db_with_where(DATABASE_NAME, TABLE_NAME, make_type_map(),null,null)
    }
}