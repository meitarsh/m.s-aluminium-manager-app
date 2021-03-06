package com.example.chaosruler.msa_manager.activies.KablanMforat

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Looper
import android.text.InputType
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_big_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.R.id.activity_kablan_mforat_ahoz_meosher
import com.example.chaosruler.msa_manager.activies.KablanMforat.KablanArrayAdapter.KablanArrayAdapter_Inventory_Id
import com.example.chaosruler.msa_manager.activies.KablanMforat.KablanArrayAdapter.KablanArrayAdapter_Opr
import com.example.chaosruler.msa_manager.object_types.big_table.big_table_data
import com.example.chaosruler.msa_manager.object_types.vendor_data.vendor_data
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
    private lateinit var adapter1:ArrayAdapter<vendor_data>

    private lateinit var adapter2:ArrayAdapter<big_table_data>

    private lateinit var adapter3:ArrayAdapter<big_table_data>

    private var chosen_vendor_id: String = ""
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
                        Vector(global_variables_dataclass.db_big_vec.filter { it.get_PROJECT_ID() == global_variables_dataclass.projid && it.get_FLAT() == global_variables_dataclass.flat })
                    else
                        Vector(global_variables_dataclass.DB_BIG!!.server_data_to_vector<big_table_data>().filter { it.get_FLAT() == global_variables_dataclass.flat && it.get_FLOOR() == global_variables_dataclass.floor })
            big_table.sort()
            global_variables_dataclass.log("Kablan", "Chose ${big_table.size} out of ${global_variables_dataclass.db_big_vec.size}")
            global_variables_dataclass.log("Kablan", "Looking for ${global_variables_dataclass.floor} in $big_table")
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
        global_variables_dataclass.log("Floor is", global_variables_dataclass.flat ?: "No flat")
        adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item,
                filter_vendor_list(big_table))


        activity_kablan_mforat_spinner.adapter = adapter1

        activity_kablan_mforat_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onNothingSelected(parent: AdapterView<*>?)
            {
                adapter2 = KablanArrayAdapter_Inventory_Id(baseContext, android.R.layout.simple_spinner_item, Vector())
                activity_kablan_mforat_mispar_mozar.adapter = adapter2

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                chosen_vendor_id = adapter1.getItem(position).get_accountnum()?:""
                adapter2 = KablanArrayAdapter_Inventory_Id(baseContext, android.R.layout.simple_spinner_item, filter_out_big_by_db(big_table))
                activity_kablan_mforat_mispar_mozar.adapter = adapter2

            }

        }
        activity_kablan_mforat_mispar_mozar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // case there was nothing to select (empty database)
            }

            @SuppressLint("SetTextI18n")
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                val big_item: big_table_data = activity_kablan_mforat_mispar_mozar.adapter.getItem(position) as big_table_data
                (view as TextView).text = big_item.get_INVENTORY_ID()?:""
                val oprs = get_all_inventory_unique(big_item.get_INVENTORY_ID()?:"")
                adapter3 = KablanArrayAdapter_Opr(baseContext, android.R.layout.simple_spinner_item, oprs)
                activity_kablan_mforat_oprname.adapter = adapter3
            }

        }

        activity_kablan_mforat_oprname.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onNothingSelected(parent: AdapterView<*>?)
            {

            }

            @SuppressLint("SetTextI18n")
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {

                // upon Spinner selecting a user, update the other fields
                val big_item: big_table_data = activity_kablan_mforat_oprname.adapter.getItem(position) as big_table_data
                (view as TextView).text = big_item.get_OPRID()?:""
                val percentforaccount: Double = try{(big_item.get_PERCENTFORACCOUNT() ?: "0").toString().toDouble()} catch (e: NumberFormatException) {0.0}
                activity_kablan_mforat_kamot_hoza.text = (big_item.get_QTY() ?: "0")
                activity_kablan_mforat_yehida_price.text = (big_item.get_SALESPRICE() ?: "0").trim()
                @SuppressLint("SetTextI18n")
                activity_kablan_mforat_peola_percent.text = (big_item.get_MILESTONEPERCENT()?:"0").toInt().toString() + "%"
//                activity_kablan_mforat_kamot_helki.hint = (big_item.get_QTYINPARTIALACC() ?: "0").trim()
//                activity_kablan_mforat_kamot_helki.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
                activity_kablan_mforat_kamot_kablan.hint = (big_item.get_QTYFORACCOUNT() ?: "0").trim()
                activity_kablan_mforat_kamot_kablan.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
                activity_kablan_mforat_ahoz_meosher.hint = (percentforaccount.toInt().toString() + "%").trim() // ((big_item.get_MILESTONEPERCENT()?:"0").toInt()).toString() + "%"
                activity_kablan_mforat_ahoz_meosher.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
                val price = (big_item.get_SALESPRICE() ?: "0").toDouble()
                val count = (big_item.get_QTYFORACCOUNT() ?: "0").toDouble()
                activity_kablan_mforat_tashlom_sah.text = (price*count*percentforaccount*0.01).roundToInt().toString().trim()
                activity_kablan_mforat_qty.text = (big_item.get_QTY()?:"0").toString()
//                activity_kablan_mforat_kamot_helki.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
//                    if(hasFocus || activity_kablan_mforat_kamot_helki.text.isEmpty() )
//                        return@OnFocusChangeListener
//                    val str = activity_kablan_mforat_kamot_helki.text.toString()
//                    Thread({
//                        Looper.prepare()
//                        val update_value: HashMap<String, String> = HashMap()
//                        update_value[remote_big_table_helper.QTYINPARTIALACC] = str
//                        remote_big_table_helper.push_update(big_item, update_value, baseContext)
//                        big_item.set_QTYINPARTIALACC(str)
//                        global_variables_dataclass.DB_BIG!!.add_big(big_item)
//                        themer.hideKeyboard(baseContext,activity_kablan_mforat_kamot_helki)
//                        Thread { compute_saah_hakol(big_table) }.run()
//
//                    }).start()
//                    activity_kablan_mforat_kamot_helki.hint = str.trim()
//                    activity_kablan_mforat_kamot_helki.text.clear()
//                }


//                activity_kablan_mforat_qty.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
//                    if(hasFocus || activity_kablan_mforat_qty.text.isEmpty())
//                        return@OnFocusChangeListener
//                    val str = activity_kablan_mforat_qty.text.toString()
//                    Thread {
//                        Looper.prepare()
//                        val update_value: HashMap<String, String> = HashMap()
//                        update_value[remote_big_table_helper.QTY] = str
//                        remote_big_table_helper.push_update(big_item, update_value, baseContext)
//                        big_item.set_QTY(str)
//                        global_variables_dataclass.DB_BIG!!.add_to_table(big_item)
//                        themer.hideKeyboard(baseContext,activity_kablan_mforat_qty)
//                        Thread { compute_saah_hakol(big_table) }.run()
//                        runOnUiThread {
//                            val peola_parcent_local: String = (big_item.get_PERCENTFORACCOUNT() ?: 0).toString()
//                            val price_local = (big_item.get_SALESPRICE() ?: "0").toDouble()
//                            val count_local = (big_item.get_QTYFORACCOUNT() ?: "0").toDouble()
//                            val parcent_local = if(peola_parcent_local.isEmpty())
//                                0.0
//                            else
//                                peola_parcent_local.toDouble()
//                            activity_kablan_mforat_tashlom_sah.text = (price_local*count_local*parcent_local*0.01).roundToInt().toString().trim()
//                        }
//                    }.start()
//                    activity_kablan_mforat_qty.hint = str.trim()
//                    activity_kablan_mforat_qty.text.clear()
//                }

                activity_kablan_mforat_kamot_kablan.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                    if(hasFocus || activity_kablan_mforat_kamot_kablan.text.isEmpty())
                        return@OnFocusChangeListener
                    val str = activity_kablan_mforat_kamot_kablan.text.toString()
                    if(big_item.get_QTY() != null && !big_item.get_QTY()!!.isEmpty() && big_item.get_QTY()!!.toInt() < str.toInt() )
                    {
                        activity_kablan_mforat_kamot_kablan.text.clear()
                        global_variables_dataclass.log("kablan_mforat", "op cancelled")
                        return@OnFocusChangeListener
                    }
                    Thread {
                        Looper.prepare()
                        val update_value: HashMap<String, String> = HashMap()
                        update_value[remote_big_table_helper.QTYFORACCOUNT] = str
                        remote_big_table_helper.push_update(big_item, update_value, baseContext)
                        big_item.set_QTYFORACCOUNT(str)
                        global_variables_dataclass.DB_BIG!!.add_to_table(big_item)
                        themer.hideKeyboard(baseContext,activity_kablan_mforat_kamot_kablan)
                        Thread { compute_saah_hakol(big_table) }.run()
                        runOnUiThread {
                            val peola_parcent_local: String = (big_item.get_PERCENTFORACCOUNT() ?: 0).toString()
                            val price_local = (big_item.get_SALESPRICE() ?: "0").toDouble()
                            val count_local = (big_item.get_QTYFORACCOUNT() ?: "0").toDouble()
                            val parcent_local = if(peola_parcent_local.isEmpty())
                                0.0
                            else
                                peola_parcent_local.toDouble()
                            activity_kablan_mforat_tashlom_sah.text = (price_local*count_local*parcent_local*0.01).roundToInt().toString().trim()
                        }
                    }.start()
                    activity_kablan_mforat_kamot_kablan.hint = str.trim()
                    activity_kablan_mforat_kamot_kablan.text.clear()
                }

                activity_kablan_mforat_ahoz_meosher.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                    if(hasFocus || activity_kablan_mforat_ahoz_meosher.text.isEmpty())
                        return@OnFocusChangeListener
                    val str = activity_kablan_mforat_ahoz_meosher.text.toString()
                    if(big_item.get_MILESTONEPERCENT()!=null && !big_item.get_MILESTONEPERCENT()!!.isEmpty() && big_item.get_MILESTONEPERCENT()!!.toInt() < str.toInt())
                    {
                        activity_kablan_mforat_ahoz_meosher.text.clear()
                        global_variables_dataclass.log("kablan_mforat", "op cancelled")
                        return@OnFocusChangeListener
                    }
                    Thread {
                        Looper.prepare()
                        val update_value: HashMap<String, String> = HashMap()
                        update_value[remote_big_table_helper.PERCENTFORACCOUNT] = str
                        remote_big_table_helper.push_update(big_item, update_value, baseContext)
                        big_item.set_PERCENTFORACCOUNT(str)
                        global_variables_dataclass.DB_BIG!!.add_to_table(big_item)
                        themer.hideKeyboard(baseContext,activity_kablan_mforat_ahoz_meosher)
                        Thread { compute_saah_hakol(big_table) }.run()
                    }.start()
                    @Suppress("ConvertToStringTemplate")
                    activity_kablan_mforat_ahoz_meosher.hint = (str + "%").trim()
                    activity_kablan_mforat_ahoz_meosher.text.clear()
                }
            }

        }

        Thread{
            compute_saah_hakol(big_table)
        }.start()
    }

    /**
     * Creates a vendor list of a big table
     * @author Chaosruler972
     * @param vector the big table to filter
     * @return the result vector, all the vendor data you need!
     */
    private fun filter_vendor_list(vector: Vector<big_table_data>) : Vector<vendor_data>
    {
        val current = HashMap<String, Int>()
        val result = Vector<vendor_data>()
        for (item in vector)
        {
            val key = item.get_VENDOR_ID()
            if(key != null && !current.containsKey(key))
            {
                current[key] = 1
                val filtered = global_variables_dataclass.db_vendor_vec.filter { it.get_accountnum() == key }
                if(filtered.isNotEmpty())
                {
                   result.addElement(filtered.first())
                }
            }
        }
        return result
    }

    /**
     * Creates a big list of a big table by vendor id
     * @author Chaosruler972
     * @param vector the big table to filter
     * @return the result vector, all the vendor data you need!
     */
    private fun filter_out_big_by_db(vector: Vector<big_table_data>) : Vector<big_table_data>
    {
        val result = Vector<big_table_data>()
        val current = HashMap<String, Int>()
        for(item in vector)
        {
            val key = item.get_VENDOR_ID()
            if(key != null && key == chosen_vendor_id)
            {
                val second_key = item.get_INVENTORY_ID()
                if(second_key != null && !current.containsKey(second_key))
                {
                    current[second_key] = 1
                    result.addElement(item)
                }

            }
        }
        result.sortWith((compareBy({it.get_INVENTORY_ID()})))
        return result
    }

    /**
     *   compute sum and puts into textview and colors it
     * @author Chaosruler972
     */
    private fun compute_saah_hakol(big_table: Vector<big_table_data>)
    {
        if(baseContext == null)
            return
        var price:Double=0.toDouble()
            for (big_item in big_table) {
                var current_price = (big_item.get_SALESPRICE() ?: "0").toDouble()
                val count = (big_item.get_QTYFORACCOUNT() ?: "0").toDouble()
                val percentforaccount: String = (big_item.get_PERCENTFORACCOUNT() ?: 0).toString()
                val parcent = if (percentforaccount.isEmpty())
                    0.0
                else
                    percentforaccount.toDouble()
                current_price *= count * parcent*0.01
                global_variables_dataclass.log("Kablan", current_price.toString() + "," + count.toString() + "," + percentforaccount.toString() + "," + parcent.toString())
                global_variables_dataclass.log("Kablan", current_price.toString())
                price += current_price
            }
        runOnUiThread {
            if (price > 0)
                activity_kablan_mforat_saah_hakol.setTextColor(getColor(R.color.green))
            else // <= 0
                activity_kablan_mforat_saah_hakol.setTextColor(getColor(R.color.red))
            try {
                activity_kablan_mforat_saah_hakol.text = price.toInt().toString().trim()
            }
            catch (e: NumberFormatException)
            {
                activity_kablan_mforat_saah_hakol.text = "0"
            }
        }
    }

    private fun get_all_inventory_unique(inventory_id: String): Vector<big_table_data> {
        val vec = Vector<big_table_data>()
        val hashmap: HashMap<String, Boolean> = HashMap()
        for(big_item in global_variables_dataclass.db_big_vec)
        {
            if (big_item.get_PROJECT_ID() == global_variables_dataclass.projid && big_item.get_INVENTORY_ID() == inventory_id && !hashmap.containsKey(big_item.get_OPRID()?:""))
            {
                vec.addElement(big_item)
                hashmap[big_item.get_OPRID()?:""] = true
            }
        }
        global_variables_dataclass.log("mforat", "hashmap size is ${hashmap.size}")
        return vec
    }
}
