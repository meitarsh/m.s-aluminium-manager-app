package com.example.chaosruler.msa_manager.MSSQL_helpers

import android.content.Context
import android.widget.Toast
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.remote_helper
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.opr_data
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass
import com.example.chaosruler.msa_manager.services.offline_mode_service
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper


class remote_opr_table_helper()
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
            TABLE_NAME = context.getString(R.string.TABLE_OPR)
            DATABASE_NAME = context.getString(R.string.DATABASE_NAME)
            ID = context.getString(R.string.OPR_ID)
            ID_TYPE = context.getString(R.string.OPR_ID_TYPE)

            NAME = context.getString(R.string.OPR_NAME)
            NAME_TYPE = context.getString(R.string.OPR_NAME_TYPE)

            DATAAREAID = context.getString(R.string.OPR_DATAAREAID)
            DATAAREAID_TYPE = context.getString(R.string.OPR_DATAAREAID_TYPE)
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
            API call
         */
        override fun push_update(obj: table_dataclass, map: HashMap<String, String>, context: Context)
        {
            if(obj is opr_data)
                push_update(obj,map,context)
        }

        /*
            push update to db
         */
        public fun push_update(opr: opr_data,map:HashMap<String,String>,context: Context)
        {
            var typemap = make_type_map()
            for(item in map)
            {
                if((typemap[item.key] ?: "") == "text" || (typemap[item.key] ?: "") != "varchar" || (typemap[item.key] ?: "") != "nvarchar" )
                    item.setValue("N"+remote_SQL_Helper.add_quotes(item.value))
            }
            var where_clause:HashMap<String,String> = HashMap()
            where_clause[remote_opr_table_helper.ID] =  opr.get_oprid()?: ""
            var query = remote_SQL_Helper.construct_update_str_multiwhere_text(remote_opr_table_helper.DATABASE_NAME,remote_opr_table_helper.TABLE_NAME,where_clause,"varchar",map)
            query = query.replace("'","&quote;")
            var str = offline_mode_service.general_push_command(query, remote_SQL_Helper.getusername())
            Toast.makeText(context,str, Toast.LENGTH_SHORT).show()
        }
    }
}