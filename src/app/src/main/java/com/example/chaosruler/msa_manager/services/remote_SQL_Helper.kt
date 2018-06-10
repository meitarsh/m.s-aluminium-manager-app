package com.example.chaosruler.msa_manager.services

import android.annotation.SuppressLint
import android.content.Context
import android.net.VpnService
import android.os.AsyncTask
import android.preference.PreferenceManager
import com.example.chaosruler.msa_manager.BuildConfig
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.activies.LoginActivity
import com.example.chaosruler.msa_manager.object_types.User
import com.example.chaosruler.msa_manager.services.deprected_vpn_packages.VPN_google_toyVPN.vpn_connection
import java.sql.*
import java.util.*
import java.util.Date
import kotlin.collections.HashMap

/**
 * a singleton object that connects us to the database and sends queries to it
 * responsible for all the remote commands
 * @author Chaosruler972
 * @constructor constructed automaticily, but should call connect() before operations
 */
@SuppressLint("StaticFieldLeak")
object remote_SQL_Helper {


    /**
     * The current working context
     * @author Chaosruler972
     */
    @SuppressLint("StaticFieldLeak")
    private lateinit var context: Context
    /**
     * the last calling activity that needs that login, usually MainActivity should hold this
     * @author Chaosruler972
     */
    private lateinit var act: LoginActivity
    /**
     * The login username
     * @author Chaosruler972
     */
    private var username: String = ""
    /**
     * The current user
     * @author Chaosruler972
     */
    var user: User? = null
    /**
     * The login password
     * @author Chaosruler972
     */
    private var password: String = ""
    /**
     * if connection is still valid
     * @author Chaosruler972
     */
    private var isvalid: Boolean = false
    /**
     * instance of the SQL connection from JTDS
     * @author Chaosruler972
     * @see net.sourceforge.jtds
     */
    private var connection: Connection? = null
    /**
     * last exception got
     * @author Chaosruler972
     */
    private lateinit var exception: SQLException

    /**
     * the name of the sync clumn
     * @author Chaosruler972
     */
    private var sync_column: String? = null

    /**
     * username logged in
     * @author Chaosruler972
     * @return gets the username that is logged in
     */
    fun getusername(): String = username

    /**
     * password of user logged in
     * @author Chaosruler972
     * @return gets the password that is logged in
     */
    fun getpassword(): String = password

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
    fun Connect(con: Context, user: String, pass: String, act: LoginActivity): Boolean {
        if (isvalid)
            return true
        context = con
        username = user
        password = pass
        sync_column = con.getString(R.string.moddate)
        this.act = act

        if (vpn_connection.check_if_need_to_connect(con)) {
            val i = VpnService.prepare(act)
            if (i == null) {
                act.startActivityForResult(i, act.resources.getInteger(R.integer.VPN_request_code))
            } else {
                act.mark_vpn_ready(i)
            }
        }

        if(connection!=null)
            connection!!.close()
        val ip: String = PreferenceManager.getDefaultSharedPreferences(con).getString(con.getString(R.string.IP), context.getString(R.string.REMOTE_IP_ADDR))
        val port: String = PreferenceManager.getDefaultSharedPreferences(con).getString(con.getString(R.string.sql_port), con.getString(R.string.default_port_num))

        val windows_auth = if (con.resources.getBoolean(R.bool.default_windows_auth))
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
            global_variables_dataclass.log("remote SQL", "EXCEPTION ${e.message}")
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
        /**
         * Depracted and removed
         */
        assert(false)
        val vector: Vector<HashMap<String, String>> = Vector()
        try {
            connection!!.isReadOnly
        } catch (e: SQLException) {
            return if (e.errorCode == 0) {
                ReConnect()
                vector
            } else {
                global_variables_dataclass.log("remote SQL", "EXCEPTION ${e.message}")
                vector
            }
        } catch (e: KotlinNullPointerException) {
            ReConnect()
            global_variables_dataclass.log("remote SQL", "EXCEPTION kotlin null pointer exception")
            return vector
        }
        if (!isvalid)
            return vector
        try {
            val lock = java.lang.Object()
            AsyncTask.execute(
                    {
                        var rs: ResultSet?
                        @Suppress("LiftReturnOrAssignment")
                        try {
//                            if(BuildConfig.DEBUG)
//                                rs = connection!!.createStatement().executeQuery("USE [$db] SELECT * FROM [dbo].[$table]")
//                            else
                                rs = connection!!.createStatement().executeQuery("USE [$db] SELECT * FROM [dbo].[$table]"
                                + " WHERE $sync_column >= dateadd(s,${get_latest_sync_time().time/1000},'19700101 00:00:00:000') ")
                            global_variables_dataclass.log("remote", "USE [$db] SELECT * FROM [dbo].[$table]"
                                        + " WHERE $sync_column >= dateadd(s,${get_latest_sync_time().time/1000},'19700101 00:00:00:000') ")
                        } catch (e: SQLTimeoutException) {
                            global_variables_dataclass.log("remote", "EXCEPTION SQL timeout")
                            rs = null
                        } catch (e: SQLException) {
                            global_variables_dataclass.log("remote", "EXCEPTION ${e.message}")
                            rs = null
                        } catch (e: KotlinNullPointerException) {
                            global_variables_dataclass.log("remote", "EXCEPTION kotlin null pointer exception")
                            rs = null
                        }
                        if (rs == null)
                        {
                            lock.notify()
                            return@execute
                        }
                        val columnCount = rs.metaData.columnCount
                        global_variables_dataclass.log("columns", "For DB $db is ${columnCount.toString()}")
                        val rs_meta = rs.metaData
                        while (rs.next()) {
                            val map: HashMap<String, String> = HashMap()
                            for (i in 1..(columnCount)) {
                                val colum_name: String = rs_meta.getColumnName(i)

                                try
                                {
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
                global_variables_dataclass.log("remote SQL", "done syncing table")
            }

        } catch (e: SQLException) {
            global_variables_dataclass.log("remote SQL", "EXCEPTION ${e.message}")
            exception = e

        }
        return vector
    }

    /**
     * gets the latest sync time of the current user
     * @author Chaosruler972
     * @return latest sync time of user
     */
    fun get_latest_sync_time() : Date
    {
        return user!!.get_last_sync_time()
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
    fun select_columns_from_db_with_where(db: String, table: String, colm_to_type: HashMap<String, String>, where_column: String?, where_compare: String?, modified_time: Boolean): Vector<HashMap<String, String>> {
        global_variables_dataclass.log("remote SQL", "Started")
        val vector: Vector<HashMap<String, String>> = Vector()
        try {
            connection!!.isReadOnly
        } catch (e: SQLException) {
            if (e.errorCode == 0) {
                ReConnect()
            }
            global_variables_dataclass.log("remote_SQL", "EXCEPTION ${e.message}")
        } catch (e: KotlinNullPointerException) {
            ReConnect()
            global_variables_dataclass.log("remote_SQL", "EXCEPTION kotlin null pointer exception")
        }
        if (!isvalid)
            return vector
        try {
            var rs: ResultSet?
            try {
                var qry = if (BuildConfig.DEBUG)
                    "USE [$db] SELECT TOP 1000 "
                else
                    "USE [$db] SELECT DISTINCT "
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
                    qry += " WHERE "
                    qry += " CONVERT(${colm_to_type.getValue(where_column)},$where_column) = $item "
                }
                if(modified_time) {
                    val where_or_not = if (where_column == null)
                        " WHERE "
                    else
                        " AND "
                    qry += "$where_or_not $sync_column >= dateadd(s,${get_latest_sync_time().time / 1000},'19700101 00:00:00:000')"
                }
                global_variables_dataclass.log("remote_SQL", qry)
                rs = connection!!.createStatement().executeQuery(qry)
            } catch (e: SQLTimeoutException) {
                global_variables_dataclass.log("remote_SQL", "EXCEPTION SQL timeout exception")
                rs = null
            } catch (e: SQLException) {
                global_variables_dataclass.log("remote_SQL", "EXCEPTION ${e.message}")
                rs = null
            } catch (e: KotlinNullPointerException) {
                global_variables_dataclass.log("remote_SQL", "EXCEPTION kotlin null pointer exception")
                rs = null
            }
            if (rs == null) {
                return vector
            }
            val columnCount = rs.metaData.columnCount
            global_variables_dataclass.log("remote_SQL", "For DB $db is ${columnCount.toString()}")
            val rs_meta = rs.metaData
            var count = 0
            while (rs.next()) {
                count++
                val map: HashMap<String, String> = HashMap()
                for (i in 1..(columnCount)) {
                    val colum_name: String = rs_meta.getColumnName(i)
                    if (colm_to_type[colum_name] == "datetime") {
                        val date = rs.getDate(colum_name)
                        map[colum_name] = date.time.toString()
                    } else {
                        try {
                            map[colum_name] = rs.getString(colum_name)
                        } catch (e: Exception) {
                            map[colum_name] = ""
                        }
                    }
                }
                vector.addElement(map)
            }
            global_variables_dataclass.log("remote_SQL", "Count for $table is $count")


        } catch (e: SQLException) {
            global_variables_dataclass.log("remote_SQL", "EXCEPTION ${e.message}")
            exception = e

        }
        global_variables_dataclass.log("remote_SQL", "Done with table $table")
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
            return false
        } catch (e: KotlinNullPointerException) {
            ReConnect()
            return false
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
                        global_variables_dataclass.log("rsql_exec", command)
                        return_value = connection!!.prepareStatement(command).executeUpdate() >= 0
                    } catch (e: SQLTimeoutException) {
                        global_variables_dataclass.log("remote SQL", "Timed out! command: $command")
                    } catch (e: SQLException) {
                        global_variables_dataclass.log("remote SQL", "EXCEPTION command: $command and exception ${e.message}")
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
                global_variables_dataclass.log("remote SQL", "Done sync")
            }
            return return_value
        } catch (e: SQLException) {
            global_variables_dataclass.log("remote SQL", "EXCEPTION command: $command and exception ${e.message}")
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
                try {
                    connection!!.close()
                }
                catch (e:Exception)
                {

                }
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
        return !connection!!.isClosed
    }

    /**
     *     android lifecycle - is in charge of giving a living context, maybe be called once on main activity for all application lifecycle
     *
     * IS CALLED ON MAINACTIVITY, ITS ENOUGH HANDLING ON THAT DEPARTMENT
     * @author Chaosruler972
     * @param con the base context we want to refres
     */
    fun refresh_context(con: Context) {
        context = con
        sync_column = con.getString(R.string.moddate)
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
    fun construct_add_str(db: String, table: String, vector: Vector<String>, map: HashMap<String, String>, modified_time: Boolean): String {
        var command: String = "USE [$db] " +
                "INSERT INTO [dbo].[$table] ("

        for (item in vector) {
            command += "[$item]"
            if(vector.lastElement() != item)
                command += ","
        }
        if(modified_time)
            command += " , ${sync_column!!}"
        command += ") VALUES ("

        for (item in vector) {
            command += map[item]
            if(vector.lastElement() != item)
                command += ","
        }
        if(modified_time)
            command += " , dateadd(s,${Date().time/1000},'19700101 00:00:00:000') "
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
    fun construct_update_str(db: String, table: String, where_clause: String, compare_to: Array<String>, type: String, update_to: HashMap<String, String>, modified_time: Boolean): String {
        var command: String = "USE [$db]" +
                " UPDATE [dbo].[$table] SET "
        var breaker = 0
        for (item in update_to) {
            command += " [${item.key}] = ${item.value} "
            breaker++
        }
        if(modified_time)
            command += " [$sync_column] = dateadd(s,${Date().time/1000},'19700101 00:00:00:000') "
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
    fun construct_update_str_multiwhere_text(db: String, table: String, where_clause: HashMap<String, String>, all_type: String, update_to: HashMap<String, String>, complete_map: HashMap<String, String>, modified_time: Boolean): String {
        var command: String = "USE [$db]" +
                " UPDATE [dbo].[$table] SET "
        var breaker = 0
        for (item in update_to) {
            command += " [${item.key}] = ${item.value} "
            breaker++
            if (breaker < update_to.size)
                command += " , "
            else if(modified_time)
            {
                command += " , [${context.getString(R.string.moddate)}] = dateadd(s,${Date().time/1000},'19700101 00:00:00:000') "
            }
        }
        command += " WHERE "
        var where_counter = 0
        for (item in where_clause) {
            command += "CONVERT($all_type,${item.key}) = '${item.value}' "
            where_counter++
            if (where_counter < where_clause.size)
                command += " AND "
        }
        command += " IF @@ROWCOUNT=0 "
        command += " INSERT INTO $table("
        breaker = 0
        for( item in complete_map)
        {
            command += item.key
            breaker++
            if( breaker < complete_map.size)
                command += " , "
            else if(modified_time)
            {
                    command += " , ${context.getString(R.string.moddate)} "
            }
        }
        command += ") values( "
        breaker = 0
        for(item in complete_map)
        {
            command += " ${item.value} "
            breaker++
            if( breaker < complete_map.size)
                command += " , "
            else if(modified_time)
                command += " , dateadd(s,${Date().time/1000},'19700101 00:00:00:000') "
        }
        command += " ) "
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

