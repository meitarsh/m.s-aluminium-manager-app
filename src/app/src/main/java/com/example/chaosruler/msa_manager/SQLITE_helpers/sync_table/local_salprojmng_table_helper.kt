package com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_salprojmng_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.local_SQL_Helper
import com.example.chaosruler.msa_manager.abstraction_classes.remote_helper
import com.example.chaosruler.msa_manager.abstraction_classes.syncable
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass_hashmap_createable
import com.example.chaosruler.msa_manager.object_types.salprojmng_table_data.salprojmng_builder
import java.util.*

class local_salprojmng_table_helper(private var context: Context):
        local_SQL_Helper(context, context.getString(R.string.LOCAL_SYNC_DATABASE_NAME), null, context.resources.getInteger(R.integer.LOCAL_SALPROJMANG_TABLE_VERSION), context.getString(R.string.LOCAL_SALPROJMANG_TABLE_NAME)), syncable
{
    /**
     * The id field name
     * @author Chaosruler972
     */
    val PROJID: String = context.getString(R.string.LOCAL_SALPROJMNG_COLUMN_PROJID)
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

    override var USER: String = context.getString(R.string.LOCAL_SALPROJMNG_COLUMN_USERNAME)

    override var filtering_mz11_enabled: Boolean = context.resources.getBoolean(R.bool.filtering_mz11_enabled)

    override var REMOTE_DATABASE_NAME: String = context.getString(R.string.DATABASE_NAME)
    override var REMOTE_TABLE_NAME: String = context.getString(R.string.TABLE_SALPROJMNG)
    override var REMOTE_DATAARAEID_KEY: String = context.getString(R.string.TABLE_SALPROJMNG_DATAAREAID)
    override var REMOTE_DATAARAEID_VAL: String = context.getString(R.string.DATAAREAID_DEVELOP)

    override var remote_sql_helper: remote_helper = remote_salprojmng_table_helper

    override var builder: table_dataclass_hashmap_createable = salprojmng_builder

    /**
     *    MUST BE CALLED, it reports to the database about the table schema, is used by the abstracted
     * SQL class
     * @author Chaosruler972
     */
    init
    {
        val vector: Vector<String> = Vector()
        vector.add(PROJID)
        vector.add(USERID)
        vector.add(DATAARAEID)
        vector.addElement(RECVERSION)
        vector.addElement(RECID)
        vector.add(USER)
        init_vector_of_variables(vector)

    }

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
        map[PROJID] = "$type "
        map[USERID] = "$type"
        map[USER] = "$type"
        map[RECVERSION] = "$type"
        map[RECID] = "$type PRIMARY KEY"
        map[DATAARAEID] = "$type"
        val extra = " PRIMARY KEY($RECID, $USER) "
//        createDB(db,map, HashMap(), extra)
        createDB(db, map)
    }
}