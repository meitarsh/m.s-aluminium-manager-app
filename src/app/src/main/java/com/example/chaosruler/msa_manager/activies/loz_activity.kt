package com.example.chaosruler.msa_manager.activies

import android.app.Activity
import android.os.Bundle
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.services.themer

class loz_activity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loz_activity)
    }
}
