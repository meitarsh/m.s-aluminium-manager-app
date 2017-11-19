package com.example.chaosruler.msa_manager

import android.app.IntentService
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_project_options.*
import java.lang.Thread.sleep
import java.util.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.app.PendingIntent
import android.content.Intent.ACTION_MAIN





class offline_mode_service() : IntentService(".offline_mode_service") {

    override fun onHandleIntent(intent: Intent?)
    {
        init_cache(this.applicationContext)
    }

    companion object
    {
        private lateinit var cache: cache_server_commands
        private var timer:Timer = Timer()
        private  var task:TimerTask =  object : TimerTask() {
            override fun run()
            {
                try_to_run_command()
            }
        }

        fun init_cache(context: Context)
        {
            cache = cache_server_commands(context)
            init_timer(context)
        }

        fun init_timer(context: Context)
        {
            if(task.scheduledExecutionTime() == "0".toLong())
                timer.schedule(task, 0, context.getString(R.string.time_to_sync_in_sec).toLong() * context.getString(R.string.millis_in_sec).toLong() )
        }





        fun push_add_command(db: String, table: String, vector: Vector<String>, map: HashMap<String, String>) {
            var str = remote_SQL_Helper.construct_add_str(db, table, vector, map).replace("'","&quote;")
            var username = remote_SQL_Helper.username
            cache.add_command_to_list(cache_command(str, username))
        }

        fun push_update_command(db: String, table: String, where_clause: String, compare_to: Array<String>, type: String, update_to: HashMap<String, String>) {
            var str = remote_SQL_Helper.construct_update_str(db, table, where_clause, compare_to, type, update_to).replace("'","&quote;")
            var username = remote_SQL_Helper.username
            cache.add_command_to_list(cache_command(str, username))
        }

        fun push_remove_command(db: String, table: String, where_clause: String, compare_to: Array<String>, type: String) {
            var str = remote_SQL_Helper.construct_remove_str(db, table, where_clause, compare_to, type).replace("'","&quote;")
            var username = remote_SQL_Helper.username
            cache.add_command_to_list(cache_command(str, username))
        }

        fun get_DB_string(): String
                = cache.get_db_string()

        fun get_DB(): Vector<cache_command>
                = cache.get_entire_db()

        fun try_to_run_command()
        {
            var vector = get_DB()
            for(item in vector)
            {
                if(item.__user == remote_SQL_Helper.username)
                {
                    var result_of_query = remote_SQL_Helper.run_command(item.__command.replace("&quote;", "'"))
                    if (result_of_query)
                        cache.remove_command(item)
                }
            }
        }


    }


}
