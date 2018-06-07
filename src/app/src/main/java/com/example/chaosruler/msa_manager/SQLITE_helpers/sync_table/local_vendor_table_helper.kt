@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_vendors_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.local_SQL_Helper
import com.example.chaosruler.msa_manager.abstraction_classes.syncable
import com.example.chaosruler.msa_manager.object_types.vendor_data
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper
import java.util.*
import kotlin.collections.HashMap

/**
 * implenting the SQL helper on projects database (SQLITE)
 * @author Chaosruler972
 * @constructor a context to work with, the rest comes from strings.xml
 */
class local_vendor_table_helper(
        /**
         * The context we are working with
         * @author Chaosruler972
         */
        private var context: Context
) : local_SQL_Helper(context, context.getString(R.string.LOCAL_SYNC_DATABASE_NAME), null, context.resources.getInteger(R.integer.LOCAL_VENDORS_TABLE_VERSION), context.getString(R.string.LOCAL_VENDORS_TABLE_NAME)), syncable {
    /**
     * the vendor id field name
     * @author Chaosruler972
     */
    val ID: String = context.getString(R.string.LOCAL_VENDORS_COLUMN_ID)
    /**
     * The vendor name field name
     * @author Chaosruler972
     */
    val NAME: String = context.getString(R.string.LOCAL_VENDORS_COLUMN_NAME)
    /**
     * the dataaraeid field name
     * @author Chaosruler972
     */
    val DATAARAEID: String = context.getString(R.string.LOCAL_VENDORS_COLUMN_DATAARAEID)
    /**
     * The username field name
     * @author Chaosruler972
     */

    override var filtering_mz11_enabled: Boolean = context.resources.getBoolean(R.bool.filtering_mz11_enabled)

    override var USER: String = context.getString(R.string.LOCAL_VENDORS_COLUMN_USERNAME)

    override var REMOTE_DATABASE_NAME: String = context.getString(R.string.DATABASE_NAME)
    override var REMOTE_TABLE_NAME: String = context.getString(R.string.TABLE_VENDORS)
    override var REMOTE_DATAARAEID_KEY: String = context.getString(R.string.VENDORS_DATAAREAID)
    override var REMOTE_DATAARAEID_VAL: String = context.getString(R.string.DATAAREAID_DEVELOP)

    override fun get_remote_typemap(): HashMap<String, String> = remote_vendors_table_helper.define_type_map()


    override fun hashmap_to_table_dataclass_local(hashMap: HashMap<String, String>): vendor_data {
        return vendor_data((hashMap[ID] ?: "").trim(), (hashMap[NAME]
                ?: "").trim(), (hashMap[DATAARAEID] ?: "").trim(), (hashMap[USER] ?: "").trim())
    }

    override fun hashmap_to_table_dataclass_remote(hashMap: HashMap<String, String>): vendor_data {
        return vendor_data((hashMap[remote_vendors_table_helper.ID] ?: "").trim(),
                (hashMap[remote_vendors_table_helper.NAME]
                        ?: "").trim(), (hashMap[remote_vendors_table_helper.DATAAREAID]
                ?: "").trim(),
                remote_SQL_Helper.getusername().trim())
    }

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
        map[ID] = "TEXT PRIMARY KEY"
        map[NAME] = "TEXT"
        map[USER] = "TEXT"
        map[DATAARAEID] = "TEXT"
        createDB(db, map)
    }

}