package com.example.chaosruler.msa_manager.services


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.VpnService
import android.preference.PreferenceManager
import android.util.Log
import com.example.chaosruler.msa_manager.R


@Suppress("unused", "MemberVisibilityCanPrivate")
class vpn_connection : VpnService()
{
    fun prepare(act:Activity)
    {

        val new_intent: Intent? = VpnService.prepare(act)
        if(new_intent == null)
        {

        }
        else
        {
            act.startActivityForResult(new_intent,act.resources.getInteger(R.integer.VPN_request_code))
        }

        val builder = this.Builder()
        builder.addAddress(ip, 32)
        builder.setBlocking(true)
        val fd = builder.establish()
        if(fd != null)
        {
            Log.d("File Descriptor", fd.fd.toString())
        }
    }
    companion object
    {

        var ip:String = ""
        var port:String = ""
        var username:String = ""
        var password:String = ""

        fun init_vars(context: Context)
        {
            ip = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.vpn_ip_key),"0.0.0.0")
            port = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.vpn_port),context.getString(R.string.vpn_port_default))
            username=PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.vpn_username),"")
            password=PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.vpn_password),"")
        }

        fun check_if_need_to_connect(context: Context):Boolean = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getString(R.string.vpn_connect_key),false)


        fun connect(@Suppress("UNUSED_PARAMETER") context: Context):Boolean
        {
            val EXTRA_NAME = "de.blinkt.openvpn.shortcutProfileName"

            val shortcutIntent = Intent(Intent.ACTION_MAIN)
            shortcutIntent.setClassName("de.blinkt.openvpn", "de.blinkt.openvpn.LaunchVPN")
            shortcutIntent.putExtra(EXTRA_NAME,"upb ssl")
            context.startActivity(shortcutIntent)
            return false

        }
    } // end COMPANION OBJECT


}