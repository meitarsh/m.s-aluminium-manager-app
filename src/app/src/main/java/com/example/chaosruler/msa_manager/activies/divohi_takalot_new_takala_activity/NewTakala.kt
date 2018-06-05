package com.example.chaosruler.msa_manager.activies.divohi_takalot_new_takala_activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_takala_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.object_types.project_data
import com.example.chaosruler.msa_manager.object_types.takala_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.remote_SQL_Helper
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_new_takala.*
import java.util.*
import kotlin.collections.HashMap

/**
 * To add a new takala
 * @author Chaosruler972
 */
class NewTakala : AppCompatActivity() {

    /**
     * The adapter of big table to populate
     * @author Chaosruler972
     */
    private lateinit var adapter: ArrayAdapter<project_data>



    /**
     * Creates the activity on new takala
     * @author Chaosruler972
     * @param savedInstanceState the last state
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_takala)
        init_spinner()
        init_buttons()
    }

    /**
     * inits the projid spinner
     * @author Chaosruler972
     */
    private fun init_spinner()
    {
        Thread {
            val projects =global_variables_dataclass.db_project_vec

            runOnUiThread { on_adapter_set(projects) }
        }.start()
    }

    /**
     * after syncding is done, we are initating the spinner, this function is responsible of that
     * @author Chaosruler972
     * @param projects the synced known projects
     */
    private fun on_adapter_set(projects: Vector<project_data>) {

        adapter = new_takala_projid_arrayadapter(baseContext, android.R.layout.simple_spinner_item, projects)
        new_takala_projid_spinner.adapter = adapter
        new_takala_projid_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                finish()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (view != null) {
                    (view as TextView).text = adapter.getItem(position).get_project_name()
                }
            }
        }

    }


    /**
     * inits button press
     * @author Chaosruler972
     */
    private fun init_buttons()
    {
        new_takala_send_button.setOnClickListener {
            new_takala_send_button.isEnabled = false
            val projid = adapter.getItem(new_takala_projid_spinner.selectedItemPosition).getProjID()?:""
            val itemid = new_takala_itemid.text.toString()
            val teeur = new_takala_teeur.text.toString()
            val mumlatz = new_takala_mumlatz.text.toString()
            val tguva = new_takala_tgova.text.toString()
            val kamot = new_takala_kamot.text.toString()
            val alut = new_takala_alut.text.toString()
            val empty_hashmap = HashMap<String, String>()
            empty_hashmap[remote_takala_table_helper.SUG] = ""
            var RECID = global_variables_dataclass.db_salprojtakala_vec.size.toString()
            val RECID_LEN = baseContext.resources.getInteger(R.integer.remote_recID_len)
            while (RECID.length < RECID_LEN)
            {
                RECID = "0$RECID"
            }
            val RECVERSION = baseContext.getString(R.string.remote_RECVERSION)
            val takala_data = takala_data(projid, itemid, "mz11", kamot, "", "",
                    "",teeur,mumlatz, "",tguva,"",alut,"",RECVERSION,RECID, remote_SQL_Helper.getusername())

            remote_takala_table_helper.push_update(takala_data,empty_hashmap, baseContext)
            global_variables_dataclass.db_salprojtakala_vec.addElement(takala_data)
            global_variables_dataclass.DB_SALPROJTAKALA!!.add_to_table(takala_data)
            Toast.makeText(baseContext, getString(R.string.takala_nosfa_success), Toast.LENGTH_SHORT).show()

            new_takala_itemid.setText("")
            new_takala_teeur.setText("")
            new_takala_mumlatz.setText("")
            new_takala_tgova.setText("")
            new_takala_kamot.setText("")
            new_takala_alut.setText("")
            new_takala_send_button.isEnabled  = true
        }
    }

}
