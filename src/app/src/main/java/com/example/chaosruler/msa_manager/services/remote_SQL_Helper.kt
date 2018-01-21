package com.example.chaosruler.msa_manager.services

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.preference.PreferenceManager
import android.util.Log
import com.example.chaosruler.msa_manager.R
import java.sql.*

import java.util.*
import kotlin.collections.HashMap

/**
 * a singleton object that connects us to the database and sends queries to it
 * responsible for all the remote commands
 * @author Chaosruler972
 * @constructor constructed automaticily, but should call connect() before operations
 */
@SuppressLint("StaticFieldLeak")
@Suppress("unused")
object remote_SQL_Helper {


    @SuppressLint("StaticFieldLeak")
    private lateinit var context: Context
    private lateinit var act: Activity
    private var username: String = ""
    private var password: String = ""
    private var isvalid: Boolean = false
    private var connection: Connection? = null
    /**
     * last exception got
     * @author Chaosruler972
     */
    private lateinit var exception: SQLException

    /**
     * username logged in
     * @author Chaosruler972
     * @return gets the username that is logged in
     */
    fun getusername(): String = username

    /**
     * is connection online?
     * @return true if connection is online
     * @author Chaosruler972
     */
    fun isValid(): Boolean = isvalid

    /**
     * a getter for the most recent SQL exception
     * @author Chaosruler972
     * @return the most recent SQLException
     */
    fun getSQLException(): SQLException = exception

    /**
     * subroutine neccesery to connect to db, without this call, no DB operations can be done
     * @author Chaosruler972
     * @param act the activity that called this, must be refreshed constantly
     * @param con the context that called this, must be refreshed constantly
     * @param user the username that needs to connect to database
     * @param pass the password of that username
     * @return if connection was successfull
     */
    fun Connect(con: Context, user: String, pass: String, act: Activity): Boolean {
        if (isvalid)
            return true
        context = con
        username = user
        password = pass
        this.act = act
        /*
        vpn_connection.init_vars(con)
        if(vpn_connection.check_if_need_to_connect(con))
        {
            vpn_connection().prepare(act)
            if(!vpn_connection.connect(con))
            {
                Log.d("Connection",con.getString(R.string.unable_to_connect_vpn))
                exception = SQLException()
                return false
            }
        }
        */
        val ip: String = PreferenceManager.getDefaultSharedPreferences(con).getString(con.getString(R.string.IP), context.getString(R.string.REMOTE_IP_ADDR))
        val port: String = PreferenceManager.getDefaultSharedPreferences(con).getString(con.getString(R.string.sql_port), con.getString(R.string.default_port_num))
        val windows_auth = if (PreferenceManager.getDefaultSharedPreferences(con).getBoolean(con.getString(R.string.windows_auth_key), false))
            con.getString(R.string.REMOTE_CONNECTION_WINDOWS_AUTH)
        else
            ""
        try {
            Class.forName(context.getString(R.string.class_jtds_jdbc))
            val conn: Connection? = DriverManager.getConnection(
                    context.getString(R.string.REMOTE_CONNECT_STRING) + ip + ":" + port + context.getString(R.string.REMOTE_CONNECT_OPTIONS) + windows_auth
                    , username,
                    password)
            if (conn != null) {
                isvalid = true
                connection = conn
            }
        } catch (e: SQLException) {
            Log.d("remote SQL", "EXCEPTION ${e.message}")
            exception = e
            isvalid = false

        }
        return isvalid
    }


    /**
     * subroutine that gets as parameters an entire table and converts it into Hashmap vector, which is later can be converted to string, sql is select * form table
     * obviously threadded but until vector results is recieved, thread is blocked
     * @author Chaosruler972
     * @param db the database name that we are working with
     * @param table the table name that we are working with
     * @return a vector of the items inside this table, each element represent a row, hashmap represent cols
     */
    fun get_all_table(db: String, table: String): Vector<HashMap<String, String>> {
        val vector: Vector<HashMap<String, String>> = Vector()
        try {
            connection!!.isReadOnly
        } catch (e: SQLException) {
            if (e.errorCode == 0) {
                ReConnect()
            } else {
                Log.d("remote SQL", "EXCEPTION ${e.message}")
            }
        } catch (e: KotlinNullPointerException) {
            ReConnect()
            Log.d("remote SQL", "EXCEPTION kotlin null pointer exception")
        }
        if (!isvalid)
            return vector
        try {
            val lock = java.lang.Object()
            AsyncTask.execute(
                    {
                        var rs: ResultSet?
                        try {
                            rs = connection!!.createStatement().executeQuery("USE [$db] SELECT * FROM [dbo].[$table]")
                        } catch (e: SQLTimeoutException) {
                            Log.d("remote SQL", "EXCEPTION SQL timeout")
                            rs = null
                        } catch (e: SQLException) {
                            Log.d("remote SQL", "EXCEPTION ${e.message}")
                            rs = null
                        } catch (e: KotlinNullPointerException) {
                            Log.d("remote SQL", "EXCEPTION kotlin null pointer exception")
                            rs = null
                        }
                        if (rs == null)
                            return@execute
                        val columnCount = rs.metaData.columnCount
                        val rs_meta = rs.metaData
                        while (rs.next()) {
                            val map: HashMap<String, String> = HashMap()
                            for (i in 1..(columnCount)) {
                                val colum_name: String = rs_meta.getColumnName(i)
                                try {
                                    map[colum_name] = rs.getString(colum_name)
                                } catch (e: Exception) {
                                    map[colum_name] = ""
                                }
                            }
                            vector.addElement(map)
                        }
                        synchronized(lock)
                        {
                            lock.notify()
                        }
                    })
            try {
                synchronized(lock)
                {
                    lock.wait()
                }
            } catch (e: InterruptedException) {
                Log.d("remote SQL", "done syncing table")
            }

        } catch (e: SQLException) {
            Log.d("remote SQL", "EXCEPTION ${e.message}")
            exception = e

        }
        return vector
    }


    /**
     *   simulates select * from db where ...
     *   @author Chaosruler972
     *   @param colm_to_type what type are the colms
     *   @param db the database name
     *   @param table the table name
     *   @param where_column where clause
     *   @param where_compare where clause data (compare to)
     *   @return a vector of hashmap represnting the results, each element represent a row, hashmap represnts col
     */
    fun select_columns_from_db_with_where(db: String, table: String, colm_to_type: HashMap<String, String>, where_column: String?, where_compare: String?): Vector<HashMap<String, String>> {
        val vector: Vector<HashMap<String, String>> = Vector()
        try {
            connection!!.isReadOnly
        } catch (e: SQLException) {
            if (e.errorCode == 0) {
                ReConnect()
            }
            Log.d("remote SQL", "EXCEPTION ${e.message}")
        } catch (e: KotlinNullPointerException) {
            ReConnect()
            Log.d("remote SQL", "EXCEPTION kotlin null pointer exception")
        }
        if (!isvalid)
            return vector
        try {
            val lock = java.lang.Object()
            AsyncTask.execute(
                    {
                        var rs: ResultSet?
                        try {
                            var qry = "USE [$db] SELECT "
                            var first = false
                            for (column in colm_to_type) {
                                if (first)
                                    qry += " ,"
                                qry += "[${column.key}]"
                                first = true
                            }

                            qry += " FROM [dbo].[$table] "
                            if (where_column != null && where_compare != null) {
                                val item: String = if (colm_to_type.getValue(where_column) == "text" || colm_to_type.getValue(where_column) == "varchar" || colm_to_type.getValue(where_column) == "nvarchar") {
                                    "N" + add_quotes(where_compare)
                                } else
                                    where_compare
                                qry += "WHERE "
                                qry += "CONVERT(${colm_to_type.getValue(where_column)},$where_column) = $item"
                            }
                            rs = connection!!.createStatement().executeQuery(qry)
                        } catch (e: SQLTimeoutException) {
                            Log.d("remote SQL", "EXCEPTION SQL timeout exception")
                            rs = null
                        } catch (e: SQLException) {
                            Log.d("remote SQL", "EXCEPTION ${e.message}")
                            rs = null
                        } catch (e: KotlinNullPointerException) {
                            Log.d("remote SQL", "EXCEPTION kotlin null pointer exception")
                            rs = null
                        }
                        if (rs == null)
                            return@execute
                        val columnCount = rs.metaData.columnCount
                        val rs_meta = rs.metaData
                        while (rs.next()) {
                            val map: HashMap<String, String> = HashMap()
                            for (i in 1..(columnCount)) {
                                val colum_name: String = rs_meta.getColumnName(i)
                                try {
                                    map[colum_name] = rs.getString(colum_name)
                                } catch (e: Exception) {
                                    map[colum_name] = ""
                                }
                            }
                            vector.addElement(map)
                        }
                        synchronized(lock)
                        {
                            lock.notify()
                        }
                    })
            try {
                synchronized(lock)
                {
                    lock.wait()
                }
            } catch (e: InterruptedException) {
                Log.d("remote SQL", "done syncing table")
            }

        } catch (e: SQLException) {
            Log.d("remote SQL", "EXCEPTION ${e.message}")
            exception = e

        }
        return vector
    }


    /**
     * subroutine to take parameters of an update query and template it into MSSQL acceptable query, excepts update parameters to be with quotes if neccesery
     * @param command the command to run (the query)
     * @author Chaosruler972
     * @return if command succeeded or not
     */
    fun run_command(command: String): Boolean {
        try {
            connection!!.isReadOnly
        } catch (e: SQLException) {
            if (e.errorCode == 0)
                ReConnect()
        } catch (e: KotlinNullPointerException) {
            ReConnect()
        }
        if (!isvalid)
            return false
        try {
            if (connection!!.isReadOnly) {
                return false
            }
            var return_value = false
            val lock = java.lang.Object()
            AsyncTask.execute {
                Runnable {
                    try {
                        return_value = connection!!.prepareStatement(command).executeUpdate() >= 0
                    } catch (e: SQLTimeoutException) {
                        Log.d("remote SQL", "Timed out! command: $command")
                    } catch (e: SQLException) {
                        Log.d("remote SQL", "EXCEPTION command: $command and exception ${e.message}")
                    }
                    synchronized(lock)
                    {
                        lock.notify()
                    }
                }.run()
            }
            try {
                synchronized(lock)
                {
                    lock.wait()
                }
            } catch (e: InterruptedException) {
                Log.d("remote SQL", "Done sync")
            }
            return return_value
        } catch (e: SQLException) {
            Log.d("remote SQL", "EXCEPTION command: $command and exception ${e.message}")
            exception = e
            return false
        }

    }

    /**
     * subroutine to handle disconnection
     * @author Chaosruler972
     */
    fun Disconnect() {
        if (isvalid) {
            isvalid = false
            if (connection != null)
                connection!!.close()
        }
    }

    /**
     *  subroutine to handle result set converted vector to String, for testing purposes
     * @author Chaosruler972
     * @param vector the vector that we want to stringify
     * @return the stringfied form of that vector we got, or "Empty" if vector is empty
     */
    fun VectorToString(vector: Vector<HashMap<String, String>>?): String {
        if (vector == null || vector.size == 0)
            return "Empty"
        var str = ""
        var i = 0
        for (item in vector) {
            str += "row ${++i}: "
            for (colum in item) {
                str += "[${colum.key}] = ${colum.value} "
            }
            str += "\n"

        }
        return str
    }


    /**
     * subroutine in charge to check if connection is alive
     * @author Chaosruler972
     * @return True if connection is alive
     */
    @Suppress("unused")
    fun isAlive(): Boolean {
        try {
            connection!!.isReadOnly
        } catch (e: SQLException) {
            if (e.errorCode == 0) {
                ReConnect()
            } else {
                Log.d("remote SQL", "EXCEPTION ${e.message}")
            }
        } catch (e: KotlinNullPointerException) {
            ReConnect()
            Log.d("remote SQL", "EXCEPTION null pointer exception")
        }
        if (!isvalid)
            return false
        val lock = java.lang.Object()
        AsyncTask.execute {
            Runnable {
                try {
                    connection!!.prepareStatement(context.getString(R.string.get_date_from_server)).execute()
                } catch (e: SQLException) {
                    Log.d("remote SQL", "EXCEPTION ${e.message}")
                }
                synchronized(lock)
                {
                    lock.notify()
                }
            }
        }

        try {
            synchronized(lock)
            {
                lock.wait()
            }
        } catch (e: InterruptedException) {
            Log.d("remote SQL", "Done login sync")
        }
        return true
    }

    /**
     *     android lifecycle - is in charge of giving a living context, maybe be called once on main activity for all application lifecycle
     *
     * IS CALLED ON MAINACTIVITY, ITS ENOUGH HANDLING ON THAT DEPARTMENT
     * @author Chaosruler972
     * @param con the base context we want to refresh
     */
    fun refresh_context(con: Context) {
        context = con
    }

    /**
     *  Attempts to reconnect with the server if connection is broke
     *  @author Chaosruler972
     *  @return true if we managed to reconnect, false if not
     */
    private fun ReConnect(): Boolean {
        isvalid = false
        connection = null
        return Connect(context, username, password, act)

    }

    /**
     *  subroutine to constuct an add statement for the MSSQL
     *  @author Chaosruler972
     *  @param db the database we are working with
     *  @param table the table name we are working with
     *  @param vector what data we want to add
     *  @param map what field types we have on that table that we add data to
     *  @return a string of the MSSQL command to add that specified data
     */
    fun construct_add_str(db: String, table: String, vector: Vector<String>, map: HashMap<String, String>): String {
        var command: String = "USE [$db] " +
                "INSERT INTO [dbo].[$table] ("

        for (item in vector) {
            command += "[$item]"
            if (item != vector.lastElement())
                command += ","
        }
        command += ") VALUES ("

        for (item in vector) {
            command += map[item]
            if (item != vector.lastElement())
                command += ","
        }
        command += ")"
        return command
    }


    /**
     *  subroutine to construct a remove statement for the MSSQL database
     *  @author Chaosruler972
     *  @param db the database we are working with
     *  @param table the table name we are working with
     *  @param where_clause the whereclause to find what data to remove
     *  @param compare_to what data to compare our data to
     *  @param type what is the type of the data we compare
     *  @return a string of the MSSQL command to remove that specified data
     */
    fun construct_remove_str(db: String, table: String, where_clause: String, compare_to: Array<String>, type: String): String {
        var command: String = "USE [$db]" +
                " DELETE FROM [dbo].[$table] WHERE "
        for (item in compare_to) {
            command += "CONVERT($type,$where_clause) = $item "
            if (item != compare_to.last())
                command += " OR "
        }
        return command
    }


    /**
     *  subroutine to construct a update statement for the MSSQL database
     *   for mulitple where clauses
     *  @author Chaosruler972
     *  @param db the database we are working with
     *  @param table the table name we are working with
     *  @param where_clause the whereclause to find what data to remove
     *  @param compare_to what data to compare our data to
     *  @param type what is the type of the data we compare
     *  @param update_to what to update our data to
     *  @return a string of the MSSQL command to update that specified data
     */
    fun construct_update_str(db: String, table: String, where_clause: String, compare_to: Array<String>, type: String, update_to: HashMap<String, String>): String {
        var command: String = "USE [$db]" +
                " UPDATE [dbo].[$table] SET "
        var breaker = 0
        for (item in update_to) {
            command += " [${item.key}] = ${item.value} "
            breaker++
            if (breaker < update_to.size)
                command += " , "
            else
                break
        }
        command += " WHERE "
        for (item in compare_to) {
            command += "CONVERT($type,$where_clause) = $item "
            if (item != compare_to.last())
                command += " OR "
        }
        return command
    }


    /**
     *  subroutine to construct a update statement for the MSSQL database
     *   for mulitple where clauses
     *   like previous, only multi-variables
     *  @author Chaosruler972
     *  @param db the database we are working with
     *  @param table the table name we are working with
     *  @param where_clause the whereclause to find what data to remove
     *  @param update_to what to update our data to
     *  @param all_type whats the type of all our data that we compare
     *  @return a string of the MSSQL command to update that specified data
     */
    fun construct_update_str_multiwhere_text(db: String, table: String, where_clause: HashMap<String, String>, all_type: String, update_to: HashMap<String, String>): String {
        var command: String = "USE [$db]" +
                " UPDATE [dbo].[$table] SET "
        var breaker = 0
        for (item in update_to) {
            command += " [${item.key}] = ${item.value} "
            breaker++
            if (breaker < update_to.size)
                command += " , "
            else
                break
        }
        command += " WHERE "
        var where_counter = 0
        for (item in where_clause) {
            command += "CONVERT($all_type,${item.key}) = '${item.value}' "
            where_counter++
            if (where_counter < where_clause.size)
                command += " AND "
        }
        return command
    }

    /**
     * add quotes to a given string
     * @author Chaosruler972
     * @param str a string that we want to add quotes to
     * @return the input strings with quote to the leftmost and to the rightmost
     */
    fun add_quotes(str: String): String = "'$str'"

    /**
     *  looks at entire hashmap and add quotes if needed
     *  @author Chaosruler972
     *  @param input the input hashmap that we want to stringify the value if eeded
     *  @param types the typemap, to know if we want to stringify results
     */
    @Suppress("unused")
    fun nirmol_input(input: HashMap<String, String>, types: HashMap<String, String>) {
        for (column in input) {
            if (types[column.key] != null && (types[column.key] == "text" || types[column.key] == "varchar" || types[column.key] == "nvarchar")) {
                column.setValue("N" + add_quotes(column.value))
            }
        }
    }


}