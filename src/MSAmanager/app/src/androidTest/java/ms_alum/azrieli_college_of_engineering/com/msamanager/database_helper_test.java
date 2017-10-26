package ms_alum.azrieli_college_of_engineering.com.msamanager;

import android.content.Context;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;


/**
 * Created by Chaosruler on 9/25/2017.
 */
@RunWith(AndroidJUnit4.class)
public class database_helper_test
{
    @Test
    public void user_database_tests()
    {
        Context con = InstrumentationRegistry.getTargetContext();
        user_database_helper db = new user_database_helper(con);
        int test_num = 1;
        final int amount_of_tests = 14;
        String amount_str = " Out of "+ amount_of_tests + " ";

        String username = "dummy";
        String password = "1234";
        String password2 = password + "9";

        db.delete_user(con,username); // non test, delete data

        try
        {
            assertFalse("user database tests: register #" + test_num+amount_str+": fails\n ",db.add_user(con,username,null));
            assertFalse("user database tests: register #" + test_num+amount_str+": fails\n ",db.add_user(con,null,password));
            assertFalse("user database tests: register #" + test_num+amount_str+": fails\n ",db.add_user(con,null,null));
            Log.d("user database tests","register #" + test_num+amount_str+": success\n ");
        }
        catch (Exception e)
        {
            Log.d("user database tests","register #" + test_num+amount_str+": fails\n ");
        }

        test_num++;


        try
        {
            assertFalse("user database tests: find user #" + test_num+amount_str+": fails\n ",db.check_user(con,username));
            Log.d("user database tests","find user #" + test_num+amount_str+": success\n ");
        }
        catch (Exception e)
        {
            Log.d("user database tests","find user #" + test_num+amount_str+": fails\n ");
        }
        test_num++;

        try
        {
            assertTrue("user database tests: register #" + test_num+amount_str+": fails\n ",db.add_user(con,username,password));
            Log.d("user database tests","register #" + test_num+amount_str+": success\n ");
        }
        catch (Exception e)
        {
            Log.d("user database tests","register #" + test_num+amount_str+": fails\n ");
        }

        test_num++;
        try
        {
            assertTrue("user database tests: find user #" + test_num+amount_str+": fails\n ",db.check_user(con,username));
            Log.d("user database tests","find user #" + test_num+amount_str+": success\n ");
        }
        catch (Exception e)
        {
            Log.d("user database tests","find user +#" + test_num+amount_str+": fails\n ");
        }

        test_num++;


        try
        {
            assertNotNull("user database tests: confirm password #" + test_num+amount_str+": fails\n ",db.get_user_by_id(con,username));
            User usr = db.get_user_by_id(con,username);
            assertTrue("user database tests: confirm password #" + test_num+amount_str+": fails\n ",usr.get__password().equals(password));
            Log.d("user database tests","confirm password #" + test_num+amount_str+": success\n ");
        }
        catch (Exception e)
        {
            Log.d("user database tests","confirm password #" + test_num+amount_str+": fails\n ");
        }

        test_num++;


        try
        {
            assertNotNull("user database tests: verify user exists #" + test_num+amount_str+": fails\n ",db.get_user_by_id(con,username));
            User usr = db.get_user_by_id(con,username);
            assertNotNull("user database tests: verify user exists #" + test_num+amount_str+": fails\n ",db.get_entire_db(con));
            assertTrue("user database tests: verify user exists #" + test_num+amount_str+": fails\n ",db.check_user(con,username));
            Log.d("user database tests","verify user exists #" + test_num+amount_str+": success\n ");
        }
        catch (Exception e)
        {
            Log.d("user database tests","verify user exists #" + test_num+amount_str+": fails\n ");
        }

        test_num++;

        try
        {
            assertTrue("user database tests: delete #" + test_num+amount_str+": fails\n ",db.delete_user(con,username));
            Log.d("user database tests","delete #" + test_num+amount_str+": success\n ");
        }
        catch (Exception e)
        {
            Log.d("user database tests","delete #" + test_num+amount_str+": fails\n ");
        }

        test_num++;

        try
        {
            assertFalse("user database tests: delete #" + test_num+amount_str+": fails\n ",db.delete_user(con,username));
            Log.d("user database tests","delete #" + test_num+amount_str+": success\n ");
        }
        catch (Exception e)
        {
            Log.d("user database tests","delete #" + test_num+amount_str+": fails\n ");
        }

        test_num++;

        try
        {
            assertNull("user database tests: verify user doesn't exist #" + test_num+amount_str+": fails\n ",db.get_user_by_id(con,username));
            assertNotNull("user database tests: verify user doesn't exist #" + test_num+amount_str+": fails\n ",db.get_entire_db(con));
            User usr = new User(username,password);
            assertFalse("user database tests: verify user doesn't exist #" + test_num+amount_str+": fails\n ",db.get_entire_db(con).contains(usr));
            assertFalse("user database tests: verify user doesn't exist #" + test_num+amount_str+": fails\n ",db.check_user(con,username));
            Log.d("user database tests","verify user doesn't exist #" + test_num+amount_str+": success\n ");
        }
        catch (Exception e)
        {
            Log.d("user database tests","verify user doesn't exist #" + test_num+amount_str+": fails\n ");
        }
        test_num++;

        try
        {
            assertTrue("user database tests: register #" + test_num+amount_str+": fails\n ",db.add_user(con,username,password));
            Log.d("user database tests","register #" + test_num+amount_str+": success\n ");
        }
        catch (Exception e)
        {
            Log.d("user database tests","register #" + test_num+amount_str+": fails\n ");
        }
        test_num++;
        try
        {
            assertNotNull("user database tests: verify user exists #" + test_num+amount_str+": fails\n ",db.get_user_by_id(con,username));
            User usr = db.get_user_by_id(con,username);
            assertNotNull("user database tests: verify user exists #" + test_num+amount_str+": fails\n ",db.get_entire_db(con));
            assertTrue("user database tests: verify user exists #" + test_num+amount_str+": fails\n ",db.check_user(con,username));
            Log.d("user database tests","verify user exists #" + test_num+amount_str+": success\n ");
        }
        catch (Exception e)
        {
            Log.d("user database tests","verify user exists #" + test_num+amount_str+": fails\n ");
        }
        test_num++;
        try
        {
            assertTrue("user database tests: update password #" + test_num+amount_str+": fails\n ",db.update_user(con,username,password2));
            Log.d("user database tests","update password #" + test_num+amount_str+": success\n ");
        }
        catch (Exception e)
        {
            Log.d("user database tests","update password #" + test_num+amount_str+": fails\n ");
        }
        test_num++;

        try
        {
            assertNotNull("user database tests: verify user exists #" + test_num+amount_str+": fails\n ",db.get_user_by_id(con,username));
            User usr = db.get_user_by_id(con,username);
            assertNotNull("user database tests: verify user exists #" + test_num+amount_str+": fails\n ",db.get_entire_db(con));
            assertTrue("user database tests: verify user exists #" + test_num+amount_str+": fails\n ",db.check_user(con,username));
            assertTrue("user database tests: verify user exists #" + test_num+amount_str+": fails\n ",usr.get__password().equals(password2));
            Log.d("user database tests","verify user exists #" + test_num+amount_str+": success\n ");
        }
        catch (Exception e)
        {
            Log.d("user database tests","verify user exists #" + test_num+amount_str+": fails\n ");
        }
        test_num++;

        try
        {
            assertTrue("user database tests: delete #" + test_num+amount_str+": fails\n ",db.delete_user(con,username));
            Log.d("user database tests","delete #" + test_num+amount_str+": success\n ");
        }
        catch (Exception e)
        {
            Log.d("user database tests","delete #" + test_num+amount_str+": fails\n ");
        }

    }

}
