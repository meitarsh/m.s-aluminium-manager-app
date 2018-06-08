package com.example.chaosruler.msa_manager.activies.floor_chooser

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.activies.KablanMforat.kablan_mforat
import com.example.chaosruler.msa_manager.activies.divohi_takalot_tofes_activity.DivohiTakalotTofesActivity
import com.example.chaosruler.msa_manager.object_types.big_table.big_table_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_floor_chooser.*
import java.util.*

/**
 * Floor chooser for projects
 * @author Chaosruler972
 */
class floor_chooser : AppCompatActivity() {

    /**
     * Populating array adapter of big table data
     * @author Chaosruler972
     */
    private lateinit var adapter: ArrayAdapter<big_table_data>

    /**
     * initates the activity
     * @author Chaosruler972
     * @param savedInstanceState last state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_floor_chooser)
        if (!global_variables_dataclass.GUI_MODE)
            global_variables_dataclass.flat = intent.getStringExtra(getString(R.string.key_pass_main_to_options))
        else
            global_variables_dataclass.flat = ""
        init_spinner()
        init_buttons()
    }

    /**
     * inits spinner, works in multi threading to not block the UI thread
     * @author Chaosruler972
     */
    private fun init_spinner()
    {
        Thread {
            val projects =
                    if (global_variables_dataclass.isLocal)
                        global_variables_dataclass.db_big_vec
                    else
                        global_variables_dataclass.db_big_vec
            runOnUiThread { on_adapter_set(projects) }
        }.start()

    }

    /**
     * after syncding is done, we are initating the spinner, this function is responsible of that
     * @author Chaosruler972
     * @param projects the synced known projects
     */
    private fun on_adapter_set(projects: Vector<big_table_data>)
    {
        projects.sort()
        adapter = floorArrayAdapter(this, android.R.layout.simple_spinner_item, projects.filter { global_variables_dataclass.projid!! == it.get_PROJECT_ID() && global_variables_dataclass.flat == it.get_FLAT() })
        floor_spinner.adapter = adapter

        floor_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onNothingSelected(parent: AdapterView<*>?)
            {
                finish()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                if(view != null)
                {
                    (view as TextView).text = adapter.getItem(position).get_FLOOR()
                }
            }
        }

    }

    /**
     * Inits the button for the activity
     * @author Chaosruler972
     */
    private fun init_buttons()
    {
        floor_button_choose.setOnClickListener({

            val intent =
                    if (global_variables_dataclass.floor_moving_to == 0)
                        Intent(this@floor_chooser, kablan_mforat::class.java)
                    else // 1
                        Intent(this@floor_chooser, DivohiTakalotTofesActivity::class.java)
            if(!global_variables_dataclass.GUI_MODE && floor_spinner.selectedItemPosition == Spinner.INVALID_POSITION)
            {
                Toast.makeText(baseContext, getString(R.string.floor_not_chosen_toast), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (global_variables_dataclass.GUI_MODE)
                global_variables_dataclass.floor = "dummy_floor"
            if(!global_variables_dataclass.GUI_MODE)
                global_variables_dataclass.floor = (floor_spinner.adapter.getItem(floor_spinner.selectedItemPosition) as big_table_data).get_FLOOR() ?: ""
            startActivity(intent)
            finish()
        })
    }

}


