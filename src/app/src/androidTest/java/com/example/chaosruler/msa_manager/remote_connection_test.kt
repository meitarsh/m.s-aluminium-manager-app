package com.example.chaosruler.msa_manager

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import kotlin.collections.HashMap


@RunWith(AndroidJUnit4::class)
class remote_connection_test {
    @Test
    fun remote_connection_test()
    {
        val can_run_test = true

        /*
        if(!can_run_test)
            return
        */

        val con = InstrumentationRegistry.getTargetContext()
        var test_num = 1
        val amount_of_tests = 7
        val amount_str = " Out of $amount_of_tests "
        val username = "chaosruler"
        val password = "*niu9p2w"
        val db_name = "main"
        val table_name = "projects"

        val project_ID = "project_ID"
        val project_name = "project_name"
        val project_manager_name = "project_manager_name"


        System.out.println("This test requires internet connection!\ninitating ")
        try
        {
            remote_SQL_Helper.Connect(con,username,password)
            Assert.assertTrue("remote database tests: connect #$test_num$amount_str: fails\n ", remote_SQL_Helper.isValid() )
            Log.d("remote database tests", "connect #$test_num$amount_str: success\n ")
        } catch (e: Exception) {
            Log.d("remote database tests", "connect #$test_num$amount_str: fails\n ")
        }

        test_num++

        try
        {
            var string_vector:Vector<String> = Vector()
            string_vector.addElement(project_ID)
            string_vector.addElement(project_name)
            string_vector.addElement(project_manager_name)
            var map:HashMap<String,String> = HashMap()
            map[project_ID] = 1.toString()
            map[project_name] = "'hi'"
            map[project_manager_name] = "'bye'"
            Assert.assertTrue("remote database tests: add #$test_num$amount_str: fails\n ", remote_SQL_Helper.add_data(db_name,table_name,string_vector,map) )
            Log.d("remote database tests", "add #$test_num$amount_str: success\n ")
        } catch (e: Exception)
        {
            Log.d("remote database tests", "add #$test_num$amount_str: fails\n ")
        }

        test_num++

        try
        {

            Assert.assertTrue("remote database tests: print all #$test_num$amount_str: fails\n ", remote_SQL_Helper.get_all_table(db_name,table_name)!!.size==1 )
            printVector(remote_SQL_Helper.get_all_table(db_name,table_name)!!)
            Log.d("remote database tests", "print all #$test_num$amount_str: success\n ")
        } catch (e: Exception) {
            Log.d("remote database tests", "print all#$test_num$amount_str: fails\n ")
        }


        test_num++

        try
        {
            var map:HashMap<String,String> = HashMap()
            map[project_ID] = 2.toString()
            Assert.assertTrue("remote database tests: update #$test_num$amount_str: fails\n ", remote_SQL_Helper.update_query(db_name,table_name,project_ID, arrayOf("1"),"int",map) )
            Log.d("remote database tests", "update #$test_num$amount_str: success\n ")
        } catch (e: Exception)
        {
            Log.d("remote database tests", "update #$test_num$amount_str: fails\n ")
        }


        test_num++

        try
        {

            Assert.assertTrue("remote database tests: print all #$test_num$amount_str: fails\n ", remote_SQL_Helper.get_all_table(db_name,table_name)!!.size==1 )
            printVector(remote_SQL_Helper.get_all_table(db_name,table_name)!!)
            Log.d("remote database tests", "print all #$test_num$amount_str: success\n ")
        } catch (e: Exception)
        {
            Log.d("remote database tests", "print all#$test_num$amount_str: fails\n ")
        }


        test_num++

        try
        {

            Assert.assertTrue("remote database tests: remove #$test_num$amount_str: fails\n ", remote_SQL_Helper.remove_data(db_name,table_name,project_manager_name, arrayOf("'bye'"),"varchar(max)") )
            Log.d("remote database tests", "remove  #$test_num$amount_str: success\n ")
        } catch (e: Exception) {
            Log.d("remote database tests", "remove  #$test_num$amount_str: fails\n ")
        }

        test_num++

        try
        {
            Assert.assertTrue("remote database tests: print all #$test_num$amount_str: fails\n ", remote_SQL_Helper.get_all_table(db_name,table_name)!!.size==0 )
            printVector(remote_SQL_Helper.get_all_table(db_name,table_name)!!)
            Log.d("remote database tests", "print all #$test_num$amount_str: success\n ")
        } catch (e: Exception)
        {
            Log.d("remote database tests", "print all#$test_num$amount_str: fails\n ")
        }

    }

    public fun printVector(vector:Vector<HashMap<String,String>>):String
    {
        var str:String = ""
        var i:Int = 0
        for(item in vector)
        {
            str+= "row $i: "
            for(colum in item)
            {
                str+="[${colum.key}] = ${colum.value} "
            }
            str+="\n"
        }
        return str
    }


}



