@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.SQLITE_helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.abstraction_classes.local_SQL_Helper
import com.example.chaosruler.msa_manager.object_types.cache_command
import java.util.*

/**
 * implenting the SQL helper on cache database (SQLITE)
 * @author Chaosruler972
 * @constructor a context to work with, the rest comes from strings.xml
 */
class cache_server_commands(context: Context) : local_SQL_Helper(context, context.getString(R.string.cache_DB_NAME), null, context.resources.getInteger(R.integer.cache_db_ver), context.getString(R.string.cache_table_name))
{
    /**
     * a command id (raising, autoincrement) field name
     * @author Chaosruler972
     */
    private val ID: String = context.getString(R.string.cache_col_1)
    /**
     * a command field name
     * @author Chaosruler972
     */
    private val COMMAND:String = context.getString(R.string.cache_col_2)
    /**
     * calling user field name
     * @author Chaosruler972
     */
    private val USER:String = context.getString(R.string.cache_col_3)

    /**
     *    MUST BE CALLED, it reports to the database about the table schema, is used by the abstracted
     * SQL class
     * @author Chaosruler972
     */
    init {
        val vector: Vector<String> = Vector()
        vector.add(ID)
        vector.add(COMMAND)
        vector.add(USER)
        init_vector_of_variables(vector)
    }

    /**
     * provides info for the abstracted SQL class
     * on what the table schema is for creation
     * @author Chaosruler972
     * @param db an instance of database
     */
    override fun onCreate(db: SQLiteDatabase) {
        val map: HashMap<String, String> = HashMap()
        map[ID] = "INTEGER primary key AUTOINCREMENT"
        map[COMMAND] = "TEXT"
        map[USER] = "TEXT"
        createDB(db,map)
    }

    /**
     *      add command mechanism
     * if command is invalid, forget about it
     * if command is valid, and it exists, update it
     * if its a new command, add a new user to table
     * @author Chaosruler972
     * @param command the password we need to input
     * @return if adding the new command was success
     */
    fun add_command_to_list(command: cache_command): Boolean {
        return if (check_command_exists(command)) {
            false
        } else {
            insert_command(command)
            true
        }

    }

    /**
     *      checks if command exists, query is not that smart, gets an ENTIRE table and than checks
     * if the command is there
     *
     * // on update
     * will select command only
     * @author Chaosruler972
     * @param command the username we want to check if it exists
     * @return true if command exists, false if not
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun check_command_exists(command: cache_command): Boolean {
        val input_map = HashMap<String, String>()
        input_map[COMMAND] = "'${command.__command}'"
        input_map[USER] = "'${command.__user}'"
        return get_rows(input_map).size > 0


    }

    /**
     * gets command id of specified command
     * @author Chaosruler972
     * @param command the username we want to grab it's id
     * @return an id represneting the command, -1 if command doesn't exist
     */
    fun get_id_of_command(command: cache_command): Long {
        val input_map = HashMap<String, String>()
        input_map[COMMAND] = "'${command.__command}'"
        input_map[USER] = "'${command.__user}'"
        if(get_rows(input_map).size <= 0)
            return -1
        return ((get_rows(input_map).firstElement()[ID]?:"-1").trim()).toLong()
    }


    /**
     *   subroutine in charge of feeding schema and database information to SQL
     * abstract implentation on insert queries
     * @author Chaosruler972
     * @param command the password of the user we want to enter
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun insert_command(command: cache_command) {
        val everything_to_add: Vector<HashMap<String, String>> = Vector()

        val data: HashMap<String, String> = HashMap()
        Log.d("Going to add command:",command.__command)
        data[COMMAND] = command.__command
        data[USER] = command.__user
        everything_to_add.addElement(data)
        add_data(everything_to_add)
    }


    /**
     *     subroutine to remove command from localDB of commands
     *  @author Chaosruler972
     *  @param command the command we want to delete
     *  @return if delete was successfull, returns true
     */
    fun remove_command(command: cache_command): Boolean {
        return remove_from_db(arrayOf(COMMAND,USER), arrayOf(command.__command,command.__user))
    }


    /**
     *  subroutine that converts the entire table from hashmap to vector of commands
     *  @author Chaosruler972
     *  @return a vector of all the commands in the local database
     */
    fun get_entire_db():Vector<cache_command> // subroutine to get the entire database as an iterateable vector
    {
        Log.d("DB OF: ","Cache")
        val commands: Vector<cache_command> = Vector()
        val vector: Vector<HashMap<String, String>> = get_db()
        vector
                .map { cache_command(it!![COMMAND].toString(), it[USER].toString()) }
                .forEach { commands.addElement(it) }

        return commands
    }

    /**
     *  subroutine that converts the entire table from hashmap to vector of commands
     *  and than represents them in string (development command)
     *  @author Chaosruler972
     *  @return a string representing the  vector of all the commands in the local database
     */
    fun get_db_string(): String {
        val vector = get_entire_db()
        var str = ""
        var i = 0
        for (item in vector) {
            str += "row ${++i}: "
            for (command in vector) {
                str += "COMMAND = ${command.__command} USER = ${command.__user} "
            }
            str += "\n"

        }
        return str
    }


}