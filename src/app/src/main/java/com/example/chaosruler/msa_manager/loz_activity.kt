package com.example.chaosruler.msa_manager

import android.app.Activity
import android.os.Bundle

class loz_activity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loz_activity)
    }
}
