package com.example.chaosruler.msa_manager.activies.testing_do_all_table_activities.table_projects_edit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.*
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_projects_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.project_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_table_projects_edit.*
import java.util.*

class table_projects_edit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_projects_edit)
        if(!global_variables_dataclass.GUI_MODE && !init_table())
            finish()
    }

    private fun init_table():Boolean
    {
        Thread{
            var arr: Vector<project_data> =
                    if (global_variables_dataclass.GUI_MODE)
                        Vector<project_data>()
                    else if (!global_variables_dataclass.GUI_MODE && global_variables_dataclass.isLocal)
                        global_variables_dataclass.DB_project!!.get_local_DB_by_projname((global_variables_dataclass.projid?:"").trim())
                    else
                        global_variables_dataclass.DB_project!!.server_data_to_vector_by_projid((global_variables_dataclass.projid?:"").trim())
            runOnUiThread {table_projects_listview.adapter = table_projects_arrayadapter(this,arr) }
        }.start()
       return true
    }

}
