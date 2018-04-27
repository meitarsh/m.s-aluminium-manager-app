package com.example.chaosruler.msa_manager.activies.flat_chooser

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.activies.floor_chooser.floor_chooser
import com.example.chaosruler.msa_manager.object_types.big_table_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_flat_chooser.*
import java.util.*

class flat_chooser : AppCompatActivity() {

    private lateinit var adapter: ArrayAdapter<big_table_data>

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flat_chooser)
        init_spinner()
        init_buttons()
    }

    /**
     * inits spinner, works in multi threading to not block the UI thread
     * @author Chaosruler972
     */
    private fun init_spinner() {
        Thread {
            Log.d("DB_BIG", global_variables_dataclass.DB_BIG.toString())
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
    private fun on_adapter_set(projects: Vector<big_table_data>) {
        projects.sort()
        adapter = flatArrayAdapter(this, android.R.layout.simple_spinner_item, projects.filter { global_variables_dataclass.projid!! == it.get_PROJECT_ID() })
        flat_spinner.adapter = adapter

        flat_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                finish()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (view != null) {
                    (view as TextView).text = adapter.getItem(position).get_FLAT()
                }
                // Toast.makeText(baseContext,(main_spinner.adapter.getItem(position) as project_data).get_project_name(),Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun init_buttons() {
        flat_button_choose.setOnClickListener({
            val intent = Intent(this@flat_chooser, floor_chooser::class.java)
            if (!global_variables_dataclass.GUI_MODE && flat_spinner.selectedItemPosition == Spinner.INVALID_POSITION) {
                Toast.makeText(baseContext, getString(R.string.no_flat_chosen_spinner_toast), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (global_variables_dataclass.GUI_MODE)
                global_variables_dataclass.flat = "dummy_flat"
            if (!global_variables_dataclass.GUI_MODE)
                intent.putExtra(getString(R.string.key_pass_main_to_options), (flat_spinner.adapter.getItem(flat_spinner.selectedItemPosition) as big_table_data).get_FLAT()
                        ?: "")
            startActivity(intent)
            finish()
        })
    }

}
