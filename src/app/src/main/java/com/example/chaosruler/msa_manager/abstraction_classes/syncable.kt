package com.example.chaosruler.msa_manager.abstraction_classes

import com.example.chaosruler.msa_manager.BuildConfig
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper
import java.util.*

/**
 * Defines a syncale table
 * @author Chaosruler972
 */
@Suppress("UNCHECKED_CAST")
interface syncable {


    var REMOTE_DATABASE_NAME: String

    var REMOTE_TABLE_NAME: String

    var REMOTE_DATAARAEID_KEY: String

    var REMOTE_DATAARAEID_VAL: String

    var USER: String

    /**
     * Converts local hashmap to table dataclass
     * @author Chaosruler972
     * @return a local hashmap of this dataclass
     */
    fun hashmap_to_table_dataclass_local(hashMap: HashMap<String, String>): table_dataclass

    /**
     * Converts remote hashmap to table dataclass
     * @author Chaosruler972
     * @return a remote hashmap of this dataclass
     */
    fun hashmap_to_table_dataclass_remote(hashMap: HashMap<String, String>): table_dataclass


    abstract fun remove_from_db(where_clause: String, equal_to: Array<String>): Boolean

    /**
     * adds all big, updates, inserts... whatever
     * @author Chaosruler972
     */
    fun <T : table_dataclass> sync_db() {
        if (!global_variables_dataclass.isLocal)
            return
        val server_vec = server_data_to_vector<T>()
        global_variables_dataclass.log("db_sync", "Successfully loaded entire db $REMOTE_TABLE_NAME vector with ${server_vec.size.toString()}")
        for (item in server_vec) {
            add_to_table(item)
        }
    }


    abstract fun get_db(): Vector<HashMap<String, String>>


    /**
     *  converts DB to vector of big data
     *  @author Chaosruler972
     *  @return a vector of big table from local DB
     */
    fun <T : table_dataclass> get_local_DB(): Vector<T> {
        val vector: Vector<T> = Vector()

        val all_db: Vector<java.util.HashMap<String, String>> = get_db()
        all_db
                .filter { it[USER] != null && it[USER] ?: "" == remote_SQL_Helper.getusername() }
                .forEach { vector.addElement(hashmap_to_table_dataclass_local(it) as T) }
        return vector
    }


    fun get_remote_typemap(): HashMap<String, String>

    /**
     * server data to vector... by projid
     * @author Chaosruler972
     * @return a vector of big table filtered by project name
     */
    fun <T : table_dataclass> server_data_to_vector(): Vector<T> {

        val typemap: HashMap<String, String> = get_remote_typemap()
        val server_data: Vector<java.util.HashMap<String, String>> =
                if (BuildConfig.DEBUG) {
                    remote_SQL_Helper.select_columns_from_db_with_where(REMOTE_DATABASE_NAME, REMOTE_TABLE_NAME, typemap, REMOTE_DATAARAEID_KEY, REMOTE_DATAARAEID_VAL)
                } else {
                    remote_SQL_Helper.select_columns_from_db_with_where(REMOTE_DATABASE_NAME, REMOTE_TABLE_NAME, typemap, null, null)
                }
        val result_vector: Vector<T> = Vector()

        @Suppress("UNCHECKED_CAST")
        for (map in server_data)
            result_vector.addElement(hashmap_to_table_dataclass_remote(map) as T)
        return result_vector
    }


    fun get_rows(map: HashMap<String, String>): Vector<HashMap<String, String>>

    /**
     *   subroutine that is in charge of getting the big class
     * by query
     * @author Chaosruler972
     * @param table_dataclass the big data to filter by
     * @return the big data from the server, null if not found
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun get_item_by_key_item(table_dataclass: table_dataclass) // subroutine to get a opr object
            : table_dataclass? {
        val input_map = java.util.HashMap<String, String>()
        val keys = table_dataclass.to_key_hashmap()
        input_map[keys.first] = "'${keys.second}'"

        val vector = get_rows(input_map)
        if (vector.size > 0) {
            return hashmap_to_table_dataclass_local(vector.firstElement())
        }

        return null
    }


    /**
     * add big mechanism
     * if big is invalid, forget about it
     * if big is valid, and it exists, update it
     * if its a new opr, add a new big to table
     * @author Chaosruler972
     * @param table_dataclass the big data object to add
     * @return if add was successfull true, else false
     */
    fun add_to_table(table_dataclass: table_dataclass) // subroutine that manages the big adding operation to the database
            : Boolean {
        global_variables_dataclass.log("sync", "Got request to insert to table $REMOTE_TABLE_NAME")
        val res = (remote_SQL_Helper.get_latest_sync_time().time > 0.toLong() &&
                upd_data(table_dataclass, table_dataclass.copy())) // checks if big exists in database
        // if it does, lets update
        if (!res) // if it doesn't lets create a new entry for the big
        {
            global_variables_dataclass.log("sync", "Got request to add to table $REMOTE_TABLE_NAME")
            return ins_data(table_dataclass)
        }
        return res
    }

    /**
     * checks if big exists, query is not that smart, gets an ENTIRE table and than checks
     * if the big is there
     * @param table_dataclass the big to check if exists or not
     * @return if the big data was found or not
     * @author Chaosruler972
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun get_item_by_item(table_dataclass: table_dataclass) // subroutine to check if big exists on the database
            : Boolean {

        val res: table_dataclass? = get_item_by_key_item(table_dataclass)
        return res != null
    }


    abstract fun add_data(variables: Vector<HashMap<String, String>>, check_input: Boolean): Boolean


    /**
     *  subroutine in charge of feeding schema and database information to SQL
     * abstract implentation on insert queries
     * @author Chaosruler972
     * @param table_dataclass the big table data that we want to insert
     * @return if insertion was successfull or not
     */
    private fun ins_data(table_dataclass: table_dataclass): Boolean // subroutine to insert a big to the database
    {

        val everything_to_add: Vector<HashMap<String, String>> = Vector()

        everything_to_add.addElement(table_dataclass.to_sql_hashmap())
        return add_data(everything_to_add, false)
    }


    abstract fun update_data(where_clause: String, equal_to: Array<String>, update_to: HashMap<String, String>): Boolean

    /**
     *  subroutine in charge of feeding information and database information to
     * SQL abstraction on update queries
     * @author Chaosruler972
     * @param from the source that we want to update
     * @param to what we want to update it to
     * @return if update was successfull or not
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun upd_data(from: table_dataclass, to: table_dataclass) // subroutine to update data of a big that exists on the database
            : Boolean {


        val change_to = to.to_non_key_hashmap()
        val keys = from.to_key_hashmap()
        change_to["username"] = remote_SQL_Helper.getusername()

        return update_data(keys.first, arrayOf(keys.second), change_to)
    }

    /**
     * subroutine in charge of feeding information and database information to
     * SQL abstraction on delete queries
     * @author Chaosruler972
     * @param table_dataclass the source that we want to remove
     * @return if removal was successfull or not
     */
    @Suppress("unused")
    fun del_data(table_dataclass: table_dataclass): Boolean // subroutine to delete a big from the database (local)
    {

        val key = table_dataclass.to_key_hashmap()
        return remove_from_db(key.first, arrayOf(key.second))
    }
}