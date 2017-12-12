package com.example.chaosruler.msa_manager.SQLITE_helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.local_SQL_Helper
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.cache_command
import java.util.*


class cache_server_commands( context: Context) : local_SQL_Helper(context,context.getString(R.string.cache_DB_NAME),null,context.resources.getInteger(R.integer.cache_db_ver),context.getString(R.string.cache_table_name))
{

    private val ID: String = context.getString(R.string.cache_col_1)
    private val COMMAND:String = context.getString(R.string.cache_col_2)
    private val USER:String = context.getString(R.string.cache_col_3)
    /*
    MUST BE CALLED, it reports to the database about the table schema, is used by the abstracted
    SQL class
 */
    init
    {
        var vector: Vector<String> = Vector()
        vector.add(ID)
        vector.add(COMMAND)
        vector.add(USER)
        init_vector_of_variables(vector)
    }
    /*
           provides info for the abstracted SQL class
           on what the table schema is for creation
        */
    override fun onCreate(db: SQLiteDatabase)
    {
        var map:HashMap<String,String> = HashMap()
        map[ID] = "INTEGER primary key AUTOINCREMENT"
        map[COMMAND] = "text"
        map[USER] = "text"
        createDB(db,map)
    }

    /*
        add command to list
     */
    fun add_command_to_list(command: cache_command):Boolean
    {
        return if(check_command_exists(command))
        {
            false
        }
        else
        {
            insert_command(command)
            true
        }

    }

    /*
        check if command exists
     */
    fun check_command_exists(command: cache_command):Boolean
    {
            var input_map = HashMap<String,String>()
            input_map[COMMAND] = "'${command.__command}'"
            input_map[USER] = "'${command.__user}'"
            return get_rows(input_map).size>0


    }

    /*
        get the command id
     */
    fun get_id_of_command(command: cache_command):Long
    {
        var input_map = HashMap<String,String>()
        input_map[COMMAND] = "'${command.__command}'"
        input_map[USER] = "'${command.__user}'"
        if(get_rows(input_map).size <= 0)
            return -1
        return ((get_rows(input_map).firstElement()[ID]?:"-1").trim()).toLong()
    }

    /*
        inserts a new command to db
     */
    fun insert_command(command: cache_command)
    {
        var everything_to_add:Vector<HashMap<String,String>> = Vector()

        var data: HashMap<String,String> = HashMap()
        data[COMMAND] = command.__command
        data[USER] = command.__user
        everything_to_add.addElement(data)
        add_data(everything_to_add)
    }
    /*
        subroutine to remove command from localDB of commands
     */
    fun remove_command(command: cache_command):Boolean
    {
        return remove_from_db(arrayOf(COMMAND,USER), arrayOf(command.__command,command.__user))
    }

    /*
       subroutine that converts the entire table from hashmap to vector of users
    */
    fun get_entire_db():Vector<cache_command> // subroutine to get the entire database as an iterateable vector
    {
        var commands:Vector<cache_command> = Vector()
        var vector:Vector<HashMap<String,String>> = get_db()
        for(item in vector)
        {
            var command: cache_command = cache_command(item!![COMMAND].toString(), item[USER].toString())
            commands.addElement(command)
        }
        return commands
    }

    /*
        gets entire DB to string
     */
    fun get_db_string():String
    {
        var vector = get_entire_db()
        var str: String = ""
        var i: Int = 0
        for (item in vector)
        {
            str += "row ${++i}: "
            for (command in vector)
            {
                str += "COMMAND = ${command.__command} USER = ${command.__user} "
            }
            str += "\n"

        }
        return str
    }


}