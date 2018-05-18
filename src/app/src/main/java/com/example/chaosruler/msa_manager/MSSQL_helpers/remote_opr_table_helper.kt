@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.MSSQL_helpers

import android.content.Context
import android.widget.Toast
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.remote_helper
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass
import com.example.chaosruler.msa_manager.object_types.opr_data
import com.example.chaosruler.msa_manager.services.offline_mode_service
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper


@Suppress("MemberVisibilityCanPrivate")
/**
 * a representation of the opr table
 * @author Chaosruler972
 * @constructor empty, this class can be used from mulitple contexts and activities
 */
class remote_opr_table_helper {
    companion object : remote_helper()
    {

        /**
         * Datbase name
         * @author Chaosruler972
         */
        var DATABASE_NAME: String = ""
        /**
         * Table name for opr table
         * @author Chaosruler972
         */
        var TABLE_NAME: String = ""

        /**
         * Operation ID field name
         * @author Chaosruler972
         */
        var ID: String = ""
        /**
         * Operation id field type
         * @author Chaosruler972
         */
        var ID_TYPE: String = ""

        /**
         * Operation name field name
         * @author Chaosruler972
         */
        var NAME: String = ""
        /**
         * Operation name field type
         * @author Chaosruler972
         */
        var NAME_TYPE: String = ""

        /**
         * Operation dataaraeid field name
         * @author Chaosruler972
         */
        var DATAAREAID: String = ""
        /**
         * operation dataaraeid field type
         * @author Chaosruler972
         */
        var DATAAREAID_TYPE: String = ""


        /**
         * Inits all the variables with the data from strings.xml holding right for opr database remote metadata
         * @author Chaosruler972
         * @param context a baseContext to work with
         */
        override fun extract_variables(context: Context)
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

        /**
         * defines a type map as a hashmap that each key is the variable name, and value is is type
         * @author Chaosruler972
         * @return the typemap in hashmap format
         */
        override fun define_type_map():HashMap<String,String>
        {
            val map: HashMap<String, String> = HashMap()
            map[ID] = ID_TYPE
            map[NAME] = NAME_TYPE
            map[DATAAREAID] = DATAAREAID_TYPE
            return map
        }

        /**
         * pushes an update to the database on remote call
         * @author Chaosruler972
         * @param obj a representation of the object data class we want to push an update to
         * @param context a baseContext to work with
         * @param map a map of the identifying traits of what we should update on the object in the remote database
         */
        override fun push_update(obj: table_dataclass, map: HashMap<String, String>, context: Context)
        {
            if(obj is opr_data)
                push_update(obj,map,context)
        }


                /**
         * a function to take the object dataclass and initate and take the identifying traits and he updated traits and create an update query matching that
         * @author Chaosruler972
         * @param context a baseContext to work with
         * @param map a map of the variables we want to identify the object with
         * @param opr the data-object we want to update and take the data from
         */
        fun push_update(opr: opr_data, map: HashMap<String, String>, context: Context)
        {
            val typemap = define_type_map()
            normalize_hashmap(map, typemap)
            val where_clause: HashMap<String, String> = HashMap()
            where_clause[remote_opr_table_helper.ID] =  opr.get_oprid()?: ""

            val all_map = opr.to_hashmap()
            normalize_hashmap(all_map, typemap)
            for(item in map)
                all_map[item.key] = item.value
            var query = remote_SQL_Helper.construct_update_str_multiwhere_text(remote_opr_table_helper.DATABASE_NAME,remote_opr_table_helper.TABLE_NAME,where_clause,"varchar",map, all_map)
            query = query.replace("'","&quote;")
            val str = offline_mode_service.general_push_command(query, remote_SQL_Helper.getusername())
            Toast.makeText(context,str, Toast.LENGTH_SHORT).show()
        }
    }
}