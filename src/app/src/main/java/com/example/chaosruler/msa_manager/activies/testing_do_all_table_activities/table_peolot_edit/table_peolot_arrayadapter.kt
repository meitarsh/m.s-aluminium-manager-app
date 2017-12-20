package com.example.chaosruler.msa_manager.activies.testing_do_all_table_activities.table_peolot_edit

import android.content.Context
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_opr_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.object_types.opr_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import java.util.*


class table_peolot_arrayadapter(context: Context, arr: Vector<opr_data>) : ArrayAdapter<opr_data>(context, R.layout.item_opr,arr.toTypedArray())
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {

        @Suppress("NAME_SHADOWING")
        val convertView: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_opr, parent, false)

        val peolot_data: opr_data = getItem(position)
        val all_views = Vector<View>()

        val id = themer.get_view(convertView, R.id.item_opr_id) as TextView
        val dataaraeid = themer.get_view(convertView, R.id.item_opr_dataaraeid) as TextView
        val name = themer.get_view(convertView, R.id.item_opr_name) as EditText



        all_views.addElement(id)
        all_views.addElement(dataaraeid)
        all_views.addElement(name)

        id.text = (peolot_data.get_oprid()?:"").trim()

        dataaraeid.text = (peolot_data.get_DATAREAID()?:"").trim()

        name.hint = (peolot_data.get_opr_name()?:"").trim()


        name.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if(hasFocus || name.text.isEmpty() )
                return@OnFocusChangeListener
            val str = name.text.toString()
            Thread({
                Looper.prepare()
                val update_value: HashMap<String, String> = HashMap()
                update_value[remote_opr_table_helper.NAME] = str
                remote_opr_table_helper.push_update(peolot_data, update_value, context)
                peolot_data.set_oprname(str)
                global_variables_dataclass.DB_OPR!!.add_opr(peolot_data)
                themer.hideKeyboard(context,name)
            }).start()
            name.hint = str.trim()
            name.text.clear()
        }

        themer.center_all_views(all_views)

        return convertView


    }
}