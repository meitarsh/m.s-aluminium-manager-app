package com.example.chaosruler.msa_manager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_project_options.*

class project_options : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_options)
        init_buttons()
    }

    private fun init_buttons()
    {

        project_options_btn_divohi_takalot.setOnClickListener(
        {
                    val intent:Intent = Intent(this@project_options, divohi_takalot_activity::class.java)
                    startActivity(intent)
        })

        project_options_btn_kablni_mishne.setOnClickListener(
        {
                    val intent:Intent = Intent(this@project_options, kablni_mishne_activity::class.java)
                    startActivity(intent)
        })

        project_options_btn_loz.setOnClickListener(
        {
                    val intent:Intent = Intent(this@project_options, loz_activity::class.java)
                    startActivity(intent)
        })

    }


}
