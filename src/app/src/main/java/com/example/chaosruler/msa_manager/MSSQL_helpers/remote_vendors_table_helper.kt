package com.example.chaosruler.msa_manager.MSSQL_helpers

import android.content.Context
import com.example.chaosruler.msa_manager.R

class remote_vendors_table_helper()
{
    companion object
    {
        public var DATABASE_NAME:String = ""
        public var TABLE_NAME:String = ""

        public var ID:String = ""
        public var ID_TYPE:String = ""

        public var NAME:String = ""
        public var NAME_TYPE:String = ""

        public var DATAAREAID:String = ""
        public var DATAAREAID_TYPE:String = ""


        public fun init_variables(context: Context)
        {
            TABLE_NAME = context.getString(R.string.TABLE_VENDORS)
            DATABASE_NAME = context.getString(R.string.DATABASE_NAME)
            ID = context.getString(R.string.VENDORS_ID)
            ID_TYPE = context.getString(R.string.VENDORS_ID_TYPE)

            NAME = context.getString(R.string.VENDORS_NAME)
            NAME_TYPE = context.getString(R.string.VENDORS_NAME_TYPE)

            DATAAREAID = context.getString(R.string.VENDORS_DATAAREAID)
            DATAAREAID_TYPE = context.getString(R.string.VENDORS_DATAAREAID_TYPE)
        }
        fun make_type_map():HashMap<String,String>
        {
            var map:HashMap<String,String> = HashMap()
            map[ID] = ID_TYPE
            map[NAME] = NAME_TYPE
            map[DATAAREAID] = DATAAREAID_TYPE
            return map
        }
    }
}