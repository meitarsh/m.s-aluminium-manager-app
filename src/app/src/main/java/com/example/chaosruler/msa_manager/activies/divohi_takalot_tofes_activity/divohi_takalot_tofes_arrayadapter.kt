package com.example.chaosruler.msa_manager.activies.divohi_takalot_tofes_activity

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.object_types.big_table_data
import com.example.chaosruler.msa_manager.object_types.inventory_data
import com.example.chaosruler.msa_manager.object_types.project_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import java.util.*

/**
 * Created by chaosruler on 12/10/17.
 */
class divohi_takalot_tofes_arrayadapter(private var context: Activity, arr: Vector<big_table_data>) : ArrayAdapter<big_table_data>(context, R.layout.item_divohi_takalot_tofes,arr.toTypedArray())
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {

        @Suppress("NAME_SHADOWING")
        var convertView: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_divohi_takalot_tofes,parent,false)

        var mispar_parit = themer.get_view(convertView,R.id.item_divohi_takalot_tofes_mispar_parit) as TextView
        var shem_parit = themer.get_view(convertView,R.id.item_divohi_takalot_tofes_shem_parit) as TextView
        var mispar_project = themer.get_view(convertView,R.id.item_divohi_takalot_tofes_mispar_project) as TextView
        var shem_project = themer.get_view(convertView,R.id.item_divohi_takalot_tofes_shem_project) as TextView
        var kamot = themer.get_view(convertView,R.id.item_divohi_takalot_tofes_kamot) as TextView
        var sog_takala = themer.get_view(convertView,R.id.item_divohi_takalot_tofes_sog_takala) as TextView
        var koma = themer.get_view(convertView,R.id.item_divohi_takalot_tofes_koma) as TextView
        var bnian = themer.get_view(convertView,R.id.item_divohi_takalot_tofes_bnian) as TextView
        var dira = themer.get_view(convertView,R.id.item_divohi_takalot_tofes_dira) as TextView
        var tiaor_takala = themer.get_view(convertView,R.id.item_divohi_takalot_tofes_tiaaor_takala) as TextView
        var peolot_ltikon = themer.get_view(convertView,R.id.item_divohi_takalot_tofes_peolot_ltikon) as TextView
        var peolot_monoot = themer.get_view(convertView,R.id.item_divohi_takalot_tofes_peolot_monoot) as TextView
        var tgovat_mnaal = themer.get_view(convertView,R.id.item_divohi_takalot_tofes_tgovat_mnaal) as TextView
        var alot_takala = themer.get_view(convertView,R.id.item_divohi_takalot_tofes_alot_takala) as TextView

        var all_txtviews = Vector<View>()
        all_txtviews.add(mispar_parit)
        all_txtviews.add(shem_parit)
        all_txtviews.add(mispar_project)
        all_txtviews.add(shem_project)
        all_txtviews.add(kamot)
        all_txtviews.add(sog_takala)
        all_txtviews.add(koma)
        all_txtviews.add(bnian)
        all_txtviews.add(dira)
        all_txtviews.add(tiaor_takala)
        all_txtviews.add(peolot_ltikon)
        all_txtviews.add(peolot_monoot)
        all_txtviews.add(tgovat_mnaal)
        all_txtviews.add(alot_takala)


        val big_item:big_table_data = getItem(position)

        val project_item: project_data = global_variables_dataclass.DB_project!!.get_project_by_id(big_item.get_PROJECT_ID()?:"")!!
        val inventory: inventory_data = global_variables_dataclass.DB_INVENTORY!!.get_inventory_by_id(big_item.get_INVENTORY_ID()?:"")!!

        mispar_parit.text = (big_item.get_ITEMNUMBER() ?: "").trim()
        shem_parit.text = (inventory.get_itemname() ?: "").trim()
        mispar_project.text = (project_item.getProjID() ?: "").trim()
        shem_project.text = (project_item.get_project_name() ?: "").trim()
        kamot.text = (big_item.get_QTY() ?: "").trim()
        sog_takala.text = "No value from database"
        koma.text = (big_item.get_FLOOR() ?: "").trim()
        bnian.text = (big_item.get_FLAT() ?: "").trim()
        dira.text = (big_item.get_DIRANUM() ?: "").trim()
        tiaor_takala.text = "No value from database"
        peolot_ltikon.text = "No value from database"
        peolot_monoot.text = "No value from database"
        tgovat_mnaal.text = "No value from database"
        alot_takala.text = (big_item.get_TOTALSUM() ?: "").trim()


        themer.center_all_views(all_txtviews)

        return convertView

    }


}