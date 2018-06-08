package com.example.chaosruler.msa_manager.services

import android.annotation.SuppressLint
import android.content.Context
import android.security.keystore.KeyProperties
import android.util.Base64
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.services.encryption.encrypt
import com.example.chaosruler.msa_manager.services.encryption.generate_key
import com.yakivmospan.scytale.Crypto
import com.yakivmospan.scytale.Options
import com.yakivmospan.scytale.Store
import java.io.IOException
import java.nio.charset.Charset
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


/**
 * a Singleton object (only one instance in runtime) responsible for encryptiing and decrypting
 * data stored in local database and local android machine
 * AES 256bit was chosen for encryption protocol
 * @author Chaosruler972
 * @constructor no construction needed, though key Generation is a must before each and every encryption or decryption
 * @sample generate_key(baseContext)
 * @sample encrypt(a.toByteArray())
 * @see <a href="https://en.wikipedia.org/wiki/Advanced_Encryption_Standard">AES Encryption protocol</a>
 */
@Suppress("unused")
object encryption
{
    /**
     * a boolean value that tells me if encryption is enabled or not
     * @author Chaosruler972
     */
    private var encryption_enabled: Boolean = true

    /**
     * The secret key representation of AES on Android API
     * @author Chaosruler972
     * @see javax.crypto.SecretKey
     */
    private var secretKey:SecretKey? = null
    /**
     * this function is responsible for generatoin an AES key from the keystore per encryption/decryption, and refreshing the current one
     * @author Chaosruler972
     * @param context a base Context, must not be null, for keyStore access
     */
    fun generate_key(context: Context)
    {
        if (secretKey == null) {
            val alias = context.getString(R.string.ENC_KEY)
            val store = Store(context)
            encryption_enabled = context.resources.getBoolean(R.bool.encryption_enabled)

            secretKey = if(store.hasKey(alias)) {
                val key_to_get_key = store.getSymmetricKey(alias,null)

                get_key_from_file(key_to_get_key,true,context)
            }
            else
            {
                val key_to_get_key = store.generateSymmetricKey(alias,null)

                get_key_from_file(key_to_get_key,false,context)
            }

        }
    }

    /**
     * Function to generate a key from file and decrypt it to be a secret key
     * @author Chaosruler972
     * @param flag true -> we should generate key and save to file, false we should just load from file
     * @param key_to_encrypt the key to encrypt our key (recursive, eh?)
     * @return an AES key to use!
     */
    private fun get_key_from_file(key_to_encrypt: SecretKey,flag:Boolean,context: Context) : SecretKey?
    {
        val bits = context.resources.getInteger(R.integer.encryption_bits)
        if(flag) // load from file only
        {
            val encoded = ReadFromFile("key.key",context) ?: return null
            val new_a = Base64.decode(encoded,Base64.DEFAULT)
            val crypto = Crypto(Options.TRANSFORMATION_SYMMETRIC)
            val decrypted_string_from_file = crypto.decrypt(String(new_a), key_to_encrypt)
            val decrypted_from_file = decrypted_string_from_file.hexStringToByteArray()
            global_variables_dataclass.log("Key length Read:", decrypted_from_file.size.toString())
            return SecretKeySpec(decrypted_from_file,0,decrypted_from_file.size,"AES")
        }
        else // generate key and save to file
        {
            val crypto = Crypto(Options.TRANSFORMATION_SYMMETRIC)
            val keyGen = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES)
            keyGen.init(bits)
            val new_key = keyGen.generateKey()
            val encoded = new_key.encoded
            global_variables_dataclass.log("Key length Saved:", encoded.size.toString())
            val encoded_and_encrypted_to_file =  Base64.encode(crypto.encrypt(encoded.toHex(), key_to_encrypt).toByteArray(Charset.forName("UTF-8")),Base64.DEFAULT)
            writeToFile(encoded_and_encrypted_to_file,"key.key",context)
            return new_key
        }
    }

    /**
     * Function done specificilly to this class, to convert Hex string to ByteArray
     * @author Chaosruler
     * @return a Bytearray that represents this String's hexstring
     */
    private fun String.hexStringToByteArray() = ByteArray(this.length / 2) { this.substring(it * 2, it * 2 + 2).toInt(16).toByte() }

    /**
     * Function done specificilly to this class to convert ByteArray to HexString
     * @author Chaosruler972
     * @return a Hexstring from this ByteArray
     */
    private fun ByteArray.toHex() = this.joinToString(separator = "") { it.toInt().and(0xff).toString(16).padStart(2, '0') }

    /**
     * Function to read data from file
     * @author Chaosruler972
     * @param fileName the filename that we should read data from
     * @return the data that we read
     */
    private fun ReadFromFile(fileName: String,context: Context) : ByteArray?
    {
        var byteArray:ByteArray? = null
        try
        {
            context.openFileInput(fileName).use {
                byteArray = it.readBytes()
            }

        }
        catch (e:IOException)
        {
            byteArray = null
            global_variables_dataclass.log("Encryption", "Saving to file IO Exception")
        }
        return byteArray
    }

    /**
     * Function to write data to file
     * @author Chaosruler972
     * @param data the data to write
     * @param fileName the filename that we should write to/open
     */
    private fun writeToFile(data: ByteArray, fileName: String,context: Context)
    {
        try
        {
            context.openFileOutput(fileName,Context.MODE_PRIVATE).use {
                it.write(data)
            }
        }
        catch (e:IOException)
        {
            global_variables_dataclass.log("Encryption", "Saving to file IO Exception")
        }
    }
    /**
     * This function encrypts a byteArray and returns an encrypted form of that byteArray
     * Must call generate_key() before this function
     * @author Chaosruler972
     * @param a byteArray to encrypt
     * @return the encrypted form of that bytearray
     */
    @SuppressLint("GetInstance")
    fun encrypt(a: ByteArray): ByteArray
    {
        if (secretKey == null || a.isEmpty() || !encryption_enabled)
            return a
        val c = Cipher.getInstance("AES")
        c.init(Cipher.ENCRYPT_MODE, secretKey!!,IvParameterSpec(ByteArray(16)))
        return Base64.encode(c.doFinal(a),Base64.DEFAULT)

    }

    /**
     * This function decrypts a byteArray
     * Must call generate_key() before this function
     * @author Chaosruler972
     * @param a encrypted byteArray
     * @return a decrypted form of that bytearray
     */
    @SuppressLint("GetInstance")
    fun decrypt(a:ByteArray): ByteArray
    {

        if (secretKey == null || a.isEmpty() || !encryption_enabled)
            return a
        val new_a = Base64.decode(a,Base64.DEFAULT)
        val c = Cipher.getInstance("AES")
        c.init(Cipher.DECRYPT_MODE, secretKey!!,IvParameterSpec(ByteArray(16)))
        return c.doFinal(new_a)
    }

}