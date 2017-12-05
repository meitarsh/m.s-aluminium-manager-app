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
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.big_table_data
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.inventory_data
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.opr_data
import java.util.*


/**
 * Created by chaosruler on 12/5/17.
 */
class kablan_pashot_listview_arrayadapter(context: Context,arr: Vector<big_table_data>) : ArrayAdapter<big_table_data>(context, R.layout.kablan_pashot_listview,arr.toTypedArray())
{
    private var opr_db:local_OPR_table_helper = local_OPR_table_helper(context)
    private var item_db:local_inventory_table_helper = local_inventory_table_helper(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {
        var convertView:View = convertView ?: LayoutInflater.from(context).inflate(R.layout.kablan_pashot_listview,parent,false)

        val hoza:TextView = get_view(convertView,R.id.item_kablan_pashot_hoza) as TextView
        val peola:TextView = get_view(convertView,R.id.item_kablan_pashot_peola) as TextView
        val ahoz:TextView = get_view(convertView,R.id.item_kablan_pashot_ahoz_peola) as TextView

        val big_item:big_table_data = getItem(position)
        val opr_item: opr_data = opr_db.get_opr_by_id(big_item.get_OPRID()!!)  ?: return convertView
        val inventory:inventory_data = item_db.get_opr_by_id(big_item.get_INVENTORY_ID()!!) ?: return convertView

        hoza.text = opr_item.get_opr_name() ?: ""
        peola.text = inventory.get_itemname() ?: ""
        var parcent:String = big_item.get_MILESTONEPERCENT()?:0.toString()
        ahoz.text = (parcent.toDouble()*100).toInt().toString() + "%"
        return convertView
    }


    private fun get_view(convertView: View,id:Int) : View = convertView.findViewById(id) // grabs the correpsonding view by id from layout

}