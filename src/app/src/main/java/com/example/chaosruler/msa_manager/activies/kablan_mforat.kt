package com.example.chaosruler.msa_manager.activies

import android.app.Activity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_big_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.*
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_kablan_mforat.*
import java.util.*
import kotlin.collections.HashMap

class kablan_mforat : Activity() {

    private lateinit var adapter:ArrayAdapter<big_table_data>
    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kablan_mforat)
        init_spinner()
        compute_saah_hakol()
    }

    /*
                   inits spinner
            */
    private fun init_spinner()
    {
        val big_table:Vector<big_table_data> =
        if(global_variables_dataclass.GUI_MODE)
            Vector<big_table_data>()
        else if (!global_variables_dataclass.GUI_MODE && global_variables_dataclass.isLocal)
            global_variables_dataclass.DB_BIG!!.get_local_DB_by_projname(global_variables_dataclass.projid)
        else
            global_variables_dataclass.DB_BIG!!.server_data_to_vector_by_projname(global_variables_dataclass.projid)


        adapter = ArrayAdapter<big_table_data>(this, android.R.layout.simple_spinner_item,big_table)

        activity_kablan_mforat_spinner.adapter = adapter
        activity_kablan_mforat_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long)
            {
                // upon Spinner selecting a user, update the other fields
                val big_item:big_table_data = activity_kablan_mforat_spinner.adapter.getItem(i) as big_table_data

                var peola_parcent:String = big_item.get_PERCENTFORACCOUNT()?:0.toString()
                var milestone_parcent:String = big_item.get_PERCENTFORACCOUNT()?:0.toString()

               // var txtview:TextView = view as TextView
              //  txtview.text = vendor_item.get_accountname()

                activity_kablan_mforat_kamot_hoza.text = (big_item.get_QTY() ?: "0").trim()
                activity_kablan_mforat_yehida_price.text = (big_item.get_SALESPRICE() ?: "0").trim()
                activity_kablan_mforat_peola_percent.text = ((peola_parcent.toDouble()).toInt().toString() + "%").trim()
                activity_kablan_mforat_kamot_helki.hint = (big_item.get_QTYFORACCOUNT() ?: "0").trim()
                activity_kablan_mforat_kamot_helki.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
                activity_kablan_mforat_kamot_kablan.hint = (big_item.get_QTYFORACCOUNT() ?: "0").trim()
                activity_kablan_mforat_kamot_kablan.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
                activity_kablan_mforat_ahoz_meosher.hint = ((milestone_parcent.toDouble()).toInt().toString() + "%").trim()
                activity_kablan_mforat_ahoz_meosher.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
                var price = (big_item.get_SALESPRICE() ?: "0").toDouble()
                var count = (big_item.get_QTYFORACCOUNT() ?: "0").toDouble()
                var parcent = milestone_parcent.toDouble()/100
                activity_kablan_mforat_tashlom_sah.text = (price*count*parcent).toString().trim()

                activity_kablan_mforat_kamot_helki.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                    if(hasFocus || activity_kablan_mforat_kamot_helki.text.isEmpty() )
                        return@OnFocusChangeListener
                    var str = activity_kablan_mforat_kamot_helki.text.toString()
                    var update_value:HashMap<String,String> = HashMap()
                    update_value[remote_big_table_helper.QTYFORACCOUNT] = str
                    remote_big_table_helper.push_update(big_item,update_value,baseContext)
                    activity_kablan_mforat_kamot_helki.hint = str.trim()
                    activity_kablan_mforat_kamot_helki.text.clear()
                    big_item.set_QTYFORACCOUNT(str)
                    global_variables_dataclass.DB_BIG!!.add_big(big_item)
                    compute_saah_hakol()
                    hideKeyboard(activity_kablan_mforat_kamot_helki)
                }

                activity_kablan_mforat_kamot_kablan.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                    if(hasFocus || activity_kablan_mforat_kamot_kablan.text.isEmpty())
                        return@OnFocusChangeListener
                    var str = activity_kablan_mforat_kamot_kablan.text.toString()
                    var update_value:HashMap<String,String> = HashMap()
                    update_value[remote_big_table_helper.QTYFORACCOUNT] = str
                    remote_big_table_helper.push_update(big_item,update_value,baseContext)
                    activity_kablan_mforat_kamot_kablan.hint = str.trim()
                    activity_kablan_mforat_kamot_kablan.text.clear()
                    big_item.set_QTYFORACCOUNT(str)
                    global_variables_dataclass.DB_BIG!!.add_big(big_item)
                    compute_saah_hakol()
                    hideKeyboard(activity_kablan_mforat_kamot_kablan)
                }

                activity_kablan_mforat_ahoz_meosher.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                    if(hasFocus || activity_kablan_mforat_ahoz_meosher.text.isEmpty())
                        return@OnFocusChangeListener
                    var str = activity_kablan_mforat_ahoz_meosher.text.toString()
                    Log.d("kbalan_mforat","str is : " + str)
                    var update_value:HashMap<String,String> = HashMap()
                    update_value[remote_big_table_helper.PERCENTFORACCOUNT] = str
                    remote_big_table_helper.push_update(big_item,update_value,baseContext)
                    big_item.set_PERCENTFORACCOUNT(str)
                    global_variables_dataclass.DB_BIG!!.add_big(big_item)
                    Log.d("kbalan_mforat","done")
                    activity_kablan_mforat_ahoz_meosher.hint = (str + "%").trim()
                    activity_kablan_mforat_ahoz_meosher.text.clear()
                    compute_saah_hakol()
                    hideKeyboard(activity_kablan_mforat_ahoz_meosher)
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>)
            {
                // case there was nothing to select (empty database)
            }
        }

    }
    /*
                   compute sum and puts into textview and colors it
            */
    private fun compute_saah_hakol()
    {
        var price:Double=0.toDouble()
        for(i in 0..activity_kablan_mforat_spinner.adapter.count-1)
        {
            val big_item:big_table_data = activity_kablan_mforat_spinner.adapter.getItem(i) as big_table_data
            var price = (big_item.get_SALESPRICE() ?: "0").toDouble()
            var count = (big_item.get_QTYFORACCOUNT() ?: "0").toDouble()
            var milestone_parcent:String = big_item.get_PERCENTFORACCOUNT()?:0.toString()
            var parcent = milestone_parcent.toDouble()/100
            var current_price = price*count*parcent
            price+=current_price
        }
        if(price>0)
            activity_kablan_mforat_saah_hakol.setTextColor(getColor(R.color.green))
        else // <= 0
            activity_kablan_mforat_saah_hakol.setTextColor(getColor(R.color.red))
        activity_kablan_mforat_saah_hakol.text = price.toString().trim()
    }
    /*
       hides softkeyboard from specific view
    */
    fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager!!.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
