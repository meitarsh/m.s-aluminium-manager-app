@file:Suppress("MemberVisibilityCanBePrivate", "DEPRECATION")

package com.example.chaosruler.msa_manager.services.VPN_google_toyVPN


import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.VpnService
import android.os.Handler
import android.os.Message
import android.os.ParcelFileDescriptor
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import com.example.chaosruler.msa_manager.R
import java.io.IOException
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference


/**
 * a class representing a VPN connection
 * @author Chaosruler972
 * @constructor since this is a service, construction is done on StartService API call
 * All calls and implentations is based on Google's ToyVPN directly! please watch it
 * @see <a href=" https://android.googlesource.com/platform/development/+/master/samples/ToyVpn/src/com/example/android/toyvpn/ToyVpnService.java">ToyVpnService</a>
 */
@Suppress("unused", "MemberVisibilityCanPrivate")
class vpn_connection : VpnService(), android.os.Handler.Callback
{
    /**
     * class tag string
     */
    private val TAG = vpn_connection::class.java.simpleName

    /**
     * defining an intent action for connect
     */
    val ACTION_CONNECT = "com.example.chaosruler.msa_manager.START"

    /**
     * defining an intent action for disconnect
     */
    val ACTION_DISCONNECT = "com.example.chaosruler.msa_manager.STOP"

    /**
     * for the mssages
     */
    private var mHandler: Handler? = null

    /**
     * defining a connection into a thread and a pfd
     */
    private class Connection(thread: Thread, pfd: ParcelFileDescriptor, val pair: Pair<Thread, ParcelFileDescriptor> = Pair(thread, pfd))


    /**
     * The thread for the connection
     */
    private val mConnectingThread = AtomicReference<Thread>()
    /**
     * the connection itself
     */
    private val mConnection = AtomicReference<Connection>()
    /**
     * the connection id
     */
    private val mNextConnectionId = AtomicInteger(1)
    /**
     * the configuration intent
     */
    private var mConfigureIntent: PendingIntent? = null

    /**
     * initates handler and intent upon creation
     */
    override fun onCreate()
    {
        // The handler is only used to show messages.
        if (mHandler == null) {
            mHandler = Handler(this)
        }
        // Create the intent to "configure" the connection (just start ToyVpnClient).
        mConfigureIntent = PendingIntent.getActivity(this, baseContext.resources.getInteger(R.integer.VPN_request_code), Intent(this, vpn_connection::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT)
    }

    /**
     * when thread starts, keep it alive
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return if (intent != null && ACTION_DISCONNECT == intent.action) {
            disconnect()
            Service.START_NOT_STICKY
        } else {
            connect()
            Service.START_STICKY
        }
    }

    /**
     * when done, destroy
     */
    override fun onDestroy() {
        disconnect()
    }

    /**
     * Handles message pop up
     * @author Chaosruler972
     * @param message the message itself
     * @return upon success (always true)
     */
    override fun handleMessage(message: Message): Boolean {
        Toast.makeText(this, message.what, Toast.LENGTH_SHORT).show()
        @Suppress("DEPRECATED_IDENTITY_EQUALS")
        if (message.what !== R.string.disconnected) {
            updateForegroundNotification(message.what)
        }
        return true
    }

    /**
     * pop up an notification by predefined messages from ID
     * @author Chaosruler972
     * @param message the id of the message to pull
     */
    private fun updateForegroundNotification(message: Int) {
        startForeground(1, Notification.Builder(this)
                .setContentText(getString(message))
                .setContentIntent(mConfigureIntent)
                .build())
    }

    /**
     * Disconnects
     * @author Chaosruler972
     */
    private fun disconnect() {
        mHandler?.sendEmptyMessage(R.string.disconnected)
        setConnectingThread(null)
        setConnection(null)
        stopForeground(true)
    }

    /**
     * add connection to new thread
     * @author Chaosruler972
     * @param thread the thread we should add to pool
     */
    private fun setConnectingThread(thread: Thread?) {
        val oldThread = mConnectingThread.getAndSet(thread)
        oldThread?.interrupt()
    }

    /**
     * a setter for connection
     * @author Chaosruler972
     * @param connection the connection itself
     */
    private fun setConnection(connection: Connection?) {
        val oldConnection = mConnection.getAndSet(connection)
        if (oldConnection != null) {
            try {
                oldConnection.pair.first.interrupt()
                oldConnection.pair.second.close()
            } catch (e: IOException) {
                Log.e(TAG, "Closing VPN interface", e)
            }

        }
    }

    /**
     * Starts a new connection with the confiugration of VPN_conn_helper
     * @param connection the configuration
     * @author Chaosruler972
     */
    private fun startConnection(connection: VPN_conn_helper) {
        // Replace any existing connecting thread with the  new one.
        val thread = Thread(connection, "VPN Connection thread")
        setConnectingThread(thread)
        // Handler to mark as connected once onEstablish is called.
        connection.setConfigureIntent(mConfigureIntent!!)
        connection.setOnEstablishListener(object : VPN_conn_helper.OnEstablishListener {
            override fun onEstablish(tunInterface: ParcelFileDescriptor) {
                mHandler?.sendEmptyMessage(R.string.connected)
                mConnectingThread.compareAndSet(thread, null)
                setConnection(Connection(thread, tunInterface))
            }
        })
        thread.start()
    }

    /**
     * Connects to specified IP
     * @author Chaosruler972
     */
    private fun connect() {
        init_vars(baseContext)
        /*
        if(ip.isEmpty() || port.isEmpty() || psk.isEmpty() || username.isEmpty() || password.isEmpty())
            return
        */
        if(ip.isEmpty() || port.isEmpty() || psk.isEmpty())
            return
        // Become a foreground service. Background services can be VPN services too, but they can
        // be killed by background check before getting a chance to receive onRevoke().
        updateForegroundNotification(R.string.connecting)
        mHandler?.sendEmptyMessage(R.string.connecting)
        // Extract information from the shared preferences.
        val server = ip
        val secret = psk.toByteArray()
        val port_n = port.toInt()

        // Kick off a connection.
        startConnection(VPN_conn_helper(
                this, mNextConnectionId.getAndIncrement(), server, port_n, secret))
    }

    /**
     * IP address to connect to
     */
    private var ip:String = ""
    /**
     * Port to connect to
     */
    private var port:String = ""
    /**
     * username to use
     * --NOT IMPLENTED YET--
     */
    private var username:String = ""
    /**
     * Password to use
     * --NOT IMPLENTED YET--
     */
    private var password:String = ""
    /**
     * Secret login key for VPN security
     */
    private var psk:String = ""
    /**
     * Inits all the variables from config for the VPN Connection
     * @param context the context we work with
     * @author Chaosruler972
     */
    fun init_vars(context: Context)
    {
        /**
         * if we decided to store VPN configuration on settings seucrely, this is the way to read them
         */
        /*
        encryption.generate_key(context)
        ip = String(encryption.decrypt(PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.vpn_ip_key),"0.0.0.0").toByteArray(Charset.forName("UTF-8"))))
        encryption.generate_key(context)
        port = String(encryption.decrypt(PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.vpn_port),context.getString(R.string.vpn_port_default)).toByteArray(Charset.forName("UTF-8"))))
        encryption.generate_key(context)
        psk= String(encryption.decrypt(PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.vpn_psk),"").toByteArray(Charset.forName("UTF-8"))))
        encryption.generate_key(context)
        username= String(encryption.decrypt(PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.vpn_username),"").toByteArray(Charset.forName("UTF-8"))))
        encryption.generate_key(context)
        password= String(encryption.decrypt(PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.vpn_password),"").toByteArray(Charset.forName("UTF-8"))))
        */

        ip = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.vpn_ip_key),"0.0.0.0")
        port = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.vpn_port),context.getString(R.string.vpn_port_default))
        psk= PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.vpn_psk),"")
        username= PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.vpn_username),"")
        password= PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.vpn_password),"")


    }


    companion object {


    /**
     * Checks if VPN configuration is required or not at all
     * @author Chaosruler972
     * @param context the context to work with
     * @return true if VPN connection is required, false otherwise
     */
    fun check_if_need_to_connect(context: Context):Boolean = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getString(R.string.vpn_connect_key),false)

    }




}