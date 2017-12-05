package com.example.chaosruler.msa_manager.activies.kablan_pashot_activity

import android.app.Activity

import android.os.Bundle
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_big_table_helper
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.big_table_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import kotlinx.android.synthetic.main.activity_kablan_pashot.*
import java.util.*

class kablan_pashot : Activity()
{

    private lateinit var big_db:local_big_table_helper
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kablan_pashot)
        init_db()
        init_listview()
    }

    private fun init_db()
    {
        big_db = local_big_table_helper(baseContext)
    }

    private fun init_listview()
    {
        var arr:Vector<big_table_data> =
        if(global_variables_dataclass.GUI_MODE)
            Vector<big_table_data>()
        else if(!global_variables_dataclass.GUI_MODE && global_variables_dataclass.isLocal)
            big_db.get_local_DB()
        else
            big_db.server_data_to_vector()

        activity_kablan_listview.adapter = kablan_pashot_listview_arrayadapter(baseContext,arr)
    }
}
