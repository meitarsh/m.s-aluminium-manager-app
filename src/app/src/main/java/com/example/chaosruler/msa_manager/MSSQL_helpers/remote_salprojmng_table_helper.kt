package com.example.chaosruler.msa_manager.MSSQL_helpers

import android.content.Context
import android.widget.Toast
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.remote_helper
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass
import com.example.chaosruler.msa_manager.object_types.salprojmng_table_data
import com.example.chaosruler.msa_manager.services.offline_mode_service
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper
import java.util.*

/**
 * a representation of the remote table
 * REMOVED FROM USE
 * @author Chaosruler972
 * @constructor empty, this class can be used from mulitple contexts and activities
 */
class remote_salprojmng_table_helper {
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
         * PROJ ID field name
         * @author Chaosruler972
         */
        var PROJID: String = ""
        /**
         * PROJ ID field type
         * @author Chaosruler972
         */
        var PROJID_TYPE: String = ""

        /**
         * Userid name field name
         * @author Chaosruler972
         */
        var USERID: String = ""
        /**
         * Userid name field type
         * @author Chaosruler972
         */
        var USERID_TYPE: String = ""

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
         * Recid field name
         * @author Chaosruler972
         */
        var RECID: String = ""
        /**
         * Recid field type
         * @author Chaosruler972
         */
        var RECID_TYPE: String = ""

        private var DATAARAEID_VAL: String = ""
        /**
         * Inits all the variables with the data from strings.xml holding right for remote database remote metadata
         * @author Chaosruler972
         * @param context a baseContext to work with
         */
        override fun extract_variables(context: Context) {
            DATABASE_NAME = context.getString(R.string.DATABASE_NAME)
            TABLE_NAME = context.getString(R.string.TABLE_SALPROJMNG)

            PROJID = context.getString(R.string.TABLE_SALPROJMNG_PROJID)
            PROJID_TYPE = context.getString(R.string.TABLE_SALPROJMNG_PROJID_TYPE)

            USERID = context.getString(R.string.TABLE_SALPROJMNG_USERID)
            USERID_TYPE = context.getString(R.string.TABLE_SALPROJMNG_USERID_TYPE)

            DATAAREAID = context.getString(R.string.TABLE_SALPROJMNG_DATAAREAID)
            DATAAREAID_TYPE = context.getString(R.string.TABLE_SALPROJMNG_DATAAREAID_TYPE)

            RECVERSION = context.getString(R.string.TABLE_SALPROJMNG_RECVERSION)
            RECVERSION_TYPE = context.getString(R.string.TABLE_SALPROJMNG_RECVERSION_TYPE)

            RECID = context.getString(R.string.TABLE_SALPROJMNG_RECID)
            RECID_TYPE = context.getString(R.string.TABLE_SALPROJMNG_RECID_TYPE)

            DATAARAEID_VAL = context.getString(R.string.DATAAREAID_DEVELOP)
        }

        /**
         * defines a type map as a hashmap that each key is the variable name, and value is is type
         * @author Chaosruler972
         * @return the typemap in hashmap format
         */
        override fun define_type_map(): HashMap<String, String> {
            val map: HashMap<String, String> = HashMap()
            map[remote_salprojmng_table_helper.PROJID] = remote_salprojmng_table_helper.PROJID_TYPE
            map[remote_salprojmng_table_helper.USERID] = remote_salprojmng_table_helper.USERID_TYPE
            map[remote_salprojmng_table_helper.DATAAREAID] = remote_salprojmng_table_helper.DATAAREAID_TYPE
            map[remote_salprojmng_table_helper.RECVERSION] = remote_salprojmng_table_helper.RECVERSION_TYPE
            map[remote_salprojmng_table_helper.RECID] = remote_salprojmng_table_helper.RECID_TYPE
            return map
        }

        /**
         * Select * from REMOTE_TABLE function
         * @author Chaosruler972
         * @return returns a vector of hashmap of strings, each element represents a row, hasmap items represents columns
         */
        @Suppress("unused")
        fun select_wildcard(): Vector<HashMap<String, String>> = remote_SQL_Helper.select_columns_from_db_with_where(DATABASE_NAME, TABLE_NAME, define_type_map(), DATAAREAID, DATAAREAID_TYPE)

        /**
         * pushes an update to the database on remote call
         * @author Chaosruler972
         * @param obj a representation of the object data class we want to push an update to
         * @param context a baseContext to work with
         * @param map a map of the identifying traits of what we should update on the object in the remote database
         */
        override fun push_update(obj: table_dataclass, map: HashMap<String, String>, context: Context) {
            if (obj is salprojmng_table_data)
                push_update(obj, map, context)
        }

        /**
         * a function to take the object dataclass and initate and take the identifying traits and he updated traits and create an update query matching that
         * @author Chaosruler972
         * @param context a baseContext to work with
         * @param map a map of the variables we want to identify the object with
         * @param salprojmng_table_data the data-object we want to update and take the data from
         */
        fun push_update(salprojmng_table_data: salprojmng_table_data, map: HashMap<String, String>, context: Context) {
            val typemap = define_type_map()
            normalize_hashmap(map, typemap)
            val where_clause: HashMap<String, String> = HashMap()
            where_clause[remote_salprojmng_table_helper.RECID] = salprojmng_table_data.get_RECID() ?: ""

            val all_map = salprojmng_table_data.to_hashmap()
            normalize_hashmap(all_map, typemap)
            for (item in map)
                all_map[item.key] = item.value

            var query = remote_SQL_Helper.construct_update_str_multiwhere_text(remote_salprojmng_table_helper.DATABASE_NAME, remote_salprojmng_table_helper.TABLE_NAME, where_clause, "varchar", map, all_map)
            query = query.replace("'", "&quote;")
            val str = offline_mode_service.general_push_command(query, remote_SQL_Helper.getusername())
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
        }

    }
}