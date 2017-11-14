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
        val con = InstrumentationRegistry.getTargetContext()
        var test_num = 1
        val amount_of_tests = 1
        val amount_str = " Out of $amount_of_tests "
        var vector:Vector<HashMap<String,String>> = Vector()
        val username = "chaosruler"
        val password = "*niu9p2w"

        System.out.println("This test requires internet connection!\ninitating ")
        var remote_connection:remote_SQL_Helper
        try
        {
            remote_connection = remote_SQL_Helper(con, username,password)
            Assert.assertTrue("remote database tests: connect #$test_num$amount_str: fails\n ", remote_connection.isValid() )
            vector = remote_connection.get_all_table("main","projects")!!
            var str:String = "DB length = ${vector.size}: "
            for (item in vector)
            {
                for(value in item)
                {
                    str+="[${value.key}] = ${value.value} "
                }
                str+="\n"
            }
            Log.d("key",str)



            Log.d("remote database tests", "connect #$test_num$amount_str: success\n ")
        } catch (e: Exception) {
            Log.d("remote database tests", "connect #$test_num$amount_str: fails\n ")
        }



    }

}



