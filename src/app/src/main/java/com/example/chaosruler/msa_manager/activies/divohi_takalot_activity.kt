package com.example.chaosruler.msa_manager.activies

import android.app.Activity
import android.os.Bundle
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.services.themer

/*
    class for the activity view of divohi takalot
 */
class divohi_takalot_activity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_divohi_takalot_activity)

    }
}
