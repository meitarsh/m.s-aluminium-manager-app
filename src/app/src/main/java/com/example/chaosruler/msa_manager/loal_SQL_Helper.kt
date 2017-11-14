package com.example.chaosruler.msa_manager

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by chaosruler on 11/14/17.
 */
abstract class loal_SQL_Helper(context: Context, protected var DATABASE_NAME: String, factory: SQLiteDatabase.CursorFactory?, version: Int, protected var TABLE_NAME:String) : SQLiteOpenHelper(context, DATABASE_NAME, factory, version)
{

    /*
        Basic idea is to initate the vector with all the variables with a call to init_vector
        before doing SQL functions and do call onCreate with the variable types per key
     */
    protected lateinit var vector_of_variables:Vector<String>

    protected fun init_vector_of_variables(vector:Vector<String>)
    {
        vector_of_variables = vector
    }

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

    protected fun createDB(db: SQLiteDatabase ,variables: HashMap<String,String>) {

        var create_statement:String = "create table $TABLE_NAME ("
        for(element in vector_of_variables)
        {
            create_statement += element + " " + variables[element]
            if(vector_of_variables.lastElement() != element)
                create_statement+=" ,"
        }


        create_statement += ")"

        db.execSQL(create_statement)

    }

    protected fun get_db():Vector<HashMap<String,String>>
    {
        var db:SQLiteDatabase = this.readableDatabase
        var vector:Vector<HashMap<String,String>> = Vector()

        val c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
        c.moveToFirst()
        while (!c.isAfterLast) {
            var small_map:HashMap<String,String> = HashMap()
            for(variable in vector_of_variables)
            {
                small_map[variable] = c.getString(c.getColumnIndex(variable))
            }
            vector.addElement(small_map)
            c.moveToNext()
        }
        db.close()
        return vector
    }

    protected fun add_data(variables: Vector<HashMap<String,String>>):Boolean
    {
        var return_value = false
        var db:SQLiteDatabase = this.writableDatabase
        for(item in variables)
        {
            if(add_single_data(db, item))
                return_value=true
        }
        db.close()
        return return_value
    }
    protected fun add_single_data(db:SQLiteDatabase,items:HashMap<String,String>):Boolean
    {
        val values = ContentValues()
        for(item in items)
            values.put(item.key,item.value)
        if(db.insert(TABLE_NAME, null, values)>0)
            return true
        return false
    }

    protected fun remove_from_db( where_clause:String,  equal_to: Array<String>) :Boolean
    {
        var result:Boolean = false
        var db:SQLiteDatabase = this.writableDatabase
        if(db.delete(TABLE_NAME,where_clause + "=?", equal_to) >0)
            result = true
        db.close()
        return result
    }

    protected fun update_data(where_clause: String, equal_to: Array<String>, update_to: HashMap<String,String>):Boolean
    {
        var result:Boolean = false
        var db:SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        for(item in update_to)
            values.put(item.key,item.value)
        if(db.update(TABLE_NAME,values,where_clause + "=?", equal_to)>0)
            result = true
        db.close()
        return result
    }

}