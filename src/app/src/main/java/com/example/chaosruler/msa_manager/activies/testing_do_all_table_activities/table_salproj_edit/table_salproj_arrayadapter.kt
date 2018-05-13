package com.example.chaosruler.msa_manager.activies.testing_do_all_table_activities.table_salproj_edit

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.object_types.salprojluz_data
import com.example.chaosruler.msa_manager.services.themer
import java.util.*


/**
 * Array adapter responsible for populating the listview of table vendors
 * @author Chaosruler972
 * @constructor the context and the list of data that we want to populate
 */
class table_salproj_arrayadapter(context: Context, arr: Vector<salprojluz_data>) : ArrayAdapter<salprojluz_data>(context,
        R.layout.item_salproj,arr.toTypedArray())
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
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {

        @Suppress("NAME_SHADOWING")
        val convertView:View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_salproj,parent,false)

        val all_views = Vector<View>()

        val salprojluz_data: salprojluz_data = getItem(position)

        val id = themer.get_view(convertView,R.id.item_salproj_projid) as TextView
        val dataaraeid = themer.get_view(convertView,R.id.item_salproj_dataaraeid) as TextView
        val startdate = themer.get_view(convertView,R.id.item_salproj_startdate) as TextView
        val enddate = themer.get_view(convertView,R.id.item_salproj_finishdate) as TextView
        val isfinished = themer.get_view(convertView,R.id.item_salproj_isfinished) as TextView
        val siumbpoal = themer.get_view(convertView,R.id.item_salproj_fsiumbpoal) as TextView
        val notes = themer.get_view(convertView,R.id.item_salproj_notes) as TextView
        val koma = themer.get_view(convertView,R.id.item_salproj_koma) as TextView
        val bnian = themer.get_view(convertView,R.id.item_salproj_bnian) as TextView
        val percentexc = themer.get_view(convertView,R.id.item_salproj_percentexc) as TextView


        all_views.addElement(id)
        all_views.addElement(dataaraeid)
        all_views.addElement(startdate)
        all_views.addElement(enddate)
        all_views.addElement(isfinished)
        all_views.addElement(siumbpoal)
        all_views.addElement(notes)
        all_views.addElement(koma)
        all_views.addElement(bnian)
        all_views.addElement(percentexc)


        id.text = (salprojluz_data.get_projid()?:"").trim()

        dataaraeid.text = (salprojluz_data.get_dataaraeid()?:"").trim()

        startdate.text = stringToDateString(salprojluz_data.get_startdate()?:"").trim()
        enddate.text = stringToDateString(salprojluz_data.get_finishdate()?:"").trim()
        isfinished.text = if (salprojluz_data.get_isfinished())
            "כן"
        else
            "לא"
        siumbpoal.text = stringToDateString(salprojluz_data.get_siumbpoal()?:"").trim()
        notes.text = (salprojluz_data.get_notes()?:"").trim()
        koma.text = (salprojluz_data.get_koma()?:"").trim()
        bnian.text = (salprojluz_data.get_building()?:"").trim()
        percentexc.text = (salprojluz_data.get_percentexc()?:"0").trim()+"%"

        themer.center_all_views(all_views)

        return convertView


    }
    private fun stringToDateString(string: String) : String
    {
        return string
//        val d = Date(string.toLong())
//        return d.toString()
    }
}