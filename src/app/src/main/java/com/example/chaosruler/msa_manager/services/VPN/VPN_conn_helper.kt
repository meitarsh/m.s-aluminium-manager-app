@file:Suppress("unused")

package com.example.chaosruler.msa_manager.services.VPN

import android.app.PendingIntent
import android.net.VpnService
import android.os.ParcelFileDescriptor
import android.util.Log
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.InetSocketAddress
import java.net.SocketAddress
import java.net.SocketException
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel
import java.nio.charset.StandardCharsets.US_ASCII
import java.util.concurrent.TimeUnit

/**
 * a connection configuratoin class
 * based on Google's toyVPN directly, changed names for easier understanding
 * @see <a href="https://android.googlesource.com/platform/development/+/master/samples/ToyVpn/src/com/example/android/toyvpn/ToyVpnConnection.java">ToyVPNConnection</a>
 */
class VPN_conn_helper(
        /**
         * The VPN service that we are linked to
         */
        private val mService: VpnService,
        /**
         * the connection id to identify it
         */
        private val mConnectionId: Int,
        /**
         * the servername (ip)
         */
        private val mServerName: String,
        /**
         * the port to connect to
         */
        private val mServerPort: Int,
        /**
         * the PSK
         */
        private val mSharedSecret: ByteArray) : Runnable {
    /**
     * connection intent after configuring is done
     */
    private var mConfigureIntent: PendingIntent? = null
    /**
     * after connection is done, this is called
     */
    private var mOnEstablishListener: OnEstablishListener? = null
    /**
     * class tag
     */
    private val tag: String
        get() = VPN_conn_helper::class.java.simpleName + "[" + mConnectionId + "]"

    /**
     * Callback interface to let the [VPN_conn_helper] know about new connections
     * and update the foreground notification with connection status.
     * @author Chaosruler972
     */
    interface OnEstablishListener {
        fun onEstablish(tunInterface: ParcelFileDescriptor)
    }

    /**
     * Optionally, set an intent to configure the VPN. This is `null` by default.
     * @author Chaosruler972
     */
    fun setConfigureIntent(intent: PendingIntent) {
        mConfigureIntent = intent
    }

    /**
     * setter for listener
     * @author Chaosruler972
     * @param listener the listener to set
     */
    fun setOnEstablishListener(listener: OnEstablishListener) {
        mOnEstablishListener = listener
    }

    /**
     * Working wihtout connectivity manager
     * @author Chaosruler972
     */
    override fun run() {
        try {
            Log.i(tag, "Starting")
            // If anything needs to be obtained using the network, get it now.
            // This greatly reduces the complexity of seamless handover, which
            // tries to recreate the tunnel without shutting down everything.
            // In this demo, all we need to know is the server address.
            val serverAddress = InetSocketAddress(mServerName, mServerPort)
            // We try to create the tunnel several times.
            // TODO: The better way is to work with ConnectivityManager, trying only when the
            //       network is available.
            // Here we just use a counter to keep things simple.
            var attempt = 0
            while (attempt < 10) {
                // Reset the counter if we were connected.
                if (run(serverAddress)) {
                    attempt = 0
                }
                // Sleep for a while. This also checks if we got interrupted.
                Thread.sleep(3000)
                ++attempt
            }
            Log.i(tag, "Giving up")
        } catch (e: IOException) {
            Log.e(tag, "Connection failed, exiting", e)
        } catch (e: InterruptedException) {
            Log.e(tag, "Connection failed, exiting", e)
        } catch (e: IllegalArgumentException) {
            Log.e(tag, "Connection failed, exiting", e)
        }

    }

    /**
     * Run -> connects, handshake, inits service...
     * @author Chaosruler972
     * @param server the ip address we should connect to
     * @return if connection was successfull, true, else false
     */
    @Throws(IOException::class, InterruptedException::class, IllegalArgumentException::class)
    private fun run(server: SocketAddress): Boolean {
        var iface: ParcelFileDescriptor? = null
        var connected = false
        // Create a DatagramChannel as the VPN tunnel.
        try {
            DatagramChannel.open().use({ tunnel ->
                // Protect the tunnel before connecting to avoid loopback.
                try
                {
                    mService.protect(tunnel.socket())
                }
                catch (e:IllegalStateException)
                {
                    Log.d(tag,"Cannot protect the tunnel")
                    return false
                }
                // Connect to the server.
                tunnel.connect(server)
                // For simplicity, we use the same thread for both reading and
                // writing. Here we put the tunnel into non-blocking mode.
                tunnel.configureBlocking(false)
                // Authenticate and configure the virtual network interface.
                iface = handshake(tunnel)
                // Now we are connected. Set the flag.
                connected = true
                // Packets to be sent are queued in this input stream.
                val `in` = FileInputStream(iface!!.fileDescriptor)
                // Packets received need to be written to this output stream.
                val out = FileOutputStream(iface!!.fileDescriptor)
                // Allocate the buffer for a single packet.
                val packet = ByteBuffer.allocate(MAX_PACKET_SIZE)
                // Timeouts:
                //   - when data has not been sent in a while, send empty keepalive messages.
                //   - when data has not been received in a while, assume the connection is broken.
                var lastSendTime = System.currentTimeMillis()
                var lastReceiveTime = System.currentTimeMillis()
                // We keep forwarding packets till something goes wrong.
                while (true) {
                    // Assume that we did not make any progress in this iteration.
                    var idle = true
                    // Read the outgoing packet from the input stream.
                    var length = `in`.read(packet.array())
                    if (length > 0) {
                        // Write the outgoing packet to the tunnel.
                        packet.limit(length)
                        tunnel.write(packet)
                        packet.clear()
                        // There might be more outgoing packets.
                        idle = false
                        lastReceiveTime = System.currentTimeMillis()
                    }
                    // Read the incoming packet from the tunnel.
                    length = tunnel.read(packet)
                    if (length > 0) {
                        // Ignore control messages, which start with zero.
                        @Suppress("DEPRECATED_IDENTITY_EQUALS")
                        if (packet.get(0) !== 0.toByte()) {
                            // Write the incoming packet to the output stream.
                            out.write(packet.array(), 0, length)
                        }
                        packet.clear()
                        // There might be more incoming packets.
                        idle = false
                        lastSendTime = System.currentTimeMillis()
                    }
                    // If we are idle or waiting for the network, sleep for a
                    // fraction of time to avoid busy looping.
                    if (idle) {
                        Thread.sleep(IDLE_INTERVAL_MS)
                        val timeNow = System.currentTimeMillis()
                        if (lastSendTime + KEEPALIVE_INTERVAL_MS <= timeNow) {
                            // We are receiving for a long time but not sending.
                            // Send empty control messages.
                            packet.put(0.toByte()).limit(1)
                            for (i in 0..2) {
                                packet.position(0)
                                tunnel.write(packet)
                            }
                            packet.clear()
                            lastSendTime = timeNow
                        } else if (lastReceiveTime + RECEIVE_TIMEOUT_MS <= timeNow) {
                            // We are sending for a long time but not receiving.
                            throw IllegalStateException("Timed out")
                        }
                    }
                }
            })
        } catch (e: SocketException) {
            Log.e(tag, "Cannot use socket", e)
        } finally {
            if (iface != null) {
                try {
                    iface!!.close()
                } catch (e: IOException) {
                    Log.e(tag, "Unable to close interface", e)
                }

            }
        }
        return connected
    }

    /**
     * Creates a handshake between me and VPN server
     * @author Chaosruler972
     * @param tunnel the tunnel of the connection that I should handshake on
     * @return fd of the connection after handshake
     */
    @Throws(IOException::class, InterruptedException::class)
    private fun handshake(tunnel: DatagramChannel): ParcelFileDescriptor {
        // To build a secured tunnel, we should perform mutual authentication
        // and exchange session keys for encryption. To keep things simple in
        // this demo, we just send the shared secret in plaintext and wait
        // for the server to send the parameters.
        // Allocate the buffer for handshaking. We have a hardcoded maximum
        // handshake size of 1024 bytes, which should be enough for demo
        // purposes.
        val packet = ByteBuffer.allocate(1024)
        // Control messages always start with zero.
        packet.put(0.toByte()).put(mSharedSecret).flip()
        // Send the secret several times in case of packet loss.
        for (i in 0..2) {
            packet.position(0)
            tunnel.write(packet)
        }
        packet.clear()
        // Wait for the parameters within a limited time.
        for (i in 0 until MAX_HANDSHAKE_ATTEMPTS) {
            Thread.sleep(IDLE_INTERVAL_MS)
            // Normally we should not receive random packets. Check that the first
            // byte is 0 as expected.
            val length = tunnel.read(packet)
            @Suppress("DEPRECATED_IDENTITY_EQUALS")
            if (length > 0 && packet.get(0) === 0.toByte()) {
                return configure(String(packet.array(), 1, length - 1, US_ASCII).trim { it <= ' ' })
            }
        }
        throw IOException("Timed out")
    }

    /**
     * Configures a VPN connection
     * @author Chaosruler972
     * @param parameters the parameters of the connection
     * @return fd of the connection configuration as saved
     */
    @Throws(IllegalArgumentException::class)
    private fun configure(parameters: String): ParcelFileDescriptor {
        // Configure a builder while parsing the parameters.
        val builder = mService.Builder()
        for (parameter in parameters.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
            val fields = parameter.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            try {
                when (fields[0][0]) {
                    'm' -> builder.setMtu(java.lang.Short.parseShort(fields[1]).toInt())
                    'a' -> builder.addAddress(fields[1], Integer.parseInt(fields[2]))
                    'r' -> builder.addRoute(fields[1], Integer.parseInt(fields[2]))
                    'd' -> builder.addDnsServer(fields[1])
                    's' -> builder.addSearchDomain(fields[1])
                }
            } catch (e: NumberFormatException) {
                throw IllegalArgumentException("Bad parameter: " + parameter)
            }

        }
        // Create a new interface using the builder and save the parameters.
        var vpnInterface: ParcelFileDescriptor
        synchronized(mService) {
            vpnInterface = builder
                    .setSession(mServerName)
                    .setConfigureIntent(mConfigureIntent)
                    .establish()
            if (mOnEstablishListener != null) {
                mOnEstablishListener!!.onEstablish(vpnInterface)
            }
            return vpnInterface
        }
        //Log.i(tag, "New interface: $vpnInterface($parameters)")

    }

    companion object {
        /** Maximum packet size is constrained by the MTU, which is given as a signed short.  */
        private const val MAX_PACKET_SIZE = java.lang.Short.MAX_VALUE.toInt()
        /** Time to wait in between losing the connection and retrying.  */
        private val RECONNECT_WAIT_MS = TimeUnit.SECONDS.toMillis(3)
        /** Time between keepalives if there is no traffic at the moment.
         *
         * TODO: don't do this; it's much better to let the connection die and then reconnect when
         * necessary instead of keeping the network hardware up for hours on end in between.
         */
        private val KEEPALIVE_INTERVAL_MS = TimeUnit.SECONDS.toMillis(15)
        /** Time to wait without receiving any response before assuming the server is gone.  */
        private val RECEIVE_TIMEOUT_MS = TimeUnit.SECONDS.toMillis(20)
        /**
         * Time between polling the VPN interface for new traffic, since it's non-blocking.
         *
         * TODO: really don't do this; a blocking read on another thread is much cleaner.
         */
        private val IDLE_INTERVAL_MS = TimeUnit.MILLISECONDS.toMillis(100)
        /**
         * Number of periods of length {@IDLE_INTERVAL_MS} to wait before declaring the handshake a
         * complete and abject failure.
         *
         * TODO: use a higher-level protocol; hand-rolling is a fun but pointless exercise.
         */
        private const val MAX_HANDSHAKE_ATTEMPTS = 50
    }
}