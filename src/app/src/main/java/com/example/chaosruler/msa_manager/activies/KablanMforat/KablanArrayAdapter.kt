package com.example.chaosruler.msa_manager.activies.KablanMforat

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.chaosruler.msa_manager.object_types.big_table_data
import com.example.chaosruler.msa_manager.object_types.vendor_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass

/**
 * Drop down view to list items with their flat place
 */
class KablanArrayAdapter(context: Context, resource_id: Int, list: List<big_table_data> ) : ArrayAdapter<big_table_data>(context, resource_id, list)
{
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val textView = TextView(context)
        val vendor_data : vendor_data = try {
            global_variables_dataclass.db_vendor_vec.filter { it.get_accountnum() == this.getItem(position).get_VENDOR_ID() }[0]!!
        }
        catch (e: Exception) {
            vendor_data("", "", "", "")
        }
        textView.text = vendor_data.get_accountname() ?: ""
        return textView
    }
}