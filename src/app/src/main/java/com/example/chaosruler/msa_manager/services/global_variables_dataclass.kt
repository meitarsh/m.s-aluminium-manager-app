package com.example.chaosruler.msa_manager.services

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import android.preference.PreferenceManager
import android.util.Base64
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.*
import com.example.chaosruler.msa_manager.activies.MainActivity
import java.nio.charset.Charset
import kotlin.experimental.xor
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.util.*


class global_variables_dataclass
{
    companion object
    {
        var isLocal: Boolean = true
        var GUI_MODE: Boolean = false
        var DB_BIG: local_big_table_helper? = null
        var projid: String? = null
        @SuppressLint("StaticFieldLeak")
        var DB_project: local_projects_table_helper? = null
        @SuppressLint("StaticFieldLeak")
        var DB_OPR: local_OPR_table_helper? = null
        @SuppressLint("StaticFieldLeak")
        var DB_VENDOR: local_vendor_table_helper? = null
        @SuppressLint("StaticFieldLeak")
        var DB_INVENTORY: local_inventory_table_helper? = null

        /*
            init databases for all the application
         */
        fun init_dbs(context: Context)
        {
            isLocal = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getString(R.string.local_or_not),true)
            GUI_MODE = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getString(R.string.gui_mode_key),false)

            DB_BIG = local_big_table_helper(context)
            DB_INVENTORY = local_inventory_table_helper(context)
            DB_OPR = local_OPR_table_helper(context)
            DB_VENDOR = local_vendor_table_helper(context)
            DB_project = local_projects_table_helper(context)


        }
        fun report_to_Main_Activity_Thread_syncing_is_done()
        {
            MainActivity.service_sync_done = true
        }
        /*
        xors both byte arrays, encryption
     */

        @SuppressLint("GetInstance")
        fun xorWithKey(a: ByteArray, key: ByteArray, flag:Boolean): ByteArray
        {

            val new_a = to_hebrew_unicode(String(a)).toByteArray()
            val out = ByteArray(new_a.size)
            for (i in new_a.indices) {
                out[i] = (new_a[i] xor key[i % key.size])
            }
            //return to_hebrew_unicode(String(out)).toByteArray()
            /*
            val c = Cipher.getInstance("AES")
            val key_arr = ByteArray(16)
            for(i in 0 until key_arr.size)
            {
                try
                {
                    key_arr[i] = key[i]
                }
                catch (e:Exception)
                {
                    key_arr[i] = 0
                }
            }
            val k = SecretKeySpec(key_arr, "AES")
            if(flag)
                c.init(Cipher.ENCRYPT_MODE, k)
            else
                c.init(Cipher.DECRYPT_MODE, k)

            return c.doFinal(new_a)
            */
            return if(flag)
                Base64.decode(new_a,Base64.DEFAULT)
            else
                Base64.encode(new_a,Base64.DEFAULT)

            //return new_a
        }

        @SuppressLint("WifiManagerPotentialLeak", "HardwareIds")
        fun get_device_id(con: Context):String
        {
            val wifiManager = con.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wInfo = wifiManager.connectionInfo
            return wInfo.macAddress
        }

        private fun to_hebrew_unicode(str:String):String
        {

            //WINDOWS-1255
            // UTF-8
           // Log.d("Char unicoded", toHex(str))
            val charSet = "UTF-8"
           // Log.d("Result after decode:",String(str.toByteArray(charset = Charset.forName(charSet)),Charset.forName(charSet)))
            return String(str.toByteArray(charset = Charset.forName(charSet)),Charset.forName(charSet))
        }

        @Suppress("unused")
        private fun charset(value: String, charsets: Array<String>): String {
            val probe = StandardCharsets.UTF_8.name()
            for (c in charsets) {
                val charset = Charset.forName(c)
                if (charset != null) {
                    if (value == convert(convert(value, charset.name(), probe), probe, charset.name() )) {
                        return c
                    }
                }
            }
            return StandardCharsets.UTF_8.name()
        }

        private fun convert(value: String, fromEncoding: String, toEncoding: String): String= String(value.toByteArray(charset(fromEncoding)), Charset.forName(toEncoding))

        @Suppress("unused")
        fun toHex(arg: String): String {
            return String.format("%040x", BigInteger(1, arg.toByteArray()/*YOUR_CHARSET?*/))
        }
    }
}