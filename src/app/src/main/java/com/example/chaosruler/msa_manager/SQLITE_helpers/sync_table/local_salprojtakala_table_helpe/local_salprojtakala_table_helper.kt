@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_salprojtakala_table_helpe

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_takala_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.local_SQL_Helper
import com.example.chaosruler.msa_manager.abstraction_classes.remote_helper
import com.example.chaosruler.msa_manager.abstraction_classes.syncable
import com.example.chaosruler.msa_manager.abstraction_classes.table_dataclass_hashmap_createable
import com.example.chaosruler.msa_manager.object_types.takala_data.takala_builder
import java.util.*
import kotlin.collections.HashMap

class local_salprojtakala_table_helper(private var context: Context) :
        local_SQL_Helper(context,
                context.getString(R.string.LOCAL_SYNC_BAKARA_DB__NAME),
                null,
                context.resources.getInteger(R.integer.LOCAL_SALPROJBAKARA_TABLE_VERSION),
                context.getString(R.string.LOCAL_SALPROJBAKARA_TABLE_NAME),
                create_vector_of_variables(context)), syncable {
    override var filtering_mz11_enabled: Boolean = context.resources.getBoolean(R.bool.filtering_mz11_enabled)

    override var USER: String = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_USERNAME)

    override var REMOTE_DATABASE_NAME: String = context.getString(R.string.DATABASE_NAME)
    override var REMOTE_TABLE_NAME: String = context.getString(R.string.TABLE_SALPROJBAKARA)
    override var REMOTE_DATAARAEID_KEY: String = context.getString(R.string.TABLE_SALPROJBAKARA_DATAAREAID)
    override var REMOTE_DATAARAEID_VAL: String = context.getString(R.string.DATAAREAID_DEVELOP)

    override var remote_sql_helper: remote_helper = remote_takala_table_helper

    override var builder: table_dataclass_hashmap_createable = takala_builder

    override fun SPECIAL_SEARCH_COLUMN(): String = context.getString(R.string.TABLE_SALPROJBAKARA_PROJID)

    /**
     * provides info for the abstracted SQL class
     * on what the table schema is for creation
     * @author Chaosruler972
     * @param db an instance of database
     */
    override fun onCreate(db: SQLiteDatabase) {
        val map: HashMap<String, String> = HashMap()
        val type = context.getString(R.string.SQLITE_VAL_TYPE)
        map[hashmap_of_variables[local_salprojtakala_enum.ID]!!] = "$type "
        map[hashmap_of_variables[local_salprojtakala_enum.ITEMID]!!] = "$type "
        map[hashmap_of_variables[local_salprojtakala_enum.DATAAREAID]!!] = "$type"
        map[hashmap_of_variables[local_salprojtakala_enum.QTY]!!] = "$type"
        map[hashmap_of_variables[local_salprojtakala_enum.KOMA]!!] = "$type"
        map[hashmap_of_variables[local_salprojtakala_enum.BINYAN]!!] = "$type"
        map[hashmap_of_variables[local_salprojtakala_enum.KOMA]!!] = "$type"
        map[hashmap_of_variables[local_salprojtakala_enum.DIRA]!!] = "$type"
        map[hashmap_of_variables[local_salprojtakala_enum.TEUR]!!] = "$type"
        map[hashmap_of_variables[local_salprojtakala_enum.MUMLATZ]!!] = "$type"
        map[hashmap_of_variables[local_salprojtakala_enum.MONAAT]!!] = "$type"
        map[hashmap_of_variables[local_salprojtakala_enum.TGUVA]!!] = "$type"
        map[hashmap_of_variables[local_salprojtakala_enum.SUG]!!] = "$type"
        map[hashmap_of_variables[local_salprojtakala_enum.ALUT]!!] = "$type"
        map[hashmap_of_variables[local_salprojtakala_enum.ITEMTXT]!!] = "$type"
        map[hashmap_of_variables[local_salprojtakala_enum.RECVERSION]!!] = "$type"
        map[hashmap_of_variables[local_salprojtakala_enum.RECID]!!] = "$type PRIMARY KEY"
        map[hashmap_of_variables[local_salprojtakala_enum.USERNAME]!!] = "$type"
//        val empty_hashmap = HashMap<String, String>()
//        val extra = " , PRIMARY KEY (${hashmap_of_variables[local_salprojtakala_enum.RECID]!!}, ${hashmap_of_variables[local_salprojtakala_enum.USERNAME]!!}) "
//        createDB(db, map, empty_hashmap, extra)
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
             * ITEMID
             * @author Chaosruler972
             */
            val ITEMID = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_ITEMID)!!

            /**
             * DATAAREAID
             * @author Chaosruler972
             */
            val DATAAREAID = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_DATAAREAID)!!

            /**
             * QTY
             * @author Chaosruler972
             */
            val QTY = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_QTY)!!

            /**
             * KOMA
             * @author Chaosruler972
             */
            val KOMA = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_KOMA)!!

            /**
             * BINYAN
             * @author Chaosruler972
             */
            val BINYAN = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_BINYAN)!!

            /**
             * DIRA
             * @author Chaosruler972
             */
            val DIRA = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_DIRA)!!

            /**
             * TEUR
             * @author Chaosruler972
             */
            val TEUR = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_TEUR)!!

            /**
             * MUMLATZ
             * @author Chaosruler972
             */
            val MUMLATZ = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_MUMLATZ)!!

            /**
             * MONAAT
             * @author Chaosruler972
             */
            val MONAAT = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_MONAAT)!!

            /**
             * TGUVA
             * @author Chaosruler972
             */
            val TGUVA = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_TGUVA)!!

            /**
             * SUG
             * @author Chaosruler972
             */
            val SUG = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_SUG)!!

            /**
             * ALUT
             * @author Chaosruler972
             */
            val ALUT = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_ALUT)!!

            /**
             * ITEMTXT
             * @author Chaosruler972
             */
            val ITEMTXT = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_ITEMTXT)!!

            /**
             * RECVERSION
             * @author Chaosruler972
             */
            val RECVERSION = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_RECVERSION)!!

            /**
             * RECID
             * @author Chaosruler972
             */
            val RECID = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_RECID)!!

            /**
             * USERNAME
             * @author Chaosruler972
             */
            val USERNAME = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_USERNAME)!!
            val hashmap_of_variables = HashMap<Int, String>()
            hashmap_of_variables[local_salprojtakala_enum.ALUT] = ALUT
            hashmap_of_variables[local_salprojtakala_enum.BINYAN] = BINYAN
            hashmap_of_variables[local_salprojtakala_enum.DATAAREAID] = DATAAREAID
            hashmap_of_variables[local_salprojtakala_enum.DIRA] = DIRA
            hashmap_of_variables[local_salprojtakala_enum.ID] = ID
            hashmap_of_variables[local_salprojtakala_enum.ITEMTXT] = ITEMTXT
            hashmap_of_variables[local_salprojtakala_enum.KOMA] = KOMA
            hashmap_of_variables[local_salprojtakala_enum.RECID] = RECID
            hashmap_of_variables[local_salprojtakala_enum.RECVERSION] = RECVERSION
            hashmap_of_variables[local_salprojtakala_enum.USERNAME] = USERNAME
            hashmap_of_variables[local_salprojtakala_enum.QTY] = QTY
            hashmap_of_variables[local_salprojtakala_enum.TEUR] = TEUR
            hashmap_of_variables[local_salprojtakala_enum.ITEMID] = ITEMID
            hashmap_of_variables[local_salprojtakala_enum.MUMLATZ] = MUMLATZ
            hashmap_of_variables[local_salprojtakala_enum.MONAAT] = MONAAT
            hashmap_of_variables[local_salprojtakala_enum.SUG] = SUG
            hashmap_of_variables[local_salprojtakala_enum.TGUVA] = TGUVA
            return hashmap_of_variables
        }
    }

}