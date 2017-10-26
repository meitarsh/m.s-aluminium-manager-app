package ms_alum.azrieli_college_of_engineering.com.msamanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class divohi_takalot_activity extends AppCompatActivity {

    private Button btn_edit;
    private Button btn_form;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divohi_takalot);

        //subroutine to connect GUI elements to program
        btn_edit = (Button)findViewById(R.id.divohi_takalot_edit);
        btn_form = (Button)findViewById(R.id.divohi_takalot_form);



    }
}
