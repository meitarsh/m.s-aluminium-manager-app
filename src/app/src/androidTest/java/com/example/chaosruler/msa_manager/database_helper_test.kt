package com.example.chaosruler.msa_manager

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log


import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Assert.assertTrue



@RunWith(AndroidJUnit4::class)
class database_helper_test {
    @Test
    fun user_database_tests() {
        val con = InstrumentationRegistry.getTargetContext()
        val db = user_database_helper(con)
        var test_num = 1
        val amount_of_tests = 14
        val amount_str = " Out of $amount_of_tests "

        val username = "dummy"
        val password = "1234"
        val password2 = password + "9"

        db.delete_user( username) // non test, delete data

        try {

            assertFalse("user database tests: register #$test_num$amount_str: fails\n ", db.add_user( "", password))
            Log.d("user database tests", "register #$test_num$amount_str: success\n ")
        }
        catch (e: AssertionError)
        {
            Log.d("user database tests", "register #$test_num$amount_str: fails\n ")
        }

        test_num++


        try {
            assertFalse("user database tests: find user #$test_num$amount_str: fails\n ", db.check_user( username))
            Log.d("user database tests", "find user #$test_num$amount_str: success\n ")
        } catch (e: Exception) {
            Log.d("user database tests", "find user #$test_num$amount_str: fails\n ")
        }

        test_num++

        try {
            assertTrue("user database tests: register #$test_num$amount_str: fails\n ", db.add_user( username, password))
            Log.d("user database tests", "register #$test_num$amount_str: success\n ")
        } catch (e: Exception) {
            Log.d("user database tests", "register #$test_num$amount_str: fails\n ")
        }

        test_num++
        try {
            assertTrue("user database tests: find user #$test_num$amount_str: fails\n ", db.check_user( username))
            Log.d("user database tests", "find user #$test_num$amount_str: success\n ")
        } catch (e: Exception) {
            Log.d("user database tests", "find user +#$test_num$amount_str: fails\n ")
        }

        test_num++


        try {
            assertNotNull("user database tests: confirm password #$test_num$amount_str: fails\n ", db.get_user_by_id( username))
            val usr = db.get_user_by_id( username)
            assertTrue("user database tests: confirm password #$test_num$amount_str: fails\n ", usr!!.get__password() == password)
            Log.d("user database tests", "confirm password #$test_num$amount_str: success\n ")
        } catch (e: Exception) {
            Log.d("user database tests", "confirm password #$test_num$amount_str: fails\n ")
        }

        test_num++


        try {
            assertNotNull("user database tests: verify user exists #$test_num$amount_str: fails\n ", db.get_user_by_id( username))
            @SuppressWarnings("unused")val usr = db.get_user_by_id(username)
            assertNotNull("user database tests: verify user exists #$test_num$amount_str: fails\n ", db.get_entire_db())
            assertTrue("user database tests: verify user exists #$test_num$amount_str: fails\n ", db.check_user(username))
            Log.d("user database tests", "verify user exists #$test_num$amount_str: success\n ")
        } catch (e: Exception) {
            Log.d("user database tests", "verify user exists #$test_num$amount_str: fails\n ")
        }

        test_num++

        try {
            assertTrue("user database tests: delete #$test_num$amount_str: fails\n ", db.delete_user(username))
            Log.d("user database tests", "delete #$test_num$amount_str: success\n ")
        } catch (e: Exception) {
            Log.d("user database tests", "delete #$test_num$amount_str: fails\n ")
        }

        test_num++

        try {
            assertFalse("user database tests: delete #$test_num$amount_str: fails\n ", db.delete_user( username))
            Log.d("user database tests", "delete #$test_num$amount_str: success\n ")
        } catch (e: Exception) {
            Log.d("user database tests", "delete #$test_num$amount_str: fails\n ")
        }

        test_num++

        try {
            assertNull("user database tests: verify user doesn't exist #$test_num$amount_str: fails\n ", db.get_user_by_id( username))
            assertNotNull("user database tests: verify user doesn't exist #$test_num$amount_str: fails\n ", db.get_entire_db())
            val usr = User(username, password)
            assertFalse("user database tests: verify user doesn't exist #$test_num$amount_str: fails\n ", db.get_entire_db().contains(usr))
            assertFalse("user database tests: verify user doesn't exist #$test_num$amount_str: fails\n ", db.check_user( username))
            Log.d("user database tests", "verify user doesn't exist #$test_num$amount_str: success\n ")
        } catch (e: Exception) {
            Log.d("user database tests", "verify user doesn't exist #$test_num$amount_str: fails\n ")
        }

        test_num++

        try {
            assertTrue("user database tests: register #$test_num$amount_str: fails\n ", db.add_user( username, password))
            Log.d("user database tests", "register #$test_num$amount_str: success\n ")
        } catch (e: Exception) {
            Log.d("user database tests", "register #$test_num$amount_str: fails\n ")
        }

        test_num++
        try {
            assertNotNull("user database tests: verify user exists #$test_num$amount_str: fails\n ", db.get_user_by_id( username))
            val usr = db.get_user_by_id( username)
            assertNotNull("user database tests: verify user exists #$test_num$amount_str: fails\n ", db.get_entire_db())
            assertTrue("user database tests: verify user exists #$test_num$amount_str: fails\n ", db.check_user( username))
            Log.d("user database tests", "verify user exists #$test_num$amount_str: success\n ")
        } catch (e: Exception) {
            Log.d("user database tests", "verify user exists #$test_num$amount_str: fails\n ")
        }

        test_num++
        try {
            assertTrue("user database tests: update password #$test_num$amount_str: fails\n ", db.update_user(username, password2))
            Log.d("user database tests", "update password #$test_num$amount_str: success\n ")
        } catch (e: Exception) {
            Log.d("user database tests", "update password #$test_num$amount_str: fails\n ")
        }

        test_num++

        try {
            assertNotNull("user database tests: verify user exists #$test_num$amount_str: fails\n ", db.get_user_by_id( username))
            val usr = db.get_user_by_id( username)
            assertNotNull("user database tests: verify user exists #$test_num$amount_str: fails\n ", db.get_entire_db())
            assertTrue("user database tests: verify user exists #$test_num$amount_str: fails\n ", db.check_user( username))
            assertTrue("user database tests: verify user exists #$test_num$amount_str: fails\n ", usr!!.get__password() == password2)
            Log.d("user database tests", "verify user exists #$test_num$amount_str: success\n ")
        } catch (e: Exception) {
            Log.d("user database tests", "verify user exists #$test_num$amount_str: fails\n ")
        }

        test_num++

        try {
            assertTrue("user database tests: delete #$test_num$amount_str: fails\n ", db.delete_user( username))
            Log.d("user database tests", "delete #$test_num$amount_str: success\n ")
        } catch (e: Exception) {
            Log.d("user database tests", "delete #$test_num$amount_str: fails\n ")
        }

    }

}



