package com.example.chaosruler.msa_manager.activies.testing_do_all_table_activities.table_peolot_edit

import android.app.Activity
import android.os.Bundle
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_opr_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.opr_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_table_peolot_edit.*
import java.util.*

class table_peolot_edit : Activity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_peolot_edit)
        if(!global_variables_dataclass.GUI_MODE && !init_table())
            finish()
    }


    private fun init_table():Boolean
    {
        Thread{
            var arr: Vector<opr_data> =
                    if (global_variables_dataclass.GUI_MODE)
                        Vector<opr_data>()
                    else if (!global_variables_dataclass.GUI_MODE && global_variables_dataclass.isLocal)
                        global_variables_dataclass.DB_OPR!!.get_local_DB_by_projname((global_variables_dataclass.projid?:"").trim())
                    else
                        global_variables_dataclass.DB_OPR!!.server_data_to_vector_by_projname((global_variables_dataclass.projid?:"").trim())

            runOnUiThread {table_opr_listview.adapter = table_peolot_arrayadapter(this,arr) }
        }.start()
        return true
    }


}
