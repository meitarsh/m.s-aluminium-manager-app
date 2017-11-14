package com.example.chaosruler.msa_manager

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.util.*

/**
 * Created by chaosruler on 11/14/17.
 */
class user_database_helper(context: Context) : SQL_Helper(context, "Users.db", null, DB_version) {
    private val TABLE: String
    private val USERS_ID: String
    private val PASSWORD: String

    init {
        TABLE = context.getString(R.string.USER_TABLE_NAME)
        USERS_ID = context.getString(R.string.USER_COL_ID)
        PASSWORD = context.getString(R.string.USER_COL_PASSWORD)
    }


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table " + TABLE + " " +
                "(" + USERS_ID + " text primary key, " + PASSWORD + " text" + ") ")
    }


    fun add_user(con: Context?, username: String?, password: String?) // subroutine that manages the user adding operation to the database
            : Boolean {
        if (username == null || password == null || username.length == 0 || password.length == 0 || con == null)
            return false
        val db = this.readableDatabase
        if (check_user(con, username) == true)
        // checks if user exists in database
        {
            update_user(con, username, password) // if it does, lets update its password
        } else
        // if it doesn't lets create a new entry for the user
        {
            insert_user(con, username, password)
        }
        db.close()
        return true

    }

    fun check_user(con: Context?, username: String?) // subroutine to check if users exists on the database
            : Boolean {
        if (username == null || username.length == 0 || con == null)
            return false
        val user = get_user_by_id(con, username)
        return if (user != null) true else false
    }

    private fun insert_user(con: Context?, username: String?, password: String?) // subroutine to insert a user to the database
            : Boolean {
        if (username == null || password == null || username.length == 0 || password.length == 0 || con == null)
            return false
        val database = this.writableDatabase
        val values = ContentValues()
        values.put(USERS_ID, username)
        values.put(PASSWORD, password)
        database.insert(TABLE, null, values)
        database.close()
        return true
    }

    fun update_user(con: Context?, username: String?, password: String?) // subroutine to update data of a user that exists on the database
            : Boolean {
        if (username == null || password == null || username.length == 0 || password.length == 0 || con == null)
            return false
        val database = this.writableDatabase
        val values = ContentValues()
        values.put(PASSWORD, password)
        database.update(TABLE, values, USERS_ID + "=?", arrayOf(username))
        database.close()
        return true

    }

    fun delete_user(con: Context?, username: String?) // subroutine to delete a user from the database (local)
            : Boolean {
        if (username == null || username.length == 0 || con == null)
            return false
        if (check_user(con, username) == false)
            return false
        val database = this.writableDatabase
        database.delete(TABLE, USERS_ID + "=?", arrayOf(username))
        database.close()
        return true
    }

    fun get_entire_db(con: Context?) // subroutine to get the entire database as an iterateable vector
            : Vector<User>? {
        if (con == null)
            return null
        val db = this.readableDatabase
        val users = Vector<User>()
        val c = db.rawQuery("SELECT * FROM " + TABLE, null)
        c.moveToFirst()
        while (!c.isAfterLast) {
            val usr = User(c.getString(c.getColumnIndex(USERS_ID)), c.getString(c.getColumnIndex(PASSWORD)))
            users.add(usr)
            c.moveToNext()
        }
        db.close()
        return users
    }

    fun get_user_by_id(con: Context?, username: String?) // subroutine to get a User object representing a user by the user id (username)
            : User? {
        if (username == null || username.length == 0 || con == null)
            return null
        val users = get_entire_db(con)
        for (i in users!!.indices) {
            if (users.elementAt(i).get__username() != null && users.elementAt(i).get__username().equals(username))
                return users.elementAt(i)
        }
        return null
    }

    companion object {
        private val DB_version = 3
    }

}