package com.example.chaosruler.msa_manager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        create_intro_text()
        init_buttons()
    }

    private fun create_intro_text()
    {
        val name = "Template Name"
        val old_text_view_str = main_textview.getText().toString()
        old_text_view_str.replace("שלום", "שלום " + name)
    }

    private fun init_buttons()
    {
        main_button_choose.setOnClickListener({
            val intent = Intent(this@MainActivity, project_options::class.java)
            startActivity(intent)
        })
    }
}
