@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_salprojluz_table_helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_salprojluz_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.local_SQL_Helper
import com.example.chaosruler.msa_manager.abstraction_classes.remote_helper
import com.example.chaosruler.msa_manager.abstraction_classes.syncable
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass_hashmap_createable
import com.example.chaosruler.msa_manager.object_types.salprojluz_data.salprojluz_builder
import java.util.*
import kotlin.collections.HashMap


/**
 * Local salproijluz table helper
 * @author Chaosruler972
 */
class local_salprojluz_table_helper(
        /**
         * The context we are working with
         * @author Chaosruler972
         */
        private var context: Context
) : local_SQL_Helper(context,
        context.getString(R.string.LOCAL_SYNC_SALPROJLUZ_DB__NAME),
        null,
        context.resources.getInteger(R.integer.LOCAL_SALPROJLUZ_TABLE_VERSION),
        context.getString(R.string.LOCAL_SALPROJLUZ_TABLE_NAME),
        create_vector_of_variables(context)
), syncable
{
    override var filtering_mz11_enabled: Boolean = context.resources.getBoolean(R.bool.filtering_mz11_enabled)

    override var USER: String = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_USERNAME)

    override var REMOTE_DATABASE_NAME: String = context.getString(R.string.DATABASE_NAME)
    override var REMOTE_TABLE_NAME: String = context.getString(R.string.TABLE_SALPROJLUZ)
    override var REMOTE_DATAARAEID_KEY: String = context.getString(R.string.TABLE_SALPROJLUZ_DATAAREAID)
    override var REMOTE_DATAARAEID_VAL: String = context.getString(R.string.DATAAREAID_DEVELOP)

    override var remote_sql_helper: remote_helper = remote_salprojluz_table_helper

    override var builder: table_dataclass_hashmap_createable = salprojluz_builder

    override fun SPECIAL_SEARCH_COLUMN(): String = context.getString(R.string.TABLE_SALPROJLUZ_PROJID)


    /**
     * provides info for the abstracted SQL class
     * on what the table schema is for creation
     * @author Chaosruler972
     * @param db an instance of database
     */
    override fun onCreate(db: SQLiteDatabase) {
        val map: HashMap<String, String> = HashMap()
        val type = context.getString(R.string.SQLITE_VAL_TYPE)
        map[hashmap_of_variables[local_salprojluz_enum.ID]!!] = "$type "
        map[hashmap_of_variables[local_salprojluz_enum.STARTDATE]!!] = "$type"
        map[hashmap_of_variables[local_salprojluz_enum.FINISHDATE]!!] = "$type"
        map[hashmap_of_variables[local_salprojluz_enum.SIUMBPOAL]!!] = "$type"
        map[hashmap_of_variables[local_salprojluz_enum.IS_FINISHED]!!] = "$type"
        map[hashmap_of_variables[local_salprojluz_enum.NOTES]!!] = "$type"
        map[hashmap_of_variables[local_salprojluz_enum.KOMA]!!] = "$type"
        map[hashmap_of_variables[local_salprojluz_enum.BUILDING]!!] = "$type"
        map[hashmap_of_variables[local_salprojluz_enum.DATAARAEID]!!] = "$type"
        map[hashmap_of_variables[local_salprojluz_enum.USERNAME]!!] = "$type"
        map[hashmap_of_variables[local_salprojluz_enum.PERCENTEXC]!!] = "$type"
        map[hashmap_of_variables[local_salprojluz_enum.RECID]!!] = "$type PRIMARY KEY"
        map[hashmap_of_variables[local_salprojluz_enum.RECVERSION]!!] = "$type"
//        val extra = " PRIMARY KEY(${hashmap_of_variables[local_salprojluz_enum.RECID]!!}, ${hashmap_of_variables[local_salprojluz_enum.USERNAME]!!}) "
//        createDB(db, map, HashMap(), extra)
        createDB(db, map)
    }

    companion object vector_of_variables_maker{
        fun create_vector_of_variables(context: Context): HashMap<Int, String> {

            /**
             * ID
             * @author Chaosruler972
             */
            val ID = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_ID)!!
            /**
             * startdate
             * @author Chaosruler972
             */
            val STARTDATE = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_STARTDATE)!!
            /**
             * finish date
             * @author Chaosruler972
             */
            val FINISHDATE = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_FINISHDATE)!!
            /**
             * sium bpoal
             * @author Chaosruler972
             */
            val SIUMBPOAL = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_SIUMBPOAL)!!
            /**
             * is finished
             * @author Chaosruler972
             */
            val IS_FINISHED = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_ISFINISHED)!!
            /**
             * notes
             * @author Chaosruler972
             */
            val NOTES = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_NOTES)!!
            /**
             * koma
             * @author Chaosruler972
             */
            val KOMA = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_KOMA)!!
            /**
             * building
             * @author Chaosruler972
             */
            val BUILDING = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_BUILDING)!!

            /**
             * Percent executed
             * @author Chaosruler972
             */
            val PERCENTEXC = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_PERCENTEXC)!!

            /**
             * dataaraeid
             * @author Chaosruler972
             */
            val DATAARAEID = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_DATAARAEID)!!

            /**
             * recid
             * @author Chaosruler972
             */
            val RECID = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_RECID)!!

            /**
             * recversion
             * @author Chaosruler972
             */
            val RECVERSION = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_RECVERSION)!!

            /**
             * username
             * @author Chaosruler972
             */
            val USERNAME = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_USERNAME)!!
            val hashmap_of_variables = HashMap<Int, String>()

            hashmap_of_variables[local_salprojluz_enum.BUILDING] = BUILDING
            hashmap_of_variables[local_salprojluz_enum.DATAARAEID] = DATAARAEID
            hashmap_of_variables[local_salprojluz_enum.FINISHDATE] = FINISHDATE
            hashmap_of_variables[local_salprojluz_enum.ID] = ID
            hashmap_of_variables[local_salprojluz_enum.IS_FINISHED] = IS_FINISHED
            hashmap_of_variables[local_salprojluz_enum.KOMA] = KOMA
            hashmap_of_variables[local_salprojluz_enum.NOTES] = NOTES
            hashmap_of_variables[local_salprojluz_enum.PERCENTEXC] = PERCENTEXC
            hashmap_of_variables[local_salprojluz_enum.RECID] = RECID
            hashmap_of_variables[local_salprojluz_enum.RECVERSION] = RECVERSION
            hashmap_of_variables[local_salprojluz_enum.USERNAME] = USERNAME
            hashmap_of_variables[local_salprojluz_enum.STARTDATE] = STARTDATE
            hashmap_of_variables[local_salprojluz_enum.SIUMBPOAL] = SIUMBPOAL
            return hashmap_of_variables
        }
    }
}