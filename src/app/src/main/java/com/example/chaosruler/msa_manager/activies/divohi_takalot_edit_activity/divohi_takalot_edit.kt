package com.example.chaosruler.msa_manager.activies.divohi_takalot_edit_activity

import android.app.Activity
import android.os.Bundle
import android.text.InputType
import android.view.View
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_big_table_helper
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_inventory_table_helper
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_projects_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.big_table_data
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.inventory_data
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.project_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.divohi_takalot_tofes.*
import java.util.*
import android.content.Intent
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.widget.*
import kotlinx.android.synthetic.main.divohi_takalot_edit.*
import java.io.File


class divohi_takalot_edit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.divohi_takalot_edit)
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
                        global_variables_dataclass.DB_BIG!!.get_local_DB_by_projname((global_variables_dataclass.projid ?: "").trim())
                    else
                        global_variables_dataclass.DB_BIG!!.server_data_to_vector_by_projname((global_variables_dataclass.projid ?: "").trim())

            runOnUiThread({divohi_takalot_edit_listview.adapter = divohi_takalot_edit_arrayadapter(this, arr)
            })

        }).start()
        return true
    }




    /*
        get the file
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent)
    {
       if(resultCode == Activity.RESULT_OK)
       {

                val uri = data.data
                var parent = divohi_takalot_edit_listview.getChildAt(requestCode) as LinearLayout
                var button = parent.getChildAt(parent.childCount-1) as Button
                button.isEnabled = false
                button.setBackgroundResource(0)
                button.text = File(uri.path).absolutePath
                parent.refreshDrawableState()


       }
        super.onActivityResult(requestCode, resultCode, data)
    }


}
