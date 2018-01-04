package com.example.chaosruler.msa_manager.services

import android.annotation.SuppressLint
import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import com.example.chaosruler.msa_manager.R
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey


@Suppress("unused")
object encryption
{
    private var secretKey:SecretKey? = null
    private var iv = ByteArray(16)
    fun generate_key(context: Context)
    {
        iv = ByteArray(16)
        if(secretKey==null)
        {
            val store = KeyStore.getInstance(KeyStore.getDefaultType())
            store.load(null)
            val alias = context.getString(R.string.ENC_KEY)
            if (store.containsAlias(alias))
            {
                secretKey = (store.getEntry(alias, null) as KeyStore.SecretKeyEntry).secretKey
            }
            else
            {
                val keyGen = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES)
                keyGen.init(256)
                secretKey = keyGen.generateKey()
                if(secretKey==null)
                    Log.d("Key", "Is null")
                val skEntry = KeyStore.SecretKeyEntry(secretKey)
                store.setEntry(alias, skEntry,KeyStore.PasswordProtection(alias.toCharArray()))

            }
        }
    }

    @SuppressLint("GetInstance")
    fun encrypt(a: ByteArray): ByteArray
    {

        val c = Cipher.getInstance("AES")
        c.init(Cipher.ENCRYPT_MODE, secretKey!!)
        return c.doFinal(a)
    }

    @SuppressLint("GetInstance")
    fun decrypt(a:ByteArray): ByteArray
    {
        val c = Cipher.getInstance("AES")
        c.init(Cipher.DECRYPT_MODE, secretKey!!)
        return c.doFinal(a)
    }

}