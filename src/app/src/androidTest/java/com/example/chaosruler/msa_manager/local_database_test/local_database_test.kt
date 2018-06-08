package com.example.chaosruler.msa_manager.local_database_test
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.example.chaosruler.msa_manager.abstraction_classes.local_SQL_Helper


import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Assert.assertTrue
import java.util.*

/**
 * a Unit test to test local User database on SQLITE
 * Since all local databases extend from the same class, we don't need to test them all
 * @author Chaosruler972
 */
@RunWith(AndroidJUnit4::class)
class local_database_test {
    @Test
    fun test_local_database()
    {
        val con = InstrumentationRegistry.getTargetContext()
        val db = dull_database(con)
        db.empty_db()
        val dummy_data_1 = "1"
        val dummy_data_2 = "2"

        Log.d("test_local_database", "Testing for insert")
        db.insert(dummy_data_1)
        assertTrue("Failed on insert #1 test", db.get_entire_db().size == 1)

        Log.d("test_local_database", "Testing for insert again")
        db.insert(dummy_data_1)
        assertTrue("Failed on insert #2 test", db.get_entire_db().size == 1)

        Log.d("test_local_database", "Another insert test")
        db.insert(dummy_data_2)
        assertTrue("Failed on insert #3 test", db.get_entire_db().size == 2)

        Log.d("test_local_database", "remove test")

        assertTrue("Failed on remove #1 test", db.remove_data(dummy_data_2))

        Log.d("test_local_database", "remove test again")

        assertFalse("Failed on remove #2 test", db.remove_data(dummy_data_2))

        Log.d("test_local_database", "Data test")
        assertTrue("Failed on data #1 test", db.get_entire_db().size == 1)

        Log.d("test_local_database", "integrity test")
        assertTrue("Failed on integrity test #1", db.get_entire_db()[0]["data"] == dummy_data_1)

    }
}

