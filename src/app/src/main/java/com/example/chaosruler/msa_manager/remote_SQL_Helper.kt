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

        public fun getusername():String
                = username

        public fun isValid():Boolean
                = isvalid

        public fun Connect(con:Context,user: String, pass: String): Boolean {
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

            } catch (e: Exception) {
                e.printStackTrace()
                isvalid = false

            }
            return isvalid
        }

        public fun add_data(db: String, table: String, vector: Vector<String>, map: HashMap<String, String>): Boolean {
            try {
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
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }

        }


        public fun remove_data(db: String, table: String, where_clause: String, compare_to: Array<String>, type: String): Boolean {
            try {
                var command: String = "USE [$db]" +
                        " DELETE FROM [dbo].[$table] WHERE "
                for (item in compare_to) {
                    command += "CONVERT($type,$where_clause) = $item "
                    if (item != compare_to.last())
                        command += " OR "
                }
                AsyncTask.execute(Runnable { connection!!.prepareStatement(command).execute() })
                return true
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }

        }

        public fun get_all_table(db: String, table: String): Vector<HashMap<String, String>>? {
            try {
                var vector: Vector<HashMap<String, String>> = Vector()
                var done:Boolean = false
                AsyncTask.execute(Runnable {
                    val rs = connection!!.createStatement().executeQuery("USE [$db] SELECT * FROM [dbo].[$table]")
                    val columnCount = rs.metaData.columnCount
                    val rs_meta = rs.metaData
                    while (rs.next()) {
                        var map: HashMap<String, String> = HashMap()
                        for (i in 0..(columnCount - 1)) {
                            var colum_name: String = rs_meta.getColumnName(i + 1)
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

        public fun update_query(db: String, table: String, where_clause: String, compare_to: Array<String>, type: String, update_to: HashMap<String, String>): Boolean {
            try {
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
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }

        }

        public fun Disconnect() {
            this.isvalid = false
            connection!!.close()
        }

        public fun VectorToString(vector: Vector<HashMap<String, String>>?): String {
            if(vector == null)
                return "Empty"
            var str: String = ""
            var i: Int = 0
            for (item in vector) {
                str += "row $i: "
                for (colum in item) {
                    str += "[${colum.key}] = ${colum.value} "
                }
                str += "\n"
            }
            return str
        }
    }
}