package com.example.chaosruler.msa_manager.activies.testing_do_all_table_activities


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.activies.testing_do_all_table_activities.table_big_edit.table_big_edit
import com.example.chaosruler.msa_manager.activies.testing_do_all_table_activities.table_inventory_edit.table_inventory_edit
import com.example.chaosruler.msa_manager.activies.testing_do_all_table_activities.table_peolot_edit.table_peolot_edit
import com.example.chaosruler.msa_manager.activies.testing_do_all_table_activities.table_projects_edit.table_projects_edit
import com.example.chaosruler.msa_manager.activies.testing_do_all_table_activities.table_vendors_edit.table_vendors_edit
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_table_chooser.*

class table_chooser : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_chooser)
        init_buttons()
    }

    /*
        init buttons
     */
    private fun init_buttons()
    {
        table_chooser_big.setOnClickListener({startActivity(Intent(this@table_chooser, table_big_edit::class.java))})

        table_chooser_opr.setOnClickListener({startActivity(Intent(this@table_chooser, table_peolot_edit::class.java))})

        table_chooser_project.setOnClickListener({ startActivity(Intent(this@table_chooser, table_projects_edit::class.java))})

        table_chooser_vendor.setOnClickListener({startActivity(Intent(this@table_chooser, table_vendors_edit::class.java))})

        table_chooser_inventory.setOnClickListener({startActivity(Intent(this@table_chooser, table_inventory_edit::class.java))})
    }
}
