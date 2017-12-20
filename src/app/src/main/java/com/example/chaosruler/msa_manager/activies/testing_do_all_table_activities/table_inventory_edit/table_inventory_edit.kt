package com.example.chaosruler.msa_manager.activies.testing_do_all_table_activities.table_inventory_edit

import android.app.Activity
import android.os.Bundle
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.object_types.inventory_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_table_inventory_edit.*
import java.util.*

class table_inventory_edit : Activity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_inventory_edit)
        if(!global_variables_dataclass.GUI_MODE && !init_table())
            finish()
    }

    private fun init_table():Boolean
    {
        Thread{
            var arr: Vector<inventory_data> =
                    if (global_variables_dataclass.GUI_MODE)
                        Vector<inventory_data>()
                    else if (!global_variables_dataclass.GUI_MODE && global_variables_dataclass.isLocal)
                        global_variables_dataclass.DB_INVENTORY!!.get_local_DB_by_projname((global_variables_dataclass.projid?:"").trim())
                    else
                        global_variables_dataclass.DB_INVENTORY!!.server_data_to_vector_by_projname((global_variables_dataclass.projid?:"").trim())

            runOnUiThread {             table_inventory_listview.adapter = table_inventory_arrayadapter(this,arr)
            }
        }.start()
        return true
    }


}
