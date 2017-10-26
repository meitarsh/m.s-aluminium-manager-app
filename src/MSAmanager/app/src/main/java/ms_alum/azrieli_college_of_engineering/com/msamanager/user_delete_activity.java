package ms_alum.azrieli_college_of_engineering.com.msamanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Vector;

import static android.R.layout.simple_spinner_item;

public class user_delete_activity extends AppCompatActivity
{
    // variables
    private Button delete_btn;
    private Button change_pw_btn;
    private Button confirm_chages;
    private TextView change_pw_textview2;
    private EditText pw1;
    private EditText pw2;
    private Spinner spinner;
    private ArrayAdapter<User> adapter;
    private user_database_helper db;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_delete_activity);

        //subroutine to connect graphical objects into their programmical template
        delete_btn = (Button) findViewById(R.id.delete_delete);
        change_pw_btn = (Button) findViewById(R.id.delete_change_password);
        confirm_chages = (Button)findViewById(R.id.delete_send_changes_btn);
        change_pw_textview2 = (TextView)findViewById(R.id.delete_password2_textview);
        pw1 = (EditText)findViewById(R.id.delete_password1_edittext);
        pw2 = (EditText)findViewById(R.id.delete_password2_edittext);
        spinner = (Spinner)findViewById(R.id.delete_spinner);

        // subroutine to grab the entire user database into an iteratable vector and import it into the Spinner and it's adapter
        db = new user_database_helper(getBaseContext());
        final Vector<User> users = db.get_entire_db(getBaseContext());
        adapter = new ArrayAdapter<User >(this, simple_spinner_item, users);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                // upon Spinner selecting a user, update the other fields
                pw1.setText(users.elementAt(i).get__password());
                delete_btn.setEnabled(true);
                change_pw_textview2.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                // case there was nothing to select (empty database)
                pw1.setText("");
                delete_btn.setEnabled(false);
                change_pw_textview2.setEnabled(false);
            }
        });
        // button to activate subroutine to delete a user from database
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                db.delete_user(getBaseContext(),((User)spinner.getSelectedItem()).get__username());
                adapter.remove((User) spinner.getSelectedItem());
            }
        });

        // button to activate the changing password mechanism and subroutine, in actual there's a hidden change password button and password confirm field
        // after pressing this button those fields become visible which allows the user to actually change the password
        change_pw_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                pw1.setEnabled(true);
                pw2.setVisibility(View.VISIBLE);
                change_pw_textview2.setVisibility(View.VISIBLE);
                confirm_chages.setVisibility(View.VISIBLE);
            }
        });
        // part 2 of the subroutine to change the password - the actual job
        confirm_chages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String new_pass = pw1.getText().toString(); // grabs the input password
                if(new_pass.length() == 0) // checks validity
                {
                    Toast.makeText(user_delete_activity.this, getResources().getString(R.string.delete_empty_pw), Toast.LENGTH_SHORT).show(); // invalid password was confirmed.
                    reset_password_fields();
                    return;
                }
                else if(new_pass.equals(pw2.getText().toString()) == false) // password field and password confirm field didn't match, user mistyped the password in this case
                {
                    Toast.makeText(user_delete_activity.this, getResources().getString(R.string.delete_mismatch), Toast.LENGTH_SHORT).show();
                    reset_password_fields();
                    return;
                }
                else
                {
                    Toast.makeText(user_delete_activity.this, getResources().getString(R.string.delete_confirmed), Toast.LENGTH_SHORT).show(); // confirmed match, this is when action is sent and confirmed
                    db.update_user(getBaseContext(),((User)spinner.getSelectedItem()).get__username(),new_pass);
                    reset_password_fields();
                    return;
                }
            }
        });
    }
    private void reset_password_fields()
    {
        // subroutine to reset the password fields to their defaults (meaning like it was when the activity first launched)
        pw1.setEnabled(false);
        pw2.setVisibility(View.INVISIBLE);
        change_pw_textview2.setVisibility(View.INVISIBLE);
        change_pw_textview2.setText("");
        confirm_chages.setVisibility(View.INVISIBLE);
    }
    @Override
    public void onBackPressed() // overridden to make sure that pressing back right now will return us to the login activity, and won't exit the app, also reloading the login activity will reload the spinner on the login activity
    {
        Intent intent = new Intent(user_delete_activity.this,LoginActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

}
