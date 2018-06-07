@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_salprojluz_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.local_SQL_Helper
import com.example.chaosruler.msa_manager.abstraction_classes.syncable
import com.example.chaosruler.msa_manager.object_types.salprojluz_data
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper
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
        context.getString(R.string.LOCAL_SYNC_DATABASE_NAME),
        null,
        context.resources.getInteger(R.integer.LOCAL_SALPROJLUZ_TABLE_VERSION),
        context.getString(R.string.LOCAL_SALPROJLUZ_TABLE_NAME)
), syncable
{
    /**
     * ID
     * @author Chaosruler972
     */
    val ID = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_ID)!!
    /**
     * startdate
     * @author Chaosruler972
     */
    val STARTDATE = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_STARTDATE)
    /**
     * finish date
     * @author Chaosruler972
     */
    val FINISHDATE = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_FINISHDATE)
    /**
     * sium bpoal
     * @author Chaosruler972
     */
    val SIUMBPOAL = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_SIUMBPOAL)
    /**
     * is finished
     * @author Chaosruler972
     */
    val IS_FINISHED = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_ISFINISHED)
    /**
     * notes
     * @author Chaosruler972
     */
    val NOTES = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_NOTES)
    /**
     * koma
     * @author Chaosruler972
     */
    val KOMA = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_KOMA)
    /**
     * building
     * @author Chaosruler972
     */
    val BUILDING = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_BUILDING)

    /**
     * Percent executed
     * @author Chaosruler972
     */
    val PERCENTEXC = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_PERCENTEXC)

    /**
     * dataaraeid
     * @author Chaosruler972
     */
    val DATAARAEID = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_DATAARAEID)

    /**
     * recid
     * @author Chaosruler972
     */
    val RECID = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_RECID)

    /**
     * recversion
     * @author Chaosruler972
     */
    val RECVERSION = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_RECVERSION)

    /**
     * username
     * @author Chaosruler972
     */
    val USERNAME = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_USERNAME)

    override var filtering_mz11_enabled: Boolean = context.resources.getBoolean(R.bool.filtering_mz11_enabled)

    override var USER: String = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_USERNAME)

    override var REMOTE_DATABASE_NAME: String = context.getString(R.string.DATABASE_NAME)
    override var REMOTE_TABLE_NAME: String = context.getString(R.string.TABLE_SALPROJLUZ)
    override var REMOTE_DATAARAEID_KEY: String = context.getString(R.string.TABLE_SALPROJLUZ_DATAAREAID)
    override var REMOTE_DATAARAEID_VAL: String = context.getString(R.string.DATAAREAID_DEVELOP)

    override fun get_remote_typemap(): HashMap<String, String> = remote_salprojluz_table_helper.define_type_map()


    override fun hashmap_to_table_dataclass_local(hashMap: HashMap<String, String>): salprojluz_data {
        return salprojluz_data(
                hashMap[ID] ?: "", hashMap[STARTDATE] ?: "", hashMap[FINISHDATE] ?: "",
                ((hashMap[IS_FINISHED] ?: "0") == "0"), hashMap[SIUMBPOAL] ?: "", hashMap[NOTES]
                ?: "",
                hashMap[KOMA] ?: "", hashMap[BUILDING] ?: "", hashMap[PERCENTEXC] ?: "",
                hashMap[DATAARAEID] ?: "", hashMap[RECID] ?: "", hashMap[RECVERSION]
                ?: "", hashMap[USERNAME] ?: ""
        )
    }

    override fun hashmap_to_table_dataclass_remote(hashMap: HashMap<String, String>): salprojluz_data {
        return salprojluz_data(
                (hashMap[remote_salprojluz_table_helper.ID] ?: ""),
                (hashMap[remote_salprojluz_table_helper.STARTDATE] ?: ""),
                (hashMap[remote_salprojluz_table_helper.FINISHDATE] ?: ""),
                ((hashMap[remote_salprojluz_table_helper.IS_FINISHED] ?: "") == "true"),
                (hashMap[remote_salprojluz_table_helper.SIUMBPOAL] ?: ""),
                (hashMap[remote_salprojluz_table_helper.NOTES] ?: ""),
                (hashMap[remote_salprojluz_table_helper.KOMA] ?: ""),
                (hashMap[remote_salprojluz_table_helper.BUILDING] ?: ""),
                (hashMap[remote_salprojluz_table_helper.PERCENTEXC] ?: ""),
                (hashMap[remote_salprojluz_table_helper.DATAAREAID] ?: ""),
                (hashMap[remote_salprojluz_table_helper.RECID] ?: ""),
                (hashMap[remote_salprojluz_table_helper.RECVERION] ?: ""),
                remote_SQL_Helper.getusername()
        )
    }

    init {
        val vector: Vector<String> = Vector()
        vector.add(ID)
        vector.add(STARTDATE)
        vector.add(FINISHDATE)
        vector.add(SIUMBPOAL)
        vector.add(IS_FINISHED)
        vector.add(NOTES)
        vector.add(KOMA)
        vector.add(BUILDING)
        vector.add(DATAARAEID)
        vector.add(USERNAME)
        vector.add(PERCENTEXC)
        vector.add(RECID)
        vector.addElement(RECVERSION)
        init_vector_of_variables(vector)
    }

    /**
     * provides info for the abstracted SQL class
     * on what the table schema is for creation
     * @author Chaosruler972
     * @param db an instance of database
     */
    override fun onCreate(db: SQLiteDatabase) {
        val map: HashMap<String, String> = HashMap()
        map[ID] = "TEXT "
        map[STARTDATE] = "TEXT"
        map[FINISHDATE] = "TEXT"
        map[SIUMBPOAL] = "TEXT"
        map[IS_FINISHED] = "INTEGER"
        map[NOTES] = "TEXT"
        map[KOMA] = "TEXT"
        map[BUILDING] = "TEXT"
        map[DATAARAEID] = "TEXT"
        map[USERNAME] = "TEXT"
        map[PERCENTEXC] = "TEXT"
        map[RECID] = "TEXT "
        map[RECVERSION] = "TEXT"
        val extra = " PRIMARY KEY($RECID, $USER) "
        createDB(db, map, HashMap(), extra)
    }

}