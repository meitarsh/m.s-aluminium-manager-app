package com.example.chaosruler.msa_manager.services

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.preference.PreferenceManager
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.example.chaosruler.msa_manager.MSSQL_helpers.*
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.SQLITE_helpers.cache_server_commands
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.*
import com.example.chaosruler.msa_manager.object_types.cache_command
import java.util.*


/**
 * service responsible for storing server commands offline and sending them whenever possible
 * @author Chaosruler972
 * @constructor a service is constructed using the android API call
 */
class offline_mode_service : Service(){


    /**
     * Service binder
     * @author Chaosruler972
     */
    inner class the_binder : Binder() {
        @Suppress("PropertyName", "unused")
        internal val service_label: offline_mode_service
            get() = this@offline_mode_service
    }

    /**
     * the binder variable
     * @author Chaosruler972
     */
    private val binder = the_binder()

    /**
     * returns the binder on bind, for binding purposes, because I love to say bind, yeah bind
     * @author Chaosruler972
     * @param intent the intent we might get
     * @return the binder
     */
    override fun onBind(intent: Intent?): IBinder = binder


    /**
     * on start service intent, inits the cache
     * @author Chaosruler972
     * @param flags the flags we got
     * @param intent the intent we recived to start on
     * @param startId serviceID
     * @return a service ID
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(intent!=null)
            init_cache(baseContext,intent)
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * so the service will not require instanced in order to preform functions
     * @author Chaosruler972
     */
    companion object {
        /**
         * Cache server db instance on SQLITE
         * @author Chaosruler972
         */
        @SuppressLint("StaticFieldLeak")
        private lateinit var cache: cache_server_commands

        /**
         * local inventory db instance on SQLITE
         * @author Chaosruler972
         */
        @SuppressLint("StaticFieldLeak")
        private lateinit var inventory: local_inventory_table_helper

        /**
         * local project db instance on SQLITE
         * @author Chaosruler972
         */
        @SuppressLint("StaticFieldLeak")
        private lateinit var projects: local_projects_table_helper

        /**
         * local OPR db instance on SQLITE
         * @author Chaosruler972
         */
        @SuppressLint("StaticFieldLeak")
        private lateinit var opr: local_OPR_table_helper

        /**
         * local vendor db instance on SQLITE
         * @author Chaosruler972
         */
        @SuppressLint("StaticFieldLeak")
        private lateinit var vendor: local_vendor_table_helper

        /**
         * local BIG db instance on SQLITE
         * @author Chaosruler972
         */
        private lateinit var big_table: local_big_table_helper

        /**
         * Context we work with for strings.xml values extraction
         * @author Chaosruler972
         */
        @SuppressLint("StaticFieldLeak")
        private lateinit var ctx:Context
        /**
         * Time to sync
         * @author Chaosruler972
         */
        private var time:Long = 0
        /*
            thread, run in thread to not hang the UI
         */
        /**
         * creates a syncing thread
         * @author Chaosruler972
         */
        private var trd:Thread= Thread({

            Timer().scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    try_to_run_command()
                    Log.d("offline_mode", "Did a trd run")
                }
            }, 0, time)//put here time 1000 milliseconds=1 second
//            while (true)
//            {
//                Log.d("offline_mode","Did a trd run")
//                try {
//                    Thread({
//                        try_to_run_command()
//                    }).start()
//                }
//                catch (e:Exception)
//                {
//                    Log.d("Command","Had an exception!")
//                }
//                try
//                {
//                    Log.d("Going to sleep for ", (time/1000).toString() +" Seconds")
//                    sleep(time)
//                }
//                catch (e: InterruptedException)
//                {
//                    Log.d("offline mode","retring a re-sync of everything")
//                }
//
//            }
        })


        /**
         * subroutine respoonsible to initate the service with a thread that automaticily sends commends every X seconds
         * @author Chaosruler972
         * @param context the context we work with
         * @param intent the intent to report to when done
         */
        private fun init_cache(context: Context, intent: Intent) {

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

            if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getString(R.string.local_or_not), true)) {
                sync_local(context, intent)
            } else
                mark_done(context, intent)

        }

        /**
         * adds to intent that sync is done
         * @author Chaosruler972
         * @param context the context to work with
         * @param intent the intent to report to when done
         */
        private fun mark_done(context: Context, intent: Intent) {

            global_variables_dataclass.report_to_Main_Activity_Thread_syncing_is_done()
            intent.putExtra(context.getString(R.string.key_sync_offline),context.getString(R.string.key_sync_offline))
        }


        /**
         *  grab time from settings
         *  @param context the context to work with
         *  @author Chaosruler972
         */
        private fun grab_time(context: Context) {
            time = ctx.resources.getInteger(R.integer.millis_in_sec).toLong()
            val sec = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.sync_frequency), context.resources.getInteger(R.integer.time_to_sync_in_sec).toString()).toLong()
            time *=sec
        }

        /**
         *  init all remove database to variables
         *  @author Chaosruler972
         *  @param context the context to work with
         */
        private fun init_remote_databases(context: Context) {
            remote_vendors_table_helper.extract_variables(context)
            remote_big_table_helper.extract_variables(context)
            remote_inventory_table_helper.extract_variables(context)
            remote_opr_table_helper.extract_variables(context)
            remote_projects_table_helper.extract_variables(context)
        }

        /**
         * tarts the sync thread
         * @author Chaosruler972
         */
        private fun start_trd() {
            if(time == 0.toLong())
                return
            if(trd.state == Thread.State.NEW) // new thread, not started
            {
                trd.start()
            }
        }

        /**
         *     following subroutines pushes server commands to stack
         * @author Chaosruler972
         * @param db the database to work with
         * @param map the hashmap of what we want to add (the types)
         * @param table the table that we work with
         * @param vector the data
         * @return the stringified form of the SQL request
         */
        @Suppress("unused")
        fun push_add_command(db: String, table: String, vector: Vector<String>, map: HashMap<String, String>): String {

            val str = remote_SQL_Helper.construct_add_str(db, table, vector, map).replace("'", "&quote;")
            val username = remote_SQL_Helper.getusername()
            return general_push_command(str, username)
        }

        /**
         *      pushes update command
         * @author Chaosruler972
         * @param db the database to work with
         * @param type the data types (if needed to stringify)
         * @param table the table name
         * @param compare_to what to compare the data to to identifiy target
         * @param update_to what should we update the data to
         * @param where_clause what field should we compare
         * @return the stringified form of the SQL request
         */
        @Suppress("unused")
        fun push_update_command(db: String, table: String, where_clause: String, compare_to: Array<String>, type: String, update_to: HashMap<String, String>): String {

            val str = remote_SQL_Helper.construct_update_str(db, table, where_clause, compare_to, type, update_to).replace("'", "&quote;")
            val username = remote_SQL_Helper.getusername()
            return general_push_command(str, username)
        }

        /**
         *  removes a stored command
         * @author Chaosruler972
         * @param db the database to work with
         * @param type the data types (if needed to stringify)
         * @param table the table name
         * @param compare_to what to compare the data to to identifiy target
         * @param where_clause what field should we compare
         * @return the stringified form of the SQL request
         */
        @Suppress("unused")
        fun push_remove_command(db: String, table: String, where_clause: String, compare_to: Array<String>, type: String): String {

            val str = remote_SQL_Helper.construct_remove_str(db, table, where_clause, compare_to, type).replace("'", "&quote;")
            val username = remote_SQL_Helper.getusername()
            return general_push_command(str, username)
        }


        /**
         *   pushes command with an already prepared string and its usernames
         * @author Chaosruler972
         * @param command the command in stringified form
         * @param username the username that pushed that command
         * @return What should we print to the user from result operation (success or not)
         */
        fun general_push_command(command: String, username: String): String {

            @Suppress("UnnecessaryVariable")
            val string: String = if (PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(ctx.getString(R.string.local_or_not), true)) {
                cache.add_command_to_list(cache_command(command, username))
                ctx.getString(R.string.successfull_operation) + ctx.getString(R.string.notificatoin_op_id) + cache.get_id_of_command(cache_command(command, username))
            } else {
                remote_SQL_Helper.run_command(command.replace("&quote;", "'"))
                ctx.getString(R.string.successfull_operation)
            }
            return string
        }

        /**
         *  gets all the comand cache database to a string
         * @author Chaosruler972
         * @return string to represent entire DB
         */
        @Suppress("unused")
        private fun get_DB_string(): String = cache.get_db_string()


        /**
         *  gets all the command cache database to a vector
         * @author Chaosruler972
         * @return vector to represent entire cache DB
         */
        private fun get_DB(): Vector<cache_command> = cache.get_entire_db()

        /**
         *  attempts to run all the commands in the command cache database
         *  @author Chaosruler972
         */
        fun try_to_run_command() {


            val vector = get_DB()
            Log.d("offline_mode","cache command size: " + vector.size.toString())

            for (item in vector) {
                if (item.__user == remote_SQL_Helper.getusername()) {
                    Log.d("Running command: ",item.__command)
                    val result_of_query = remote_SQL_Helper.run_command(item.__command.replace("&quote;", "'"))
                    Log.d("offline_mode","Query to try :" + item.__command.replace("&quote;", "'"))
                    Log.d("offline_mode","Query Result: " + result_of_query.toString())
                    if (result_of_query) {

                        if(PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(ctx.getString(R.string.notification),false))
                            build_small_notification(cache.get_id_of_command(item).toString())
                        cache.remove_command(item)
                    }

                }
            }

            try {
                if(PreferenceManager.getDefaultSharedPreferences(ctx).getString(ctx.getString(R.string.sync_frequency), 15.toString()).toInt()!=0)
                    db_sync_func_without_mark()
            } catch (e: Exception) {
                Log.d("offline_mode","Couldn't sync! error on time")
            }


        }

        /**
         * build notificatoin to show on screen
         * @author Chaosruler972
         * @param string the string that appears on the notification
         */
        @SuppressLint("PrivateResource")
        private fun build_small_notification(string: String) {
            @Suppress("DEPRECATION")
            val mBuilder = NotificationCompat.Builder(ctx)
                    .setSmallIcon(R.drawable.notification_icon_background)
                    .setContentTitle(ctx.getString(R.string.notification_title))
                    .setContentText(ctx.getString(R.string.notification_sync_successfuk) + ctx.getString(R.string.notificatoin_op_id) + string)
            (ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?)?.notify(1, mBuilder.build())
        }

        /**
         * updates all DB on thread
         * @author Chaosruler972
         * @param context the context to work with
         * @param intent the intent to report to
         */
        private fun sync_local(context: Context, intent: Intent) {
            Thread({
                db_sync_func(context,intent)
            }).start()
        }

        /**
         * inner call with sync-wait
         * @author Chaosruler972
         * @param context the context to work with
         * @param intent the intent to report to when done
         */
        fun db_sync_func(context: Context, intent: Intent) {

            db_sync_func_without_mark()
            mark_done(context,intent)
        }

        /**
         * inner call with sync-wait without mark
         * @author Chaosruler972
         */
        private fun db_sync_func_without_mark() {
            try {
                projects.sync_db()
                inventory.sync_db()
                opr.sync_db()
                vendor.sync_db()
                big_table.sync_db()
            } catch (e: Exception) {
                Log.d("offline_mode","Couldn't sync for first time for some reason")
            }
        }

        /**
         * Sync local database
         * @author Chaosruler972
         */
        @Suppress("unused")
        fun sync_local() {

            Thread({ db_sync_func()
            }).start()
        }

        /**
         * Syncs databases without marking when done
         * @author Chaosruler972
         */
        private fun db_sync_func() {
            db_sync_func_without_mark()
        }

    } // companion end


}
