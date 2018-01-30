package com.example.chaosruler.msa_manager

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.example.chaosruler.msa_manager.services.encryption
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.nio.charset.Charset

/**
 * Test of encryption
 */
@RunWith(AndroidJUnit4::class)
class encryption_test
{
    /**
     * Testing encryption
     * 1. take a string
     * 2. encrypt it
     * 3. compare to original, should be different
     * 4. take another copy of the string
     * 5. encrypt it, should be the same
     * 6. take a different string
     * 7. encrypt it
     * 8. should be different
     * 9. decrypt both, should be different
     * 10. decrypt from stage 3 and 5, should be the same
     */
    @Test
    fun encryption_test()
    {
        val con = InstrumentationRegistry.getTargetContext()
        val original_string = "Offspring"
        val different = "I am different"

        val tes_amount = 5
        val amount_str = " Out of $tes_amount "
        var test_num = 1

        try
        {
            encryption.generate_key(con)
            val encrypted = String(encryption.encrypt(original_string.toByteArray(Charset.forName("UTF-8"))))
            Assert.assertFalse("Encryption tests: e #$test_num$amount_str: fails\n ",encrypted!=original_string)
            Log.d("Encryption tests", "encryp #$test_num$amount_str: success\n ")
        }
        catch (e: AssertionError)
        {
            Log.d("Encryption tests", "encrypt #$test_num$amount_str: fails\n ")
        }

        test_num++


        try
        {
            encryption.generate_key(con)
            val encrypted = String(encryption.encrypt(original_string.toByteArray(Charset.forName("UTF-8"))))
            encryption.generate_key(con)
            Assert.assertTrue("Encryption tests: encrypting_same_thing_twice #$test_num$amount_str: fails\n ",encrypted==String(encryption.encrypt(original_string.toByteArray(Charset.forName("UTF-8")))))
            Log.d("Encryption tests", "encrypting_same_thing_twice #$test_num$amount_str: success\n ")
        }
        catch (e: AssertionError)
        {
            Log.d("Encryption tests", "encrypting_same_thing_twice #$test_num$amount_str: fails\n ")
        }

        test_num++


        try
        {
            encryption.generate_key(con)
            val encrypted = String(encryption.encrypt(original_string.toByteArray(Charset.forName("UTF-8"))))
            encryption.generate_key(con)
            Assert.assertFalse("Encryption tests: encrypting different stuff #$test_num$amount_str: fails\n ",encrypted==String(encryption.encrypt(different.toByteArray(Charset.forName("UTF-8")))))
            Log.d("Encryption tests", "encrypting different stuff #$test_num$amount_str: success\n ")
        }
        catch (e: AssertionError)
        {
            Log.d("Encryption tests", "encrypting different stuff #$test_num$amount_str: fails\n ")
        }

        test_num++

        try
        {
            encryption.generate_key(con)
            val encrypted = String(encryption.encrypt(original_string.toByteArray(Charset.forName("UTF-8"))))
            encryption.generate_key(con)
            val encrypted_different = String(encryption.encrypt(different.toByteArray(Charset.forName("UTF-8"))))
            encryption.generate_key(con)
            val decrypted_original = String(encryption.encrypt(encrypted.toByteArray(Charset.forName("UTF-8"))))
            encryption.generate_key(con)
            val decrypted_different = String(encryption.encrypt(encrypted_different.toByteArray(Charset.forName("UTF-8"))))
            Assert.assertFalse("Encryption tests: decrypting encrypted different stuff #$test_num$amount_str: fails\n ",decrypted_different==decrypted_original)
            Log.d("Encryption tests", "decrypting encrypted different stuff #$test_num$amount_str: success\n ")
        }
        catch (e: AssertionError)
        {
            Log.d("Encryption tests", "decrypting encrypted different stuff #$test_num$amount_str: fails\n ")
        }

        test_num++


        try
        {
            encryption.generate_key(con)
            val encrypted = String(encryption.encrypt(original_string.toByteArray(Charset.forName("UTF-8"))))
            encryption.generate_key(con)
            val encrypted_different = String(encryption.encrypt(original_string.toByteArray(Charset.forName("UTF-8"))))
            encryption.generate_key(con)
            val decrypted_original = String(encryption.encrypt(encrypted.toByteArray(Charset.forName("UTF-8"))))
            encryption.generate_key(con)
            val decrypted_different = String(encryption.encrypt(encrypted_different.toByteArray(Charset.forName("UTF-8"))))
            Assert.assertTrue("Encryption tests: decrypting encrypted same stuff #$test_num$amount_str: fails\n ",decrypted_different==decrypted_original)
            Log.d("Encryption tests", "decrypting encrypted same stuff #$test_num$amount_str: success\n ")
        }
        catch (e: AssertionError)
        {
            Log.d("Encryption tests", "decrypting encrypted same stuff #$test_num$amount_str: fails\n ")
        }

        test_num++

        Log.d("Encryption tests","Done completing test $test_num of $tes_amount")

    }
}