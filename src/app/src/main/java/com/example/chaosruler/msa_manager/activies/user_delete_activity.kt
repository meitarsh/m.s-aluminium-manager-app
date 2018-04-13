package com.example.chaosruler.msa_manager.activies

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.SQLITE_helpers.user_database_helper
import com.example.chaosruler.msa_manager.activies.settings_activity.SettingsActivity
import com.example.chaosruler.msa_manager.object_types.User
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_user_delete_activity.*

/**
 * Class responsible for the logic behind the user-delete activity, the activity that handles deleting and changing password for various users
 * @author Chaosruler
 * @constructor as an activity, the constructor is the default constructor
 */
class user_delete_activity : Activity() {

    /**
     * the current working user database
     * @author Chaosruler972
     */
    private lateinit var db: user_database_helper
    /**
     * an arrayadapter containing all the current users in unencrypted form
     * @author Chaosruler972
     */
    private lateinit var adapter: ArrayAdapter<User>

    /**
     * android activity lifecycle function
     * responsible for initating the database, and the adapter for the spinner and its values
     * @author Chaosruler972
     * @param savedInstanceState current state of activity
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        init_dbs()
        setContentView(R.layout.activity_user_delete_activity)
        init_spinner()
        init_buttons()
    }

    /**
     * inits dataases
     * @author Chaosruler972
     */
    private fun init_dbs()
    {
        db = user_database_helper(baseContext)
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, db.get_entire_db())
    }

    /**
     *  inits spinner
     *  @author Chaosruler972
     */
    private fun init_spinner()
    {
        val users = db.get_entire_db()
        delete_spinner.adapter = adapter
        delete_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                // upon Spinner selecting a user, update the other fields
                delete_password1_edittext.setText(users.elementAt(i).get__password())
                delete_delete.isEnabled = true
                delete_password2_textview.isEnabled = true
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                // case there was nothing to select (empty database)
                delete_password1_edittext.text.clear()
                delete_delete.isEnabled = false
                delete_password2_textview.isEnabled = false
            }
        }
    }

    /**
     *     inits buttons
     *  @author Chaosruler972
     */
    private fun init_buttons()
    {
        // button to activate subroutine to delete a user from database
        delete_delete.setOnClickListener({
            if(db.delete_user( (delete_spinner.selectedItem as User).get__username()))
                adapter.remove(delete_spinner.selectedItem as User)
        })

        // button to activate the changing password mechanism and subroutine, in actual there's a hidden change password button and password confirm field
        // after pressing this button those fields become visible which allows the user to actually change the password
        delete_change_password.setOnClickListener({
            delete_password1_edittext.isEnabled = true
            delete_password2_edittext.visibility = View.VISIBLE
            delete_password2_textview.visibility = View.VISIBLE
            delete_send_changes_btn.visibility = View.VISIBLE
        })
        // part 2 of the subroutine to change the password - the actual job
        delete_send_changes_btn.setOnClickListener(View.OnClickListener {
            val new_pass = delete_password1_edittext.text.toString() // grabs the input password
            when {
                new_pass.isEmpty()
                    // checks validity
                -> {
                    Toast.makeText(this@user_delete_activity, resources.getString(R.string.delete_empty_pw), Toast.LENGTH_SHORT).show() // invalid password was confirmed.
                    reset_password_fields()
                    return@OnClickListener
                }
                new_pass != delete_password2_edittext.text.toString()
                    // password field and password confirm field didn't match, user mistyped the password in this case
                -> {
                    Toast.makeText(this@user_delete_activity, resources.getString(R.string.delete_mismatch), Toast.LENGTH_SHORT).show()
                    reset_password_fields()
                    return@OnClickListener
                }
                else -> {
                    Toast.makeText(this@user_delete_activity, resources.getString(R.string.delete_confirmed), Toast.LENGTH_SHORT).show() // confirmed match, this is when action is sent and confirmed
                    db.update_user((delete_spinner.selectedItem as User).get__username(), new_pass, (delete_spinner.selectedItem as User).get_last_sync_time().time)
                    reset_password_fields()
                    return@OnClickListener
                }
            }
        })
    }

    /**
     * inits reset both password fields, makes fields invisible
     * @author Chaosruler972
     */
    private fun reset_password_fields() {
        // subroutine to reset the password fields to their defaults (meaning like it was when the activity first launched)
        delete_password1_edittext.isEnabled = false
        delete_password2_edittext.visibility = View.INVISIBLE
        delete_password2_textview.visibility = View.INVISIBLE
        delete_password2_textview.text.javaClass
        delete_send_changes_btn.visibility = View.INVISIBLE
    }

    /**
     *  inits go back to new login activity, android lifecycle function
     *  will reopen mainactivity
     *  @author Chaosruler972
     */
    override fun onBackPressed() // overridden to make sure that pressing back right now will return us to the login activity, and won't exit the app, also reloading the login activity will reload the spinner on the login activity
    {
        val intent = Intent(this@user_delete_activity, LoginActivity::class.java)
        startActivity(intent)
        super.onBackPressed()
    }

    /**
     *  inits menu, inflates the top bar menu
     *  @author Chaosruler972
     *  @param menu the menu we want to inflate
     *  @return always true
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater = menuInflater
        inflater.inflate(R.menu.user_delete_activity_menu, menu)
        return true
    }

    /**
     * event handler for menu item
     * @author Chaosruler972
     * @param item the item we pressed
     * @return if the item exists on menu, rue, else, it returns false
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.return_to_login ->
            {
                onBackPressed()
            }
            R.id.settings ->
                startActivity(Intent(this@user_delete_activity, SettingsActivity::class.java))
            else ->
            {
                return false
            }
        }
        return true
    }
}
