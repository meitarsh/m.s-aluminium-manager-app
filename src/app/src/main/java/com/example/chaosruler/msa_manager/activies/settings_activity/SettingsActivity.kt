package com.example.chaosruler.msa_manager.activies.settings_activity


import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.preference.*
import android.view.MenuItem
import android.widget.Toast
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.SQLITE_helpers.cache_server_commands
import com.example.chaosruler.msa_manager.SQLITE_helpers.user_database_helper
import com.example.chaosruler.msa_manager.activies.LoginActivity
import com.example.chaosruler.msa_manager.services.encryption
import com.example.chaosruler.msa_manager.services.themer


/**
 * A [PreferenceActivity] that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 *
 * See [Android Design: Settings](http://developer.android.com/design/patterns/settings.html)
 * for design guidelines and the [Settings API Guide](http://developer.android.com/guide/topics/ui/settings.html)
 * for more information on developing a Settings UI.
 * @author Chaosruler972
 * @constructor default constructor for activity constructor
 */
class SettingsActivity : AppCompatPreferenceActivity()
{

    /**
     * override activity to initate perference activity
     * @author Chaosruler972
     * @param savedInstanceState the last state of the activity
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setupActionBar()
    }


    /**
     *
     * Set up the [android.app.ActionBar], if the API is available.
     * @author Chaosruler972
     */
    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * {@inheritDoc}
     * @author Chaosruler972
     * @return true = tablet, false= phone
     */
    override fun onIsMultiPane(): Boolean = isXLargeTablet(this)

    /**
     * {@inheritDoc}
     * @author Chaosruler972
     * @param target list of headers
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onBuildHeaders(target: List<PreferenceActivity.Header>) =
            loadHeadersFromResource(R.xml.pref_headers, target)

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     * @author Chaosruler972
     * @param fragmentName the name of the fragment we want to load
     * @return upon success true, if no fragment name like that exists, false
     */
    override fun isValidFragment(fragmentName: String): Boolean {
        return PreferenceFragment::class.java.name == fragmentName
                || GeneralPreferenceFragment::class.java.name == fragmentName
                || DataSyncPreferenceFragment::class.java.name == fragmentName
                || NotificationPreferenceFragment::class.java.name == fragmentName
                || DevelopMentSettingsPrefFragment::class.java.name == fragmentName
                || VPNSettingsFragment::class.java.name == fragmentName
    }

    /**
     * menu options, removed because we don't want options on settings
     * @author Chaosruler972
     * @param item the item we clicked
     * @return always true
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home)
        {
            startActivity(Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     * @author Chaosruler972
     * @constructor constructs fragment for general pereference
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    class GeneralPreferenceFragment : PreferenceFragment()
    {
        /**
         * Part of the activity lifecycle to generate summary to values for each preference
         * @author Chaosruler972
         * @param savedInstanceState the last state of the fragment
         */
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_general)
            setHasOptionsMenu(true)

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference(getString(R.string.username_key)), null)
            bindPreferenceSummaryToValue(findPreference(getString(R.string.style)), null)
            findPreference(getString(R.string.style)).setOnPreferenceChangeListener { _, _ ->
                restart_app()
                return@setOnPreferenceChangeListener true
            }
            findPreference(getString(R.string.style)).setOnPreferenceClickListener {
                Toast.makeText(activity.baseContext,getString(R.string.pref_style_summary),Toast.LENGTH_SHORT).show()
                return@setOnPreferenceClickListener true
            }


        }

        /**
         *  restarts entire app after style change, required for visiblity of new style to show
         *  @author Chaosruler972
         */
        private fun restart_app()
        {
            startActivity(Intent(activity.baseContext, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }

        /**
         *  on option selected event
         *  @param item the item we clicked
         *  @author Chaosruler972
         *  @return if item exists, true, else false
         */
        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.itemId
            if (id == android.R.id.home)
            {
                activity.onBackPressed()
                return true
            }
            return super.onOptionsItemSelected(item)
        }


    }

    /**
     * This fragment shows notification preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     * @author Chaosruler972
     * @constructor constructs fragment for notification pereference
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    class NotificationPreferenceFragment : PreferenceFragment() {
        /**
         * Part of the activity lifecycle to generate summary to values for each preference
         * @author Chaosruler972
         * @param savedInstanceState the last state of the fragment
         */
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_notification)
            setHasOptionsMenu(true)

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            //bindPreferenceSummaryToValue(findPreference("notifications_new_message_ringtone"))
            bindPreferenceSummaryToValue(findPreference(getString(R.string.notification)), null)
        }

        /**
         *  on option selected event
         *  @param item the item we clicked
         *  @author Chaosruler972
         *  @return if item exists, true, else false
         */
        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.itemId
            if (id == android.R.id.home)
            {
                //startActivity(Intent(activity, SettingsActivity::class.java))
                activity.finish()
                return true
            }
            return super.onOptionsItemSelected(item)
        }
    }

    /**
     * This fragment shows data and sync preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     * @author Chaosruler972
     * @constructor constructs fragment for data sync pereference
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    class DataSyncPreferenceFragment : PreferenceFragment() {
        /**
         * Part of the activity lifecycle to generate summary to values for each preference
         * @author Chaosruler972
         * @param savedInstanceState the last state of the fragment
         */
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_data_sync)
            setHasOptionsMenu(true)
            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.

            bindPreferenceSummaryToValue(findPreference(getString(R.string.sync_frequency)), null)


        }
        /**
         *  on option selected event
         *  @param item the item we clicked
         *  @author Chaosruler972
         *  @return if item exists, true, else false
         */
        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.itemId
            if (id == android.R.id.home)
            {
                activity.onBackPressed()
                return true
            }
            return super.onOptionsItemSelected(item)
        }
    }



    /**
     * This fragment shows VPN preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     * @author Chaosruler972
     * @constructor constructs fragment for data VPN pereference
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    class VPNSettingsFragment : PreferenceFragment(),Preference.OnPreferenceChangeListener {

        override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean
        {
            if(preference!=null && newValue!=null)
            {
                preference.onPreferenceChangeListener = null
                val key = preference.key
                val manager =  PreferenceManager.getDefaultSharedPreferences(context).edit()
                encryption.generate_key(context)
                manager.putString(key,String(encryption.encrypt(newValue.toString().toByteArray())))
                manager.apply()
                preference.onPreferenceChangeListener = this
                return true
            }
            return false
        }

        /**
         * Part of the activity lifecycle to generate summary to values for each preference
         * @author Chaosruler972
         * @param savedInstanceState the last state of the fragment
         */
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_vpn)
            setHasOptionsMenu(true)
            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.

            bindPreferenceSummaryToValue(findPreference(getString(R.string.vpn_connect_key)), activity.baseContext)
            bindPreferenceSummaryToValue(findPreference(getString(R.string.vpn_ip_key)), activity.baseContext)
            bindPreferenceSummaryToValue(findPreference(getString(R.string.vpn_port)), activity.baseContext)
            bindPreferenceSummaryToValue(findPreference(getString(R.string.vpn_psk)), activity.baseContext)
            bindPreferenceSummaryToValue(findPreference(getString(R.string.vpn_username)), activity.baseContext)
            bindPreferenceSummaryToValue(findPreference(getString(R.string.vpn_password)),activity.baseContext)

            /**
             * Upon decision to use implented VPN, this is a secure way to store VPN data
             */
            /*
            val vpn_ip_pref = findPreference(getString(R.string.vpn_ip_key))
            val vpn_port_pref = findPreference(getString(R.string.vpn_port))
            val vpn_psk_pref = findPreference(getString(R.string.vpn_psk))
            val vpn_username_pref = findPreference(getString(R.string.vpn_username))
            val vpn_password_pref = findPreference(getString(R.string.vpn_password))

            vpn_ip_pref.onPreferenceChangeListener = this
            vpn_port_pref.onPreferenceChangeListener = this
            vpn_psk_pref.onPreferenceChangeListener = this
            vpn_username_pref.onPreferenceChangeListener = this
            vpn_password_pref.onPreferenceChangeListener = this
            */
        }
        /**
         *  on option selected event
         *  @param item the item we clicked
         *  @author Chaosruler972
         *  @return if item exists, true, else false
         */
        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.itemId
            if (id == android.R.id.home)
            {
                activity.onBackPressed()
                return true
            }
            return super.onOptionsItemSelected(item)
        }
    }

    /**
     * This fragment shows development-mode preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     * @author Chaosruler972
     * @constructor constructs fragment for development pereference
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    class DevelopMentSettingsPrefFragment : PreferenceFragment() {
        /**
         * Part of the activity lifecycle to generate summary to values for each preference
         * @author Chaosruler972
         * @param savedInstanceState the last state of the fragment
         */
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_development)
            setHasOptionsMenu(true)

            bindPreferenceSummaryToValue(findPreference(getString(R.string.IP)), null)
            bindPreferenceSummaryToValue(findPreference(context.getString(R.string.windows_auth_key)),activity.baseContext)
          //  bindPreferenceSummaryToValue(findPreference(getString(R.string.delete_users_key)), activity.baseContext)
          //  bindPreferenceSummaryToValue(findPreference(getString(R.string.delete_offline_key)), activity.baseContext)
            bindPreferenceSummaryToValue(findPreference(getString(R.string.gui_mode_key)), activity.baseContext)
        }
        /**
         *  on option selected event
         *  @param item the item we clicked
         *  @author Chaosruler972
         *  @return if item exists, true, else false
         */
        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.itemId
            if (id == android.R.id.home)
            {
                activity.onBackPressed()
                return true
            }
            return super.onOptionsItemSelected(item)
        }
    }

    companion object {

        /**
         * A preference value change listener that updates the preference's summary
         * to reflect its new value.
         * @author Chaosruler972
         */
        private val sBindPreferenceSummaryToValueListener = Preference.OnPreferenceChangeListener { preference, value ->
            val stringValue = value.toString()

            if (preference is ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                val index = preference.findIndexOfValue(stringValue)

                // Set the summary to reflect the new value.
                preference.setSummary(
                        if (index >= 0)
                            preference.entries[index]
                        else
                            null)

            }

            true
        }
        /**
         * Helper method to determine if the device has an extra-large screen. For
         * example, 10" tablets are extra-large.
         * @author Chaosruler972
         * @param context the context we work with
         * @return if the device is tablet or phone
         */
        private fun isXLargeTablet(context: Context): Boolean =
                context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_XLARGE

        /**
         * Binds a preference's summary to its value. More specifically, when the
         * preference's value is changed, its summary (line of text below the
         * preference title) is updated to reflect the value. The summary is also
         * immediately updated upon calling this method. The exact display format is
         * dependent on the type of preference.

         * @see .sBindPreferenceSummaryToValueListener
         * @author Chaosruler972
         * @param context the context we work with (nullable!)
         * @param preference the preference we want to bind summary to
         * @return true if successfull, false if not
         */
        private fun bindPreferenceSummaryToValue(preference: Preference, context: Context?): Boolean {
            // Set the listener to watch for value changes.
            preference.onPreferenceChangeListener = sBindPreferenceSummaryToValueListener

            // Trigger the listener immediately with the preference's
            // current value.
            when {
                preference.key == "notification" -> sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                        PreferenceManager
                                .getDefaultSharedPreferences(preference.context)
                                .getBoolean(preference.key,false))
                preference.key == "delete_users" -> preference.setOnPreferenceClickListener {
                    user_database_helper(context!!).clearDB()
                    Toast.makeText(context,context.getString(R.string.successfull_operation),Toast.LENGTH_SHORT).show()
                    return@setOnPreferenceClickListener true
                }
                preference.key == "delete_offline" -> preference.setOnPreferenceClickListener {
                    if (context != null) {
                        cache_server_commands(context).clearDB()
                        Toast.makeText(context,context.getString(R.string.successfull_operation),Toast.LENGTH_SHORT).show()
                    }
                    return@setOnPreferenceClickListener true
                }
                preference.key == "gui_mode_key" -> sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,PreferenceManager.getDefaultSharedPreferences(preference.context).getBoolean(preference.key,false))
                preference.key == "vpn_connect_key" -> sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,PreferenceManager.getDefaultSharedPreferences(preference.context).getBoolean(preference.key,false))
                preference.key == "windows_auth_key" -> sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,PreferenceManager.getDefaultSharedPreferences(preference.context).getBoolean(preference.key,false))
                else -> sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                        PreferenceManager
                                .getDefaultSharedPreferences(preference.context)
                                .getString(preference.key, ""))
            }
            return true
        }


    }


}
