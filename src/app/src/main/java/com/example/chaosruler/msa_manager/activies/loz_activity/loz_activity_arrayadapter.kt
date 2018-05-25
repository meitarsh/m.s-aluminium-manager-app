package com.example.chaosruler.msa_manager.activies.loz_activity

import android.content.Context
import android.os.Looper
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_big_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.object_types.big_table_data
import com.example.chaosruler.msa_manager.object_types.salprojluz_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import java.text.SimpleDateFormat
import java.util.*

/**
 * Array adapter responsible for populating the listview of table for loz activity
 * @author Chaosruler972
 * @constructor the context and the list of data that we want to populate
 */
class loz_activity_arrayadapter (context: Context, arr: Vector<salprojluz_data>) : ArrayAdapter<salprojluz_data>(context, R.layout.item_loz,arr.toTypedArray())
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
        val convertView: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_loz, parent, false)

        val bnian = themer.get_view(convertView, R.id.item_loz_bnian) as EditText
        val koma = themer.get_view(convertView, R.id.item_loz_koma) as EditText
        val begin = themer.get_view(convertView, R.id.item_loz_begin) as EditText
        val finish = themer.get_view(convertView, R.id.item_loz_end) as EditText
        val ahoz_bizoaa = themer.get_view(convertView, R.id.item_loz_ahoz_bizoaa) as EditText
        val isFinished = themer.get_view(convertView, R.id.item_loz_isfinished) as CheckBox
        val haarot = themer.get_view(convertView, R.id.item_loz_haarot) as EditText

        val all_views = Vector<View>()
        all_views.add(bnian)
        all_views.add(koma)
        all_views.add(begin)
        all_views.add(finish)
        all_views.add(ahoz_bizoaa)
        all_views.add(isFinished)
        all_views.add(haarot)


        val salprojluz_data:salprojluz_data = getItem(position)



        bnian.hint = (salprojluz_data.get_building() ?: "").trim()
        bnian.isEnabled = false
        koma.hint = (salprojluz_data.get_koma() ?:  "").trim()
        koma.isEnabled = false
        begin.hint = (stringToDateString(salprojluz_data.get_startdate()?:"0")).trim()
        begin.isEnabled = false
        finish.hint = (stringToDateString(salprojluz_data.get_finishdate()?:"0")).trim()
        finish.isEnabled = false

        isFinished.isEnabled = salprojluz_data.get_isfinished()
        isFinished.isActivated = false

        ahoz_bizoaa.hint = (salprojluz_data.get_percentexc()?:"0").trim()+"%"
        ahoz_bizoaa.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
        ahoz_bizoaa.isEnabled = false
        haarot.hint = (salprojluz_data.get_notes()?:"").trim()
        haarot.isEnabled = false


        themer.center_all_views(all_views)

        return convertView


    }

    private fun stringToDateString(string: String) : String
    {
        val d = Date(string.toLong())
        val fmtOut = SimpleDateFormat("dd-MM-yyyy")
        return fmtOut.format(d)
    }
}