package com.example.chaosruler.msa_manager.activies.kablan_pashot_activity

import android.app.Activity

import android.os.Bundle
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_big_table_helper
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.big_table_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_kablan_pashot.*
import java.util.*

class kablan_pashot : Activity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kablan_pashot)
        if(!init_listview())
            finish()
    }


    private fun init_listview():Boolean
    {
        try
        {
            var arr: Vector<big_table_data> =
                    if (global_variables_dataclass.GUI_MODE)
                        Vector<big_table_data>()
                    else if (!global_variables_dataclass.GUI_MODE && global_variables_dataclass.isLocal)
                        global_variables_dataclass.DB_BIG!!.get_local_DB()
                    else
                        global_variables_dataclass.DB_BIG!!.server_data_to_vector()

            activity_kablan_listview.adapter = kablan_pashot_listview_arrayadapter(baseContext, arr)
            return true
        }
        catch (e:NullPointerException)
        {
            return false
        }
    }
}
