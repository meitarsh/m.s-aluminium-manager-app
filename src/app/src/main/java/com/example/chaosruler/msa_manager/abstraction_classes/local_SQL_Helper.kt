package com.example.chaosruler.msa_manager.abstraction_classes

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import java.util.*
import kotlin.collections.HashMap




abstract class local_SQL_Helper(@Suppress("CanBeParameter") private val context: Context, protected var DATABASE_NAME: String, factory: SQLiteDatabase.CursorFactory?, version: Int, private var TABLE_NAME: String) : SQLiteOpenHelper(context, DATABASE_NAME, factory, version)
{

    /*
        Basic idea is to initate the vector with all the variables with a call to init_vector
        before doing SQL functions and do call onCreate with the variable types per key
     */
    private lateinit var vector_of_variables: Vector<String>

    /*
        must call - inits vector that we work on later!
     */
    protected fun init_vector_of_variables(vector:Vector<String>)
    {
        vector_of_variables = vector
        try
        {
            val db = this.writableDatabase
            if(!isTableExists(TABLE_NAME))
                this.onCreate(db) // ensures this is called, android by itself will only do it if it needs to read/write the database
            //db.close()
        }
        catch (e:SQLiteException)
        {
            Log.d("Local SQL Exception","DB $DATABASE_NAME was not created yet")
        }
    }

    /*
    check if database exists
     */
    private fun isTableExists(tableName: String): Boolean {

        val mDatabase: SQLiteDatabase = readableDatabase

        val cursor = mDatabase.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '$tableName'", null)
        if (cursor != null) {
            if (cursor.count > 0) {
                cursor.close()
                return true
            }
            cursor.close()
        }
        return false
    }
    /*
        API method abstraction
     */
    abstract override fun onCreate(db: SQLiteDatabase)

    /*
        checks if we should upgrade.. and drops and recreates
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion)
        {
            dropDB(db)
            onCreate(db)
        }
    }


    /*
        drop database
     */
    private fun dropDB(db: SQLiteDatabase?) // subroutine to delete the entire database, including the file
    {
        db?.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME)
    }

    /*
        clears all database values
     */
    fun clearDB()
    {
        this.writableDatabase.execSQL("delete from " + TABLE_NAME)
    }

    /*
        subroutine to template a create table statement
            takes parameters neccesery
     */
    protected fun createDB(db: SQLiteDatabase ,variables: HashMap<String,String>) {

        var create_statement = "create table IF NOT EXISTS $TABLE_NAME ("
        for(element in vector_of_variables)
        {
            create_statement += element + " " + variables[element]
            if(vector_of_variables.lastElement() != element)
                create_statement+=" ,"
        }


        create_statement += ")"

        db.execSQL(create_statement)

    }

    /*
        subroutine to template a create table statement
            takes parameters neccesery
            *with foreign key support*
     */
    protected fun createDB(db: SQLiteDatabase ,variables: HashMap<String,String>, foregin:HashMap<String,String>, extra:String?) {

        var create_statement = "create table $TABLE_NAME ("
        for(element in vector_of_variables)
        {
            create_statement += element + " " + variables[element]
            if(vector_of_variables.lastElement() != element)
                create_statement+=" ,"
        }
        for(element in foregin)
        {
            create_statement += ",FOREIGN KEY(${element.key}) REFERENCES ${element.value}"
        }
        if(extra!=null)
            create_statement += extra
        create_statement += ")"


        db.execSQL(create_statement)

    }

    /*
        subroutine gets entire database to vector of hashmap values, self colum feeder
     */
    fun get_db(): Vector<HashMap<String, String>>
    {
        val db: SQLiteDatabase = this.readableDatabase
        val vector: Vector<HashMap<String, String>> = Vector()

        val syncToken = Object()
        // to not hang the ui
        Thread({
            @Suppress("CanBeVal")
            var c:Cursor? = null
            try
            {
                c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
                c.moveToFirst()
            }
            catch (e: Exception)
            {
                Log.d("Local SQL helper","Failed for some reason with DB $DATABASE_NAME ${e.message}" )
                synchronized(syncToken)
                {
                    syncToken.notify()
                }

            }
            while (c!=null && !c.isAfterLast)
            {
                val small_map: HashMap<String, String> = HashMap()
                for (variable in vector_of_variables)
                {
                    val input_str = String(global_variables_dataclass.xorWithKey(c.getString(c.getColumnIndex(variable)).toByteArray(),global_variables_dataclass.get_device_id(context).toByteArray(),true))
                    small_map[variable] = input_str
                }
                vector.addElement(small_map)
                c.moveToNext()
            }
            synchronized(syncToken)
            {
                syncToken.notify()
            }
            if(c!=null)
                c.close()
        }).start()


        synchronized(syncToken)
        {
            try
            {
                syncToken.wait()

            } catch (e: InterruptedException)
            {
                Log.d("Local SQL helper","sync done with $DATABASE_NAME")
            }
        }
        //db.close()
        return vector
    }
    /*
        subroutine that templates an add query
     */
    protected fun add_data(variables: Vector<HashMap<String,String>>):Boolean
    {
        val db: SQLiteDatabase = this.writableDatabase
        //db.close()
        return variables.any { add_single_data(db, it) }
    }
    /*
        adds a single data
     */
    private fun add_single_data(db:SQLiteDatabase,items:HashMap<String,String>):Boolean
    {
        val values = ContentValues()
        for(item in items)
            values.put(item.key, String(global_variables_dataclass.xorWithKey(item.value.toByteArray(),global_variables_dataclass.get_device_id(context).toByteArray(),false) ) )
        if(db.insertWithOnConflict(TABLE_NAME,null,values,SQLiteDatabase.CONFLICT_REPLACE)>0)
            return true
        return false
    }

    /*
        subroutine that templates remove query
     */
    protected fun remove_from_db( where_clause:String,  equal_to: Array<String>) :Boolean
    {
        var result = false
        for(item in equal_to)
            equal_to[equal_to.indexOf(item)] = String( global_variables_dataclass.xorWithKey(item.toByteArray(),global_variables_dataclass.get_device_id(context).toByteArray() ,false) )
        val db: SQLiteDatabase = this.writableDatabase
        if(db.delete(TABLE_NAME,where_clause + "=?", equal_to) >0)
            result = true
        //db.close()
        return result
    }
    /*
     subroutine that templates remove query with two or more where clauses
  */
    protected fun remove_from_db( where_clause:Array<String>,  equal_to: Array<String>) :Boolean
    {
        var result = false
        for(item in equal_to)
            equal_to[equal_to.indexOf(item)] = String( global_variables_dataclass.xorWithKey(item.toByteArray(),global_variables_dataclass.get_device_id(context).toByteArray() ,false) )
        val db: SQLiteDatabase = this.writableDatabase
        var where_clause_arguemnt = ""
        for(item in where_clause)
        {
            where_clause_arguemnt += item + " =?"
            if(item != where_clause.last())
                where_clause_arguemnt += " AND "
        }
        if(db.delete(TABLE_NAME,where_clause_arguemnt, equal_to) >0)
            result = true
        //db.close()
        return result
    }



    /*
        subroutine that templates update query
     */
    protected fun update_data(where_clause: String, equal_to: Array<String>, update_to: HashMap<String,String>):Boolean
    {
        var result = false
        for(item in equal_to)
            equal_to[equal_to.indexOf(item)] = String( global_variables_dataclass.xorWithKey(item.toByteArray(),global_variables_dataclass.get_device_id(context).toByteArray() ,false) )
        Log.d("Equal to",equal_to[0])
        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        for(item in update_to)
            values.put(item.key, String(global_variables_dataclass.xorWithKey(item.value.toByteArray(),global_variables_dataclass.get_device_id(context).toByteArray(),false)))
        if(db.update(TABLE_NAME,values,where_clause + "=?", equal_to)>0)
            result = true
        //db.close()
        return result
    }

    /*
       subroutine that templates update query
    */
    protected fun update_data(where_clause: Array<String>, equal_to: Array<String>, update_to: HashMap<String,String>):Boolean
    {
        var result = false
        for(item in equal_to)
            equal_to[equal_to.indexOf(item)] = String( global_variables_dataclass.xorWithKey(item.toByteArray(),global_variables_dataclass.get_device_id(context).toByteArray() ,false) )
        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        for(item in update_to)
            values.put(item.key, String(global_variables_dataclass.xorWithKey(item.value.toByteArray(),global_variables_dataclass.get_device_id(context).toByteArray(),false)))
        var where_str = ""
        for(item in where_clause)
        {
            where_str += item + "+? "
            if(item != where_clause.last())
                where_str+=" AND "
        }
        Log.d("Equal to",equal_to[0])
        if(db.update(TABLE_NAME,values,where_str, equal_to)>0)
            result = true
        //db.close()
        return result
    }
    /*
        subroutine to look for and get a row from the database that accepts certain conditions (ALL)
     */
    fun get_rows(map:HashMap<String,String>):Vector<HashMap<String,String>>
    {
        val db = this.readableDatabase
        for(item in map)
            map[item.key] = String(global_variables_dataclass.xorWithKey(item.value.toByteArray(),global_variables_dataclass.get_device_id(context).toByteArray(),false))
        val vector = Vector<HashMap<String, String>>()
        val sync_token = Object()
        //to not hang the ui
        Thread({
            var sql_query = "SELECT * FROM $TABLE_NAME WHERE"
            var breaker = 0
            val where_args:Vector<String> = Vector()
            var where_clause = ""
            for (item in map)
            {
                where_args.addElement(item.value)
                where_clause+=item.key + " = ? "
                sql_query += " ${item.key} = '${item.value}' "
                breaker++
                if (breaker < map.size)
                {
                    where_clause+= " AND "
                    sql_query += " AND "
                }
                else
                    break
            }
            @Suppress("CanBeVal")
            var c:Cursor?
            try
            {
                Log.d("SQL raw query",sql_query)
                //c = db.rawQuery(sql_query, null)
                c = db.query(TABLE_NAME,null,where_clause,where_args.toTypedArray(),null,null,null)
            }
            catch (e:SQLException)
            {
                Log.d("Local SQL helper","SQL Exception $DATABASE_NAME ${e.message}")
                synchronized(sync_token)
                {
                    sync_token.notify()
                }
                return@Thread
            }
            catch (e:IllegalStateException)
            {
                Log.d("Local SQL helper","Illegal State ${e.message}")
                synchronized(sync_token)
                {
                    sync_token.notify()
                }
                return@Thread
            }
            try
            {
                c.moveToFirst()
            }
            catch (e: Exception)
            {
                Log.d("Local SQL helper","Error syncing ${e.message}")
                synchronized(sync_token)
                {
                    sync_token.notify()
                }
            }
            if(c!=null)
            {
                try
                {
                    while (!c.isAfterLast) {
                        val small_map: HashMap<String, String> = HashMap()
                        for (variable in vector_of_variables) {
                            val item = c.getString(c.getColumnIndex(variable))
                            val str_item = String(global_variables_dataclass.xorWithKey(item.toByteArray(),global_variables_dataclass.get_device_id(context).toByteArray(),true))
                            small_map[variable] = str_item
                        }
                        vector.addElement(small_map)
                        c.moveToNext()
                    }
                }
                catch (e:IllegalStateException)
                {
                    Log.d("local","Couldn't grab SQL table")
                }
            }
            synchronized(sync_token)
            {
                sync_token.notify()
            }

            if (c != null)
            {
                Log.d("Where Query",c.count.toString())
                c.close()
            }
        }).start()

        synchronized(sync_token)
        {
            try
            {
                sync_token.wait()
            }
            catch (e:InterruptedException)
            {
                Log.d("Local SQL helper","sync done with $DATABASE_NAME")
            }
        }

        //db.close()
        return vector

    }



    /*
       subroutine gets entire database to vector of hashmap values, self colum feeder
       includes sorting
    */
    @Suppress("unused")
    protected fun get_db(sort_by_value: String, isAscending: Boolean): Vector<HashMap<String, String>>
    {
        val db: SQLiteDatabase = this.readableDatabase
        val vector: Vector<HashMap<String, String>> = Vector()

        val syncToken = Object()
        // to not hang the ui
        Thread({
            val ascending_descending_str: String = if (isAscending) {
                " ASC"
            } else {
                " DESC"
            }
            val c = db.query(TABLE_NAME,null,null,null,null,null,sort_by_value+ascending_descending_str)
            try
            {
                c.moveToFirst()
            }
            catch (e: Exception)
            {
                Log.d("Local SQL helper","SQL Error with $DATABASE_NAME ${e.message}")
                synchronized(syncToken)
                {
                    syncToken.notify()
                }
            }
            while (c.isAfterLast.not())
            {
                val small_map: HashMap<String, String> = HashMap()
                for (variable in vector_of_variables)
                {
                    val item = c.getString(c.getColumnIndex(variable))
                    val item_str = String(global_variables_dataclass.xorWithKey(item.toByteArray(),global_variables_dataclass.get_device_id(context).toByteArray(),true))
                    small_map[variable] = item_str
                }
                vector.addElement(small_map)
                c.moveToNext()
            }
            synchronized(syncToken)
            {
                syncToken.notify()
            }
            c.close()
        }).start()


        synchronized(syncToken)
        {
            try
            {
                syncToken.wait()

            } catch (e: InterruptedException)
            {
                Log.d("Local SQL helper","sync done with $DATABASE_NAME")
            }
        }
        //db.close()
        return vector
    }

    /*
        replace data with another data! testing that
     */
    fun replace(map: HashMap<String, String>): Boolean
    {

        val db = this.writableDatabase
        /*
        var qry = "INSERT OR REPLACE INTO $TABLE_NAME"
        var before:String = "("
        var after:String = "("
        var counter = 0
        for(item in map)
        {
            before+=item.key
            after+=remote_SQL_Helper.add_quotes(item.value)
            counter++
            if(counter < map.size)
            {
                before += ","
                after += ","
            }
        }
        before+=")"
        after+=")"
        qry += " $before VALUES $after"
        val cursor = db.execSQL(qry)
        */
        val cv = ContentValues()
        map.forEach { cv.put(it.key,String(global_variables_dataclass.xorWithKey(it.value.toByteArray(),global_variables_dataclass.get_device_id(context).toByteArray(),false)) ) }
        val count = db.replace(TABLE_NAME, null, cv)
        db.close()
        return count > 0

    }

}