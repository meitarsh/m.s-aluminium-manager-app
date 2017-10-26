package ms_alum.azrieli_college_of_engineering.com.msamanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // variables declerations
    private Spinner spinner;
    private TextView textView;
    private Button choosebtn;
    ArrayAdapter<Project> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //connectins graphical objects to their programical counterparts
        spinner = (Spinner)findViewById(R.id.main_spinner);
        textView = (TextView)findViewById(R.id.main_textview);
        choosebtn = (Button)findViewById(R.id.main_button_choose);

        // subroutine to update the text view with user name
        String name = "Template Name";
        String old_text_view_str = textView.getText().toString();
        old_text_view_str.replace("שלום", "שלום " + name);

        choosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, project_options.class);
                startActivity(intent);
            }
        });
    }

    public void onBackPressed() // override to onbackpressed, makes sure we return to the login activity
    {
        //Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        //startActivity(intent);
        super.onBackPressed();
    }
}
