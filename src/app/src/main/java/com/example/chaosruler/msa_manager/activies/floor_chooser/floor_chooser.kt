package com.example.chaosruler.msa_manager.activies.floor_chooser

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.activies.KablanMforat.kablan_mforat
import com.example.chaosruler.msa_manager.activies.divohi_takalot_tofes_activity.DivohiTakalotTofesActivity
import com.example.chaosruler.msa_manager.object_types.big_table_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_floor_chooser.*
import java.util.*

class floor_chooser : AppCompatActivity() {

    private lateinit var adapter: ArrayAdapter<big_table_data>

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_floor_chooser)
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
                        global_variables_dataclass.DB_BIG!!.get_local_DB()
                    else
                        global_variables_dataclass.DB_BIG!!.server_data_to_vector()
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
        adapter = floorArrayAdapter(this, android.R.layout.simple_spinner_item, projects.filter { global_variables_dataclass.projid!! == it.get_PROJECT_ID() })
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
                // Toast.makeText(baseContext,(main_spinner.adapter.getItem(position) as project_data).get_project_name(),Toast.LENGTH_SHORT).show()
            }
        }

    }

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
                Toast.makeText(baseContext,getString(R.string.no_floor_chosen_spinner_toast), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!global_variables_dataclass.GUI_MODE)
                intent.putExtra(getString(R.string.key_pass_main_to_options),(floor_spinner.adapter.getItem(floor_spinner.selectedItemPosition) as big_table_data).get_FLAT()?:"")
            startActivity(intent)
        })
    }

}
