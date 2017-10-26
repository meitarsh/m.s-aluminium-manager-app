package ms_alum.azrieli_college_of_engineering.com.msamanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class kablni_mishne_activity extends AppCompatActivity {

    private Button btn_simple;
    private Button btn_last_transactions;
    private Button btn_more_info;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kablni_mishne_activity);
        //subroutine to connect GUI to program
        btn_simple = (Button)findViewById(R.id.kablni_mishne_pshot);
        btn_last_transactions = (Button)findViewById(R.id.kablni_mishne_ntonim_hodshiim);
        btn_more_info = (Button)findViewById(R.id.kablni_mishne_pirot);

    }
}
