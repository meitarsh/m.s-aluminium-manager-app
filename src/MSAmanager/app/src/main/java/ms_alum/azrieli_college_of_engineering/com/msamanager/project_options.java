package ms_alum.azrieli_college_of_engineering.com.msamanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class project_options extends AppCompatActivity {

    private Button btn_kablni_mishne;
    private Button btn_divohi_takalot;
    private Button btn_loz;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_options);
        //subroutine to connect graphical objects to their programical representatoins

        btn_divohi_takalot = (Button)findViewById(R.id.project_options_btn_divohi_takalot);
        btn_kablni_mishne = (Button)findViewById(R.id.project_options_btn_kablni_mishne);
        btn_loz = (Button)findViewById(R.id.project_options_btn_loz);


        btn_kablni_mishne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(project_options.this,kablni_mishne_activity.class);
                startActivity(intent);
            }
        });


        btn_divohi_takalot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(project_options.this,divohi_takalot_activity.class);
                startActivity(intent);
            }
        });

        btn_loz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(project_options.this,loz_activity.class);
                startActivity(intent);
            }
        });
    }
}
