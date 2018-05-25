package com.example.chaosruler.msa_manager.activies.KablanMforat

import android.app.Activity
import android.os.Bundle
import android.os.Looper
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_big_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.object_types.big_table_data
import com.example.chaosruler.msa_manager.object_types.vendor_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_kablan_mforat.*
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.roundToInt

/**
 * class for the logic behind kablan mforat activity
 * @author Chaosruler972
 * @constructor a default constructor for an activity constructor
 */
class kablan_mforat : Activity() {

    /**
     * the adapter of the spinner inside the activity
     * @author Chaosruler972
     */
    private lateinit var adapter:ArrayAdapter<big_table_data>

    /**
     * Activity lifecycle function, initates the spinner
     * @author Chaosruler972
     * @param savedInstanceState the last state of the activity
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kablan_mforat)
        init_spinner()
    }

    /**
     * inits spinner, will call the appropiate function to get the vector of data and call spinner populate function
     * @author Chaosruler972
     */
    private fun init_spinner()
    {
        Thread{

            val big_table:Vector<big_table_data> =
                    if(global_variables_dataclass.GUI_MODE)
                        Vector()
                    else if (!global_variables_dataclass.GUI_MODE && global_variables_dataclass.isLocal)
                        Vector(global_variables_dataclass.db_big_vec.filter { it.get_PROJECT_ID() == global_variables_dataclass.projid  && it.get_FLOOR() == global_variables_dataclass.floor && it.get_FLAT() == global_variables_dataclass.flat})
                    else
                        Vector(global_variables_dataclass.DB_BIG!!.server_data_to_vector_by_projname((global_variables_dataclass.projid?:"").trim()).filter { it.get_FLAT() == global_variables_dataclass.flat && it.get_FLOOR() == global_variables_dataclass.floor })
            big_table.sort()
            Log.d("Kablan","Looking for ${global_variables_dataclass.flat} in $big_table")
            runOnUiThread { spinner_populate(big_table) }
        }.start()

    }

    /**
     * Populates spinner function, will enter data to the adapter of the spinner
     * @author Chaosruler972
     * @param big_table a vector of the data we want to popular (serversided or client sided by configuration)
     */
    private fun spinner_populate(big_table:Vector<big_table_data>)
    {
        Log.d("Floor is", global_variables_dataclass.flat ?: "No flat")
        adapter = KablanArrayAdapter(this, android.R.layout.simple_spinner_item,
                big_table)

        activity_kablan_mforat_spinner.adapter = adapter

        activity_kablan_mforat_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long)
            {
                // upon Spinner selecting a user, update the other fields
                val big_item:big_table_data = activity_kablan_mforat_spinner.adapter.getItem(i) as big_table_data

                val peola_parcent: String = (big_item.get_PERCENTFORACCOUNT() ?: 0).toString()
                val milestone_parcent: String = (big_item.get_PERCENTFORACCOUNT() ?: 0).toString()
                val vendor_data : vendor_data = try {
                    global_variables_dataclass.db_vendor_vec.filter { it.get_accountnum() == big_item.get_VENDOR_ID() }[0]!!
                }
                catch (e: Exception) {
                    vendor_data("", "", "", "")
                }
                // var txtview:TextView = view as TextView
                //  txtview.text = vendor_item.get_accountname()
                (view as TextView).text = vendor_data.get_accountname() ?: ""
                activity_kablan_mforat_mispar_mozar.text = (big_item.get_INVENTORY_ID() ?: "0").trim()
                activity_kablan_mforat_kamot_hoza.text = (big_item.get_QTY() ?: "0").trim()
                activity_kablan_mforat_yehida_price.text = (big_item.get_SALESPRICE() ?: "0").trim()
                activity_kablan_mforat_peola_percent.text = ((peola_parcent.toDouble()).toInt().toString() + "%").trim()
                activity_kablan_mforat_kamot_helki.hint = (big_item.get_QTYINPARTIALACC() ?: "0").trim()
                activity_kablan_mforat_kamot_helki.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
                activity_kablan_mforat_kamot_kablan.hint = (big_item.get_QTYFORACCOUNT() ?: "0").trim()
                activity_kablan_mforat_kamot_kablan.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
                activity_kablan_mforat_ahoz_meosher.hint = ((milestone_parcent.toDouble()).toInt().toString() + "%").trim()
                activity_kablan_mforat_ahoz_meosher.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
                val price = (big_item.get_SALESPRICE() ?: "0").toDouble()
                val count = (big_item.get_QTYFORACCOUNT() ?: "0").toDouble()
                val parcent = milestone_parcent.toDouble() / 100
                activity_kablan_mforat_tashlom_sah.text = (price*count*parcent).roundToInt().toString().trim()

                activity_kablan_mforat_kamot_helki.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                    if(hasFocus || activity_kablan_mforat_kamot_helki.text.isEmpty() )
                        return@OnFocusChangeListener
                    val str = activity_kablan_mforat_kamot_helki.text.toString()
                    Thread({
                        Looper.prepare()
                        val update_value: HashMap<String, String> = HashMap()
                        update_value[remote_big_table_helper.QTYFORACCOUNT] = str
                        remote_big_table_helper.push_update(big_item, update_value, baseContext)
                        big_item.set_QTYFORACCOUNT(str)
                        global_variables_dataclass.DB_BIG!!.add_big(big_item)
                        themer.hideKeyboard(baseContext,activity_kablan_mforat_kamot_helki)
                        runOnUiThread { compute_saah_hakol() }
                    }).start()
                    activity_kablan_mforat_kamot_helki.hint = str.trim()
                    activity_kablan_mforat_kamot_helki.text.clear()
                }

                activity_kablan_mforat_kamot_kablan.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                    if(hasFocus || activity_kablan_mforat_kamot_kablan.text.isEmpty())
                        return@OnFocusChangeListener
                    val str = activity_kablan_mforat_kamot_kablan.text.toString()
                    Thread({
                        Looper.prepare()
                        val update_value: HashMap<String, String> = HashMap()
                        update_value[remote_big_table_helper.QTYFORACCOUNT] = str
                        remote_big_table_helper.push_update(big_item, update_value, baseContext)
                        big_item.set_QTYFORACCOUNT(str)
                        global_variables_dataclass.DB_BIG!!.add_big(big_item)
                        themer.hideKeyboard(baseContext,activity_kablan_mforat_kamot_kablan)
                        runOnUiThread { compute_saah_hakol() }
                    }).start()
                    activity_kablan_mforat_kamot_kablan.hint = str.trim()
                    activity_kablan_mforat_kamot_kablan.text.clear()
                }

                activity_kablan_mforat_ahoz_meosher.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                    if(hasFocus || activity_kablan_mforat_ahoz_meosher.text.isEmpty())
                        return@OnFocusChangeListener
                    val str = activity_kablan_mforat_ahoz_meosher.text.toString()
                    Thread({
                        Looper.prepare()
                        val update_value: HashMap<String, String> = HashMap()
                        update_value[remote_big_table_helper.PERCENTFORACCOUNT] = str
                        remote_big_table_helper.push_update(big_item, update_value, baseContext)
                        big_item.set_PERCENTFORACCOUNT(str)
                        global_variables_dataclass.DB_BIG!!.add_big(big_item)
                        themer.hideKeyboard(baseContext,activity_kablan_mforat_ahoz_meosher)
                        runOnUiThread { compute_saah_hakol() }
                    }).start()
                    activity_kablan_mforat_ahoz_meosher.hint = (str + "%").trim()
                    activity_kablan_mforat_ahoz_meosher.text.clear()
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>)
            {
                // case there was nothing to select (empty database)
            }
        }
        compute_saah_hakol()
    }

    /**
     *   compute sum and puts into textview and colors it
     * @author Chaosruler972
     */
    private fun compute_saah_hakol()
    {
        if(baseContext == null)
            return
        var price:Double=0.toDouble()
        for(i in 0 until activity_kablan_mforat_spinner.adapter.count)
        {
            val big_item:big_table_data = activity_kablan_mforat_spinner.adapter.getItem(i) as big_table_data
            var current_price = (big_item.get_SALESPRICE() ?: "0").toDouble()
            val count = (big_item.get_QTYFORACCOUNT() ?: "0").toDouble()
            val milestone_parcent: String = (big_item.get_PERCENTFORACCOUNT() ?: 0).toString()
            val parcent = milestone_parcent.toDouble() / 100
            current_price *= count*parcent
            Log.d("Kablan",current_price.toString() + "," + count.toString() + "," + milestone_parcent.toString() + "," + parcent.toString())
            Log.d("Kablan",current_price.toString())
            price+=current_price
        }
        if(price>0)
            activity_kablan_mforat_saah_hakol.setTextColor(getColor(R.color.green))
        else // <= 0
            activity_kablan_mforat_saah_hakol.setTextColor(getColor(R.color.red))
        activity_kablan_mforat_saah_hakol.text = price.toInt().toString().trim()
    }

}
