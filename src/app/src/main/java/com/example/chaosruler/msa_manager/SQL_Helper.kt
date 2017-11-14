package com.example.chaosruler.msa_manager

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*

/**
 * Created by chaosruler on 11/14/17.
 */
abstract class SQL_Helper(context: Context?, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version)
{
    protected var DATABASE_NAME: String = name;


    abstract override fun onCreate(db: SQLiteDatabase)

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion) {
            dropDB(db)
            onCreate(db)
        }
    }


    private fun dropDB(db: SQLiteDatabase?) // subroutine to delete the entire database, including the file
    {
        if (db == null)
            return
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME)
    }

    protected fun createDB(db: SQLiteDatabase, variables: Vector<String>) {
        /*
        db.execSQL("create table "+TABLE+" " +
                "("+USERS_ID+" text primary key, "+PASSWORD+" text" + ") ");
                */
    }

    protected fun add_data(con: Context, variables: Vector<String>): Boolean {
        return true
    }
}