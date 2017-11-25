package com.example.chaosruler.msa_manager

import android.app.Activity
import android.os.Bundle

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
