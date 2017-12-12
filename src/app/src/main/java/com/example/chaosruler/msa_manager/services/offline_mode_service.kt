package com.example.chaosruler.msa_manager.services

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.support.v4.app.NotificationCompat
import java.lang.Thread.sleep
import java.util.*
import android.app.NotificationManager
import android.util.Log
import com.example.chaosruler.msa_manager.MSSQL_helpers.*
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.SQLITE_helpers.*
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.*
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.cache_command


/*
    service responsible for storing server commands offline and sending them whenever possible
 */
class offline_mode_service private constructor(context: Context,intent: Intent){
    init
    {
        init_cache(context,intent)
    }



    companion object
    {
        @SuppressLint("StaticFieldLeak")
        private var ourInstance:offline_mode_service? = null //SINGLETON!
        public fun getInstance(context: Context,intent: Intent): offline_mode_service?
        {
            if(ourInstance == null)
            {
                init_flag=true
                ourInstance = offline_mode_service(context,intent)
                return ourInstance
            }
            else
                return ourInstance
        }
        private var init_flag:Boolean = false
        /*
                local database to store server commands with appropiate users
            */
        private lateinit var cache: cache_server_commands
        @SuppressLint("StaticFieldLeak")
        private lateinit var inventory: local_inventory_table_helper
        @SuppressLint("StaticFieldLeak")
        private lateinit var projects: local_projects_table_helper
        @SuppressLint("StaticFieldLeak")
        private lateinit var opr: local_OPR_table_helper
        @SuppressLint("StaticFieldLeak")
        private lateinit var vendor: local_vendor_table_helper
        private lateinit var big_table: local_big_table_helper
        @SuppressLint("StaticFieldLeak")
        private lateinit var ctx:Context
        private var time:Long = 0
        /*
            thread, run in thread to not hang the UI
         */
        private var trd:Thread= Thread({
            while (time!=0.toLong())
            {
                Log.d("offline_mode","Did a trd run")
                try
                {
                    try_to_run_command()
                    sleep(time)
                } catch (e: InterruptedException)
                {
                    Log.d("offline mode","retring a re-sync of everything")
                }
            }
        })


        /*
            subroutine respoonsible to initate the service with a thread that automaticily sends commends every X seconds
         */
        private fun init_cache(context: Context, intent: Intent)
        {

            ctx =context
            cache = cache_server_commands(context)
            inventory = local_inventory_table_helper(context)
            projects = local_projects_table_helper(context)
            opr = local_OPR_table_helper(context)
            vendor = local_vendor_table_helper(context)
            big_table = local_big_table_helper(context)

            grab_time(ctx)
            init_remote_databases(context)
            grab_time(ctx)

            start_trd()

           if(PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getString(R.string.local_or_not),true) )
           {
               sync_local(context,intent)
           }
           else
               mark_done(context,intent)

        }

        /*
            adds to intent that sync is done
         */
        private fun mark_done(context: Context,intent: Intent)
        {
            intent.putExtra(context.getString(R.string.key_sync_offline),context.getString(R.string.key_sync_offline))
        }

        /*
            grab time from settings
         */
        private fun grab_time(context: Context)
        {
            time = ctx.getString(R.string.millis_in_sec).toLong()
            var sec = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.sync_frequency),context.getString(R.string.time_to_sync_in_sec)).toLong()
            time *=sec
        }
        /*
            init all remove database to variables
         */
        private fun init_remote_databases(context: Context)
        {
            remote_vendors_table_helper.init_variables(context)
            remote_big_table_helper.init_variables(context)
            remote_inventory_table_helper.init_variables(context)
            remote_opr_table_helper.init_variables(context)
            remote_projects_table_helper.init_variables(context)
        }
        /*
            starts the sync thread
         */
        private fun start_trd()
        {
            if(time == 0.toLong())
                return
            if(trd.state == Thread.State.NEW) // new thread, not started
                trd.start()
        }
        /*
            following subroutines pushes server commands to stack
         */
        fun push_add_command(db: String, table: String, vector: Vector<String>, map: HashMap<String, String>):String
        {

            var str = remote_SQL_Helper.construct_add_str(db, table, vector, map).replace("'","&quote;")
            var username = remote_SQL_Helper.getusername()
            return general_push_command(str, username)
        }

        /*
            pushes update command
         */
        fun push_update_command(db: String, table: String, where_clause: String, compare_to: Array<String>, type: String, update_to: HashMap<String, String>):String
        {

            var str = remote_SQL_Helper.construct_update_str(db, table, where_clause, compare_to, type, update_to).replace("'","&quote;")
            var username = remote_SQL_Helper.getusername()
            return general_push_command(str, username)
        }

        /*
        removes a stored command
         */
        fun push_remove_command(db: String, table: String, where_clause: String, compare_to: Array<String>, type: String):String
        {

            var str = remote_SQL_Helper.construct_remove_str(db, table, where_clause, compare_to, type).replace("'","&quote;")
            var username = remote_SQL_Helper.getusername()
            return general_push_command(str, username)
        }

        /*
            pushes command with an already prepared string and its usernames
         */
        public fun general_push_command(command:String, username:String):String
        {

            var string:String = if (PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(ctx.getString(R.string.local_or_not), true)) {
                cache.add_command_to_list(cache_command(command, username))
                ctx.getString(R.string.successfull_operation) + ctx.getString(R.string.notificatoin_op_id) + cache.get_id_of_command(cache_command(command, username))
            } else {
                remote_SQL_Helper.run_command(command.replace("&quote;", "'"))
                ctx.getString(R.string.successfull_operation)
            }
            return string
        }
            /*
            gets all the comand cache database to a string
         */
        private fun get_DB_string(): String
                = cache.get_db_string()

        /*
            gets all the command cache database to a vector
         */
        private fun get_DB(): Vector<cache_command>
                = cache.get_entire_db()

        /*
              attempts to run all the commands in the command cache database
         */
        fun try_to_run_command()
        {

            var vector = get_DB()
            Log.d("offline_mode","cache command size: " + vector.size.toString())
            for(item in vector)
            {
                if(item.__user == remote_SQL_Helper.getusername())
                {
                    var result_of_query = remote_SQL_Helper.run_command(item.__command.replace("&quote;", "'"))
                    Log.d("offline_mode","Query to try :" + item.__command.replace("&quote;", "'"))
                    Log.d("offline_mode","Query Result: " + result_of_query.toString())
                    if (result_of_query)
                    {

                        if(PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(ctx.getString(R.string.notification),false))
                            build_small_notification(cache.get_id_of_command(item).toString())
                        cache.remove_command(item)
                    }
                }
            }

            try
            {
                if(PreferenceManager.getDefaultSharedPreferences(ctx).getString(ctx.getString(R.string.sync_frequency), 15.toString()).toInt()!=0)
                    db_sync_func_without_mark()
            }
            catch (e:Exception)
            {
                Log.d("offline_mode","Couldn't sync! error on time")
            }




        }

        /*
            build notificatoin to show on screen
         */
        private fun build_small_notification(string: String)
        {
            @Suppress("DEPRECATION")
            val mBuilder = NotificationCompat.Builder(ctx)
                    .setSmallIcon(R.drawable.notification_icon_background)
                    .setContentTitle(ctx.getString(R.string.notification_title))
                    .setContentText(ctx.getString(R.string.notification_sync_successfuk) + ctx.getString(R.string.notificatoin_op_id) + string)
            val mNotificationManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
            if(mNotificationManager!=null)
                 mNotificationManager.notify(1,mBuilder.build())
        }

        /*
               updates all DB on thread
            */
        private fun sync_local(context: Context,intent: Intent)
        {
            Thread({
                db_sync_func(context,intent)
            }).start()
        }

        /*
            inner call with sync-wait
         */
        public fun db_sync_func(context: Context,intent: Intent)
        {

            db_sync_func_without_mark()
            mark_done(context,intent)
        }

        /*
          inner call with sync-wait without mark
       */
        private fun db_sync_func_without_mark()
        {
            try
            {
                projects.sync_db()
                inventory.sync_db()
                opr.sync_db()
                vendor.sync_db()
                big_table.sync_db()
            }
            catch (e:Exception)
            {
                Log.d("offline_mode","Couldn't sync for first time for some reason")
            }
        }

        /*
        empty one as service
         */
        public fun sync_local()
        {

            Thread({ db_sync_func()
            }).start()
        }

        /*
            empty call for outer-service
         */
        private fun db_sync_func()
        {
            db_sync_func_without_mark()
        }

    } // companion end


}
