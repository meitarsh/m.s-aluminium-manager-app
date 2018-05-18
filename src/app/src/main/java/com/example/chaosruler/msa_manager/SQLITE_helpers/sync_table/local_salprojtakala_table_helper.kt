@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.chaosruler.msa_manager.BuildConfig
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_salprojluz_table_helper
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_takala_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.local_SQL_Helper
import com.example.chaosruler.msa_manager.object_types.takala_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper
import java.util.*
import kotlin.collections.HashMap

class local_salprojtakala_table_helper(private var context: Context) :
        local_SQL_Helper(context,
                context.getString(R.string.LOCAL_SYNC_DATABASE_NAME),
                null,
                context.resources.getInteger(R.integer.LOCAL_SALPROJBAKARA_TABLE_VERSION),
                context.getString(R.string.LOCAL_SALPROJBAKARA_TABLE_NAME)) {
    /**
     * ID
     * @author Chaosruler972
     */
    private val ID = context.getString(R.string.LOCAL_SALPROJLUZ_COLUMN_ID)
    /**
     * ITEMID
     * @author Chaosruler972
     */
    private val ITEMID = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_ITEMID)

    /**
     * DATAAREAID
     * @author Chaosruler972
     */
    private val DATAAREAID = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_DATAAREAID)

    /**
     * QTY
     * @author Chaosruler972
     */
    private val QTY = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_QTY)

    /**
     * KOMA
     * @author Chaosruler972
     */
    private val KOMA = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_KOMA)

    /**
     * BINYAN
     * @author Chaosruler972
     */
    private val BINYAN = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_BINYAN)

    /**
     * DIRA
     * @author Chaosruler972
     */
    private val DIRA = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_DIRA)

    /**
     * TEUR
     * @author Chaosruler972
     */
    private val TEUR = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_TEUR)

    /**
     * MUMLATZ
     * @author Chaosruler972
     */
    private val MUMLATZ = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_MUMLATZ)

    /**
     * MONAAT
     * @author Chaosruler972
     */
    private val MONAAT = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_MONAAT)

    /**
     * TGUVA
     * @author Chaosruler972
     */
    private val TGUVA = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_TGUVA)

    /**
     * SUG
     * @author Chaosruler972
     */
    private val SUG = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_SUG)

    /**
     * ALUT
     * @author Chaosruler972
     */
    private val ALUT = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_ALUT)

    /**
     * ITEMTXT
     * @author Chaosruler972
     */
    private val ITEMTXT = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_ITEMTXT)

    /**
     * RECVERSION
     * @author Chaosruler972
     */
    private val RECVERSION = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_RECVERSION)

    /**
     * RECID
     * @author Chaosruler972
     */
    private val RECID = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_RECID)

    /**
     * USERNAME
     * @author Chaosruler972
     */
    private val USERNAME = context.getString(R.string.LOCAL_SALPROJBAKARA_COLUMN_USERNAME)

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
        map[RECID] = "TEXT"
        map[USERNAME] = "TEXT"
//        val empty_hashmap = HashMap<String, String>()
//        val extra = " , PRIMARY KEY ($ID, $ITEMID) "
        createDB(db, map)
    }

    /**
     *  converts DB to vector of takala data
     *  @author Chaosruler972
     *  @return a vector of vendor table from local DB
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun get_local_DB(): Vector<takala_data> {
        Log.d("DB OF: ", "salprojtakala")
        val vector: Vector<takala_data> = Vector()

        val all_db: Vector<HashMap<String, String>> = get_db()
        all_db
                .filter {
                    @Suppress("USELESS_ELVIS_RIGHT_IS_NULL")
                    (it[USERNAME]
                            ?: null) != null && it[USERNAME] == remote_SQL_Helper.getusername()
                }
                .forEach {
                    vector.addElement(takala_data(
                            it[ID] ?: "",
                            it[ITEMID] ?: "",
                            it[DATAAREAID] ?: "",
                            it[QTY] ?: "",
                            it[KOMA] ?: "",
                            it[BINYAN] ?: "",
                            it[DIRA] ?: "",
                            it[TEUR] ?: "",
                            it[MUMLATZ] ?: "",
                            it[MONAAT] ?: "",
                            it[TGUVA] ?: "",
                            it[SUG] ?: "",
                            it[ALUT] ?: "",
                            it[ITEMTXT] ?: "",
                            it[RECVERSION] ?: "",
                            it[RECID] ?: "",
                            remote_SQL_Helper.getusername()
                    ))
                }
        return vector
    }

    /**
     *     subroutine to convert server data to vector of vendor data
     *  @author Chaosruler972
     *  @return a vector of vendor table from server DB
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun server_data_to_vector(): Vector<takala_data> {
        val typemap: HashMap<String, String> = remote_takala_table_helper.define_type_map()
        val server_data: Vector<java.util.HashMap<String, String>> =
                if (BuildConfig.DEBUG) {
                    remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_SALPROJBAKARA), typemap, context.getString(R.string.TABLE_SALPROJBAKARA_DATAAREAID), context.getString(R.string.DATAAREAID_DEVELOP))
                } else {
                    remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_SALPROJBAKARA), typemap, null, null)
                }

        val result_vector: Vector<takala_data> = Vector()
        server_data.mapTo(result_vector) {
            takala_data(
                    (it[remote_takala_table_helper.ID] ?: "").trim(),
                    (it[remote_takala_table_helper.ITEMID] ?: "").trim(),
                    (it[remote_takala_table_helper.DATAAREAID] ?: "").trim(),
                    (it[remote_takala_table_helper.QTY] ?: "").trim(),
                    (it[remote_takala_table_helper.KOMA] ?: "").trim(),
                    (it[remote_takala_table_helper.BINYAN] ?: "").trim(),
                    (it[remote_takala_table_helper.DIRA] ?: "").trim(),
                    (it[remote_takala_table_helper.TEUR] ?: "").trim(),
                    (it[remote_takala_table_helper.MUMLATZ] ?: "").trim(),
                    (it[remote_takala_table_helper.MONAAT] ?: "").trim(),
                    (it[remote_takala_table_helper.TGUVA] ?: "").trim(),
                    (it[remote_takala_table_helper.SUG] ?: "").trim(),
                    (it[remote_takala_table_helper.ALUT] ?: "").trim(),
                    (it[remote_takala_table_helper.ITEMTXT] ?: "").trim(),
                    (it[remote_takala_table_helper.RECVERSION] ?: "").trim(),
                    (it[remote_takala_table_helper.RECID] ?: "").trim(),
                    remote_SQL_Helper.getusername()
            )
        }
        return result_vector
    }

    /**
     * server data to vector... by projid
     * @author Chaosruler972
     * @return a vector of vendor table filtered by project name
     * @param projid the project id that represents the name of the project we want to filter
     */
    fun server_data_to_vector_by_projname(projid: String): Vector<takala_data> {
        val typemap: HashMap<String, String> = remote_takala_table_helper.define_type_map()
        val server_data_big: Vector<java.util.HashMap<String, String>> =
                if (BuildConfig.DEBUG) {
                    remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_SALPROJBAKARA), typemap, context.getString(R.string.TABLE_SALPROJBAKARA_DATAAREAID), context.getString(R.string.DATAAREAID_DEVELOP))
                } else {
                    remote_SQL_Helper.select_columns_from_db_with_where(context.getString(R.string.DATABASE_NAME), context.getString(R.string.TABLE_SALPROJBAKARA), typemap, remote_salprojluz_table_helper.ID, projid)
                }

        val result_vector: Vector<takala_data> = Vector()
        server_data_big.mapTo(result_vector) {
            takala_data(
                    (it[remote_takala_table_helper.ID] ?: "").trim(),
                    (it[remote_takala_table_helper.ITEMID] ?: "").trim(),
                    (it[remote_takala_table_helper.DATAAREAID] ?: "").trim(),
                    (it[remote_takala_table_helper.QTY] ?: "").trim(),
                    (it[remote_takala_table_helper.KOMA] ?: "").trim(),
                    (it[remote_takala_table_helper.BINYAN] ?: "").trim(),
                    (it[remote_takala_table_helper.DIRA] ?: "").trim(),
                    (it[remote_takala_table_helper.TEUR] ?: "").trim(),
                    (it[remote_takala_table_helper.MUMLATZ] ?: "").trim(),
                    (it[remote_takala_table_helper.MONAAT] ?: "").trim(),
                    (it[remote_takala_table_helper.TGUVA] ?: "").trim(),
                    (it[remote_takala_table_helper.SUG] ?: "").trim(),
                    (it[remote_takala_table_helper.ALUT] ?: "").trim(),
                    (it[remote_takala_table_helper.ITEMTXT] ?: "").trim(),
                    (it[remote_takala_table_helper.RECVERSION] ?: "").trim(),
                    (it[remote_takala_table_helper.RECID] ?: "").trim(),
                    remote_SQL_Helper.getusername()
            )
        }
        return result_vector
    }

    /**
     * adds all big, updates, inserts... whatever
     * @author Chaosruler972
     */
    fun sync_db() {
        if (!global_variables_dataclass.isLocal)
            return
        val server_vec = server_data_to_vector()
        Log.d("salprojtakala_local","Got from server ${server_vec.size} items")
        for (item in server_vec) {
            add_takala(item)
        }
    }

    /**
     *   subroutine that is in charge of getting the vendor class
     * by query
     * @author Chaosruler972
     * @param takala_data the vendor data to filter by
     * @return the vendor data from the server, null if not found
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun get_takala_by_takala(takala_data: takala_data) // subroutine to get a vendor object
            : takala_data? {
        val input_map = HashMap<String, String>()
        input_map[ID] = "'${takala_data.get_projid()}'"
        input_map[ITEMID] = "'${takala_data.get_ITEMID()}"
        val vector = get_rows(input_map)
        if (vector.size > 0) {
            val it = vector[0]

            return takala_data(
                    it[ID] ?: "",
                    it[ITEMID] ?: "",
                    it[DATAAREAID] ?: "",
                    it[QTY] ?: "",
                    it[KOMA] ?: "",
                    it[BINYAN] ?: "",
                    it[DIRA] ?: "",
                    it[TEUR] ?: "",
                    it[MUMLATZ] ?: "",
                    it[MONAAT] ?: "",
                    it[TGUVA] ?: "",
                    it[SUG] ?: "",
                    it[ALUT] ?: "",
                    it[ITEMTXT] ?: "",
                    it[RECVERSION] ?: "",
                    it[RECID] ?: "",
                    remote_SQL_Helper.getusername()
            )
        }


        return null
    }

    /**
     * add vendor mechanism
     *  add vendor mechanism
     * if vendor is invalid, forget about it
     * if vendor is valid, and it exists, update it
     * if its a new vendor, add a new vendor to table
     * @author Chaosruler972
     * @param takala_data the vendor data object to add
     * @return if add was successfull true, else false
     */
    fun add_takala(takala_data: takala_data) // subroutine that manages the vendor adding operation to the database
            : Boolean {
        val res = (remote_SQL_Helper.get_latest_sync_time().time > 0.toLong() &&
                update_takala(takala_data, takala_data.copy())) // checks if vendor exists in database
        return if(!res) {
            Log.d("Takala_database","Inserted")
            insert_takala(takala_data)
        }
        else
        {
            Log.d("Takala_database","Updated")
            res

        }
    }


    /**
     * checks if vendor exists, query is not that smart, gets an ENTIRE table and than checks
     * if the opr is there
     *
     * // on update
     * will select USERNAME only
     * @param takala_data the vendor to check if exists or not
     * @return if the vendor data was found or not
     * @author Chaosruler972
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun check_takala(takala_data: takala_data) // subroutine to check if vendor exists on the database
            : Boolean {
        val new_data: takala_data? = get_takala_by_takala(takala_data)
        return new_data != null
    }

    /**
     *  subroutine in charge of feeding schema and database information to SQL
     * abstract implentation on insert queries
     * @author Chaosruler972
     * @param takala_data the vendor table data that we want to insert
     * @return if insertion was successfull or not
     */
    private fun insert_takala(takala_data: takala_data): Boolean // subroutine to insert a vendor to the database
    {

        val everything_to_add: Vector<HashMap<String, String>> = Vector()

        val data: HashMap<String, String> = HashMap()
        data[ID] = (takala_data.get_projid() ?: "")
        data[ITEMID] = (takala_data.get_ITEMID() ?: "")
        data[DATAAREAID] = (takala_data.get_DATAAREAID() ?: "")
        data[QTY] = (takala_data.get_QTY() ?: "")
        data[KOMA] = (takala_data.get_KOMA() ?: "")
        data[BINYAN] = (takala_data.get_BINYAN() ?: "")
        data[DIRA] = (takala_data.get_DIRA() ?: "")
        data[TEUR] = (takala_data.get_TEUR() ?: "")
        data[MUMLATZ] = (takala_data.get_MUMLATZ() ?: "")
        data[MONAAT] = (takala_data.get_MONAAT() ?: "")
        data[TGUVA] = (takala_data.get_TGUVA() ?: "")
        data[SUG] = (takala_data.get_SUG() ?: "")
        data[ALUT] = (takala_data.get_ALUT() ?: "")
        data[ITEMTXT] = (takala_data.get_ITEMTXT() ?: "")
        data[RECVERSION] = (takala_data.get_RECVERSION() ?: "")
        data[RECID] = (takala_data.get_RECID() ?: "")
        data[USERNAME] = (takala_data.get_USERNAME())
        everything_to_add.addElement(data)
        return add_data(everything_to_add, false)
    }

    /**
     *  subroutine in charge of feeding information and database information to
     * SQL abstraction on update queries
     * @author Chaosruler972
     * @param from the source that we want to update
     * @param to what we want to update it to
     * @return if update was successfull or not
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun update_takala(from: takala_data, to: takala_data) // subroutine to update data of a vendor that exists on the database
            : Boolean {

        val change_to: HashMap<String, String> = HashMap()
        change_to[DATAAREAID] = (to.get_DATAAREAID() ?: "")
        change_to[QTY] = (to.get_QTY() ?: "")
        change_to[KOMA] = (to.get_KOMA() ?: "")
        change_to[BINYAN] = (to.get_BINYAN() ?: "")
        change_to[DIRA] = (to.get_DIRA() ?: "")
        change_to[TEUR] = (to.get_TEUR() ?: "")
        change_to[MUMLATZ] = (to.get_MUMLATZ() ?: "")
        change_to[MONAAT] = (to.get_MONAAT() ?: "")
        change_to[TGUVA] = (to.get_TGUVA() ?: "")
        change_to[SUG] = (to.get_SUG() ?: "")
        change_to[ALUT] = (to.get_ALUT() ?: "")
        change_to[ITEMTXT] = (to.get_ITEMTXT() ?: "")
        change_to[RECVERSION] = (to.get_RECVERSION() ?: "")
        change_to[USERNAME] = (to.get_USERNAME())
        change_to[ID] = from.get_projid()?:""
        change_to[ITEMID] = to.get_projid()?:""
        return update_data(RECID, arrayOf(from.get_RECID()?:""), change_to)
    }

    /**
     * subroutine in charge of feeding information and database information to
     * SQL abstraction on delete queries
     * @author Chaosruler972
     * @param takala_data the source that we want to remove
     * @return if removal was successfull or not
     */
    @Suppress("unused")
    fun delete_takala(takala_data: takala_data): Boolean // subroutine to delete a vendor from the database (local)
    {
        if (get_takala_by_takala(takala_data) == null)
            return false
        return remove_from_db(RECID, arrayOf(takala_data.get_RECID()?:""))
    }

    /**
     * gets an vendor by ID
     * @author Chaosruler972
     * @param proj_id the id that we want to filter by
     * @param item_id the id that we want to filter by
     * @return the vendor itself if found, null otheerwise
     */
    fun get_takala_by_id(proj_id: String, item_id: String): takala_data? = get_takala_by_takala(takala_data(proj_id, item_id, "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""))
}