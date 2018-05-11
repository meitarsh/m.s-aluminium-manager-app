package com.example.chaosruler.msa_manager.activies.floor_chooser

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.chaosruler.msa_manager.object_types.big_table_data

/**
 * Created to get dropdown list with text of flat
 * @author Chaosruler972
 */
class floorArrayAdapter(context: Context, resource_id: Int, list: List<big_table_data>) : ArrayAdapter<big_table_data>(context, resource_id, list) {
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val txtview = TextView(context)
        txtview.text = getItem(position).get_FLOOR()?:""
        return txtview
    }
}