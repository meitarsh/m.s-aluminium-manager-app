@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_takala_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.local_SQL_Helper
import com.example.chaosruler.msa_manager.abstraction_classes.syncable
import com.example.chaosruler.msa_manager.object_types.takala_data
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper
import java.util.*
import kotlin.collections.HashMap

class local_salprojtakala_table_helper(private var context: Context) :
        local_SQL_Helper(context,
                context.getString(R.string.LOCAL_SYNC_DATABASE_NAME),
                null,
                context.resources.getInteger(R.integer.LOCAL_SALPROJBAKARA_TABLE_VERSION),
                context.getString(R.string.LOCAL_SALPROJBAKARA_TABLE_NAME)), syncable {
    /**
     * ID
     * @author Chaosruler972
     */
    val ID = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_ID)!!
    /**
     * ITEMID
     * @author Chaosruler972
     */
    val ITEMID = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_ITEMID)

    /**
     * DATAAREAID
     * @author Chaosruler972
     */
    val DATAAREAID = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_DATAAREAID)

    /**
     * QTY
     * @author Chaosruler972
     */
    val QTY = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_QTY)

    /**
     * KOMA
     * @author Chaosruler972
     */
    val KOMA = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_KOMA)

    /**
     * BINYAN
     * @author Chaosruler972
     */
    val BINYAN = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_BINYAN)

    /**
     * DIRA
     * @author Chaosruler972
     */
    val DIRA = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_DIRA)

    /**
     * TEUR
     * @author Chaosruler972
     */
    val TEUR = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_TEUR)

    /**
     * MUMLATZ
     * @author Chaosruler972
     */
    val MUMLATZ = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_MUMLATZ)

    /**
     * MONAAT
     * @author Chaosruler972
     */
    val MONAAT = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_MONAAT)

    /**
     * TGUVA
     * @author Chaosruler972
     */
    val TGUVA = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_TGUVA)

    /**
     * SUG
     * @author Chaosruler972
     */
    val SUG = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_SUG)

    /**
     * ALUT
     * @author Chaosruler972
     */
    val ALUT = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_ALUT)

    /**
     * ITEMTXT
     * @author Chaosruler972
     */
    val ITEMTXT = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_ITEMTXT)

    /**
     * RECVERSION
     * @author Chaosruler972
     */
    val RECVERSION = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_RECVERSION)

    /**
     * RECID
     * @author Chaosruler972
     */
    val RECID = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_RECID)

    /**
     * USERNAME
     * @author Chaosruler972
     */
    val USERNAME = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_USERNAME)

    override var filtering_mz11_enabled: Boolean = context.resources.getBoolean(R.bool.filtering_mz11_enabled)

    override var USER: String = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_USERNAME)

    override var REMOTE_DATABASE_NAME: String = context.getString(R.string.DATABASE_NAME)
    override var REMOTE_TABLE_NAME: String = context.getString(R.string.TABLE_SALPROJBAKARA)
    override var REMOTE_DATAARAEID_KEY: String = context.getString(R.string.TABLE_SALPROJBAKARA_DATAAREAID)
    override var REMOTE_DATAARAEID_VAL: String = context.getString(R.string.DATAAREAID_DEVELOP)

    override fun get_remote_typemap(): HashMap<String, String> = remote_takala_table_helper.define_type_map()


    override fun hashmap_to_table_dataclass_local(hashMap: HashMap<String, String>): takala_data {
        return takala_data(
                hashMap[ID] ?: "",
                hashMap[ITEMID] ?: "",
                hashMap[DATAAREAID] ?: "",
                hashMap[QTY] ?: "",
                hashMap[KOMA] ?: "",
                hashMap[BINYAN] ?: "",
                hashMap[DIRA] ?: "",
                hashMap[TEUR] ?: "",
                hashMap[MUMLATZ] ?: "",
                hashMap[MONAAT] ?: "",
                hashMap[TGUVA] ?: "",
                hashMap[SUG] ?: "",
                hashMap[ALUT] ?: "",
                hashMap[ITEMTXT] ?: "",
                hashMap[RECVERSION] ?: "",
                hashMap[RECID] ?: "",
                remote_SQL_Helper.getusername()
        )
    }

    override fun hashmap_to_table_dataclass_remote(hashMap: HashMap<String, String>): takala_data {
        return takala_data(
                (hashMap[remote_takala_table_helper.ID] ?: "").trim(),
                (hashMap[remote_takala_table_helper.ITEMID] ?: "").trim(),
                (hashMap[remote_takala_table_helper.DATAAREAID] ?: "").trim(),
                (hashMap[remote_takala_table_helper.QTY] ?: "").trim(),
                (hashMap[remote_takala_table_helper.KOMA] ?: "").trim(),
                (hashMap[remote_takala_table_helper.BINYAN] ?: "").trim(),
                (hashMap[remote_takala_table_helper.DIRA] ?: "").trim(),
                (hashMap[remote_takala_table_helper.TEUR] ?: "").trim(),
                (hashMap[remote_takala_table_helper.MUMLATZ] ?: "").trim(),
                (hashMap[remote_takala_table_helper.MONAAT] ?: "").trim(),
                (hashMap[remote_takala_table_helper.TGUVA] ?: "").trim(),
                (hashMap[remote_takala_table_helper.SUG] ?: "").trim(),
                (hashMap[remote_takala_table_helper.ALUT] ?: "").trim(),
                (hashMap[remote_takala_table_helper.ITEMTXT] ?: "").trim(),
                (hashMap[remote_takala_table_helper.RECVERSION] ?: "").trim(),
                (hashMap[remote_takala_table_helper.RECID] ?: "").trim(),
                remote_SQL_Helper.getusername())
    }

    init {
        val vector: Vector<String> = Vector()
        vector.add(ID)
        vector.add(ITEMID)
        vector.add(DATAAREAID)
        vector.add(QTY)
        vector.add(KOMA)
        vector.add(BINYAN)
        vector.add(DIRA)
        vector.add(TEUR)
        vector.add(MUMLATZ)
        vector.add(MONAAT)
        vector.add(TGUVA)
        vector.add(SUG)
        vector.add(ALUT)
        vector.add(ITEMTXT)
        vector.add(RECVERSION)
        vector.add(RECID)
        vector.add(USERNAME)
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
        map[ITEMID] = "TEXT "
        map[DATAAREAID] = "TEXT"
        map[QTY] = "TEXT"
        map[KOMA] = "INTEGER"
        map[BINYAN] = "TEXT"
        map[KOMA] = "TEXT"
        map[DIRA] = "TEXT"
        map[TEUR] = "TEXT"
        map[MUMLATZ] = "TEXT"
        map[MONAAT] = "TEXT"
        map[TGUVA] = "TEXT"
        map[SUG] = "TEXT"
        map[ALUT] = "TEXT"
        map[ITEMTXT] = "TEXT"
        map[RECVERSION] = "TEXT"
        map[RECID] = "TEXT PRIMARY KEY"
        map[USERNAME] = "TEXT"
//        val empty_hashmap = HashMap<String, String>()
//        val extra = " , PRIMARY KEY ($ID, $ITEMID) "
        createDB(db, map)
    }

}