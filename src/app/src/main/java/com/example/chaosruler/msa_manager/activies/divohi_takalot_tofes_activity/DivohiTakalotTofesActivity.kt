package com.example.chaosruler.msa_manager.activies.divohi_takalot_tofes_activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.*
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.divohi_takalot_tofes.*
import java.util.*

/*
    divohi takalot form activity
 */
class DivohiTakalotTofesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.divohi_takalot_tofes)
        if(!global_variables_dataclass.GUI_MODE && !init_table())
            finish()
    }
    /*
                   inits table
            */
    private fun init_table():Boolean
    {
        Thread({
            var arr: Vector<big_table_data> =
                    if (global_variables_dataclass.GUI_MODE || global_variables_dataclass.DB_BIG == null)
                        Vector<big_table_data>()
                    else if (!global_variables_dataclass.GUI_MODE && global_variables_dataclass.isLocal)
                        global_variables_dataclass.DB_BIG!!.get_local_DB_by_projname((global_variables_dataclass.projid?:"").trim())
                    else
                        global_variables_dataclass.DB_BIG!!.server_data_to_vector_by_projname((global_variables_dataclass.projid?:"").trim())

            runOnUiThread({divohi_takalot_tofes_listview.adapter = divohi_takalot_tofes_arrayadapter(this,arr)})
        }).start()

        return true
    }




}
