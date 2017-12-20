package com.example.chaosruler.msa_manager.services

import android.annotation.SuppressLint
import android.content.Context
import android.preference.PreferenceManager
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.*

class global_variables_dataclass
{
    companion object
    {
        var isLocal: Boolean = true
        var GUI_MODE: Boolean = false
        var DB_BIG: local_big_table_helper? = null
        var projid: String? = null
        @SuppressLint("StaticFieldLeak")
        var DB_project: local_projects_table_helper? = null
        @SuppressLint("StaticFieldLeak")
        var DB_OPR: local_OPR_table_helper? = null
        @SuppressLint("StaticFieldLeak")
        var DB_VENDOR: local_vendor_table_helper? = null
        @SuppressLint("StaticFieldLeak")
        var DB_INVENTORY: local_inventory_table_helper? = null

        /*
            init databases for all the application
         */
        fun init_dbs(context: Context)
        {
            isLocal = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getString(R.string.local_or_not),true)
            GUI_MODE = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getString(R.string.gui_mode_key),false)

            DB_BIG = local_big_table_helper(context)
            DB_INVENTORY = local_inventory_table_helper(context)
            DB_OPR = local_OPR_table_helper(context)
            DB_VENDOR = local_vendor_table_helper(context)
            DB_project = local_projects_table_helper(context)
        }
    }
}