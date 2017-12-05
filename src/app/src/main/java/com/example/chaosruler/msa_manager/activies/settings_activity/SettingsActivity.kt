package com.example.chaosruler.msa_manager.activies.settings_activity

import android.annotation.TargetApi


import android.content.Context
import android.content.Intent

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.preference.ListPreference
import android.preference.Preference
import android.preference.PreferenceActivity
import android.preference.PreferenceFragment
import android.preference.PreferenceManager

import android.view.MenuItem
import android.widget.Toast
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.SQLITE_helpers.cache_server_commands
import com.example.chaosruler.msa_manager.SQLITE_helpers.user_database_helper
import com.example.chaosruler.msa_manager.activies.LoginActivity
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
 */
class SettingsActivity : AppCompatPreferenceActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setupActionBar()
    }


    /**
     *
     * Set up the [android.app.ActionBar], if the API is available.
     */
    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * {@inheritDoc}
     */
    override fun onIsMultiPane(): Boolean = isXLargeTablet(this)

    /**
     * {@inheritDoc}
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onBuildHeaders(target: List<PreferenceActivity.Header>) =
            loadHeadersFromResource(R.xml.pref_headers, target)

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    override fun isValidFragment(fragmentName: String): Boolean {
        return PreferenceFragment::class.java.name == fragmentName
                || GeneralPreferenceFragment::class.java.name == fragmentName
                || DataSyncPreferenceFragment::class.java.name == fragmentName
                || NotificationPreferenceFragment::class.java.name == fragmentName
                || DevelopMentSettingsPrefFragment::class.java.name == fragmentName
    }

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
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    class GeneralPreferenceFragment : PreferenceFragment() {
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
        fun restart_app()
        {
            startActivity(Intent(activity.baseContext, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }
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
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    class NotificationPreferenceFragment : PreferenceFragment() {
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
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    class DataSyncPreferenceFragment : PreferenceFragment() {
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    class DevelopMentSettingsPrefFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_development)
            setHasOptionsMenu(true)

            bindPreferenceSummaryToValue(findPreference(getString(R.string.IP)), null)
            bindPreferenceSummaryToValue(findPreference(getString(R.string.delete_users_key)), activity.baseContext)
            bindPreferenceSummaryToValue(findPreference(getString(R.string.delete_offline_key)), activity.baseContext)
            bindPreferenceSummaryToValue(findPreference(getString(R.string.gui_mode_key)), activity.baseContext)
        }

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
         */
        private fun bindPreferenceSummaryToValue(preference: Preference, context: Context?): Boolean {
            // Set the listener to watch for value changes.
            preference.onPreferenceChangeListener = sBindPreferenceSummaryToValueListener

            // Trigger the listener immediately with the preference's
            // current value.
            if(preference.key == "notification")
            {
                sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                        PreferenceManager
                                .getDefaultSharedPreferences(preference.context)
                                .getBoolean(preference.key,false))
            }
            else if(preference.key == "delete_users")
            {
                preference.setOnPreferenceClickListener {
                    user_database_helper(context!!).clearDB()
                    Toast.makeText(context,context.getString(R.string.successfull_operation),Toast.LENGTH_SHORT).show()
                    return@setOnPreferenceClickListener true
                }
            }
            else if(preference.key == "delete_offline")
            {
                preference.setOnPreferenceClickListener {
                    if (context != null)
                    {
                        cache_server_commands(context).clearDB()
                        Toast.makeText(context,context.getString(R.string.successfull_operation),Toast.LENGTH_SHORT).show()
                    }
                    return@setOnPreferenceClickListener true
                }
            }
            else if(preference.key == "gui_mode_key")
            {
                sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,PreferenceManager.getDefaultSharedPreferences(preference.context).getBoolean(preference.key,false))
            }
            else
            {
                sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                        PreferenceManager
                                .getDefaultSharedPreferences(preference.context)
                                .getString(preference.key, ""))
            }
            return true
        }


    }


}
