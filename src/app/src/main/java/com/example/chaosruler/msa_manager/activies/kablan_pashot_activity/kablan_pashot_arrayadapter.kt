package com.example.chaosruler.msa_manager.activies.kablan_pashot_activity

import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.object_types.big_table.big_table_data
import com.example.chaosruler.msa_manager.object_types.opr_data.opr_data
import com.example.chaosruler.msa_manager.object_types.vendor_data.vendor_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import java.util.*

/**
 * Array adapter responsible for populating the listview of table for kablan pashot activity
 * @author Chaosruler972
 * @constructor the context and the list of data that we want to populate
 */
class kablan_pashot_arrayadapter(context: Context,arr: Vector<big_table_data>) :ArrayAdapter<big_table_data>(context, R.layout.item_kablan_pashot,arr.toTypedArray())
{
    /**
     * inflates a view and generates it, writes the data in it and initates logic on press and edit
     * @author Chaosruler972
     * @param convertView the "listview" motherview
     * @param parent the parent that holds all the views together
     * @param position the position in the vector (auto iterate)
     * @return the view we inflated with all the logic initated
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {

        @Suppress("NAME_SHADOWING")
        val convertView: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_kablan_pashot, parent, false)

        val hoza:TextView = themer.get_view(convertView,R.id.item_kablan_pashot_hoza) as TextView
        val peola:TextView = themer.get_view(convertView,R.id.item_kablan_pashot_peola) as TextView
        val ahoz:TextView = themer.get_view(convertView,R.id.item_kablan_pashot_ahoz_peola) as TextView

        val big_item: big_table_data = getItem(position)
        val vendor_item: vendor_data = try {
            global_variables_dataclass.db_vendor_vec.filter { it.get_accountnum() == big_item.get_VENDOR_ID()?:"" }[0]
        }
        catch (e: IndexOutOfBoundsException)
        {
            vendor_data("", "err", "", "")
        }

        val opr: opr_data =
                try {
                    val index = global_variables_dataclass.db_opr_vec.indexOf(opr_data(big_item.get_OPRID()
                            ?: "", "", "", ""))
                    global_variables_dataclass.db_opr_vec[index]
                }
                catch (e:IndexOutOfBoundsException)
                {
                    // hack for testing case, since database weirdly doesn't have this data
                    opr_data("", big_item.get_OPRID()
                            ?: "", "", "")
                }

        hoza.text = (vendor_item.get_accountnum() ?: "").trim()
        peola.text = (opr.get_opr_name() ?: "").trim()
        val parcent: String = big_item.get_PERCENTFORACCOUNT() ?: 0.toString().trim()
        val parcent_checker = if (parcent.isEmpty())
            "0"
        else
            parcent
        ahoz.text = (((parcent_checker).toDouble()).toInt().toString() + "%").trim()
        ahoz.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED

        val all_views = Vector<View>()
        all_views.addElement(hoza)
        all_views.add(peola)
        all_views.add(ahoz)

        themer.center_all_views(all_views)

        return convertView


    }

}