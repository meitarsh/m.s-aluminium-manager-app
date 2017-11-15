package com.example.chaosruler.msa_manager

import android.content.Context
import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.util.*
import kotlin.collections.HashMap

class remote_SQL_Helper(private  val context: Context,private var username:String, private var password:String)
{
    init
    {
        Connect()
    }
    private var isValid:Boolean = false
    private var connection:Connection? = null
    public fun isValid():Boolean
    {
        return isValid
    }

    public fun Connect():Boolean
    {
        if(isValid())
            return true
        try
        {
            Class.forName(context.getString(R.string.class_jtds_jdbc))
            var con: Connection = DriverManager.getConnection(
                    context.getString(R.string.REMOTE_CONNECT_STRING) + context.getString(R.string.REMOTE_IP_ADDR)
                    ,username,
                    password)
            if(con!=null)
            {
                isValid = true
                connection = con
            }

        }
        catch (e : Exception)
        {
           e.printStackTrace()
           isValid=false
        }
        return isValid
    }

    public fun add_data( db:String,  table:String, vector: Vector<String>, map : HashMap<String,String>): Boolean
    {
        try
        {
            var command:String = "USE [$db] " +
                    "INSERT INTO [dbo].[$table] ("

            for(item in vector)
            {
                command+="[$item]"
                if(item != vector.lastElement())
                    command+=","
            }
            command+=") VALUES ("

            for(item in vector)
            {
                command += map[item]
                if(item!=vector.lastElement())
                    command+=","
            }
            command+=")"
            connection!!.prepareStatement(command).execute()
            return true
        }
        catch (e: Exception)
        {
            System.out.println("Exception: " + e.localizedMessage)
            e.printStackTrace()
            return false
        }

    }


    public fun remove_data(db:String,  table:String, where_clause:String, compare_to:Array<String>, type:String): Boolean
    {
        try {
            var command:String = "USE [$db]" +
                    " DELETE FROM [dbo].[$table] WHERE "
            for(item in compare_to)
            {
                command += "CONVERT($type,$where_clause) = $item "
                if(item != compare_to.last())
                    command += " OR "
            }
            connection!!.prepareStatement(command).execute()
            return true
        }
        catch (e: Exception)
        {
            e.printStackTrace()
            return false
        }

    }

    public fun get_all_table(db:String,table:String): Vector<HashMap<String, String>>?
    {
        try
        {
            val rs = connection!!.createStatement().executeQuery("USE [$db] SELECT * FROM [dbo].[$table]")
            val columnCount = rs.metaData.columnCount
            var vector:Vector<HashMap<String,String>> = Vector()
            val rs_meta = rs.metaData
            while (rs.next())
            {
                var map: HashMap<String,String> = HashMap()
                for(i in 0..(columnCount-1))
                {
                    var colum_name:String = rs_meta.getColumnName(i+1)
                    map[colum_name] = rs.getString(colum_name)
                }
                vector.addElement(map)
            }
            return vector
        }
        catch (e: Exception)
        {
            e.printStackTrace()
            return null
        }

    }

    public fun update_query(db:String, table:String, where_clause:String, compare_to:Array<String>, type:String, update_to : HashMap<String,String>): Boolean
    {
        try {
            var command:String = "USE [$db]" +
                    " UPDATE [dbo].[$table] SET "
            var breaker:Int = 0
            for(item in update_to)
            {
                command += " [${item.key}] = ${item.value} "
                breaker++
                if(breaker<update_to.size)
                    command += " , "
                else
                    break
            }
            command += " WHERE "
            for(item in compare_to)
            {
                command += "CONVERT($type,$where_clause) = $item "
                if(item != compare_to.last())
                    command += " OR "
            }
            connection!!.prepareStatement(command).execute()
            return true
        }
        catch (e: Exception)
        {
            e.printStackTrace()
            return false
        }

    }

    public fun Disconnect()
    {
        this.isValid = false
        connection!!.close()
    }

}