package com.example.chaosruler.msa_manager

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import kotlin.collections.HashMap

import org.junit.Assert.*
import org.junit.Assert.assertTrue
//
//@RunWith(AndroidJUnit4::class)
//class mssal_test {
//    @Test
//    fun test_mssql()
//    {
//        val con = InstrumentationRegistry.getTargetContext()
//        val db = "db"
//        val tbl = "table"
//        val colum_1 = "clm1"
//        val data_1 = "data_1"
//        val clm_2 = "clm2"
//        val data_2 = "data_2"
//        val datatype = "text"
//        val vector = Vector<String>()
//        vector.addElement(data_1)
//        vector.addElement(data_2)
//        val hashmap = HashMap<String, String>()
//        hashmap[colum_1] = datatype
//        hashmap[clm_2] = datatype
//        remote_SQL_Helper.refresh_context(con)
//        val add_qry = remote_SQL_Helper.construct_add_str(db, tbl, vector, hashmap, false)
//        val ex_add_qry = "USE [db] INSERT INTO [dbo].[table] ([data_1],[data_2],MODIFIEDDATETIME) " +
//                "VALUES " //(null,null,dateadd(s,"
//        val ex_add_qry_suffix = ")" //",'19700101 05:00:00:000') )"
//        val diff = add_qry.replace(ex_add_qry, "").replace(ex_add_qry_suffix, "")
//
//        assertTrue("Add query was supposed to be $ex_add_qry #num $ex_add_qry_suffix not $add_qry", diff.toInt() > 0)
//
//
//    }
//}