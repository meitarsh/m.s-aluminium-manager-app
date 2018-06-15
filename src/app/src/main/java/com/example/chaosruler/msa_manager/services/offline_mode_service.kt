package com.example.chaosruler.msa_manager.services

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Binder
import android.os.IBinder
import android.preference.PreferenceManager
import android.support.v4.app.NotificationCompat
import com.example.chaosruler.msa_manager.MSSQL_helpers.*
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.SQLITE_helpers.cache_server_commands.cache_server_commands
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_OPR_table_helper.local_OPR_table_helper
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_big_table_helper.local_big_table_helper
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_inventory_table_helper.local_inventory_table_helper
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_projects_table_helper.local_projects_table_helper
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_salprojluz_table_helper.local_salprojluz_table_helper
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_salprojmng_table_helper.local_salprojmng_table_helper
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_salprojtakala_table_helpe.local_salprojtakala_table_helper
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_vendor_table_helper.local_vendor_table_helper
import com.example.chaosruler.msa_manager.object_types.User
import com.example.chaosruler.msa_manager.object_types.big_table.big_table_data
import com.example.chaosruler.msa_manager.object_types.cache_command
import com.example.chaosruler.msa_manager.object_types.opr_data.opr_data
import com.example.chaosruler.msa_manager.object_types.project_data.project_data
import com.example.chaosruler.msa_manager.object_types.salprojluz_data.salprojluz_data
import com.example.chaosruler.msa_manager.object_types.salprojmng_table_data.salprojmng_table_data
import com.example.chaosruler.msa_manager.object_types.takala_data.takala_data
import com.example.chaosruler.msa_manager.object_types.vendor_data.vendor_data
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.sync.Mutex
import kotlinx.coroutines.experimental.sync.withLock
import java.util.*


@Suppress("unused", "PLATFORM_CLASS_MAPPED_TO_KOTLIN")
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

        if(intent!=null) {
            init_cache(baseContext, intent)
        }
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
        @SuppressLint("StaticFieldLeak")
        private lateinit var big_table: local_big_table_helper

        /**
         * local BIG db instance on SQLITE
         * @author Chaosruler972
         */
        @SuppressLint("StaticFieldLeak")
        private lateinit var salproj_table: local_salprojluz_table_helper

        /**
         * local BIG db instance on SQLITE
         * @author Chaosruler972
         */
        @SuppressLint("StaticFieldLeak")
        private lateinit var takala_table: local_salprojtakala_table_helper

        /**
         * local salprojmng table instance
         * @author Chaosruler972
         */
        @SuppressLint("StaticFieldLeak")
        private lateinit var salprojmng_table: local_salprojmng_table_helper

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
        private var trd:Thread= Thread {

            //            Thread.sleep(time)
//            Timer().scheduleAtFixedRate(object : TimerTask() {
//                override fun run() {
//                    if(remote_SQL_Helper.get_latest_sync_time().time > 0.toLong())
//                        try_to_run_command()
//                    global_variables_dataclass.log("offline_mode", "Did a trd run")
//                }
//            }, 0, time)//put here time 1000 milliseconds=1 second
            while (true)
            {
                global_variables_dataclass.log("offline_mode", "Did a trd run")
                try {
                    Thread {
                        try_to_run_command()
                    }.start()
                }
                catch (e:Exception)
                {
                    global_variables_dataclass.log("Command", "Had an exception!")
                }
                try
                {
                    global_variables_dataclass.log("Going to sleep for ", (time / 1000).toString() + " Seconds")
                    Thread.sleep(time)
                }
                catch (e: InterruptedException)
                {
                    global_variables_dataclass.log("offline mode", "retring a re-sync of everything")
                }

            }
        }


        /**
         * build notificatoin to show on screen
         * @author Chaosruler972
         * @param string the string that appears on the notification
         */
        @SuppressLint("PrivateResource")
        private fun build_small_notification(string: String, use_prefix: Boolean = true) {
            val send_notification = PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(ctx.getString(R.string.notification),true)
            if (!send_notification)
                return

            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val used_string_as_prefix = if(use_prefix)
                ctx.getString(R.string.notification_sync_successfuk) + " " + ctx.getString(R.string.notificatoin_op_id)
            else
                ""
            @Suppress("DEPRECATION")
            val mBuilder = NotificationCompat.Builder(ctx)
                    .setSmallIcon(R.drawable.notification_icon_background)
                    .setContentTitle(ctx.getString(R.string.notification_title))
                    .setContentText(used_string_as_prefix+ string)
                    .setSound(alarmSound)
            (ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?)?.notify(1, mBuilder.build())
        }

        /**
         * subroutine respoonsible to initate the service with a thread that automaticily sends commends every X seconds
         * @author Chaosruler972
         * @param context the context we work with
         * @param intent the intent to report to when done
         */
        private fun init_cache(context: Context, intent: Intent) {

            global_variables_dataclass.init_dbs(context)
            ctx =context
            cache = cache_server_commands(context)
            inventory = global_variables_dataclass.DB_INVENTORY!!
            projects = global_variables_dataclass.DB_project!!
            opr = global_variables_dataclass.DB_OPR!!
            vendor = global_variables_dataclass.DB_VENDOR!!
            big_table = global_variables_dataclass.DB_BIG!!
            salproj_table = global_variables_dataclass.DB_SALPROJ!!
            takala_table = global_variables_dataclass.DB_SALPROJTAKALA!!
            salprojmng_table = global_variables_dataclass.DB_SALPROJMNG!!

            grab_time(ctx)
            init_remote_databases(context)

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
//            val main_intent = Intent(context, MainActivity::class.java)
//            main_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            context.startActivity(main_intent)
        }


        /**
         *  grab time from settings
         *  @author Chaosruler972
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
            remote_salprojluz_table_helper.extract_variables(context)
            remote_inventory_table_helper.extract_variables(context)
            remote_opr_table_helper.extract_variables(context)
            remote_projects_table_helper.extract_variables(context)
            remote_takala_table_helper.extract_variables(context)
            remote_salprojmng_table_helper.extract_variables(context)
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
        fun push_add_command(db: String, table: String, vector: Vector<String>, map: HashMap<String, String>, modified_time: Boolean): String {

            val str = remote_SQL_Helper.construct_add_str(db, table, vector, map, modified_time).replace("'", "&quote;")
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
        fun push_update_command(db: String, table: String, where_clause: String, compare_to: Array<String>, type: String, update_to: HashMap<String, String>, modified_time: Boolean): String {

            val str = remote_SQL_Helper.construct_update_str(db, table, where_clause, compare_to, type, update_to, modified_time).replace("'", "&quote;")
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
            global_variables_dataclass.log("offline_mode", "cache command size: " + vector.size.toString())

            for (item in vector) {
                if (item.__user == remote_SQL_Helper.getusername()) {
                    global_variables_dataclass.log("Running command: ", item.__command)
                    val result_of_query = remote_SQL_Helper.run_command(item.__command.replace("&quote;", "'"))
                    global_variables_dataclass.log("offline_mode", "Query to try :" + item.__command.replace("&quote;", "'"))
                    global_variables_dataclass.log("offline_mode", "Query Result: " + result_of_query.toString())
                    if (result_of_query) {
                        build_small_notification(cache.get_id_of_command(item).toString())
                        cache.remove_command(item)
                    }

                }
            }

            try {
                if(PreferenceManager.getDefaultSharedPreferences(ctx).getString(ctx.getString(R.string.sync_frequency), 15.toString()).toInt()!=0)
                    db_sync_func_without_mark()
            } catch (e: Exception) {
                global_variables_dataclass.log("offline_mode", "Couldn't sync! error on time")
            }


        }



        /**
         * updates all DB on thread
         * @author Chaosruler972
         * @param context the context to work with
         * @param intent the intent to report to
         */
        private fun sync_local(context: Context, intent: Intent) {
            Thread {
                db_sync_func(context,intent)
            }.start()
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
        private fun db_sync_func_without_mark()
        {
            val user = remote_SQL_Helper.user!!
            val vecOfUsername = Vector<String>()
            vecOfUsername.addElement(user.get__username())
            global_variables_dataclass.projids_to_sync = vecOfUsername
            salprojmng_table.sync_db_by_key<salprojmng_table_data>(vecOfUsername)
            global_variables_dataclass.db_salprojmng_vec = salprojmng_table.get_local_DB()

            global_variables_dataclass.projids_to_sync = Vector()
            for(mng in global_variables_dataclass.db_salprojmng_vec)
            {
                if(mng.get_projid()!=null)
                    global_variables_dataclass.projids_to_sync.addElement(mng.get_projid())
            }

            global_variables_dataclass.log("download", "Need to find ${global_variables_dataclass.projids_to_sync.size} Projects")

            build_small_notification(ctx.getString(R.string.sync_started), false)
            val done_count = IntArray(1)
            val max_count = 6



            val mtx = Mutex()
            val lock = Object()

            async {
                load_dbs()
                synchronized(lock)
                {
                    lock.notify()
                }
            }.start()

            global_variables_dataclass.log("syncing from time", user.get_last_sync_time().toString())
            try {
                async {
                    global_variables_dataclass.log("db_sync", "projects")
                    projects.beginTrans()
                    projects.sync_db_by_key<project_data>(global_variables_dataclass.projids_to_sync)
                    projects.endTrans()
                    global_variables_dataclass.log("db_sync", "projects done")
                    done_syncing(mtx,done_count,max_count, lock, user)

                }.start()

//               async {
//                   global_variables_dataclass.log("db_sync","inventory")
////                   inventory.beginTrans()
//                   inventory.sync_db()
////                   inventory.endTrans()
//                   global_variables_dataclass.log("db_sync","inventory done")
//                   done_syncing(mtx,done_count,max_count, lock)
//               }.start()


                async {
                    global_variables_dataclass.log("db_sync", "big_table")
                    big_table.beginTrans()
                    big_table.sync_db_by_key<big_table_data>(global_variables_dataclass.projids_to_sync)
                    big_table.endTrans()
                    global_variables_dataclass.db_big_vec = big_table.get_local_DB()

                    global_variables_dataclass.log("db_sync", "big_table done")
                    done_syncing(mtx,done_count,max_count, lock, user)
                    val syncmap = global_variables_dataclass.get_hashmap_of_ids_from_big()

                    async {
                        global_variables_dataclass.log("db_sync", "vendor")
                        vendor.beginTrans()
                        vendor.sync_db_by_key<vendor_data>(syncmap["vend"]!!)
                        vendor.endTrans()
                        global_variables_dataclass.log("db_sync", "vendor done")
                        done_syncing(mtx,done_count,max_count, lock, user)

                    }.start()

                    async {
                        global_variables_dataclass.log("db_sync", "opr")
                         opr.beginTrans()
                        opr.sync_db_by_key<opr_data>(syncmap["opr"]!!)
                         opr.endTrans()
                        global_variables_dataclass.log("db_sync", "opr done")
                        done_syncing(mtx,done_count,max_count, lock, user)

                    }.start()

                }.start()

                async {
                    global_variables_dataclass.log("db_sync", "salproj_table")
                    salproj_table.beginTrans()
                    salproj_table.sync_db_by_key<salprojluz_data>(global_variables_dataclass.projids_to_sync)
                    salproj_table.endTrans()
                    global_variables_dataclass.log("db_sync", "salproj_table done")
                    done_syncing(mtx,done_count,max_count, lock, user)

                }.start()

                async {
                    global_variables_dataclass.log("db_sync", "takala_table")
                    takala_table.beginTrans()
                    takala_table.sync_db_by_key<takala_data>(global_variables_dataclass.projids_to_sync)
                    takala_table.endTrans()
                    global_variables_dataclass.log("db_sync", "takala_table done")
                    done_syncing(mtx, done_count, max_count, lock, user)

                }.start()

            } catch (e: Exception)
            {
                global_variables_dataclass.log("offline_mode", "Couldn't sync for first time for some reason")
            }
            global_variables_dataclass.log("offline_sync", "after sync before lock")
            synchronized(lock)
            {
                try {
                    lock.wait()
                }
                catch (e: InterruptedException){}
            }
            global_variables_dataclass.log("offline_sync", "after sync after lock")
            global_variables_dataclass.log("offline_sync", "Project DB has ${global_variables_dataclass.db_project_vec.count()} elements")

        }

        /**
         * Checks if we completed the number of threads required
         * @author Chaosruler972
         * @param count how many threads completed so far
         * @param maxCount how many threads should be completed
         * @param lock the cv to release after condition calues
         * @param user the user that requested the syncing service (for printing requirements)
         * @param is_sync if threads are syncing threads (results in different reward when complete)
         */
        @Suppress("UNUSED_PARAMETER")
        private fun check_for_complete(count: IntArray, maxCount: Int, lock: Object, user: User, is_sync : Boolean)
        {
            global_variables_dataclass.log("db_sync", "Checking for completeness, num is ${count[0]}, max is $maxCount for $is_sync on synced")
            if (count[0] == maxCount) {
                if(is_sync) {
                    user.set_last_sync_time(Date().time)
                    remote_SQL_Helper.user = user
                    global_variables_dataclass.DB_USERS!!.update_user(user.get__username(), user.get__password(), user.get_last_sync_time().time)
                    build_small_notification(ctx.getString(R.string.notificatoin_syncing_done), false)
                }
                else
                {
                    synchronized(lock)
                    {
                        lock.notify()
                    }
                }
            }
        }

        /**
         * Flag when done syncing on threads
         * @author Chaosruler972
         * @param mtx the mutex to lock/unlock for cv
         * done_count an array that increses until it reaches max_count, then it stops, array has only one element (need to be a pointer)
         * max_count when done_count should stop counting (number of threads)
         * lock cv to unlock
         * is_sync if the threads we are using are synchronizing threads (affects completion rewards)
         */
        private suspend fun done_syncing(mtx: Mutex, done_count: IntArray, max_count: Int, lock: Object, user: User, is_sync : Boolean = true)
        {
            mtx.withLock {
                global_variables_dataclass.log("db_sync", "Called with $is_sync")
                synchronized(done_count[0])
                {
                    done_count[0]++
                    check_for_complete(done_count, max_count, lock, user, is_sync)
                }
            }
        }

        /**
         * Sync local database
         * @author Chaosruler972
         */
        @Suppress("unused")
        fun sync_local() {

            Thread { db_sync_func()
            }.start()
        }

        /**
         * Syncs databases without marking when done
         * @author Chaosruler972
         */
        private fun db_sync_func()
        {

            db_sync_func_without_mark()
        }

        /**
         * Loads all the databases to memory
         * @author Chaosruler972
         */
        private fun load_dbs()
        {
            val mtx = Mutex()
            val lock = Object()
            val max_count = 5
            val done_count = IntArray(1)
            val user = remote_SQL_Helper.user!!

            async {
                global_variables_dataclass.log("load", "Started syncing projects")
                global_variables_dataclass.db_project_vec = projects.get_local_DB()
                global_variables_dataclass.log("load", "${global_variables_dataclass.db_project_vec.size}")
                done_syncing(mtx,done_count,max_count, lock, user, false)
            }.start()

            async {
                global_variables_dataclass.log("load", "Started syncing opr")
                global_variables_dataclass.db_opr_vec = opr.get_local_DB()
                global_variables_dataclass.log("load", "${global_variables_dataclass.db_opr_vec.size}")
                done_syncing(mtx,done_count,max_count, lock, user, false)

            }.start()

            async {
                global_variables_dataclass.log("load", "Started syncing vendor")
                global_variables_dataclass.db_vendor_vec = vendor.get_local_DB()
                global_variables_dataclass.log("load", "${global_variables_dataclass.db_vendor_vec.size}")
                done_syncing(mtx,done_count,max_count, lock, user, false)

            }.start()

//            async {
//                global_variables_dataclass.log("load", "Started syncing big")
//                global_variables_dataclass.db_big_vec = big_table.get_local_DB()
//                global_variables_dataclass.log("load", "${global_variables_dataclass.db_big_vec.size}")
//                done_syncing(mtx,done_count,max_count, lock, user, false)
//            }.start()

            async {
                global_variables_dataclass.log("load", "Started syncing salproj")
                global_variables_dataclass.db_salproj_vec = salproj_table.get_local_DB()
                global_variables_dataclass.log("load", "${global_variables_dataclass.db_salproj_vec.size}")
                done_syncing(mtx, done_count, max_count, lock, user, false)
            }.start()

            async {
                global_variables_dataclass.log("load", "Started syncing salproj baka")
                global_variables_dataclass.db_salprojtakala_vec = takala_table.get_local_DB()
                global_variables_dataclass.log("load", "salproj bakara ${global_variables_dataclass.db_salprojtakala_vec.size}")
                done_syncing(mtx,done_count,max_count, lock, user, false)
            }.start()

            synchronized(lock)
            {
                try {
                    lock.wait()
                }
                catch (e: InterruptedException){
                    build_small_notification(ctx.getString(R.string.notification_loading_done), false)
                }
            }
            global_variables_dataclass.log("load", "Loading dbs is done")


        }

    } // companion end


}
