package com.example.chaosruler.msa_manager.activies

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.ProgressBar
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.services.offline_mode_service
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Thread.sleep

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Thread({
            remote_SQL_Helper.refresh_context(baseContext)
            startService(Intent(this, offline_mode_service::class.java))
            offline_mode_service.init_cache(baseContext,intent)
        }).start()
        main_progressBar.visibility = ProgressBar.VISIBLE
        main_progressBar.max = getString(R.string.main_progress_bar_max).toInt()
        main_progressBar.progress = 0
        var rate = 1
        while(intent.getStringExtra(getString(R.string.key_sync_offline))==null)
        {
            try
            {
                sleep(1000/60) // 60 fps
            }
            catch (e:InterruptedException)
            {

            }
            main_progressBar.progress += rate*getString(R.string.main_progressbar_inc_rate).toInt()
            if(main_progressBar.progress + rate*getString(R.string.main_progressbar_inc_rate).toInt() > main_progressBar.max)
                rate=-1
            else if(main_progressBar.progress + rate*getString(R.string.main_progressbar_inc_rate).toInt() < 0)
                rate=1
        }
        intent.removeExtra(getString(R.string.key_sync_offline))
        main_progressBar.visibility = ProgressBar.INVISIBLE
        create_intro_text()
        init_buttons()

    }

    private fun create_intro_text()
    {
        val name = PreferenceManager.getDefaultSharedPreferences(baseContext).getString(getString(R.string.username_key), remote_SQL_Helper.getusername())
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

        if(PreferenceManager.getDefaultSharedPreferences(baseContext).getString(baseContext.getString(R.string.sync_frequency),baseContext.getString(R.string.time_to_sync_in_sec)).toLong() == 0.toLong())
        {
            // if sync time is equal zero - meaning OFF
            main_button_sync.visibility = View.VISIBLE
            main_button_sync.setOnClickListener { offline_mode_service.try_to_run_command() }
        }
    }

    override fun onDestroy()
    {
        super.onDestroy()
        remote_SQL_Helper.Disconnect()
    }
}
