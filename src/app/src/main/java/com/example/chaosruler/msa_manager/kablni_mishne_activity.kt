package com.example.chaosruler.msa_manager

import android.app.Activity
import android.os.Bundle

class kablni_mishne_activity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kablni_mishne_activity)
    }
}
