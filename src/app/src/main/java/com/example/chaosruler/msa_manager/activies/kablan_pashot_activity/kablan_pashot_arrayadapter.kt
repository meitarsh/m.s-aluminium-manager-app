package com.example.chaosruler.msa_manager.activies.kablan_pashot_activity

import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.object_types.*
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import java.util.*


class kablan_pashot_arrayadapter(context: Context,arr: Vector<big_table_data>) :ArrayAdapter<big_table_data>(context, R.layout.item_kablan_pashot,arr.toTypedArray())
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {

        @Suppress("NAME_SHADOWING")
        var convertView:View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_kablan_pashot,parent,false)

        val hoza:TextView = themer.get_view(convertView,R.id.item_kablan_pashot_hoza) as TextView
        val peola:TextView = themer.get_view(convertView,R.id.item_kablan_pashot_peola) as TextView
        val ahoz:TextView = themer.get_view(convertView,R.id.item_kablan_pashot_ahoz_peola) as TextView

        val big_item:big_table_data = getItem(position)
        val vendor_item: vendor_data = global_variables_dataclass.DB_VENDOR!!.get_vendor_by_id(big_item.get_VENDOR_ID()!!)!!


        hoza.text = (vendor_item.get_accountnum() ?: "").trim()
        peola.text = (vendor_item.get_accountname() ?: "").trim()
        var parcent:String = big_item.get_PERCENTFORACCOUNT()?:0.toString().trim()
        ahoz.text = ((parcent.toDouble()*1).toInt().toString() + "%").trim()
        ahoz.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED

        var all_views = Vector<View>()
        all_views.addElement(hoza)
        all_views.add(peola)
        all_views.add(ahoz)

        themer.center_all_views(all_views)

        return convertView


    }

}