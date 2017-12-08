package com.example.chaosruler.msa_manager.activies

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.*
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.project_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.offline_mode_service
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Thread.sleep


class MainActivity : Activity()
{

    private lateinit var adapter: ArrayAdapter<project_data>
    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init_companion()
        if(global_variables_dataclass.isLocal && !global_variables_dataclass.GUI_MODE)
        {
            hide_everything()
            init_sync_trd()
            progress_subroutine()
        }
        create_intro_text()
        if(!global_variables_dataclass.GUI_MODE)
            init_spinner()
        init_buttons()
    }

    private fun init_companion()
    {
        global_variables_dataclass.init_dbs(baseContext)
    }


    private fun init_sync_trd()
    {
        Thread({
            remote_SQL_Helper.refresh_context(baseContext)
            startService(Intent(this, offline_mode_service::class.java))
            offline_mode_service.init_cache(baseContext,intent)
        }).start()
    }

    private fun init_spinner()
    {
        val projects =
        if(global_variables_dataclass.isLocal)
            global_variables_dataclass.DB_project!!.get_local_DB()
        else
            global_variables_dataclass.DB_project!!.server_data_to_vector()

        adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,projects)
        main_spinner.adapter = adapter

        main_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onNothingSelected(parent: AdapterView<*>?)
            {
                finish()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {

            }

        }
    }

    private fun progress_subroutine()
    {
        main_progressBar.visibility = ProgressBar.VISIBLE
        main_progressBar.max = getString(R.string.main_progress_bar_max).toInt()
        main_progressBar.progress = 0
        var progressStatus:Int = 0
        var handler:Handler = Handler()
        var rate = 1
        Thread(Runnable {
            while (intent.getStringExtra(getString(R.string.key_sync_offline))==null)
            {
                progressStatus += rate
                // Update the progress bar and display the
                //current value in the text view
                handler.post(Runnable {
                    main_progressBar.progress = progressStatus
                })
                try
                {
                    // Sleep for 1000/60 milliseconds.
                    Thread.sleep(1000/60)
                } catch (e: InterruptedException)
                {
                }

                if(progressStatus>=getString(R.string.main_progress_bar_max).toInt() )
                    rate = -1
                else if(progressStatus<=0)
                    rate = 1
            }
            main_progressBar.progress = getString(R.string.main_progress_bar_max).toInt()
            intent.removeExtra(getString(R.string.key_sync_offline))
            runOnUiThread {
                show_everything()
                main_progressBar.visibility = ProgressBar.GONE
                init_spinner()
            }
        }).start()

    }

    private fun hide_everything()
    {
        main_spinner.visibility = Spinner.INVISIBLE
        main_textview.visibility = TextView.INVISIBLE
        main_button_choose.visibility = TextView.INVISIBLE
    }

    private fun show_everything()
    {
        main_spinner.visibility = Spinner.VISIBLE
        main_button_choose.visibility = Button.VISIBLE
        main_textview.visibility = TextView.VISIBLE
    }

    private fun create_intro_text()
    {
        var name = PreferenceManager.getDefaultSharedPreferences(baseContext).getString(getString(R.string.username_key), "")
        /*
            implement get user name as NAME
         */
        if(name.isEmpty())
            name = remote_SQL_Helper.getusername()
        main_textview.text = main_textview.text.toString().replace(getString(R.string.shalom),getString(R.string.shalom) + " " + name)
    }

    private fun init_buttons()
    {
        main_button_choose.setOnClickListener({
            val intent = Intent(this@MainActivity, project_options::class.java)
            if(!global_variables_dataclass.GUI_MODE && main_spinner.selectedItemPosition == Spinner.INVALID_POSITION)
            {
                Toast.makeText(baseContext,getString(R.string.no_project_chosen),Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!global_variables_dataclass.GUI_MODE)
                intent.putExtra(getString(R.string.key_pass_main_to_options),(main_spinner.adapter.getItem(main_spinner.selectedItemPosition) as project_data).getProjID())
            startActivity(intent)
        })

        if(PreferenceManager.getDefaultSharedPreferences(baseContext).getString(baseContext.getString(R.string.sync_frequency),baseContext.getString(R.string.time_to_sync_in_sec)).toLong() == 0.toLong())
        {
            // if sync time is equal zero - meaning OFF
            main_button_sync.visibility = View.VISIBLE
            main_button_sync.setOnClickListener { offline_mode_service.try_to_run_command() }

            main_button_download.visibility = View.VISIBLE
            main_button_download.setOnClickListener({
                offline_mode_service.db_sync_func(baseContext,intent)
                Thread({
                    while (intent.getStringExtra(baseContext.getString(R.string.key_sync_offline))==null)
                    {
                        try {
                            sleep(1000)
                        }
                        catch (e:InterruptedException)
                        {
                            Log.d("main_trd","Woke up")
                        }
                    }
                    intent.removeExtra(baseContext.getString(R.string.key_sync_offline))
                    runOnUiThread({
                        Toast.makeText(baseContext, getString(R.string.sync_done_prompt),Toast.LENGTH_SHORT).show()
                        init_spinner()
                    })
                }).start()
            })
        }
    }

    override fun onDestroy()
    {
        super.onDestroy()
        remote_SQL_Helper.Disconnect()
    }
}
