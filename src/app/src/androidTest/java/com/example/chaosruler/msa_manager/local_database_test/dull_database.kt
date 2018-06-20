package com.example.chaosruler.msa_manager.local_database_test

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.chaosruler.msa_manager.abstraction_classes.local_SQL_Helper
import java.util.*
import kotlin.collections.HashMap

class dull_database(context: Context) : local_SQL_Helper(context, "Test",null,1, "Test", test_hashmap.create_hashmap())
{
    private val ID = "id"
    private val DATA = "data"
    init {
        val vector: Vector<String> = Vector()
        vector.add(ID)
        vector.add(DATA)
        init_vector_of_variables(vector)
    }

    override fun onCreate(db: SQLiteDatabase) {
        val map: HashMap<String, String> = HashMap()
        map[ID] = "INTEGER primary key AUTOINCREMENT"
        map[DATA] = "TEXT"
        createDB(db,map)
    }

    fun get_entire_db(): Vector<HashMap<String, String>>// subroutine to get the entire database as an iterateable vector
    {
        val vector: Vector<HashMap<String, String>> = get_db()
        return vector
    }

    fun remove_data(data: String): Boolean {
        return remove_from_db(arrayOf(DATA), arrayOf(data))
    }

    fun insert(enter_data: String) {
        val everything_to_add: Vector<HashMap<String, String>> = Vector()

        val data: HashMap<String, String> = HashMap()
        data[DATA] = enter_data
        everything_to_add.addElement(data)
        add_data(everything_to_add)
    }

    fun empty_db()
    {
        this.writableDatabase.execSQL("DELETE FROM Test")
    }

    companion object test_hashmap{
        fun create_hashmap(): HashMap<Int, String>
        {
            val hashmap = HashMap<Int, String>()
            hashmap[0] = "id"
            hashmap[1] = "data"
            return hashmap
        }
    }
}