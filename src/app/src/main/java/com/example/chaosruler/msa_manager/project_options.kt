package com.example.chaosruler.msa_manager

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_project_options.*
import java.util.*
import android.os.StrictMode



class project_options : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_options)
        test_init_buttons()
    }

    private fun init_buttons()
    {

        project_options_btn_divohi_takalot.setOnClickListener(
        {
                    val intent:Intent = Intent(this@project_options, divohi_takalot_activity::class.java)
                    startActivity(intent)
        })

        project_options_btn_kablni_mishne.setOnClickListener(
        {
                    val intent:Intent = Intent(this@project_options, kablni_mishne_activity::class.java)
                    startActivity(intent)
        })

        project_options_btn_loz.setOnClickListener(
        {
                    val intent:Intent = Intent(this@project_options, loz_activity::class.java)
                    startActivity(intent)
        })

    }

    private fun test_init_buttons() {

        if (!remote_SQL_Helper.isValid()) {
            Toast.makeText(this, "Couldn't init remote_SQL_Helper", Toast.LENGTH_SHORT).show()
            return
        }
        var project_ID = 1
        var project_name = "'test'"
        var project_manager_name = "'Me'"
        var map: HashMap<String, String> = HashMap()
        map["project_ID"] = project_ID.toString()
        map["project_name"] = project_name
        map["project_manager_name"] = project_manager_name
        var vector: Vector<String> = Vector()
        vector.add("project_ID")
        vector.add("project_name")
        vector.add("project_manager_name")
        var db: String = "main"
        var table: String = "projects"


        project_options_btn_kablni_mishne.text = "הצג את כל המסד נתונים"
        project_options_btn_divohi_takalot.text = "הוסף מישהו סתמי"
        project_options_btn_loz.text = "הסר את המישהו שהוספת בכפתור הקודם"

        project_options_btn_divohi_takalot.setOnClickListener(
                {
                    Thread({
                        remote_SQL_Helper.add_data(db, table, vector, map)
                        project_options_btn_kablni_mishne.callOnClick()
                    }).start()
                })

        project_options_btn_kablni_mishne.setOnClickListener(
                {

                    Thread({
                        var str:String= remote_SQL_Helper.VectorToString(remote_SQL_Helper.get_all_table(db, table))
                        runOnUiThread { activity_project_test_textview.text = str }
                    }).start()
                })
        project_options_btn_loz.setOnClickListener(
                {

                   Thread({
                       remote_SQL_Helper.remove_data(db, table, "project_ID", arrayOf(project_ID.toString()), "int")
                       project_options_btn_kablni_mishne.callOnClick()
                   }).start()

                })

    }



}
