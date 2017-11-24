package com.example.chaosruler.msa_manager

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.support.v4.app.NotificationCompat
import java.lang.Thread.sleep
import java.util.*
import android.app.NotificationManager
import android.util.Log
import android.widget.Toast


/*
    service responsible for storing server commands offline and sending them whenever possible
 */

class offline_mode_service() : IntentService(".offline_mode_service") {

    /*
        on service start intent, initates the cache with the thread
     */
    override fun onHandleIntent(intent: Intent?)
    {

        init_cache(this.applicationContext)
    }

    companion object {
        /*
                local database to store server commands with appropiate users
            */
        private lateinit var cache: cache_server_commands
        private lateinit var ctx:Context
        private var time:Long = 0
        /*
            thread, run in thread to not hang the UI
         */
        private lateinit var trd:Thread

        /*
            subroutine respoonsible to initate the service with a thread that automaticily sends commends every X seconds
         */
        fun init_cache(context: Context)
        {

            ctx=context
            cache = cache_server_commands(context)
            grab_time(ctx)
            init_trd()
            start_trd()
        }

        fun grab_time(context: Context)
        {
            time = ctx.getString(R.string.millis_in_sec).toLong()
            var sec = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.sync_frequency),context.getString(R.string.time_to_sync_in_sec)).toLong()
            time*=sec
        }
        fun init_trd()
        {
            trd = Thread(
                    {
                        while (true)
                        {
                            try {
                                try_to_run_command()
                                sleep(time)
                            } catch (e: InterruptedException) {
                            }
                        }
                    })
        }
        fun start_trd()
        {
            if(trd.state == Thread.State.NEW) // new thread, not started
                trd.start()
        }
        /*
            following subroutines pushes server commands to stack
         */

        fun push_add_command(db: String, table: String, vector: Vector<String>, map: HashMap<String, String>):String {
            var str = remote_SQL_Helper.construct_add_str(db, table, vector, map).replace("'","&quote;")
            var username = remote_SQL_Helper.username
            return general_push_command(str,username)
        }

        fun push_update_command(db: String, table: String, where_clause: String, compare_to: Array<String>, type: String, update_to: HashMap<String, String>):String {
            var str = remote_SQL_Helper.construct_update_str(db, table, where_clause, compare_to, type, update_to).replace("'","&quote;")
            var username = remote_SQL_Helper.username
            return general_push_command(str,username)
        }

        /*
        removes a stored command
         */
        fun push_remove_command(db: String, table: String, where_clause: String, compare_to: Array<String>, type: String):String {
            var str = remote_SQL_Helper.construct_remove_str(db, table, where_clause, compare_to, type).replace("'","&quote;")
            var username = remote_SQL_Helper.username
            return general_push_command(str,username)
        }

        private fun general_push_command(command:String, username:String):String
        {
            var string:String
            if (PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(ctx.getString(R.string.local_or_not), true)) {
                cache.add_command_to_list(cache_command(command, username))
                string = ctx.getString(R.string.successfull_operation) + " קוד פעולה:  " + cache.get_id_of_command(cache_command(command, username))
            }
            else
            {
                remote_SQL_Helper.run_command(command.replace("&quote;", "'"))
                string = ctx.getString(R.string.successfull_operation)
            }
            return string
        }
            /*
            gets all the comand cache database to a string
         */
        fun get_DB_string(): String
                = cache.get_db_string()

        /*
            gets all the command cache database to a vector
         */
        fun get_DB(): Vector<cache_command>
                = cache.get_entire_db()

        /*
              attempts to run all the commands in the command cache database
         */
        fun try_to_run_command()
        {
            var vector = get_DB()
            for(item in vector)
            {
                if(item.__user == remote_SQL_Helper.username)
                {
                    var result_of_query = remote_SQL_Helper.run_command(item.__command.replace("&quote;", "'"))
                    if (result_of_query)
                    {

                        if(PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(ctx.getString(R.string.notification),false))
                            build_small_notification(cache.get_id_of_command(item).toString())
                        cache.remove_command(item)
                    }
                }
            }



        }

        fun build_small_notification(string: String)
        {
            val mBuilder = NotificationCompat.Builder(ctx)
                    .setSmallIcon(R.drawable.notification_icon_background)
                    .setContentTitle(ctx.getString(R.string.notification_title))
                    .setContentText(ctx.getString(R.string.notification_sync_successfuk) + "קוד פעולה: " + string)
            val mNotificationManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
            mNotificationManager!!.notify(1,mBuilder.build())
        }



    }


}
