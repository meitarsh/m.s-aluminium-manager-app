package com.example.chaosruler.msa_manager

import android.content.Context

class remote_vendors_table_helper()
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