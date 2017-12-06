package com.example.chaosruler.msa_manager.activies.kablan_pashot_activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_OPR_table_helper
import com.example.chaosruler.msa_manager.SQLITE_helpers.sync_table.local_inventory_table_helper
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.*
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import java.util.*


/**
 * Created by chaosruler on 12/5/17.
 */
class kablan_pashot_listview_arrayadapter(context: Context,arr: Vector<big_table_data>) : ArrayAdapter<big_table_data>(context, R.layout.kablan_pashot_listview,arr.toTypedArray())
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {
        @SuppressWarnings("hiding")
        var convertView:View = convertView ?: LayoutInflater.from(context).inflate(R.layout.kablan_pashot_listview,parent,false)

        val hoza:TextView = get_view(convertView,R.id.item_kablan_pashot_hoza) as TextView
        val peola:TextView = get_view(convertView,R.id.item_kablan_pashot_peola) as TextView
        val ahoz:TextView = get_view(convertView,R.id.item_kablan_pashot_ahoz_peola) as TextView

        val big_item:big_table_data = getItem(position)
        val opr_item: opr_data = global_variables_dataclass.DB_OPR!!.get_opr_by_id(big_item.get_OPRID()!!)!!
        val project_item:project_data = global_variables_dataclass.DB_project!!.get_project_by_id(big_item.get_PROJECT_ID()!!)!!
        val vendor_item:vendor_data = global_variables_dataclass.DB_VENDOR!!.get_vendor_by_id(big_item.get_VENDOR_ID()!!)!!
        val inventory:inventory_data = global_variables_dataclass.DB_INVENTORY!!.get_opr_by_id(big_item.get_INVENTORY_ID()!!)!!

        hoza.text = vendor_item.get_accountnum() ?: ""
        peola.text = vendor_item.get_accountname() ?: ""
        var parcent:String = big_item.get_PERCENTFORACCOUNT()?:0.toString()
        ahoz.text = (parcent.toDouble()*100).toInt().toString() + "%"
        return convertView
    }


    private fun get_view(convertView: View,id:Int) : View = convertView.findViewById(id) // grabs the correpsonding view by id from layout

}