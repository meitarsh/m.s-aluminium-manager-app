@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.MSSQL_helpers

import android.content.Context
import android.widget.Toast
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.remote_helper
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass
import com.example.chaosruler.msa_manager.object_types.salprojluz_data
import com.example.chaosruler.msa_manager.services.offline_mode_service
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper

/**
 * a table representation of remote salprojluz table
 * @author Chaosruler972
 */
class remote_salprojluz_table_helper
{

    companion object : remote_helper() {

        /**
         * Database name
         * @author Chaosruler972
         */
        var DATABASE_NAME: String = ""
        /**
         * Table name for projects
         * @author Chaosruler972
         */
        var TABLE_NAME: String = ""
        /**
         * Project ID field name
         * @author Chaosruler972
         */
        var ID: String = ""
        /**
         * Project ID field type
         * @author Chaosruler972
         */
        var ID_TYPE: String = ""
        /**
         * Start date
         * @author Chaosruler972
         */
        var STARTDATE: String = ""
        /**
         * Start date field type
         * @author Chaosruler972
         */
        var STARTDATE_TYPE: String = ""
        /**
         * Finish date
         * @author Chaosruler972
         */
        var FINISHDATE: String = ""
        /**
         * Finish date type
         * @author Chaosruler972
         */
        var FINISHDATE_TYPE: String = ""
        /**
         * Sium bpoal date
         * @author Chaosruler972
         */
        var SIUMBPOAL: String = ""
        /**
         * Sium bpoal date type
         * @author Chaosruler972
         */
        var SIUMBPOAL_TYPE: String = ""
        /**
         * is finished
         * @author Chaosruler972
         */
        var IS_FINISHED: String = ""
        /**
         * is finished type
         * @author Chaosruler972
         */
        var IS_FINISHED_TYPE: String = ""
        /**
         * Notes
         * @author Chaosruler972
         */
        var NOTES: String = ""
        /**
         * Notes field type
         * @author Chaosruler972
         */
        var NOTES_TYPE : String = ""
        /**
         * Koma
         * @author Chaosruler972
         */
        var KOMA: String = ""
        /**
         * koma field type
         * @author Chaosruler972
         */
        var KOMA_TYPE: String = ""
        /**
         * Building
         * @author Chaosruler972
         */
        var BUILDING: String = ""
        /**
         * Building field type
         * @author Chaosruler972
         */
        var BUILDING_TYPE: String = ""

        /**
         * percent executed
         * @author Chaosruler972
         */
        var PERCENTEXC: String = ""
        /**
         * percent executed type
         * @author Chaosruler972
         */
        var PERCENTEXC_TYPE: String = ""
        /**
         * Dataaraeid field name
         * @author Chaosruler972
         */
        var DATAAREAID: String = ""
        /**
         * Dataaraeid type
         * @author Chaosruler972
         */
        var DATAAREAID_TYPE: String = ""
        /**
         * Record id
         * @author Chaosruler972
         */
        var RECID: String = ""
        /**
         * RECID type
         * @author Chaosruler972
         */
        var RECID_TYPE: String = ""
        /**
         * RECVERSION
         * @author Chaosruler972
         */
        var RECVERION: String= ""
        /**
         * Recversion TYPE
         * @author Chaosruler972
         */
        var RECVERSION_TYPE: String = ""




        /**
         * Extracts variables from table
         * @author Chaosruler972
         * @param context the context to work with
         */
        override fun extract_variables(context: Context) {
            DATABASE_NAME = context.getString(R.string.DATABASE_NAME)
            TABLE_NAME = context.getString(R.string.TABLE_SALPROJLUZ)

            ID = context.getString(R.string.TABLE_SALPROJLUZ_PROJID)
            ID_TYPE = context.getString(R.string.TABLE_SALPROJLUZ_PROJID_TYPE)

            STARTDATE = context.getString(R.string.TABLE_SALPROJLUZ_STARTDATE)
            STARTDATE_TYPE = context.getString(R.string.TABLE_SALPROJLUZ_STARTDATE_TYPE)

            FINISHDATE = context.getString(R.string.TABLE_SALPROJLUZ_FINISHDATE)
            FINISHDATE_TYPE = context.getString(R.string.TABLE_SALPROJLUZ_FINISHDATE_TYPE)

            SIUMBPOAL = context.getString(R.string.TABLE_SALPROJLUZ_siumbefoal)
            SIUMBPOAL_TYPE = context.getString(R.string.TABLE_SALPROJLUZ_siumbefoal_TYPE)

            IS_FINISHED = context.getString(R.string.TABLE_SALPROJLUZ_FINISHED)
            IS_FINISHED_TYPE = context.getString(R.string.TABLE_SALPROJLUZ_FINISHED_TYPE)

            NOTES = context.getString(R.string.TABLE_SALPROJLUZ_NOTES)
            NOTES_TYPE = context.getString(R.string.TABLE_SALPROJLUZ_NOTES_TYPE)

            KOMA = context.getString(R.string.TABLE_SALPROJLUZ_KOMA)
            KOMA_TYPE = context.getString(R.string.TABLE_SALPROJLUZ_KOMA_TYPE)

            BUILDING = context.getString(R.string.TABLE_SALPROJLUZ_BUILDING)
            BUILDING_TYPE = context.getString(R.string.TABLE_SALPROJLUZ_BUILDING_TYPE)

            DATAAREAID = context.getString(R.string.TABLE_SALPROJLUZ_DATAAREAID)
            DATAAREAID_TYPE = context.getString(R.string.TABLE_SALPROJLUZ_DATAAREAID_TYPE)

            PERCENTEXC = context.getString(R.string.TABLE_SALPROJLUZ_PERCENTEXC)
            PERCENTEXC_TYPE = context.getString(R.string.TABLE_SALPROJLUZ_PERCENTEXC_TYPE)

            RECID = context.getString(R.string.TABLE_SALPROJLUZ_RECID)
            RECID_TYPE = context.getString(R.string.TABLE_SALPROJLUZ_RECID_TYPE)

            RECVERION = context.getString(R.string.TABLE_SALPROJLUZ_RECVERSION)
            RECVERSION_TYPE = context.getString(R.string.TABLE_SALPROJLUZ_RECVERSION_TYPE)

        }
        /**
         * Defines table typemap
         * @author Chaosruler972
         * @return Typemap
         */
        override fun define_type_map(): HashMap<String, String> {
            val hashmap = HashMap<String, String>()
            hashmap[ID] = ID_TYPE
            hashmap[STARTDATE] = STARTDATE_TYPE
            hashmap[FINISHDATE] = FINISHDATE_TYPE
            hashmap[SIUMBPOAL] = SIUMBPOAL
            hashmap[DATAAREAID] = DATAAREAID_TYPE
            hashmap[IS_FINISHED] = IS_FINISHED_TYPE
            hashmap[NOTES] = NOTES_TYPE
            hashmap[KOMA] = KOMA_TYPE
            hashmap[BUILDING] = BUILDING_TYPE
            hashmap[PERCENTEXC] = PERCENTEXC_TYPE
            hashmap[RECID] = RECID_TYPE
            hashmap[RECVERION] = RECVERSION_TYPE
            return hashmap
        }


        /**
         * a function to take the object dataclass and initate and take the identifying traits and he updated traits and create an update query matching that
         * @author Chaosruler972
         * @param context a baseContext to work with
         * @param map a map of the variables we want to identify the object with
         * @param salprojluz_data the data-object we want to update and take the data from
         */
        fun push_update(salprojluz_data: salprojluz_data, map: HashMap<String, String>, context: Context)
        {
            val typemap = remote_salprojluz_table_helper.define_type_map()
            normalize_hashmap(map, typemap)
            val where_clause: HashMap<String, String> = HashMap()
            where_clause[remote_salprojluz_table_helper.RECID] = (salprojluz_data.get_recid()
                    ?: "").trim()
            val all_map = salprojluz_data.to_hashmap()
            normalize_hashmap(all_map, typemap)
            for(item in map)
                all_map[item.key] = item.value
            var query = remote_SQL_Helper.construct_update_str_multiwhere_text(remote_salprojluz_table_helper.DATABASE_NAME,remote_salprojluz_table_helper.TABLE_NAME,where_clause,"varchar",map, all_map)
            query = query.replace("'","&quote;")
            val str = offline_mode_service.general_push_command(query, remote_SQL_Helper.getusername())
            Toast.makeText(context,str, Toast.LENGTH_SHORT).show()
        }

        /**
         * pushes update to remote db
         * @author Chaosruler972
         * @param obj dataclass to work with (data)
         * @param map hashmap of typemap
         * @param context the context to work with
         */
        override fun push_update(obj: table_dataclass, map: HashMap<String, String>, context: Context) {
            if(obj is salprojluz_data)
                push_update(obj, map, context)
        }
    }

}