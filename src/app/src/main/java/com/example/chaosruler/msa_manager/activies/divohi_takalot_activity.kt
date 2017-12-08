package com.example.chaosruler.msa_manager.activies

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_divohi_takalot_activity.*

/*
    class for the activity view of divohi takalot
 */
class divohi_takalot_activity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_divohi_takalot_activity)
        init_buttons()
    }
    /*
                   inits buttons
            */
    private fun init_buttons()
    {
        divohi_takalot_form.setOnClickListener { startActivity(Intent(this, DivohiTakalotTofesActivity::class.java)) }

        divohi_takalot_edit.setOnClickListener({startActivity(Intent(this,com.example.chaosruler.msa_manager.activies.divohi_takalot_edit::class.java)) })


    }
}
