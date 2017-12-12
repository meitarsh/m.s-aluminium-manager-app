package com.example.chaosruler.msa_manager.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.activies.MainActivity

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
                var service_intent = Intent(context,offline_mode_service::class.java)
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