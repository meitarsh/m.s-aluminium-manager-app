package com.example.chaosruler.msa_manager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        remote_SQL_Helper.refresh_context(baseContext)
        startService(Intent(this,offline_mode_service::class.java))
        create_intro_text()
        init_buttons()
    }

    private fun create_intro_text()
    {
        val name = PreferenceManager.getDefaultSharedPreferences(baseContext).getString(getString(R.string.username_key),remote_SQL_Helper.getusername())
        /*
            implement get user name as NAME
         */

        main_textview.text = main_textview.text.toString().replace(getString(R.string.shalom),getString(R.string.shalom) + " " + name)
    }

    private fun init_buttons()
    {
        main_button_choose.setOnClickListener({
            val intent = Intent(this@MainActivity, project_options::class.java)
            startActivity(intent)
        })
    }

    override fun onDestroy()
    {
        super.onDestroy()
        remote_SQL_Helper.Disconnect()
    }
}
