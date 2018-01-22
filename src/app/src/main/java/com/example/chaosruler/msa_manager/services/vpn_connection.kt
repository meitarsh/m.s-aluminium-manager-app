@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.chaosruler.msa_manager.services


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.VpnService
import android.preference.PreferenceManager
import android.util.Log
import com.example.chaosruler.msa_manager.R

/**
 * a class representing a VPN connection
 * @author Chaosruler972
 * @constructor since this is a service, construction is done on StartService API call
 */
@Suppress("unused", "MemberVisibilityCanPrivate")
class vpn_connection : VpnService()
{
    /**
     * Prepares this VPN connection on Android system
     * @author Chaosruler972
     * @param act the activity that wanted the connection
     */
    fun prepare(act: Activity) {

        val new_intent: Intent? = VpnService.prepare(act)
        if (new_intent == null) {

        } else {
            act.startActivityForResult(new_intent,act.resources.getInteger(R.integer.VPN_request_code))
        }

        val builder = this.Builder()
        builder.addAddress(ip, 32)
        builder.setBlocking(true)
        val fd = builder.establish()
        if (fd != null) {
            Log.d("File Descriptor", fd.fd.toString())
        }
    }

    companion object {

        var ip:String = ""
        var port:String = ""
        var username:String = ""
        var password:String = ""

        /**
         * Inits all the variables from config for the VPN Connection
         * @param context the context we work with
         * @author Chaosruler972
         */
        fun init_vars(context: Context) {
            ip = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.vpn_ip_key),"0.0.0.0")
            port = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.vpn_port),context.getString(R.string.vpn_port_default))
            username=PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.vpn_username),"")
            password=PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.vpn_password),"")
        }

        /**
         * Checks if VPN configuration is required or not at all
         * @author Chaosruler972
         * @param context the context to work with
         * @return true if VPN connection is required, false otherwise
         */
        fun check_if_need_to_connect(context: Context):Boolean = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getString(R.string.vpn_connect_key),false)


        /**
         * Connects to the VPN connection
         * Not finished!
         * @param context the context to work with
         * @author Chaosruler972
         * @return if connection was succesfull
         */
        fun connect(@Suppress("UNUSED_PARAMETER") context: Context): Boolean {
            val EXTRA_NAME = "de.blinkt.openvpn.shortcutProfileName"

            val shortcutIntent = Intent(Intent.ACTION_MAIN)
            shortcutIntent.setClassName("de.blinkt.openvpn", "de.blinkt.openvpn.LaunchVPN")
            shortcutIntent.putExtra(EXTRA_NAME,"upb ssl")
            context.startActivity(shortcutIntent)
            return false

        }
    } // end COMPANION OBJECT


}