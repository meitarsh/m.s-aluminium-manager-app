package com.example.chaosruler.msa_manager.activies.divohi_takalot_tofes_activity

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.object_types.project_data.project_data
import com.example.chaosruler.msa_manager.object_types.takala_data.takala_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import java.util.*

/**
 * Array adapter responsible for populating the listview of table for divohi takalot (form type) activity
 * @author Chaosruler972
 * @constructor the context and the list of data that we want to populate
 */
class divohi_takalot_tofes_arrayadapter(
        /**
         * The Activity we are working with (chose that context doesn't fulfiful yet, probably kotlin bug)
         * @author Chaosruler972
         */
        private var context: Activity
        , arr: List<takala_data>) : ArrayAdapter<takala_data>(context, R.layout.item_divohi_takalot_tofes,arr.toTypedArray())
{
    /**
     * inflates a view and generates it, writes the data in it and initates logic on press and edit
     * @author Chaosruler972
     * @param convertView the "listview" motherview
     * @param parent the parent that holds all the views together
     * @param position the position in the vector (auto iterate)
     * @return the view we inflated with all the logic initated
     */
    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        @Suppress("NAME_SHADOWING")
        val convertView: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_divohi_takalot_tofes, parent, false)

        val mispar_parit = themer.get_view(convertView, R.id.item_divohi_takalot_tofes_mispar_parit) as TextView
        val shem_parit = themer.get_view(convertView, R.id.item_divohi_takalot_tofes_shem_parit) as TextView
        val mispar_project = themer.get_view(convertView, R.id.item_divohi_takalot_tofes_mispar_project) as TextView
        val shem_project = themer.get_view(convertView, R.id.item_divohi_takalot_tofes_shem_project) as TextView
        val kamot = themer.get_view(convertView, R.id.item_divohi_takalot_tofes_kamot) as TextView
        val sog_takala = themer.get_view(convertView, R.id.item_divohi_takalot_tofes_sog_takala) as TextView
        val koma = themer.get_view(convertView, R.id.item_divohi_takalot_tofes_koma) as TextView
        val bnian = themer.get_view(convertView, R.id.item_divohi_takalot_tofes_bnian) as TextView
//        val dira = themer.get_view(convertView, R.id.item_divohi_takalot_tofes_dira) as TextView
        val tiaor_takala = themer.get_view(convertView, R.id.item_divohi_takalot_tofes_tiaaor_takala) as TextView
        val peolot_ltikon = themer.get_view(convertView, R.id.item_divohi_takalot_tofes_peolot_ltikon) as TextView
        val peolot_monoot = themer.get_view(convertView, R.id.item_divohi_takalot_tofes_peolot_monoot) as TextView
        val tgovat_mnaal = themer.get_view(convertView, R.id.item_divohi_takalot_tofes_tgovat_mnaal) as TextView
        val alot_takala = themer.get_view(convertView, R.id.item_divohi_takalot_tofes_alot_takala) as TextView

        val all_txtviews = Vector<View>()
        all_txtviews.add(mispar_parit)
        all_txtviews.add(shem_parit)
        all_txtviews.add(mispar_project)
        all_txtviews.add(shem_project)
        all_txtviews.add(kamot)
        all_txtviews.add(sog_takala)
        all_txtviews.add(koma)
        all_txtviews.add(bnian)
//        all_txtviews.add(dira)
        all_txtviews.add(tiaor_takala)
        all_txtviews.add(peolot_ltikon)
        all_txtviews.add(peolot_monoot)
        all_txtviews.add(tgovat_mnaal)
        all_txtviews.add(alot_takala)


        val takala_data: takala_data = getItem(position)
        val project_data: project_data = try {
            global_variables_dataclass.db_project_vec.filter { it.getProjID() == takala_data.get_projid() }[0]
        }
        catch (e: Exception)
        {
            project_data(takala_data.get_projid(), "", takala_data.get_DATAAREAID(), "")
        }


        mispar_parit.text = (takala_data.get_ITEMID() ?: "").trim()
        shem_parit.text = (takala_data.get_ITEMTXT() ?: "").trim()
        mispar_project.text = (takala_data.get_projid() ?: "").trim()
        shem_project.text = (project_data.get_project_name() ?: "").trim()
        kamot.text = (takala_data.get_QTY() ?: "").trim()
        sog_takala.text = (takala_data.get_SUG() ?: "").trim()
        koma.text = (takala_data.get_KOMA() ?: "").trim()
        bnian.text = (takala_data.get_DIRA() ?: "").trim()
        tiaor_takala.text = (takala_data.get_TEUR() ?: "").trim()
        peolot_ltikon.text = (takala_data.get_MUMLATZ() ?: "").trim()
        peolot_monoot.text = (takala_data.get_MONAAT() ?: "").trim()
        tgovat_mnaal.text = (takala_data.get_TGUVA() ?: "").trim()
        alot_takala.text = (takala_data.get_ALUT() ?: "").trim()


        themer.center_all_views(all_txtviews)

        return convertView

    }


}