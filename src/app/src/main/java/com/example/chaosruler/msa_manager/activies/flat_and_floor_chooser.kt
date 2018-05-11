package com.example.chaosruler.msa_manager.activies

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.activies.KablanMforat.kablan_mforat
import com.example.chaosruler.msa_manager.activies.divohi_takalot_tofes_activity.DivohiTakalotTofesActivity
import com.example.chaosruler.msa_manager.activies.flat_chooser.flatArrayAdapter
import com.example.chaosruler.msa_manager.activies.floor_chooser.floorArrayAdapter
import com.example.chaosruler.msa_manager.object_types.big_table_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_flat_and_floor_chooser.*
import java.util.*
import kotlin.collections.HashMap

class flat_and_floor_chooser : AppCompatActivity() {

    /**
     * Stored flat upon choosing
     * @author Chaosruler972
     */
    private var flat: Int = 0

    /**
     * Stored floor upon choosing
     * @author Chaosruler972
     */
    private var floor: Int = 0

    /**
     * the flat adapter that containing all the flat choices
     * @author Chaosruler972
     */
    private lateinit var flat_adapter: ArrayAdapter<String>

    /**
     * The floor adapter containing all the floor options
     * @author Chaosruler972
     */
    private lateinit var floor_adapter: ArrayAdapter<String>

    /**
     * The Projects vector that we compute first, for filtering purposes
     * @author Chaosruler972
     */
    private lateinit var vec_proj: List<big_table_data>

    /**
     * Creates the activity and gives funcionality for the buttons
     * @author Chaosruler972
     * @param savedInstanceState the last state
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flat_and_floor_chooser)
        init_button()
        floot_flat_choose_btn.visibility = Button.INVISIBLE
        init_flat_spinner()

    }


    /**
     * Creates funcionality to the buttons
     * @author Chaosruler972
     */
    private fun init_button()
    {
        global_variables_dataclass.flat = flat.toString()
        global_variables_dataclass.floor = floor.toString()

        floot_flat_choose_btn.setOnClickListener {
            val intent =
                    if (global_variables_dataclass.floor_moving_to == 0)
                        Intent(this@flat_and_floor_chooser, kablan_mforat::class.java)
                    else // 1
                        Intent(this@flat_and_floor_chooser, DivohiTakalotTofesActivity::class.java)
            if(!global_variables_dataclass.GUI_MODE && floor_flat__flat_spinner.selectedItemPosition == Spinner.INVALID_POSITION)
            {
                Toast.makeText(baseContext, getString(R.string.floor_or_flat_not_chosen_toast), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if (global_variables_dataclass.GUI_MODE) {
                global_variables_dataclass.floor = "0"
                global_variables_dataclass.flat = "0"
            }
            startActivity(intent)
            finish()
        }
    }

    /**
     * Creates a spinner for the floor values in a threadded way
     * @author Chaosruler972
     */
    private fun init_floor_spinner()
    {
        Thread {
//            val projects =
//                    if (global_variables_dataclass.isLocal)
//                        global_variables_dataclass.DB_BIG!!.get_local_DB()
//                    else
//                        global_variables_dataclass.DB_BIG!!.server_data_to_vector()
            runOnUiThread { on_adapter_set_floor() }
        }.start()
    }


    /**
     * Creates a spinner for the flat values in a threadded way
     * @author Chaosruler972
     */
    private fun init_flat_spinner()
    {
        Thread {
            Log.d("flat_floor", "Searching for projid ${global_variables_dataclass.projid}")
            val projects =
                    if (global_variables_dataclass.isLocal)
                        global_variables_dataclass.db_big_vec.filter { it.get_PROJECT_ID() == global_variables_dataclass.projid }
                    else
                        global_variables_dataclass.DB_BIG!!.server_data_to_vector().filter { it.get_PROJECT_ID() == global_variables_dataclass.projid }
            Log.d("found_projects", "${projects.size.toString()} from ${global_variables_dataclass.db_big_vec.size}")
            vec_proj = projects
            Log.d("flat_floor","Got ${vec_proj.size} elements for project ${global_variables_dataclass.projid}")
            runOnUiThread { on_adapter_set_flat() }
        }.start()
    }

    /**
     * after syncding is done, we are initating the spinner, this function is responsible of that
     * @author Chaosruler972
     */
    private fun on_adapter_set_flat() {
        vec_proj = vec_proj.sorted()
        flat_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, distinct_flat_floor(vec_proj, true))
        floor_flat__flat_spinner.adapter = flat_adapter

        floor_flat_floor_spinner.visibility = Spinner.INVISIBLE
        floor_flat_floor_spinner_label.visibility = Spinner.INVISIBLE
        floot_flat_choose_btn.visibility = Button.INVISIBLE

        floor_flat__flat_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?)
            {
                floor_flat_floor_spinner.visibility = Spinner.INVISIBLE
                floor_flat_floor_spinner_label.visibility = Spinner.INVISIBLE
                floot_flat_choose_btn.visibility = Button.INVISIBLE
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                if (view != null)
                {
                    (view as TextView).text = flat_adapter.getItem(position)
                }
                flat = (flat_adapter.getItem(position)).toInt()
                global_variables_dataclass.flat = flat.toString()
                Log.d("flat_floor","Flat was chosen to be $flat")
                floor_flat_floor_spinner.visibility = Spinner.VISIBLE
                floor_flat_floor_spinner_label.visibility = Spinner.VISIBLE
                init_floor_spinner()
            }
        }


    }


    /**
     * after syncding is done, we are initating the spinner, this function is responsible of that
     * @author Chaosruler972
     */
    private fun on_adapter_set_floor()
    {
        floor_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, distinct_flat_floor(vec_proj.filter { it.get_FLAT() == flat.toString() },false) )
        floor_flat_floor_spinner.adapter = floor_adapter

        floor_flat_floor_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onNothingSelected(parent: AdapterView<*>?)
            {
                floot_flat_choose_btn.visibility = Button.INVISIBLE
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                if (view != null)
                {
                    (view as TextView).text = floor_adapter.getItem(position)
                }
                floor = (floor_adapter.getItem(position)?:"0").toInt()
                floot_flat_choose_btn.visibility = Button.VISIBLE
                floot_flat_choose_btn.isEnabled = true
            }
        }

    }

    /**
     * Filters a list of big table data by making data appearing only once
     * @author Chaosruler972
     * @param list the list to filter
     * @param flat_flag true means we will filter all the flats that it will appear only once, false means it will filter all the floors that they will appear only once
     * @return a filtered vector as required
     */
    private fun distinct_flat_floor(list: List<big_table_data>, flat_flag: Boolean) : Vector<String>
    {
        val rv : Vector<String> = Vector()
        for(data: big_table_data in list)
        {
            val key = if(flat_flag)
                data.get_FLAT()
            else
                data.get_FLOOR()
            if(key != null) {
                if (!rv.contains(key))
                    rv.addElement(key)
            }
        }
        return rv
    }
}
