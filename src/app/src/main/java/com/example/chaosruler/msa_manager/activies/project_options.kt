package com.example.chaosruler.msa_manager.activies

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.chaosruler.msa_manager.BuildConfig
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.activies.loz_activity.loz_activity
import com.example.chaosruler.msa_manager.activies.testing_do_all_table_activities.table_chooser
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_project_options.*

/**
 * Class is responsible for the logic behind the project options activity (choosing what to do inside a project)
 * @author Chaosruler972
 * @constructor as an activity, constructor is default constructor
 */
class project_options : Activity() {

    /**
     * define the project name we are working on, also refreshes the global variables to know that we chose
     * that project (by name), on case that we didn't actually choose any project, activity automaticily closes
     * function is part of the activity lifecycle of android activities
     * @author Chaosruler972
     * @param savedInstanceState the state that we opened the activity last time
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_options)
        if(!global_variables_dataclass.GUI_MODE)
            global_variables_dataclass.projid = intent.getStringExtra(getString(R.string.key_pass_main_to_options))
        else
            global_variables_dataclass.projid = ""
        if(global_variables_dataclass.projid == null)
            finish()
        init_buttons()

    }

    /**
     * inits buttons
     * @author Chaosruler972
     */
    private fun init_buttons()
    {

        project_options_btn_divohi_takalot.setOnClickListener(
        {
            val intent = Intent(this@project_options, divohi_takalot_activity::class.java)
                    startActivity(intent)
        })

        project_options_btn_kablni_mishne.setOnClickListener(
        {
            val intent = Intent(this@project_options, kablni_mishne_activity::class.java)
                    startActivity(intent)
        })

        project_options_btn_loz.setOnClickListener(
        {
            val intent = Intent(this@project_options, loz_activity::class.java)
                    startActivity(intent)
        })


    }

    




}
