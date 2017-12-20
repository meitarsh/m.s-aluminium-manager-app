package com.example.chaosruler.msa_manager.activies.testing_do_all_table_activities.table_vendors_edit

import android.app.Activity
import android.os.Bundle
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.object_types.vendor_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_table_vendors_edit.*
import java.util.*

class table_vendors_edit : Activity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_vendors_edit)
        if(!global_variables_dataclass.GUI_MODE && !init_table())
            finish()
    }

    private fun init_table():Boolean
    {
        var arr: Vector<vendor_data> =
                if (global_variables_dataclass.GUI_MODE)
                    Vector<vendor_data>()
                else if (!global_variables_dataclass.GUI_MODE && global_variables_dataclass.isLocal)
                    global_variables_dataclass.DB_VENDOR!!.get_local_DB_by_projname((global_variables_dataclass.projid?:"").trim())
                else
                    global_variables_dataclass.DB_VENDOR!!.server_data_to_vector_by_projname((global_variables_dataclass.projid?:"").trim())

        table_vendor_listview.adapter = table_vendors_arrayadapter(this,arr)
        return true
    }

}
