package com.example.chaosruler.msa_manager.abstraction_classes

import android.content.Context
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper

/**
 * an abstract class that fulfiles the requirements for the application to load a remote database (MSSQL)
 * @author Chaosruler972
 * @see remote_opr_table_helper
 * @constructor empty, this class can be used from mulitple contexts and activities
 */
@Suppress("KDocUnresolvedReference")
abstract class remote_helper
{

        /**
         * Inits the variables on the remoe datbase, with the idea that they are initalized in the strings.xml file
         * init a call to each variable on remote database initated
         * @author Chaosruler972
         * @param context baseContext to work with, must be valid
         */
        abstract fun extract_variables(context: Context)

        /**
         * inits a vector of typemap (key = name, value = type) for processing
         * @author Chaosruler972
         * @return a vector of typemap (key = name, value = type)
         */
        abstract fun define_type_map(): HashMap<String, String>

        /**
         * push an update to the database
         * @author Chaosruler972
         * @param context baseContext to work with, must be valid
         * @param map the input data we wan to update, wih variable name on the key, and data on the value
         * @param obj a table dataclass metaobject to work with, in an abstract form, please see object_types package for more info
         */
        protected abstract fun push_update(obj: table_dataclass, map:HashMap<String,String>, context: Context)

        /**
         * normalizes hashmap to mssql rules
         * @author Chaosruler972
         * @param map the hashmap to normalize
         * @param typemap the hashmap typemap
         */
        fun normalize_hashmap(map: HashMap<String, String>, typemap: HashMap<String, String>)
        {
                for (item in map) {
                        if ((typemap[item.key] ?: "") == "text" || (typemap[item.key]
                                        ?: "") != "varchar" || (typemap[item.key] ?: "") != "nvarchar")
                                item.setValue("N" + remote_SQL_Helper.add_quotes(item.value))
                        else if(typemap[item.key] == "datetime")
                        {
                                item.setValue(global_variables_dataclass.time_to_mssql_time(item.value.toLong()))
                        }
                }
        }
}