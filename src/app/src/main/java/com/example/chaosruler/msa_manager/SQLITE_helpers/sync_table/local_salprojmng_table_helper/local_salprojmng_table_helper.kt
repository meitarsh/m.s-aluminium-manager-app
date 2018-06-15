package com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_salprojmng_table_helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_salprojmng_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_big_table_helper.local_big_enum
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_salprojluz_table_helper.local_salprojluz_enum
import com.example.chaosruler.msa_manager.abstraction_classes.local_SQL_Helper
import com.example.chaosruler.msa_manager.abstraction_classes.remote_helper
import com.example.chaosruler.msa_manager.abstraction_classes.syncable
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass_hashmap_createable
import com.example.chaosruler.msa_manager.object_types.salprojmng_table_data.salprojmng_builder
import java.util.*
import kotlin.collections.HashMap

class local_salprojmng_table_helper(private var context: Context):
        local_SQL_Helper(context, context.getString(R.string.LOCAL_SYNC_SALPROJMNG_DB__NAME), null, context.resources.getInteger(R.integer.LOCAL_SALPROJMANG_TABLE_VERSION), context.getString(R.string.LOCAL_SALPROJMANG_TABLE_NAME), create_vector_of_variables(context)), syncable
{

    override var USER: String = context.getString(R.string.LOCAL_SALPROJMNG_COLUMN_USERNAME)

    override var filtering_mz11_enabled: Boolean = context.resources.getBoolean(R.bool.filtering_mz11_enabled)

    override var REMOTE_DATABASE_NAME: String = context.getString(R.string.DATABASE_NAME)
    override var REMOTE_TABLE_NAME: String = context.getString(R.string.TABLE_SALPROJMNG)
    override var REMOTE_DATAARAEID_KEY: String = context.getString(R.string.TABLE_SALPROJMNG_DATAAREAID)
    override var REMOTE_DATAARAEID_VAL: String = context.getString(R.string.DATAAREAID_DEVELOP)

    override var remote_sql_helper: remote_helper = remote_salprojmng_table_helper

    override var builder: table_dataclass_hashmap_createable = salprojmng_builder

    override fun SPECIAL_SEARCH_COLUMN(): String = context.getString(R.string.TABLE_SALPROJMNG_USERID)


    /**
     * provides info for the abstracted SQL class
     * on what the table schema is for creation
     * @author Chaosruler972
     * @param db an instance of database
     */
    override fun onCreate(db: SQLiteDatabase)
    {
        val map: HashMap<String, String> = HashMap()
        val type = context.getString(R.string.SQLITE_VAL_TYPE)
        map[hashmap_of_variables[local_salprojmng_enum.ID]!!] = "$type "
        map[hashmap_of_variables[local_salprojmng_enum.USERID]!!] = "$type"
        map[hashmap_of_variables[local_salprojmng_enum.USER]!!] = "$type"
        map[hashmap_of_variables[local_salprojmng_enum.RECVERSION]!!] = "$type"
        map[hashmap_of_variables[local_salprojmng_enum.RECID]!!] = "$type PRIMARY KEY"
        map[hashmap_of_variables[local_salprojmng_enum.DATAARAEID]!!] = "$type"
//        val extra = " PRIMARY KEY(${hashmap_of_variables[local_salprojmng_enum.RECID]!!}, ${hashmap_of_variables[local_salprojmng_enum.USER]!!}) "
//        createDB(db,map, HashMap(), extra)
        createDB(db, map)
    }

    companion object vector_of_variables_maker{
        fun create_vector_of_variables(context: Context): HashMap<Int, String> {

            /**
             * The id field name
             * @author Chaosruler972
             */
            val ID: String = context.getString(R.string.LOCAL_SALPROJMNG_COLUMN_PROJID)
            /**
             * The name field name
             * @author Chaosruler972
             */
            val USERID: String = context.getString(R.string.LOCAL_SALPROJMNG_COLUMN_USERID)
            /**
             * The dataaraeid field name
             * @author Chaosruler972
             */
            val DATAARAEID: String = context.getString(R.string.LOCAL_SALPROJMNG_COLUMN_DATAAREAID)
            /**
             * The RECVERSION field name
             * @author Chaosruler972
             */
            val RECVERSION: String = context.getString(R.string.LOCAL_SALPROJMNG_COLUMN_RECVERSION)
            /**
             * The RECID field name
             * @author Chaosruler972
             */
            val RECID: String = context.getString(R.string.LOCAL_SALPROJMNG_COLUMN_RECID)

            val USER: String = context.getString(R.string.LOCAL_SALPROJMNG_COLUMN_USERNAME)

            val hashmap_of_variables = HashMap<Int, String>()
            hashmap_of_variables[local_salprojmng_enum.ID] = ID
            hashmap_of_variables[local_salprojmng_enum.USERID] = USERID
            hashmap_of_variables[local_salprojmng_enum.DATAARAEID] = DATAARAEID
            hashmap_of_variables[local_salprojmng_enum.RECID] = RECID
            hashmap_of_variables[local_salprojmng_enum.RECVERSION] = RECVERSION
            hashmap_of_variables[local_salprojmng_enum.USER] = USER

            return hashmap_of_variables
        }
    }
}