package com.example.chaosruler.msa_manager.services

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import android.preference.PreferenceManager
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.*
import com.example.chaosruler.msa_manager.activies.MainActivity
import java.math.BigInteger
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

/**
 * a Singleton like objec that holds data true across all the activities such as settings and databases along with functions usabele across activities
 * @author Chaosruler972
 * @constructor an empty constructor since this is a singleton
 */
object global_variables_dataclass
{
    var isLocal: Boolean = true
    var GUI_MODE: Boolean = false
    var DB_BIG: local_big_table_helper? = null
    var projid: String? = null
    var flat: String? = null
    var floor: String? = null
    var floor_moving_to: Int = 0
//    var flat: String? = null
    @SuppressLint("StaticFieldLeak")
    var DB_project: local_projects_table_helper? = null
    @SuppressLint("StaticFieldLeak")
    var DB_OPR: local_OPR_table_helper? = null
    @SuppressLint("StaticFieldLeak")
    var DB_VENDOR: local_vendor_table_helper? = null
    @SuppressLint("StaticFieldLeak")
    var DB_INVENTORY: local_inventory_table_helper? = null

    /**
     * Inits all the database with an instance that can be called from all the objects
     * @author Chaosruler972
     * @param context a baseContext to work with
     */
    fun init_dbs(context: Context) {
        isLocal = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getString(R.string.local_or_not), true)
        GUI_MODE = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getString(R.string.gui_mode_key), false)

        DB_BIG = local_big_table_helper(context)
        DB_INVENTORY = local_inventory_table_helper(context)
        DB_OPR = local_OPR_table_helper(context)
        DB_VENDOR = local_vendor_table_helper(context)
        DB_project = local_projects_table_helper(context)


    }

    /**
     * a bridge function to report syncing is done to main activity
     * @author Chaosruler972
     */
    fun report_to_Main_Activity_Thread_syncing_is_done() {
        MainActivity.service_sync_done = true
    }

    /**
     * Encrypts and decrypts with Base64 encoding for hashing and security
     * also adds the functionality of AES encryption using the encryption class
     * @see encryption
     * @author Chaosruler972
     * @param a a bytearray to encrypt/decrypt
     * @param key a bytearray to use for decryp/encrypt key
     * @param con a base Context to work with
     * @param flag true = decryption, false= encryption
     */
    @SuppressLint("GetInstance")
    fun xorWithKey(a: ByteArray, @Suppress("UNUSED_PARAMETER") key: ByteArray, flag: Boolean, con: Context): ByteArray {
        encryption.generate_key(con)
        return if (flag)
            encryption.decrypt(a)
        else
            encryption.encrypt(a)
        //return new_a
    }

    /**
     * Gets this device WiFi MAC ID for device identification, COULD BE SPOOFED
     * @author Chaosruler972
     * @param con the basecontext to work with
     * @return the device ID
     */
    @SuppressLint("WifiManagerPotentialLeak", "HardwareIds")
    fun get_device_id(con: Context): String {
        val wifiManager = con.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wInfo = wifiManager.connectionInfo
        return wInfo.macAddress
    }

    @Suppress("unused")
            /**
     * Converts a string to hebrew (unicode)
     * @author Chaosruler972
     * @param str the string to convert
     * @return a converted string with UTF8 this time
     */
    fun to_hebrew_unicode(str: String): String {

        //WINDOWS-1255
        // UTF-8
        // Log.d("Char unicoded", toHex(str))
        val charSet = "UTF-8"
        // Log.d("Result after decode:",String(str.toByteArray(charset = Charset.forName(charSet)),Charset.forName(charSet)))
        return String(str.toByteArray(charset = Charset.forName(charSet)), Charset.forName(charSet))
    }

    /**
     * Finds the charset of a string
     * @author Chaosruler972
     * @param value the string to scan
     * @param charsets the list of possible charsets to scan
     * @return the charset that holds right to this string
     */
    @Suppress("unused")
    private fun charset(value: String, charsets: Array<String>): String {
        val probe = StandardCharsets.UTF_8.name()
        for (c in charsets) {
            val charset = Charset.forName(c)
            if (charset != null) {
                if (value == convert(convert(value, charset.name(), probe), probe, charset.name())) {
                    return c
                }
            }
        }
        return StandardCharsets.UTF_8.name()
    }

    /**
     * Converts a string from encoding a to encoding B
     * @author Chaosruler972
     * @param value the string to convert
     * @param fromEncoding the current encoding
     * @param toEncoding the desired encoding
     * @return the resulting string after convertion
     */
    private fun convert(value: String, fromEncoding: String, toEncoding: String): String = String(value.toByteArray(charset(fromEncoding)), Charset.forName(toEncoding))

    /**
     * Converts string to its hexadecimal representation (per byte)
     * @author Chaosruler972
     * @param arg the string to convert
     * @return the converted string to hexadecimal format
     */
    @Suppress("unused")
    fun toHex(arg: String): String {
        return String.format("%040x", BigInteger(1, arg.toByteArray()/*YOUR_CHARSET?*/))
    }

}