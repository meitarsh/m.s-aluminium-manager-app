@file:Suppress("unused")

package com.example.chaosruler.msa_manager.services

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.VpnService
import android.os.ParcelFileDescriptor
import android.preference.PreferenceManager
import android.util.Log
import com.example.chaosruler.msa_manager.R

@SuppressLint("Registered")
class VPN : VpnService() {

    private lateinit var builder: Builder
    private lateinit var fd: ParcelFileDescriptor
    private fun configure_vpn() {
        builder = this.Builder()
        builder.addAddress(IP_ADDR, 32)
        fd = builder.establish()
        Log.d("VPN", "VPN Service configured and connected")
    }

    override fun onRevoke() {
        super.onRevoke()
        fd.close()
        Log.d("VPN", "VPN Service closed")
    }


    override fun onCreate() {
        super.onCreate()
        configure_vpn()
    }

    companion object {
        fun VPN_Enabled(context: Context) = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getString(R.string.vpn_connect_key), false)
        private lateinit var IP_ADDR: String
        private lateinit var PORT: String
        private lateinit var UNAME: String
        private lateinit var PWD: String

        private fun init_variables(context: Context) {
            IP_ADDR = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.vpn_ip_key), "")
            PORT = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.vpn_port), "")
            UNAME = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.vpn_username), "")
            PWD = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.vpn_password), "")
        }

        fun prepare_vpn(act: Activity) {
            init_variables(act.baseContext)

            val vpn_intent = VpnService.prepare(act.baseContext)
            if (vpn_intent != null)
                act.startActivityForResult(vpn_intent, act.resources.getInteger(R.integer.VPN_request_code))
            else
                act.startService(Intent(act, VPN::class.java))
        }


    }

}