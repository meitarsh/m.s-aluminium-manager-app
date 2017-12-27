package com.example.chaosruler.msa_manager.SQLITE_helpers

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.net.wifi.WifiManager
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.local_SQL_Helper
import com.example.chaosruler.msa_manager.object_types.User
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import java.util.*
import kotlin.collections.HashMap


class user_database_helper(private val con: Context) : local_SQL_Helper(con, con.getString(R.string.USER_database_filename), null, con.resources.getInteger(R.integer.USER_DB_VERSION), con.getString(R.string.USER_TABLE_NAME)) {
    private val USERS_ID: String = con.getString(R.string.USER_COL_ID)
    private val PASSWORD: String = con.getString(R.string.USER_COL_PASSWORD)

    /*
        MUST BE CALLED, it reports to the database about the table schema, is used by the abstracted
        SQL class
     */
    init
    {
        val vector: Vector<String> = Vector()
        vector.add(USERS_ID)
        vector.add(PASSWORD)
        init_vector_of_variables(vector)


    }

    /*
        provides info for the abstracted SQL class
        on what the table schema is for creation
     */
    override fun onCreate(db: SQLiteDatabase) {

        val map: HashMap<String, String> = HashMap()
        map[USERS_ID] = "text primary key"
        map[PASSWORD] = "text"
        createDB(db,map)
    }


    /*
        add user mechanism
        if user is invalid, forget about it
        if user is valid, and it exists, update it
        if its a new user, add a new user to table
     */
    fun add_user(username: String, password: String) // subroutine that manages the user adding operation to the database
            : Boolean {
        if ( username.isEmpty() || password.isEmpty())
            return false
        if (check_user( username)) // checks if user exists in database
            update_user(username, password) // if it does, lets update its password
        else // if it doesn't lets create a new entry for the user
            insert_user(username, password)
        return true
        /*
        var map:HashMap<String,String> = HashMap()
        map[USERS_ID] = username
        map[PASSWORD] = password
        return replace(map)
        */
    }

    /*
        checks if user exists, query is not that smart, gets an ENTIRE table and than checks
        if the user is there

        // on update
        will select USERNAME only
     */
    fun check_user(username: String) // subroutine to check if users exists on the database
            : Boolean {
        if ( username.isEmpty())
            return false
        val user = get_user_by_id( username)
        return user != null
    }

    /*
        subroutine in charge of feeding schema and database information to SQL
        abstract implentation on insert queries
     */
    private fun insert_user(username: String, password: String) // subroutine to insert a user to the database
    {
        if ( username.isEmpty() || password.isEmpty())
            return
        val everything_to_add: Vector<HashMap<String, String>> = Vector()

        val data: HashMap<String, String> = HashMap()
        data[USERS_ID] = username
        data[PASSWORD] = String(global_variables_dataclass.xorWithKey(password.toByteArray(),get_device_id().toByteArray()))
        everything_to_add.addElement(data)
        add_data(everything_to_add)
    }
    /*
        subroutine in charge of feeding information and database information to
        SQL abstraction on update queries
     */
    fun update_user(username: String, password: String) // subroutine to update data of a user that exists on the database
            : Boolean {
        if ( username.isEmpty() || password.isEmpty())
            return false

        val change_to: HashMap<String, String> = HashMap()
        change_to[PASSWORD] = String(global_variables_dataclass.xorWithKey(password.toByteArray(),get_device_id().toByteArray()))
        return update_data(USERS_ID, arrayOf(username),change_to)
    }

    /*
        subroutine in charge of feeding information and database information to
        SQL abstraction on delete queries
     */
    fun delete_user( username: String):Boolean // subroutine to delete a user from the database (local)
    {
        if ( username.isEmpty())
             return false
        if (!check_user( username))
            return false
        return remove_from_db(USERS_ID, arrayOf(username))

    }

    /*
        subroutine that converts the entire table from hashmap to vector of users
     */
    fun get_entire_db():Vector<User> // subroutine to get the entire database as an iterateable vector
    {
        val users: Vector<User> = Vector()
        val vector: Vector<HashMap<String, String>> = get_db()
        vector
                .map { User(it[USERS_ID].toString(), String(global_variables_dataclass.xorWithKey(it[PASSWORD].toString().toByteArray(),get_device_id().toByteArray()))) }
                .forEach { users.addElement(it) }
        return users
    }

    /*
        subroutine that is in charge of getting the user class
        by query
     */
    fun get_user_by_id(username: String) // subroutine to get a User object representing a user by the user id (username)
            : User?
    {
        if ( username.isEmpty())
            return null
        val input_map = HashMap<String, String>()
        input_map[USERS_ID] = "'$username'"
        val vector = get_rows(input_map)
        if(vector.size > 0)
        {
            return User((vector.firstElement()[USERS_ID]?:"").trim(),String(global_variables_dataclass.xorWithKey((vector.firstElement()[PASSWORD]?:"").trim().toByteArray(),get_device_id().toByteArray())) )
        }


        /*
        val users = get_entire_db()
        for (user in users) {
            if(user.get__username()!=null && user.get__username() == username)
                return user
                */

        return null
    }

    @SuppressLint("WifiManagerPotentialLeak", "HardwareIds")
    private fun get_device_id():String
    {
        val wifiManager = con.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wInfo = wifiManager.connectionInfo
        return wInfo.macAddress
    }

}