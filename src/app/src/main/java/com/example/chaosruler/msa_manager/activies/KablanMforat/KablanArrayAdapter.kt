package com.example.chaosruler.msa_manager.activies.KablanMforat

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.chaosruler.msa_manager.object_types.big_table_data

/**
 * Drop down view to list items with their floor place
 */
class KablanArrayAdapter(context: Context, resource_id: Int, list: List<big_table_data> ) : ArrayAdapter<big_table_data>(context, resource_id, list)
{
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val textView = TextView(context)
        textView.text = this.getItem(position).get_FLAT()?:""
        return textView
    }
}