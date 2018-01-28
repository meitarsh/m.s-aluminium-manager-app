package com.example.chaosruler.msa_manager.services

import android.annotation.SuppressLint
import android.content.Context
import android.util.Base64
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.services.encryption.encrypt
import com.example.chaosruler.msa_manager.services.encryption.generate_key
import com.yakivmospan.scytale.Crypto
import com.yakivmospan.scytale.Options
import com.yakivmospan.scytale.Store
import javax.crypto.SecretKey


/**
 * a Singleton object (only one instance in runtime) responsible for encryptiing and decrypting
 * data stored in local database and local android machine
 * https://en.wikipedia.org/wiki/Advanced_Encryption_Standard
 * AES 256bit was chosen for encryption protocol
 * @author Chaosruler972
 * @constructor no construction needed, though key Generation is a must before each and every encryption or decryption
 * @sample generate_key(baseContext)
 * @sample encrypt(a.toByteArray())
 */
@Suppress("unused")
object encryption
{
    /**
     * The secret key representation of AES on Android API
     * @author Chaosruler972
     * @see javax.crypto.SecretKey
     */
    private var secretKey:SecretKey? = null
    /**
     * this function is responsible for generatoin an AES key from the keystore per encryption/decryption, and refreshing the current one
     * @param context a base Context, must not be null, for keyStore access
     */
    fun generate_key(context: Context)
    {
        val alias = context.getString(R.string.ENC_KEY)
        val store = Store(context)
        if(secretKey==null)
        {
            secretKey = if(store.hasKey(alias)) {
                store.getSymmetricKey(alias,null)
            } else {
                store.generateSymmetricKey(alias,null)
            }
        }
    }


    /**
     * This function encrypts a byteArray and returns an encrypted form of that byteArray
     * Must call generate_key() before this function
     * @param a byteArray to encrypt
     * @return the encrypted form of that bytearray
     */
    @SuppressLint("GetInstance")
    fun encrypt(a: ByteArray): ByteArray
    {
        val crypto = Crypto(Options.TRANSFORMATION_SYMMETRIC)
        return Base64.encode(crypto.encrypt(String(a), secretKey!!).toByteArray(),Base64.DEFAULT)
    }

    /**
     * This function decrypts a byteArray
     * Must call generate_key() before this function
     * @param a encrypted byteArray
     * @return a decrypted form of that bytearray
     */
    @SuppressLint("GetInstance")
    fun decrypt(a:ByteArray): ByteArray
    {
        val new_a = Base64.decode(a,Base64.DEFAULT)
        val crypto = Crypto(Options.TRANSFORMATION_SYMMETRIC)
        return crypto.decrypt(String(new_a), secretKey!!).toByteArray()
    }

}