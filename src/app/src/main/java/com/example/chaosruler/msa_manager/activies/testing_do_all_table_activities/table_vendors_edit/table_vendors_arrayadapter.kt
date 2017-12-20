package com.example.chaosruler.msa_manager.activies.testing_do_all_table_activities.table_vendors_edit

import android.content.Context
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_vendors_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.object_types.vendor_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import java.util.*


class table_vendors_arrayadapter(context: Context, arr: Vector<vendor_data>) : ArrayAdapter<vendor_data>(context, R.layout.item_vendors,arr.toTypedArray())
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {

        @Suppress("NAME_SHADOWING")
        var convertView:View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_vendors,parent,false)

        var all_views = Vector<View>()

        val vendor_data: vendor_data = getItem(position)

        var id = themer.get_view(convertView,R.id.item_vendor_id) as TextView
        var dataaraeid = themer.get_view(convertView,R.id.item_vendor_dataaraeid) as TextView
        var name = themer.get_view(convertView,R.id.item_vendor_name) as EditText



        all_views.addElement(id)
        all_views.addElement(dataaraeid)
        all_views.addElement(name)


        id.text = (vendor_data.get_accountnum()?:"").trim()

        dataaraeid.text = (vendor_data.get_DATAREAID()?:"").trim()

        name.hint = (vendor_data.get_accountname()?:"").trim()


        name.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if(hasFocus || name.text.isEmpty() )
                return@OnFocusChangeListener
            var str = name.text.toString()
            Thread({
                Looper.prepare()
                var update_value: HashMap<String, String> = HashMap()
                update_value[remote_vendors_table_helper.NAME] = str
                remote_vendors_table_helper.push_update(vendor_data, update_value, context)
                vendor_data.set_accountname(str)
                global_variables_dataclass.DB_VENDOR!!.add_vendor(vendor_data)
                themer.hideKeyboard(context,name)
            }).start()
            name.hint = str.trim()
            name.text.clear()
        }

        themer.center_all_views(all_views)

        return convertView


    }
}