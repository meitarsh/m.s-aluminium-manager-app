package ms_alum.azrieli_college_of_engineering.com.msamanager;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.internal.zzbmn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.util.List;




public class firebase_helper
{
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    private static FirebaseUser currentUser;
    public static boolean login(String username, String password, final Intent intent,final Context act)
    {
        if(username == null || password == null || password.length() == 0 || username.length() == 0 || intent == null || act == null)
            return false;
        currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
        {
            mAuth.signOut();
            // return true; // we are already logged int
        }
        if(BuildConfig.BUILD_TYPE.equals("debug")==true) // available only in debug mode, mock for log in
        {

            if(username.equals("login_test"))
            {
                if(password.equals("approve"))
                {
                    // below object is there for JUnit and TESTING ONLY, it can be deleted but it will result with test failures
                    currentUser = new FirebaseUser() {
                        @NonNull
                        @Override
                        public String getUid() {
                            return act.getResources().getString(R.string.testing_uid);
                        }

                        @NonNull
                        @Override
                        public String getProviderId() {
                            return null;
                        }

                        @Override
                        public boolean isAnonymous() {
                            return false;
                        }

                        @Nullable
                        @Override
                        public List<String> getProviders() {
                            return null;
                        }

                        @NonNull
                        @Override
                        public List<? extends UserInfo> getProviderData() {
                            return null;
                        }

                        @NonNull
                        @Override
                        public FirebaseUser zzU(@NonNull List<? extends UserInfo> list) {
                            return null;
                        }

                        @Override
                        public FirebaseUser zzaX(boolean b) {
                            return null;
                        }

                        @NonNull
                        @Override
                        public FirebaseApp zzVH() {
                            return null;
                        }

                        @Nullable
                        @Override
                        public String getDisplayName() {
                            return null;
                        }

                        @Nullable
                        @Override
                        public Uri getPhotoUrl() {
                            return null;
                        }

                        @Nullable
                        @Override
                        public String getEmail() {
                            return act.getResources().getString(R.string.testing_email);
                        }

                        @NonNull
                        @Override
                        public zzbmn zzVI() {
                            return null;
                        }

                        @Override
                        public void zza(@NonNull zzbmn zzbmn) {

                        }

                        @NonNull
                        @Override
                        public String zzVJ() {
                            return null;
                        }

                        @NonNull
                        @Override
                        public String zzVK() {
                            return null;
                        }

                        @Override
                        public boolean isEmailVerified() {
                            return false;
                        }
                    };
                    intent.putExtra("login", "true");
                    return true;

                }
                else if(password.equals("deny"))
                {

                    intent.putExtra("login", "error");
                    return false;
                }
                return  true;
            }
        }
        Task<AuthResult> res = mAuth.signInWithEmailAndPassword(username + act.getString(R.string.gmail_suffix) ,password);
        res.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(mAuth.getCurrentUser() == null)
                {
                    intent.putExtra("login", "error");
                }
                else {
                    intent.putExtra("login", task.isSuccessful() + "");
                    currentUser = mAuth.getCurrentUser();
                }
            }
        });
        return true;
    }
    public static String get_username()
    {
        if(currentUser == null)
            return null;
        return currentUser.getEmail().substring(0,currentUser.getEmail().indexOf('@'));
    }
    public static boolean log_out()
    {
        // subroutine debug for JUnit, TESTING ONLY
        if(BuildConfig.BUILD_TYPE.equals("debug") == true)
        {
            if(get_username()!= null && get_username().equals(Resources.getSystem().getString(R.string.testing_username)))
            {
                currentUser = null;
                return true;
            }
        }
        currentUser = mAuth.getCurrentUser();
        if(currentUser == null)
            return false;
        mAuth.signOut();
        return true;
    }

}
