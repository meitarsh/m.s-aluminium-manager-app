package com.example.chaosruler.msa_manager

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by chaosruler on 11/14/17.
 */
class user_database_helper(private val con: Context) : loal_SQL_Helper(con, con.getString(R.string.USER_database_filename), null, con.getString(R.string.USER_DB_VERSION).toInt(),con.getString(R.string.USER_TABLE_NAME) ) {
    private val USERS_ID: String = con.getString(R.string.USER_COL_ID)
    private val PASSWORD: String = con.getString(R.string.USER_COL_PASSWORD)

    init
    {
        var vector:Vector<String> = Vector()
        vector.add(USERS_ID)
        vector.add(PASSWORD)
        init_vector_of_variables(vector)
    }

    override fun onCreate(db: SQLiteDatabase) {

        var map:HashMap<String,String> = HashMap()
        map[USERS_ID] = "text primary key"
        map[PASSWORD] = "text"
        createDB(db,map)
    }


    fun add_user(username: String?, password: String) // subroutine that manages the user adding operation to the database
            : Boolean {
        if (username == null || password == null || username.isEmpty() || password.isEmpty() || con == null)
            return false
        if (check_user( username)) // checks if user exists in database
            update_user(username, password) // if it does, lets update its password
        else // if it doesn't lets create a new entry for the user
            insert_user(username, password)
        return true

    }

    fun check_user(username: String) // subroutine to check if users exists on the database
            : Boolean {
        if (username == null || username.isEmpty() || con == null)
            return false
        val user = get_user_by_id( username)
        return user != null
    }

    private fun insert_user(username: String, password: String) // subroutine to insert a user to the database
    {
        if (username == null || password == null || username.isEmpty() || password.isEmpty() || con == null)
            return
        var everything_to_add:Vector<HashMap<String,String>> = Vector()

        var data: HashMap<String,String> = HashMap()
        data[USERS_ID] = username
        data[PASSWORD] = password
        everything_to_add.addElement(data)
        add_data(everything_to_add)
    }

    fun update_user(username: String, password: String) // subroutine to update data of a user that exists on the database
            : Boolean {
        if (username == null || password == null || username.isEmpty() || password.isEmpty() || con == null)
            return false

        var change_to:HashMap<String,String> = HashMap()
        change_to[PASSWORD] = password
        return update_data(USERS_ID, arrayOf(username),change_to)
    }

    fun delete_user( username: String):Boolean // subroutine to delete a user from the database (local)
    {
        if (username == null || username.isEmpty() || con == null)
            return false
        if (!check_user( username))
            return false
        return remove_from_db(USERS_ID, arrayOf(username))

    }

    fun get_entire_db():Vector<User> // subroutine to get the entire database as an iterateable vector
    {
        var users:Vector<User> = Vector()
        var vector:Vector<HashMap<String,String>> = get_db()
        for(item in vector)
        {
            var user:User = User(item!![USERS_ID].toString(), item!![PASSWORD].toString())
            users.addElement(user)
        }
        return users
    }

    fun get_user_by_id(username: String) // subroutine to get a User object representing a user by the user id (username)
            : User? {
        if (username == null || username.isEmpty() || con == null)
            return null
        val users = get_entire_db()
        for (user in users) {
            if(user.get__username()!=null && user.get__username() == username)
                return user
        }
        return null
    }


}