@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_opr_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.local_SQL_Helper
import com.example.chaosruler.msa_manager.abstraction_classes.remote_helper
import com.example.chaosruler.msa_manager.abstraction_classes.syncable
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass_hashmap_createable
import com.example.chaosruler.msa_manager.object_types.opr_data.opr_builder
import java.util.*
import kotlin.collections.HashMap

/**
 * implenting the SQL helper on operation database (SQLITE)
 * @author Chaosruler972
 * @constructor a context to work with, the rest comes from strings.xml
 */
class local_OPR_table_helper(
        /**
         * The context we are working with
         * @author Chaosruler972
         */
        private var context: Context
) : local_SQL_Helper(context, context.getString(R.string.LOCAL_SYNC_DATABASE_NAME), null, context.resources.getInteger(R.integer.LOCAL_OPR_TABLE_VERSION), context.getString(R.string.LOCAL_OPR_TABLE_NAME)), syncable {


    /**
     * The operation id field name
     * @author Chaosruler972
     */
    val ID: String = context.getString(R.string.LOCAL_OPR_COLUMN_ID)
    /**
     * the operation name field name
     * @author Chaosruler972
     */
    val NAME: String = context.getString(R.string.LOCAL_OPR_COLUMN_NAME)
    /**
     * the dataaraeid field name
     * @author Chaosruler972
     */
    val DATAARAEID: String = context.getString(R.string.LOCAL_OPR_COLUMN_DATAARAEID)
    /**
     * The username that synced this data
     * @author Chaosruler972
     */

    override var filtering_mz11_enabled: Boolean = context.resources.getBoolean(R.bool.filtering_mz11_enabled)

    override var USER: String = context.getString(R.string.LOCAL_OPR_COLUMN_USERNAME)

    override var REMOTE_DATABASE_NAME: String = context.getString(R.string.DATABASE_NAME)
    override var REMOTE_TABLE_NAME: String = context.getString(R.string.TABLE_OPR)
    override var REMOTE_DATAARAEID_KEY: String = context.getString(R.string.OPR_DATAAREAID)
    override var REMOTE_DATAARAEID_VAL: String = context.getString(R.string.DATAAREAID_DEVELOP)

    override var remote_sql_helper: remote_helper = remote_opr_table_helper

    override var builder: table_dataclass_hashmap_createable = opr_builder

    /**
     *    MUST BE CALLED, it reports to the database about the table schema, is used by the abstracted
     * SQL class
     * @author Chaosruler972
     */
    init {
        val vector: Vector<String> = Vector()
        vector.add(ID)
        vector.add(NAME)
        vector.add(DATAARAEID)
        vector.add(USER)
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
        map[NAME] = "TEXT"
        map[USER] = "TEXT"
        map[DATAARAEID] = "TEXT"
        val extra = " PRIMARY KEY($ID, $USER) "
        createDB(db, map, HashMap(), extra)
    }

}