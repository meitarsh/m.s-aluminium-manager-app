package com.example.chaosruler.msa_manager

import android.content.Context
import android.os.AsyncTask
import android.widget.Toast

import java.sql.Connection
import java.sql.DriverManager
import java.util.*
import kotlin.collections.HashMap

class remote_SQL_Helper()
{
    companion object {

        private lateinit var context:Context
        private lateinit var username: String
        private lateinit var password: String
        private var isvalid: Boolean = false
        private var connection: Connection? = null

        fun getusername():String
                = username

        fun isValid():Boolean
                = isvalid

        /*
            subroutine neccesery to connect to db, without this call, no DB operations can be done
         */
        fun Connect(con:Context,user: String, pass: String): Boolean
        {
            if (isvalid)
                return true
            context = con
            username = user
            password = pass
            try {
                Class.forName(context.getString(R.string.class_jtds_jdbc))
                var con: Connection? = DriverManager.getConnection(
                        context.getString(R.string.REMOTE_CONNECT_STRING) + context.getString(R.string.REMOTE_IP_ADDR)
                        , username,
                        password)
                if (con != null) {
                    isvalid = true
                    connection = con
                }

            }
            catch (e: Exception)
            {
                e.printStackTrace()
                isvalid = false

            }
            return isvalid
        }

        /*
            subroutine that takes as parameters the add query, it templates it into MSSQL format
            and sends it, it expects values to be with quotes if neccesery
         */
        fun add_data(db: String, table: String, vector: Vector<String>, map: HashMap<String, String>): Boolean {
            if(!isvalid)
                return false
            try
            {
                if(connection!!.isReadOnly)
                    return false
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
                AsyncTask.execute(Runnable {  connection!!.prepareStatement(command).execute() })
                return true
            }
            catch (e: Exception)
            {
                e.printStackTrace()
                return false
            }

        }

        /*
            subroutine to take as parameters the data to remove and what it matches to, it templates
            it into MSSQL query and sends it, expects compare_to to be with quotes if neccesery
         */
        fun remove_data(db: String, table: String, where_clause: String, compare_to: Array<String>, type: String): Boolean {
            if(!isvalid)
                return false
            try
            {
                if(connection!!.isReadOnly)
                    return false
                var command: String = "USE [$db]" +
                        " DELETE FROM [dbo].[$table] WHERE "
                for (item in compare_to) {
                    command += "CONVERT($type,$where_clause) = $item "
                    if (item != compare_to.last())
                        command += " OR "
                }
                AsyncTask.execute(Runnable { connection!!.prepareStatement(command).execute() })
                return true
            }
            catch (e: Exception)
            {
                e.printStackTrace()
                return false
            }

        }
        /*
            subroutine that gets as parameters an entire table and converts it into Hashmap vector, which is later can be converted to string, sql is select * form table
         */
        fun get_all_table(db: String, table: String): Vector<HashMap<String, String>>? {
            if(!isvalid)
                return null
            try
            {
                var vector: Vector<HashMap<String, String>> = Vector()
                var done:Boolean = false
                AsyncTask.execute({
                    val rs = connection!!.createStatement().executeQuery("USE [$db] SELECT * FROM [dbo].[$table]")
                    val columnCount = rs.metaData.columnCount
                    val rs_meta = rs.metaData
                    while (rs.next()) {
                        var map: HashMap<String, String> = HashMap()
                        for (i in 1..(columnCount)) {
                            var colum_name: String = rs_meta.getColumnName(i)
                            map[colum_name] = rs.getString(colum_name)
                        }
                        vector.addElement(map)
                    }
                    done=true
                })
                while(true)
                {
                    if(!done)
                        Thread.sleep(300)
                    else
                        break
                }
                return vector
            }
            catch (e: Exception)
            {
                e.printStackTrace()
                return null
            }

        }
        /*
            subroutine to take parameters of an update query and template it into MSSQL acceptable query, excepts update parameters to be with quotes if neccesery
         */
        fun update_query(db: String, table: String, where_clause: String, compare_to: Array<String>, type: String, update_to: HashMap<String, String>): Boolean {
            if(!isvalid)
                return false
            try
            {
                if(connection!!.isReadOnly)
                    return false
                var command: String = "USE [$db]" +
                        " UPDATE [dbo].[$table] SET "
                var breaker: Int = 0
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
                AsyncTask.execute { Runnable {   connection!!.prepareStatement(command).execute() } }

                return true
            }
            catch (e: Exception)
            {
                e.printStackTrace()
                return false
            }

        }
        /*
            subroutine to handle disconnection
         */
        fun Disconnect()
        {
            if(this.isvalid)
            {
                this.isvalid = false
                connection!!.close()
            }
        }

        /*
            subroutine to handle result set converted vector to String, for testing purposes
         */
        fun VectorToString(vector: Vector<HashMap<String, String>>?): String {
            if(vector == null)
                return "Empty"
            var str: String = ""
            var i: Int = 0
            for (item in vector) {
                str += "row ${++i}: "
                for (colum in item) {
                    str += "[${colum.key}] = ${colum.value} "
                }
                str += "\n"

            }
            return str
        }

        /*
            subroutine in charge to check if connection is alive
         */
        public fun isAlive():Boolean
        {
            if(!isvalid)
                return false
            try
            {
                connection!!.prepareStatement(context.getString(R.string.get_date_from_server)).execute()
                return true
            }
            catch (e:Exception)
            {
                e.printStackTrace()
                return false
            }
        }

        /*
            android lifecycle - is in charge of giving a living context, maybe be called once on main activity for all application lifecycle

            IS CALLED ON MAINACTIVITY, ITS ENOUGH HANDLING ON THAT DEPARTMENT
         */
        fun refresh_context(con: Context)
        {
            this.context = con
        }

    }
}