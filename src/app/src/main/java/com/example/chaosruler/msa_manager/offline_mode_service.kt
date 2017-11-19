package com.example.chaosruler.msa_manager

import android.app.IntentService
import android.content.Context
import android.content.Intent
import java.lang.Thread.sleep
import java.util.*


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
        /*
            thread, run in thread to not hang the UI
         */
        private var trd = Thread(
                {
                   try
                   {
                       try_to_run_command()
                       sleep(this.ctx.getString(R.string.millis_in_sec).toLong()*this.ctx.getString(R.string.time_to_sync_in_sec).toLong())
                   }
                   catch (e:InterruptedException){}
                }
        )

        /*
            subroutine respoonsible to initate the service with a thread that automaticily sends commends every X seconds
         */
        fun init_cache(context: Context)
        {
            ctx=context
            cache = cache_server_commands(context)
            start_trd()
        }


        fun start_trd()
        {
            trd.start()

        }
        /*
            following subroutines pushes server commands to stack
         */

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

        /*
        removes a stored command
         */
        fun push_remove_command(db: String, table: String, where_clause: String, compare_to: Array<String>, type: String) {
            var str = remote_SQL_Helper.construct_remove_str(db, table, where_clause, compare_to, type).replace("'","&quote;")
            var username = remote_SQL_Helper.username
            cache.add_command_to_list(cache_command(str, username))
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
                        cache.remove_command(item)
                }
            }
        }


    }


}
