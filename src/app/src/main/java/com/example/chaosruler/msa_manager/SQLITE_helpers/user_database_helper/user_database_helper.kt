package com.example.chaosruler.msa_manager.SQLITE_helpers.user_database_helper

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.local_SQL_Helper
import com.example.chaosruler.msa_manager.object_types.User
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import java.util.*
import kotlin.collections.HashMap




/**
 * implenting the SQL helper on user database (SQLITE)
 * @author Chaosruler972
 * @constructor a context to work with, the rest comes from strings.xml
 */
class user_database_helper(
        /**
         * The working context
         * @author Chaosruler972
         * @see Context
         */
        @Suppress("CanBeParameter")
        private val con: Context
) : local_SQL_Helper(con, con.getString(R.string.USER_database_filename), null, con.resources.getInteger(R.integer.USER_DB_VERSION), con.getString(R.string.USER_TABLE_NAME), create_vector_of_variables(con)) {
    /**
     * provides info for the abstracted SQL class
     * on what the table schema is for creation
     * @author Chaosruler972
     * @param db an instance of database
     */
    override fun onCreate(db: SQLiteDatabase) {

        val map: HashMap<String, String> = HashMap()
        val sqlite_val_type = con.getString(R.string.SQLITE_VAL_TYPE)
        map[hashmap_of_variables[local_user_enum.USER_LAST_SYNC]!!] = "$sqlite_val_type primary key"
        map[hashmap_of_variables[local_user_enum.PASSWORD]!!] = "$sqlite_val_type"
        map[hashmap_of_variables[local_user_enum.USER_LAST_SYNC]!!] = "$sqlite_val_type"
        createDB(db,map)
    }


    /**
     *      add user mechanism
     * if user is invalid, forget about it
     * if user is valid, and it exists, update it
     * if its a new user, add a new user to table
     * @author Chaosruler972
     * @param password the password we need to input
     * @param username the username we need to input
     * @return if adding the new username was success
     */
    fun add_user(username: String, password: String) // subroutine that manages the user adding operation to the database
            : Boolean {
        if ( username.isEmpty() || password.isEmpty())
            return false
        if (check_user( username)) // checks if user exists in database
        {
            val checked_user = get_user_by_id(username)!!
            update_user(username, password, checked_user.get_last_sync_time().time) // if it does, lets update its password
        }
        else // if it doesn't lets create a new entry for the user
            insert_user(username, password)
        return true

    }

    /**
     *      checks if user exists, query is not that smart, gets an ENTIRE table and than checks
     * if the user is there
     *
     * // on update
     * will select USERNAME only
     * @author Chaosruler972
     * @param username the username we want to check if it exists
     * @return true if user exists, false if not
     */
    fun check_user(username: String) // subroutine to check if users exists on the database
            : Boolean {
        if ( username.isEmpty())
            return false
        val user = get_user_by_id( username)
        return user != null
    }

    /**
     *   subroutine in charge of feeding schema and database information to SQL
     * abstract implentation on insert queries
     * @author Chaosruler972
     * @param password the password of the user we want to enter
     * @param username the username we want to enter
     */
    private fun insert_user(username: String, password: String) // subroutine to insert a user to the database
    {
        if ( username.isEmpty() || password.isEmpty())
            return
        val everything_to_add: Vector<HashMap<String, String>> = Vector()

        val data: HashMap<String, String> = HashMap()
        data[hashmap_of_variables[local_user_enum.USERS_ID]!!] = username
        data[hashmap_of_variables[local_user_enum.PASSWORD]!!] = password
        data[hashmap_of_variables[local_user_enum.USER_LAST_SYNC]!!] = 0.toString()
        everything_to_add.addElement(data)
        add_data(everything_to_add)
    }

    /**
     *  subroutine in charge of feeding information and database information to
     * SQL abstraction on update queries
     * @author Chaosruler972
     * @param password the password we want to update
     * @param username the username we want to find to update its password
     * @param time the latest time in sync
     * @return if successfull, true, else false
     */
    fun update_user(username: String, password: String, time: Long) // subroutine to update data of a user that exists on the database
            : Boolean {
        if ( username.isEmpty() || password.isEmpty())
            return false

        val change_to: HashMap<String, String> = HashMap()
        change_to[hashmap_of_variables[local_user_enum.PASSWORD]!!] = password
        change_to[hashmap_of_variables[local_user_enum.USER_LAST_SYNC]!!] = time.toString()
        return update_data(hashmap_of_variables[local_user_enum.USERS_ID]!!, arrayOf(username),change_to)
    }

    /**
     *    subroutine in charge of feeding information and database information to
     *  SQL abstraction on delete queries
     *  @author Chaosruler972
     *  @param username the username we want to delete
     *  @return if delete was successfull, returns true
     */
    fun delete_user( username: String):Boolean // subroutine to delete a user from the database (local)
    {
        if ( username.isEmpty())
             return false
        if (!check_user( username))
            return false
        return remove_from_db(hashmap_of_variables[local_user_enum.USERS_ID]!!, arrayOf(username))

    }

    /**
     *  subroutine that converts the entire table from hashmap to vector of users
     *  @author Chaosruler972
     *  @return a vector of all the users in the local database
     */
    fun get_entire_db():Vector<User> // subroutine to get the entire database as an iterateable vector
    {
        global_variables_dataclass.log("DB OF: ", "Users")
        val users: Vector<User> = Vector()
        val vector: Vector<HashMap<String, String>> = get_db()
        vector
                .map { User(it[hashmap_of_variables[local_user_enum.USERS_ID]!!].toString(),
                        (it[hashmap_of_variables[local_user_enum.PASSWORD]!!]?:"").trim(),
                        (it[hashmap_of_variables[local_user_enum.USER_LAST_SYNC]!!]?:"0").trim().toLong()) }
                .forEach { users.addElement(it) }
        return users
    }

    @SuppressLint("LongLogTag")
            /**
     *  subroutine that is in charge of getting the user class
     *  by query
     *  @author Chaosruler972
     *  @param username the user we want to find
     *  @return null if user was not found, if user was found we return a User dataclass representing it
     */
    fun get_user_by_id(username: String) // subroutine to get a User object representing a user by the user id (username)
            : User?
    {
        if ( username.isEmpty())
            return null
        val vector = get_entire_db()
        for (user in vector) {
            if (user.get__username() == username)
                return user
        }
        return null
    }

    companion object vector_of_variables_maker{
        fun create_vector_of_variables(context: Context): HashMap<Int, String> {

            val USERS_ID: String = context.getString(R.string.USER_COL_ID)
            val PASSWORD: String = context.getString(R.string.USER_COL_PASSWORD)
            val USER_LAST_SYNC: String = context.getString(R.string.USER_SYNC_TIME)
            val hashmap_of_variables = HashMap<Int, String>()
            hashmap_of_variables[local_user_enum.USERS_ID] = USERS_ID
            hashmap_of_variables[local_user_enum.PASSWORD] = PASSWORD
            hashmap_of_variables[local_user_enum.USER_LAST_SYNC] = USER_LAST_SYNC
            return hashmap_of_variables
        }
    }

}