package com.example.chaosruler.msa_manager

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.android.synthetic.main.activity_project_options.*
import java.lang.Thread.sleep
import java.util.*



class offline_mode_service() : Service() {

    companion object
    {
        private lateinit var cache: cache_server_commands

        fun init_cache(context: Context)
        {
            cache = cache_server_commands(context)
            Thread(
            {
                while(true)
                {
                    try_to_run_command()
                    try {
                        sleep(context.getString(R.string.time_to_sync).toLong())
                    }
                    catch (e:InterruptedException)
                    {

                    }
                }
            }).start()
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
                var result_of_query = remote_SQL_Helper.run_command(item.__command.replace("&quote;","'"))
                if(result_of_query)
                    cache.remove_command(item)
            }
        }


    }

    override fun onBind(intent: Intent): IBinder? = null
}
