package com.example.chaosruler.msa_manager.activies.kablan_pashot_activity


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.*
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_kablan_pashot.*
import java.util.*

class kablan_pashot : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kablan_pashot)
        if(!global_variables_dataclass.GUI_MODE && !init_table())
            finish()
    }

    /*
                   inits table
            */
    private fun init_table():Boolean
    {

        Thread({
            var arr: Vector<big_table_data> =
                    if (global_variables_dataclass.GUI_MODE)
                        Vector<big_table_data>()
                    else if (!global_variables_dataclass.GUI_MODE && global_variables_dataclass.isLocal)
                        global_variables_dataclass.DB_BIG!!.get_local_DB_by_projname((global_variables_dataclass.projid?:"").trim())
                    else
                        global_variables_dataclass.DB_BIG!!.server_data_to_vector_by_projname((global_variables_dataclass.projid?:"").trim())

            runOnUiThread({kablan_pashot_listview.adapter = kablan_pashot_arrayadapter(baseContext, arr)
            })

        }).start()

        return true
    }


}
