@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_projects_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.local_SQL_Helper
import com.example.chaosruler.msa_manager.abstraction_classes.syncable
import com.example.chaosruler.msa_manager.object_types.project_data
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper
import java.util.*
import kotlin.collections.HashMap

/**
 * implenting the SQL helper on projects database (SQLITE)
 * @author Chaosruler972
 * @constructor a context to work with, the rest comes from strings.xml
 */
class local_projects_table_helper(
        /**
         * The context we are working with
         * @author Chaosruler972
         */
        private var context: Context
) : local_SQL_Helper(context, context.getString(R.string.LOCAL_SYNC_DATABASE_NAME), null, context.resources.getInteger(R.integer.LOCAL_PROJECTS_TABLE_VERSION), context.getString(R.string.LOCAL_PROJECTS_TABLE_NAME)), syncable
{
    /**
     * the project id field name
     * @author Chaosruler972
     */
    val ID = context.getString(R.string.LOCAL_PROJECTS_COLUMN_ID)!!
    /**
     * the project name field name
     * @author Chaosruler972
     */
    var NAME = context.getString(R.string.LOCAL_PROJECTS_COLUMN_NAME)
    /**
     * the dataaraeid field name
     * @author Chaosruler972
     */
    var DATAAREAID = context.getString(R.string.LOCAL_PROJECTS_COLUMN_DATAARAEID)!!
    /**
     * the username field name
     * @author Chaosruler972
     */
    private var USERNAME = context.getString(R.string.LOCAL_PROJECTS_COLUMN_USERNAME)

    override var filtering_mz11_enabled: Boolean = context.resources.getBoolean(R.bool.filtering_mz11_enabled)


    override var USER = context.getString(R.string.LOCAL_PROJECTS_COLUMN_USERNAME)!!

    override var REMOTE_DATABASE_NAME: String = context.getString(R.string.DATABASE_NAME)
    override var REMOTE_TABLE_NAME: String = context.getString(R.string.TABLE_PROJECTS)
    override var REMOTE_DATAARAEID_KEY: String = context.getString(R.string.PROJECTS_DATAAREAID)
    override var REMOTE_DATAARAEID_VAL: String = context.getString(R.string.DATAAREAID_DEVELOP)

    override fun get_remote_typemap(): HashMap<String, String> = remote_projects_table_helper.define_type_map()


    override fun hashmap_to_table_dataclass_local(hashMap: HashMap<String, String>): project_data {
        return project_data(hashMap[ID] ?: "".trim(), (hashMap[NAME]
                ?: "").trim(), (hashMap[DATAAREAID] ?: "").trim(), (hashMap[USERNAME] ?: "").trim())
    }

    override fun hashmap_to_table_dataclass_remote(hashMap: HashMap<String, String>): project_data {
        return project_data((hashMap[remote_projects_table_helper.ID] ?: "").trim(),
                (hashMap[remote_projects_table_helper.NAME]
                        ?: "").trim(), (hashMap[remote_projects_table_helper.DATAAREAID]
                ?: "").trim(),
                remote_SQL_Helper.getusername().trim())
    }

    /**
     *    MUST BE CALLED, it reports to the database about the table schema, is used by the abstracted
     * SQL class
     * @author Chaosruler972
     */
    init
    {
        val vector: Vector<String> = Vector()
        vector.add(ID)
        vector.add(NAME)
        vector.add(DATAAREAID)
        vector.add(USERNAME)
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
        map[ID] = "TEXT  "
        map[NAME] = "TEXT"
        map[DATAAREAID] = "TEXT"
        map[USERNAME] = "TEXT"
        val extra = " PRIMARY KEY($ID, $USER) "
        createDB(db,map, HashMap(), extra)
    }




}