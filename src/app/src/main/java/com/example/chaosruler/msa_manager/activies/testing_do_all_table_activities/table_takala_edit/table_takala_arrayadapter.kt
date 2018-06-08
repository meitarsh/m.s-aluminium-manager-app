package com.example.chaosruler.msa_manager.activies.testing_do_all_table_activities.table_takala_edit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.object_types.takala_data.takala_data
import com.example.chaosruler.msa_manager.services.themer
import java.util.*

/**
 * table takala array adapter
 * @author Chaosruler972
 */
class table_takala_arrayadapter(context: Context, arr: Vector<takala_data>) : ArrayAdapter<takala_data>(context, R.layout.item_takala, arr.toTypedArray()) {

    /**
     * inflates a view and generates it, writes the data in it and initates logic on press and edit
     * @author Chaosruler972
     * @param convertView the "listview" motherview
     * @param parent the parent that holds all the views together
     * @param position the position in the vector (auto iterate)
     * @return the view we inflated with all the logic initated
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        @Suppress("NAME_SHADOWING")
        val convertView: View = convertView
                ?: LayoutInflater.from(context).inflate(R.layout.item_takala, parent, false)

        val all_views = Vector<View>()

        val takala_data: takala_data = getItem(position)

        val projid = themer.get_view(convertView, R.id.item_takala_projid) as TextView
        val itemid = themer.get_view(convertView, R.id.item_takala_itemid) as TextView
        val dataaraeid = themer.get_view(convertView, R.id.item_takala_dataaraeid) as TextView
        val qty = themer.get_view(convertView, R.id.item_takala_qty) as TextView
        val koma = themer.get_view(convertView, R.id.item_takala_koma) as TextView
        val binyan = themer.get_view(convertView, R.id.item_takala_binyan) as TextView
        val dira = themer.get_view(convertView, R.id.item_takala_dira) as TextView
        val teeur = themer.get_view(convertView, R.id.item_takala_teeur) as TextView
        val mumlatz = themer.get_view(convertView, R.id.item_takala_mumlatz) as TextView
        val munaat = themer.get_view(convertView, R.id.item_takala_munaat) as TextView
        val tguva = themer.get_view(convertView, R.id.item_takala_tguva) as TextView
        val sug = themer.get_view(convertView, R.id.item_takala_sug) as TextView
        val alut = themer.get_view(convertView, R.id.item_takala_alut) as TextView
        val itemtxt = themer.get_view(convertView, R.id.item_takala_itemtxt) as TextView
        val recversion = themer.get_view(convertView, R.id.item_takala_recversion) as TextView
        val recid = themer.get_view(convertView, R.id.item_takala_recid) as TextView



        all_views.addElement(projid)
        all_views.addElement(itemid)
        all_views.addElement(dataaraeid)
        all_views.addElement(qty)
        all_views.addElement(koma)
        all_views.addElement(binyan)
        all_views.addElement(dira)
        all_views.addElement(teeur)
        all_views.addElement(mumlatz)
        all_views.addElement(munaat)
        all_views.addElement(tguva)
        all_views.addElement(sug)
        all_views.addElement(alut)
        all_views.addElement(itemtxt)
        all_views.addElement(recversion)
        all_views.addElement(recid)

        projid.text = (takala_data.get_projid() ?: "").trim()

        itemid.text = (takala_data.get_ITEMID() ?: "").trim()

        dataaraeid.text = (takala_data.get_DATAAREAID() ?: "").trim()

        qty.text = (takala_data.get_QTY() ?: "").trim()

        koma.text = (takala_data.get_KOMA() ?: "").trim()

        binyan.text = (takala_data.get_BINYAN() ?: "").trim()

        dira.text = (takala_data.get_DIRA() ?: "").trim()

        teeur.text = (takala_data.get_TEUR() ?: "").trim()

        mumlatz.text = (takala_data.get_MUMLATZ() ?: "").trim()

        munaat.text = (takala_data.get_MONAAT() ?: "").trim()

        tguva.text = (takala_data.get_TGUVA() ?: "").trim()

        sug.text = (takala_data.get_SUG() ?: "").trim()

        alut.text = (takala_data.get_ALUT() ?: "").trim()

        itemtxt.text = (takala_data.get_ITEMTXT() ?: "").trim()

        recversion.text = (takala_data.get_RECVERSION() ?: "").trim()

        recid.text = (takala_data.get_RECID() ?: "").trim()

        themer.center_all_views(all_views)

        return convertView


    }
}