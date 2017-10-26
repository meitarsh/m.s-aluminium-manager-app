package ms_alum.azrieli_college_of_engineering.com.msamanager;

/**
 * Created by Chaosruler on 9/25/2017.
 */


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;
import android.util.Log;


import com.google.firebase.FirebaseApp;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;




import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class firebase_test_unit
{
    @Test
   public void test()
   {
       Context context = InstrumentationRegistry.getTargetContext();
       Intent intent = new Intent();
       FirebaseApp.initializeApp(context);
       int test_num = 1;
       final int amount_of_tests = 8;
       String amount_str = " Out of "+ amount_of_tests + " ";
       try
       {
           firebase_helper.log_out();
           assertNull("firebase tests logout test #" + test_num+amount_str+": fails\n ",firebase_helper.get_username());
           Log.d("firebase tests","logout test #" + test_num+amount_str+": success\n ");
       }
       catch(Exception e)
       {
           Log.d("firebase tests","logout test #" + test_num+amount_str+": fails\n ");
       }

       test_num++;

       try
       {
           assertTrue("firebase tests login test #" + test_num+amount_str+": fails\n ",firebase_helper.login("login_test","approve",intent,context));
           while(intent.getStringExtra("login") == null);
           if(intent.getStringExtra("login").equals("true"))
            Log.d("firebase tests","login test #" + test_num+amount_str+": success\n ");
           else if (intent.getStringExtra("login").equals("error") == true)
            Log.d("firebase tests","login test #" + test_num+amount_str+": fails\n ");
           else
               Log.d("firebase tests: login test #" + test_num + amount_str + ": fails\n",intent.getStringExtra("login"));
       }
       catch (Exception e)
       {
           Log.d("firebase tests","login test #" + test_num+amount_str+": fails\n ");
       }
       intent.removeExtra("login"); // cleaning data

       test_num++;
       try
       {
           assertNotNull("firebase tests user data test #" + test_num+amount_str+": fails\n ",firebase_helper.get_username());
           assertTrue("firebase tests user data test #" + test_num+amount_str+": fails\n ",firebase_helper.get_username().equals(Resources.getSystem().getString(R.string.testing_username)));
           Log.d("firebase tests","user data test #" + test_num+amount_str+": success\n ");
       }
       catch (Exception e)
       {
           Log.d("firebase tests","user data test #" + test_num+amount_str+": success\n ");
       }
       test_num++;

       try
       {
           assertTrue("firebase tests logout test #" + test_num+amount_str+": fails\n ",firebase_helper.log_out());
           assertNull("firebase tests logout test #" + test_num+amount_str+": fails\n ",firebase_helper.get_username());
           Log.d("firebase tests","logout test #" + test_num+amount_str+": success\n ");
       }
       catch(Exception e)
       {
           Log.d("firebase tests","logout test #" + test_num+amount_str+": success\n ");
       }
       test_num++;

       try
       {
           assertFalse("firebase tests login test #" + test_num+amount_str+": fails\n ",firebase_helper.login("login_test","deny",intent,context));
           while(intent.getStringExtra("login") == null);
           if(intent.getStringExtra("login").equals("error"))
               Log.d("firebase tests","login test #" + test_num+amount_str+": success\n ");
           else
               Log.d("firebase tests","login test #" + test_num+amount_str+": fails\n ");
       }
       catch (Exception e)
       {
           Log.d("firebase tests","login test #" + test_num+amount_str+": fails\n ");
       }
       intent.removeExtra("login"); // cleaning data

       test_num++;
       try
       {
           assertFalse("firebase tests logout test #" + test_num+amount_str+": fails\n ",firebase_helper.log_out());
           Log.d("firebase tests","logout test #" + test_num+amount_str+": success\n ");
       }
       catch(Exception e)
       {
           Log.d("firebase tests","logout test #" + test_num+amount_str+": fails\n ");
       }

       test_num++;

       try
       {
           assertNull("firebase tests user data test #" + test_num+amount_str+": fails\n ",firebase_helper.get_username());
           Log.d("firebase tests","user data test #" + test_num+amount_str+": success\n ");
       }
       catch (Exception e)
       {
           Log.d("firebase tests","user data test #" + test_num+amount_str+": fails\n ");
       }

       test_num++;
       try
       {
           assertFalse("firebase tests login test #" + test_num+amount_str+": fails\n ",firebase_helper.login(null,"approve",intent,context));
           assertFalse("firebase tests login test #" + test_num+amount_str+": fails\n ",firebase_helper.login("login_test",null,intent,context));
           assertFalse("firebase tests login test #" + test_num+amount_str+": fails\n ",firebase_helper.login("login_test","approve",null,context));
           assertFalse("firebase tests login test #" + test_num+amount_str+": fails\n ",firebase_helper.login("login_test","approve",intent,null));
           Log.d("firebase tests","login test #" + test_num+amount_str+": success\n ");
       }
       catch (Exception e)
       {
           Log.d("firebase tests","login test #" + test_num+amount_str+": fails\n ");
       }
       intent.removeExtra("login"); // cleaning data

   }
}
