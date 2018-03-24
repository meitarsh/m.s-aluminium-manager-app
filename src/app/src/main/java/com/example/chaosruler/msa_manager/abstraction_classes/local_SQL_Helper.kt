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
import java.nio.charset.Charset
import java.util.*
import kotlin.collections.HashMap

/**
 * an abstract representation of what we define as Local table (or database) in SQLite
 * this abstract implentation is semi-implented with all the basic functionality we need from a local database
 * after initating constructor, we must initate init_vector_of_variables
 * @author Chaosruler972
 * @constructor halndles data initating on the base class level
 * @param DATABASE_NAME the Database name we work with, will become DATABASE_NAME.db as a file in Android device
 * @param TABLE_NAME the table name to open and query
 * @param context baseContext to work with, must be valid at all stages
 * @param factory should be initated to null
 * @param version the database version we are working with on the local level, starting from 1 (integers only)
 * @sample
 * class cache_server_commands( context: Context) : local_SQL_Helper(context,context.getString(R.string.cache_DB_NAME),null,context.resources.getInteger(R.integer.cache_db_ver),context.getString(R.string.cache_table_name))
 * private val ID: String = context.getString(R.string.cache_col_1)
 * private val COMMAND:String = context.getString(R.string.cache_col_2)
 * private val USER:String = context.getString(R.string.cache_col_3)
 * MUST BE CALLED, it reports to the database about the table schema, is used by the abstracted
 * SQL class
 *
 * init
 * {
 * val vector: Vector<String> = Vector()
 * vector.add(ID)
 * vector.add(COMMAND)
 * vector.add(USER)
 * init_vector_of_variables(vector)
 * }
 */
abstract class local_SQL_Helper(@Suppress("CanBeParameter")
                                /**
                                 * The context that we work with
                                 * @author Chaosruler972
                                 */
                                private val context: Context,
                                /**
                                 * The database name (ends up being DATABASE_NAME.db as file in device)
                                 * @author Chaosruler972
                                 */
                                protected var DATABASE_NAME: String,
                                factory: SQLiteDatabase.CursorFactory?,
                                version: Int,
                                /**
                                 * The table name that we are going to open
                                 * @author Chaosruler972
                                 */
                                private var TABLE_NAME: String) : SQLiteOpenHelper(context,
        DATABASE_NAME, factory, version)
{

    /**
     * abstract metadata holder
     * will hold all the table variables (by name, not type)
     * @sample
     * vector.add("_id")
     */
    private lateinit var vector_of_variables: Vector<String>

    /**
     * a function to initate parameer vector_of_variables
     * it actually gets the vector and copies it to base classes vector
     * function is responsible for generating the table as well, will log on exception
     * @param vector the input vector, as expected for vector_of_variables
     * @author Chaosruler972
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

    /**
     * a function to check if table exists on database on an abstract level
     * @param tableName the table name that we are going to check if it exists
     * @sample isTableExists("Users")
     * @author Chaosruler972
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

    /**
     * Function will use all the initated data to try to do "Create Table" sql query
     * @author Chaosruler972
     * @param db an instance of the database we are working with
     * @exception SQLiteException
     */
    abstract override fun onCreate(db: SQLiteDatabase)

    /**
     * if we got a newer version in our android device than the installed version of our current database
     * we will delete all entries of our current database, drop it and its scheme and recreate a new one (without the old data)
     * @author Chaosruler972
     * @param db an instance of the database to work with
     * @param newVersion the new version number we are installing now (in integers)
     * @param oldVersion the old version number that is installed on the device
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion)
        {
            dropDB(db)
            onCreate(db)
        }
    }


    /**
     * Drops the entire database, deleting all the data and the database schema
     * @param db an instance of our database
     * @author Chaosruler972
     */
    private fun dropDB(db: SQLiteDatabase?) // subroutine to delete the entire database, including the file
    {
        db?.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME)
    }

    /**
     * Just deletes all the database enties, keeping the schema
     * @author Chaosruler972
     * @exception SQLiteException
     */
    fun clearDB()
    {
        this.writableDatabase.execSQL("delete from " + TABLE_NAME)
    }

    /**
     * Create Datbase query handler, in an abstract method
     * @author Chaosruler972
     * @param db an instance of the database we are going to work with
     * @param variables a hashmap of the variables we are going to initate in our database in format of key: name of the value, value: the type
     * @exception SQLiteException
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

    /**
     * an extension of the normal createDB function, it also initates foreign keys (extra is for experts, pushed at the end of the create table query)
     * @author Chaosruler972
     * @param db an instance of the database to work with
     * @param variables a hashmap of the variables we are going to initate in our database in format of key: name of the value, value: the type
     * @param foregin a list of foreign keys with the value name being on the key and the reference in the foreign database on the value
     * @param extra an extra added string to push at the end of the Create Table statement
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

    /**
     * Select * from DB_NAME query, generated to code
     * function works in threadded way, opening a new thread, though this thread is blocked waiting for the results
     * @author Chaosruler972
     * @return All the database in a vector of hashmap, each item in the vector represents row, each item in the inner hashmap represents a column
     * @exception SQLiteException IllegalStateException
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
            Log.d("Results amount", c?.count.toString())
            while (c!=null && !c.isAfterLast)
            {
                val small_map: HashMap<String, String> = HashMap()
                for (variable in vector_of_variables)
                {
                    Log.d("About to get columns","Column $variable and its data is ${c.getString(c.getColumnIndex(variable))}")
                    val input_str =
                            if(variable != "id")
                                String(global_variables_dataclass.xorWithKey(c.getString(c.getColumnIndex(variable)).toByteArray(),global_variables_dataclass.get_device_id(context).toByteArray(),true,context))
                            else
                                c.getString(c.getColumnIndex(variable))
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

    /**
     * Inserts LOADS of data to the database
     * @author Chaosruler972
     * @param variables the data we want to add, field name will be on the key, field value is on the value, all the rows are seperated in the vector as different elements
     * @return if at least one of the items was successfull
     */
    protected fun add_data(variables: Vector<HashMap<String,String>>):Boolean
    {
        val db: SQLiteDatabase = this.writableDatabase
        //db.close()
        return variables.any { add_single_data(db, it) }
    }

    /**
     * Inserts single instance of row into the table
     * before inserting we check that a data that is exactly like input doesn't exist already on the database
     * on case it does, we ignore adding and return false
     * on conflict of data, we choose to replace
     * @author Chaosruler972
     * @param db an instance of the database we are going to work with
     * @param items represents a row that we want to add, key = value name, value = the data itself
     * @return if data entry was successfull
     */
    private fun add_single_data(db:SQLiteDatabase,items:HashMap<String,String>):Boolean
    {
        val values = ContentValues()
        var columns_vector = ""
        val data_vector:Vector<String> = Vector()
        var counter = 0
        for(item in items)
        {
            counter++
            values.put(item.key, String(global_variables_dataclass.xorWithKey(item.value.toByteArray(Charset.forName("UTF-8")), global_variables_dataclass.get_device_id(context).toByteArray(Charset.forName("UTF-8")), false, context)))
            columns_vector += item.key + "=? "
            if(counter != items.size)
                columns_vector += " AND "
            data_vector.addElement(values.getAsString(item.key))
        }
        val exist_data = db.query(TABLE_NAME,null,columns_vector,data_vector.toTypedArray(),null,null,null)
        if(exist_data.count != 0)
        {
            Log.d("SQL","Filtered out unneeded add query")
            return false
        }
        else
        {
            Log.d("SQL","Unfiletered, we found ${exist_data.count} Elements!")
        }
        exist_data.close()
        if(db.insertWithOnConflict(TABLE_NAME,null,values,SQLiteDatabase.CONFLICT_REPLACE)>0)
            return true
        return false
    }

    /**
     * Removes an entry from the database that holds true to the arguement where_clause equals (at least one of the equal_to strings)
     * @author Chaosruler972
     * @param where_clause the field name to compare to on each row (if that field holds true, it is deleted)
     * @param equal_to what to compare the field name to (as in, what data it should be equal to)
     * @return if data removed was successfull
     */
    protected fun remove_from_db( where_clause:String,  equal_to: Array<String>) :Boolean
    {
        var result = false
        for(item in equal_to)
            equal_to[equal_to.indexOf(item)] = String( global_variables_dataclass.xorWithKey(item.toByteArray(),global_variables_dataclass.get_device_id(context).toByteArray() ,false,context) )
        val db: SQLiteDatabase = this.writableDatabase
        if(db.delete(TABLE_NAME,where_clause + "=?", equal_to) >0)
            result = true
        //db.close()
        return result
    }

    /**
     * Removes from database with multiple where clauses requirements
     * @author Chaosruler972
     * @param where_clause a vector that holds the multiple amounts of fields that we are going to compare by name
     * @param equal_to the fields value to compare to
     * @return if the data removal was successfull
     */
    protected fun remove_from_db( where_clause:Array<String>,  equal_to: Array<String>) :Boolean
    {
        var result = false
        for(item in equal_to)
            equal_to[equal_to.indexOf(item)] = String( global_variables_dataclass.xorWithKey(item.toByteArray(),global_variables_dataclass.get_device_id(context).toByteArray() ,false,context) )
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


    /**
     * a function to update a row in the database with data
     * @author Chaosruler972
     * @param where_clause which data should I update? field should be targetted by WHERE clause
     * @param equal_to the data to compare to the targeted the row we should update...
     * @param update_to what should we update it to, key = value name, value = value data
     * @return if data update was succesfull
     * @exception SQLiteException
     */
    protected fun update_data(where_clause: String, equal_to: Array<String>, update_to: HashMap<String,String>):Boolean
    {
        var result = false
        for(item in equal_to)
            equal_to[equal_to.indexOf(item)] = String( global_variables_dataclass.xorWithKey(item.toByteArray(),global_variables_dataclass.get_device_id(context).toByteArray() ,false,context) )
        Log.d("Equal to",equal_to[0])
        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        for(item in update_to)
            values.put(item.key, String(global_variables_dataclass.xorWithKey(item.value.toByteArray(),global_variables_dataclass.get_device_id(context).toByteArray(),false,context)))
        if(db.update(TABLE_NAME,values,where_clause + "=?", equal_to)>0)
            result = true
        //db.close()
        return result
    }

    /**
     * Similar to the reguler update_data function, only this one gets multiple parameters to find the specified row to update
     * @author Chaosruler972
     * @param where_clause which data should I update? field should be targetted by WHERE clause
     * @param equal_to the data to compare to the targeted the row we should update...
     * @param update_to what should we update it to, key = value name, value = value data
     * @return if data update was succesfull
     * @exception SQLiteException
     */
    protected fun update_data(where_clause: Array<String>, equal_to: Array<String>, update_to: HashMap<String,String>):Boolean
    {
        var result = false
        for(item in equal_to)
            equal_to[equal_to.indexOf(item)] = String( global_variables_dataclass.xorWithKey(item.toByteArray(),global_variables_dataclass.get_device_id(context).toByteArray() ,false,context) )
        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        for(item in update_to)
            values.put(item.key, String(global_variables_dataclass.xorWithKey(item.value.toByteArray(),global_variables_dataclass.get_device_id(context).toByteArray(),false,context)))
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

    /**
     * SELECT * FROM TABLE_NAME WHERE XXX query to kotlin code
     * function works with multi threadding but is blocked until results is generated
     * @author Chaosruler972
     * @param map filter by which values? key = name, value = data
     * @return a vector that each element represents a row and each hashtable represents all the columns (key = column name, value = data)
     * @exception SQLiteException
     */
    fun get_rows(map:HashMap<String,String>):Vector<HashMap<String,String>>
    {
        val db = this.readableDatabase
        /*
        for(item in map)
            map[item.key] = String(global_variables_dataclass.xorWithKey(item.value.toByteArray(),global_variables_dataclass.get_device_id(context).toByteArray(),false,context))
            */
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
                sql_query += " ${item.key} = ${item.value} "
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
                            val str_item = String(global_variables_dataclass.xorWithKey(item.toByteArray(Charset.forName("UTF-8")),global_variables_dataclass.get_device_id(context).toByteArray(Charset.forName("UTF-8")),true,context))
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


    /**
     * a function to get the entire database (with multi threadding yet this thread is blocked until results is generated)
     * only this one will sort the resuls by specified value
     * @author Chaosruler972
     * @param sort_by_value what value should we sort by
     * @param isAscending true=Ascending sort, false=Descending sort
     * @return a vector that each elements represents a row, and each item in the hashtable represents a column
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
                    val item_str = String(global_variables_dataclass.xorWithKey(item.toByteArray(Charset.forName("UTF-8")),global_variables_dataclass.get_device_id(context).toByteArray(Charset.forName("UTF-8")),true,context))
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



}