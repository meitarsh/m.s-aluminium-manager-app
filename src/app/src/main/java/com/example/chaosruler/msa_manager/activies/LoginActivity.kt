package com.example.chaosruler.msa_manager.activies

import android.Manifest.permission.READ_CONTACTS
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.LoaderManager.LoaderCallbacks
import android.content.CursorLoader
import android.content.Intent
import android.content.Loader
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.ContactsContract
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.SQLITE_helpers.user_database_helper
import com.example.chaosruler.msa_manager.activies.settings_activity.SettingsActivity
import com.example.chaosruler.msa_manager.object_types.User
import com.example.chaosruler.msa_manager.services.VPN_google_toyVPN.vpn_connection
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

/**
 * A login screen that offers login via email/password.
 * @author Chaosruler972
 * @constructor a default constructor for activity is a must
 */
class LoginActivity : AppCompatActivity(), LoaderCallbacks<Cursor> {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private var mAuthTask: UserLoginTask? = null
    /**
     * The current status of the menu (autofill or not)
     * @author Chaosruler972
     */
    private var status: Boolean = false
    /**
     * The current users database to fill the spinner on auto fill mode
     * @author Chaosruler972
     */
    private lateinit var db : user_database_helper
    /**
     * the adapter that serves for the spinner
     * @author Chaosruler972
     */
    private var adapter: ArrayAdapter<User>? = null

    /**
     * Activity lifecycle function, responsible for initating the spinner, the login form and
     * generating the database as well as inflating the menu
     * @author Chaosruler972
     * @param savedInstanceState the last state of the activity
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        global_variables_dataclass.init_dbs(baseContext)
        status = user_database_helper(baseContext).get_entire_db().size!=0
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //status = false // means, that we are going to login by using the spinner details
        //  I am going to support it by disabling the password input in that case
        db = user_database_helper(baseContext)
        // Set up the login form.


        val users = db.get_entire_db()
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, users)
        login_spinner.adapter = adapter
        login_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, i: Int, l: Long) {

                if(i<0)
                    return
                login_email.setText(adapter!!.getItem(i)!!.get__username())
                login_password.setText(adapter!!.getItem(i)!!.get__password())

            }

            override fun onNothingSelected(adapterView: AdapterView<*>) = Unit
        }


        populateAutoComplete()
        login_password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        login_sign_in_button.setOnClickListener { attemptLogin() }
    }

    /**
     * reguler autocomplete of the edit text population function
     * @author Chaosruler972
     */
    private fun populateAutoComplete() {
        if (!mayRequestContacts()) {
            return
        }

        loaderManager.initLoader(0, null, this)
    }

    /**
     * for edit text auto complete we must have contacts permission, subroutine requests that
     * @author Chaosruler972
     * @return if contacts request was successfull or not
     */
    @SuppressLint("ObsoleteSdkInt")
    private fun mayRequestContacts(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(login_email, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok,
                            { requestPermissions(arrayOf(READ_CONTACTS), REQUEST_READ_CONTACTS) })
        } else {
            requestPermissions(arrayOf(READ_CONTACTS), REQUEST_READ_CONTACTS)
        }
        return false
    }

    /**
     * Callback received when a permissions request has been completed.
     * @author Chaosruler972
     * @param grantResults the results of the permission grant (as an array)
     * @param permissions the permissions requested
     * @param requestCode the request code that we gave to invoke this function
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete()
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     * @author Chaosruler972
     */
    private fun attemptLogin() {
        if (mAuthTask != null) {
            return
        }

        // Reset errors.
        login_email.error = null
        login_password.error = null

        // Store values at the time of the login attempt.
        val emailStr = login_email.text.toString()
        val passwordStr = login_password.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr) && !PreferenceManager.getDefaultSharedPreferences(baseContext).getBoolean(getString(R.string.gui_mode_key),false)) {
            login_password.error = getString(R.string.error_invalid_password)
            focusView = login_password
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailStr) && !PreferenceManager.getDefaultSharedPreferences(baseContext).getBoolean(getString(R.string.gui_mode_key),false))
        {
            login_email.error = getString(R.string.error_field_required)
            focusView = login_email
            cancel = true
        } else if (!isEmailValid(emailStr) && !PreferenceManager.getDefaultSharedPreferences(baseContext).getBoolean(getString(R.string.gui_mode_key),false))
        {
            login_email.error = getString(R.string.error_invalid_email)
            focusView = login_email
            cancel = true
        }

        if (cancel)
        {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            showProgress(true)
            themer.hideKeyboard(baseContext,login_email)
            themer.hideKeyboard(baseContext,login_password)
            mAuthTask = UserLoginTask(emailStr, passwordStr)
            mAuthTask!!.execute(null as Void?)
        }
    }

    /**
     * Checks if email is valid, always returns true
     * @author Chaosruler972
     * @return always true
     */
    private fun isEmailValid(@Suppress("UNUSED_PARAMETER")email: String): Boolean =// return email.contains("@")
            true

    /**
     * checks if password is valid, always returns true
     * @author Chaosruler972
     * @return always true
     */
    private fun isPasswordValid(@Suppress("UNUSED_PARAMETER")password: String): Boolean = true

    /**
     * get current status
     * helps to know if spinner autofill mode is on or edit text fill mode is on
     * @author Chaosruler972
     * @return the status in boolean format, true is autofill, false is manual fill
     */
    private fun get_status():Boolean
            = this.status

    /**
     * on resume we want to refresh the theme, extra procatuion, activity lifecycle function
     * @author Chaosruler972
     */
    override fun onResume() {
        super.onResume()
        baseContext.setTheme(themer.style(baseContext))
    }

    /**
     * responsible for inflating the menu with options
     * @author Chaosruler972
     * @param menu the menu we want to inflate
     * @return always true
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.login_menu, menu)
        onOptionsItemSelected(menu.findItem(R.id.login_switch))
        return true
    }

    /**
     * handles menu items click
     * @author Chaosruler972
     * @param item the item we clicked
     * @return true if item exists, false if not
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.login_switch -> {
                status = !get_status()
                if (!status)
                // false case -> spinner, email invisible, spinner visible, textview (login) visible password disabled
                {
                    login_textview.visibility = View.VISIBLE
                    login_spinner.visibility = View.VISIBLE
                    login_email.visibility = View.INVISIBLE
                    login_textinputlayout_login.hint = ""
                    login_password.isEnabled = false
                    login_spinner.onItemSelectedListener.onItemSelected(login_spinner,login_spinner.selectedView,login_spinner.selectedItemPosition,login_spinner.selectedItemId)
                    item.setTitle(R.string.entry1)
                } else
                // true case -> spinner, textview login invisible, password enabled, email visible
                {
                    login_textview.visibility = View.INVISIBLE
                    login_spinner.visibility = View.INVISIBLE
                    login_email.visibility = View.VISIBLE
                    login_textinputlayout_login.hint = getString(R.string.prompt_email)
                    login_password.isEnabled = true
                    item.setTitle(R.string.entry2)
                    login_email.text.clear()
                    login_password.text.clear()
                }
            }
            R.id.manage_users_db -> {
                val intent = Intent(this@LoginActivity, user_delete_activity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.settings ->
            {
                val intent = Intent(this@LoginActivity, SettingsActivity::class.java)
                startActivity(intent)

            }
            else -> {
                return false
            }
        }
        return true
    }





    /**
     * Shows the progress UI and hides the login form.
     * @author Chaosruler972
     * @param show if we are supposed to show, true is going to show progress bar, false is going to make it disaappear
     */
    @SuppressLint("ObsoleteSdkInt")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private fun showProgress(show: Boolean) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

            login_form.visibility = if (show) View.GONE else View.VISIBLE
            login_form.animate()
                    .setDuration(shortAnimTime)
                    .alpha((if (show) 0 else 1).toFloat())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            login_form.visibility = if (show) View.GONE else View.VISIBLE
                        }
                    })

            login_progress.visibility = if (show) View.VISIBLE else View.GONE
            login_progress.animate()
                    .setDuration(shortAnimTime)
                    .alpha((if (show) 1 else 0).toFloat())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            login_progress.visibility = if (show) View.VISIBLE else View.GONE
                        }
                    })
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            login_progress.visibility = if (show) View.VISIBLE else View.GONE
            login_form.visibility = if (show) View.GONE else View.VISIBLE
        }
    }

    /**
     * Loads profile to cursor for login status, for manual autocomplete
     * @param bundle the last bundle used with all the data in it (activity state)
     * @param i the index on the database (cursor)
     * @author Chaosruler972
     */
    override fun onCreateLoader(i: Int, bundle: Bundle?): Loader<Cursor> {
        return CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE + " = ?", arrayOf(ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE),

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC")
    }

    /**
     * When we are done loading cursor we want to popular manual auto complete
     * @author Chaosruler972
     * @param cursor the cursor that we worked with to load the database of autocomplete data
     * @param cursorLoader the cursor loader for manual autocomplete
     */
    override fun onLoadFinished(cursorLoader: Loader<Cursor>, cursor: Cursor) {
        val emails = ArrayList<String>()
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS))
            cursor.moveToNext()
        }

        addEmailsToAutoComplete(emails)
    }

    /**
     * since we use manual autofill API we must override this, empty implentation
     * @param cursorLoader the cursor loader if cursor was reset
     * @author Chaosruler972
     */
    override fun onLoaderReset(cursorLoader: Loader<Cursor>) = Unit


    /**
     * autocomplete e-mail address from contacts
     * @author Chaosruler972
     * @param emailAddressCollection the collection of email addresses possible for it
     */
    private fun addEmailsToAutoComplete(emailAddressCollection: List<String>) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        val adapter = ArrayAdapter(this@LoginActivity,
                android.R.layout.simple_dropdown_item_1line, emailAddressCollection)

        login_email.setAdapter(adapter)
    }

    /**
     * the objectified data that we load from the cursor
     * @constructor the data itself
     * @author Chaosruler972
     */
    object ProfileQuery {
        val PROJECTION = arrayOf(
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY)
        const val ADDRESS = 0
        //val IS_PRIMARY = 1
    }


    /**
     * Marks that VPN is already conifured
     * @author Chaosruler972
     */
    fun mark_vpn_ready(intent: Intent)
    {
        startActivityForResult(intent,resources.getInteger(R.integer.VPN_request_code))
    }


    /**
     * Creates a service intent of the VPN to work with
     * @author Chaosruler972
     */
    private fun getServiceIntent(): Intent {
        return Intent(this, vpn_connection::class.java)
    }
    /**
     * case we want to load VPN srvice, this is a result activity that will load that activity
     * into android settings, we might not need that if we remove VPN requirements
     * @author Chaosruler972
     * @param data the data we want (VPN data such as IP)
     * @param requestCode the request code, for VPN its 1500 as default
     * @param resultCode the result (if VPN service opening succeeded)
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode)
        {
            resources.getInteger(R.integer.VPN_request_code)->
            {
                if(resultCode == Activity.RESULT_OK)
                    startService(getServiceIntent().setAction("com.example.chaosruler.msa_manager.START"))
//                    startService(Intent(this@LoginActivity, VPN::class.java))
            }
            else->
            {

            }
        }
    }
    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     * @param mEmail the email address for our login task
     * @param mPassword the password for our login task
     * @author Chaosruler972
     * @constructor opens the thread and initats the login data and runs the thread
     */
    @SuppressLint("StaticFieldLeak")
    inner class UserLoginTask internal constructor(
            /**
             * the email address for the login task (username)
             * @author Chaosruler972
             */
            private val mEmail: String,
            /**
             * The password for the login task
             * @author Chaosruler972
             */
            private val mPassword: String
        ) : AsyncTask<Void, Void, Boolean>() {

        /**
         * Login task itself, communicates with server and gets login status from the parameters
         * @author Chaosruler972
         * @param params empty, requirements because of inheritance
         */
        override fun doInBackground(vararg params: Void): Boolean?
        {

//            if(VPN.VPN_Enabled(baseContext))
//            {
//                VPN.prepare_vpn(this@LoginActivity)
//            }
            remote_SQL_Helper.Connect(baseContext, mEmail, mPassword,this@LoginActivity)
            val gui_mode_key: Boolean = PreferenceManager.getDefaultSharedPreferences(baseContext).getBoolean(getString(R.string.gui_mode_key), false)
            val result =
            if( gui_mode_key  )
                true
            else
                remote_SQL_Helper.isValid()
            if(!gui_mode_key && result && get_status())
            {
                db.add_user( mEmail, mPassword)
            }

            //result = true
            return result

        }

        /**
         * After login is complete, opens main activity on success, shows failure on failure and notifies user
         * @param success if login was success
         * @author Chaosruler972
         */
        override fun onPostExecute(success: Boolean?) {
            mAuthTask = null
            showProgress(false)
            @Suppress("SENSELESS_COMPARISON")
            if(success == null)
            {
                if(remote_SQL_Helper.getSQLException().errorCode == 0)
                    login_password.error = getString(R.string.network_error)
                else
                    login_password.error = getString(R.string.error_incorrect_password)
                login_password.requestFocus()
            }
            else if (success!=null && success)
            {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
            else
            {
                if(remote_SQL_Helper.getSQLException().errorCode == 0)
                    login_password.error = getString(R.string.network_error)
                else
                    login_password.error = getString(R.string.error_incorrect_password)
                login_password.requestFocus()
            }
        }

        /**
         * if login was cancelled
         * @author Chaosruler972
         */
        override fun onCancelled() {
            mAuthTask = null
            showProgress(false)
        }
    }

    companion object {

        /**
         * Id to identity READ_CONTACTS permission request.
         * @author Chaosruler972
         */
        private const val REQUEST_READ_CONTACTS = 0

    }
}
