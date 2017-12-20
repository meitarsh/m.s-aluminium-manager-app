package com.example.chaosruler.msa_manager.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.SQLITE_helpers.cache_server_commands
import com.example.chaosruler.msa_manager.SQLITE_helpers.user_database_helper
import com.example.chaosruler.msa_manager.activies.MainActivity
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.User
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.cache_command
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by chaosruler on 12/12/17.
 */
class broadcast_reciever : BroadcastReceiver()
{
    override fun onReceive(context: Context?, intent: Intent?)
    {
        Log.d("Broadcast", "Recieve complete on boot!")
        if(intent!=null && context!=null)  // autocast to Intent and Context
        {
            if (context.getString(R.string.boot_completed) == intent.action)
            {
                var userdb:Vector<User> // grab user database to know that users password
                try
                {
                    userdb = user_database_helper(context).get_entire_db()
                }
                catch (e:Exception)
                {
                    return
                }
                var cache_database:Vector<cache_command> // grab cache vector to know // the most frequent user to login into
                try
                {
                    cache_database = cache_server_commands(context).get_entire_db()
                }
                catch (e:Exception)
                {
                   return
                }

                var username_usage_hashmap : HashMap<String,Int> = HashMap()
                for (cache in cache_database) // satistics username coverage
                {
                    var current = username_usage_hashmap[cache.__user] ?: 0
                    username_usage_hashmap[cache.__user] = current++
                }
                var username:String? = null
                var max = 0
                for(item in username_usage_hashmap) // grabs the most used user
                {
                    if(item.value > max)
                    {
                        username = item.key
                        max = item.value
                    }
                }
                if(userdb.size == 0) // no users == no logon
                    return
                var pw:String =
                if(username==null)
                {
                    username = userdb.firstElement().get__username()
                    userdb.firstElement().get__password()
                }
                else
                {
                    var index = -1
                    userdb
                            .filter { it.get__username() == username }
                            .forEach { index = userdb.indexOf(it) }
                    if(index == -1)
                    {
                        username = userdb.firstElement().get__username()
                        userdb.firstElement().get__password()
                    }
                    else
                    {
                        username = userdb[index].get__username()
                        userdb[index].get__password()
                    }
                }
                @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
                var wait_lock:Object = Object()
                Thread({
                    remote_SQL_Helper.Connect(context,username,pw)
                    synchronized(wait_lock)
                    {
                       wait_lock.notify()
                    }
                }).start()
                synchronized(wait_lock)
                {
                    try
                    {
                        wait_lock.wait()
                    } catch (e: InterruptedException)
                    {
                        Log.d("Broadcast", "Released! logged in now!")
                    }
                }
                global_variables_dataclass.init_dbs(context)
                var service_intent = Intent(context, offline_mode_service::class.java)
                context.startService(service_intent)


            }
        }
    }

    companion object {
        public fun report_to_Main_Activity_Thread_syncing_is_done()
        {
            MainActivity.service_sync_done = true
        }
    }
}