package com.example.chaosruler.msa_manager.activies

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_kablni_mishne_activity.*

class kablni_mishne_activity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kablni_mishne_activity)
        init_buttons()
        //PreferenceManager.getDefaultSharedPreferences(baseContext).getBoolean(getString(R.string.local_or_not),true)
    }

    private fun init_buttons()
    {
        kablni_mishne_pshot.setOnClickListener({
            startActivity(Intent(this, kablan_pashot::class.java))
        })

        kablni_mishne_pirot.setOnClickListener({
            startActivity(Intent(this, kablan_mforat::class.java))
        })
    }
}
