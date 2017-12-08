package com.example.chaosruler.msa_manager.MSSQL_helpers

import android.content.Context
import android.widget.Toast
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.remote_helper
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.vendor_data
import com.example.chaosruler.msa_manager.services.offline_mode_service
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper

class remote_vendors_table_helper()
{
    companion object : remote_helper()
    {
        public var DATABASE_NAME:String = ""
        public var TABLE_NAME:String = ""

        public var ID:String = ""
        public var ID_TYPE:String = ""

        public var NAME:String = ""
        public var NAME_TYPE:String = ""

        public var DATAAREAID:String = ""
        public var DATAAREAID_TYPE:String = ""


        /*
            init database variables
         */
        override public fun init_variables(context: Context)
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

        /*
        make database typemap
         */
        override fun make_type_map():HashMap<String,String>
        {
            var map:HashMap<String,String> = HashMap()
            map[ID] = ID_TYPE
            map[NAME] = NAME_TYPE
            map[DATAAREAID] = DATAAREAID_TYPE
            return map
        }

        /*
            push update to db
         */
        public fun push_update(vendor_data: vendor_data, map:HashMap<String,String>, context: Context)
        {
            var typemap = remote_vendors_table_helper.make_type_map()
            for(item in map)
            {
                if((typemap[item.key] ?: "") == "text" || (typemap[item.key] ?: "") != "varchar" || (typemap[item.key] ?: "") != "nvarchar"  )
                    item.setValue("N"+ remote_SQL_Helper.add_quotes(item.value))
            }
            var where_clause:HashMap<String,String> = HashMap()
            where_clause[remote_vendors_table_helper.ID] = (vendor_data.get_accountnum() ?: "").trim()
            var query = remote_SQL_Helper.construct_update_str_multiwhere_text(remote_vendors_table_helper.DATABASE_NAME,remote_vendors_table_helper.TABLE_NAME,where_clause,"varchar",map)
            query = query.replace("'","&quote;")
            var str = offline_mode_service.general_push_command(query, remote_SQL_Helper.getusername())
            Toast.makeText(context,str, Toast.LENGTH_SHORT).show()
        }


        /*
            API call
         */
        override fun push_update(obj: table_dataclass, map: HashMap<String, String>, context: Context) {
            if(obj is vendor_data)
                push_update(obj,map,context)
        }
    }
}