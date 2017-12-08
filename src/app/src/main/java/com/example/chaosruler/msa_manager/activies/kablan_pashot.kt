package com.example.chaosruler.msa_manager.activies

import android.app.Activity

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TableRow
import android.widget.TextView
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.*
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_kablan_pashot.*
import java.util.*

class kablan_pashot : Activity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kablan_pashot)
        if(!global_variables_dataclass.GUI_MODE && !init_table())
            finish()
    }

    /*
                   inits table
            */
    private fun init_table():Boolean
    {

        var arr: Vector<big_table_data> =
        if (global_variables_dataclass.GUI_MODE)
            Vector<big_table_data>()
        else if (!global_variables_dataclass.GUI_MODE && global_variables_dataclass.isLocal)
            global_variables_dataclass.DB_BIG!!.get_local_DB_by_projname(global_variables_dataclass.projid)
        else
            global_variables_dataclass.DB_BIG!!.server_data_to_vector_by_projname(global_variables_dataclass.projid)


        for(item in arr)
        {
            var row = TableRow(baseContext)
            row.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT)
            row.layoutDirection = TableRow.LAYOUT_DIRECTION_RTL


            var hoza = get_textview()
            var peola = get_textview()
            var ahoz = get_textview()

            val big_item:big_table_data = item
            val vendor_item: vendor_data = global_variables_dataclass.DB_VENDOR!!.get_vendor_by_id(big_item.get_VENDOR_ID()!!)!!

            hoza.text = (vendor_item.get_accountnum() ?: "").trim()
            peola.text = (vendor_item.get_accountname() ?: "").trim()
            var parcent:String = big_item.get_PERCENTFORACCOUNT()?:0.toString().trim()
            ahoz.text = ((parcent.toDouble()*1).toInt().toString() + "%").trim()

            var all_views = Vector<View>()
            all_views.addElement(hoza)
            all_views.add(peola)
            all_views.add(ahoz)

            for(view in all_views)
                row.addView(view)

            kablan_pashot_table.addView(row)
            center_all_views(all_views)
        }


        return true
    }
    /*
                   centers all views
            */
    private fun center_all_views(vector:Vector<View>)
    {
        for(item in vector)
        {
            (item.layoutParams as TableRow.LayoutParams).gravity = Gravity.CENTER
        }
    }
    /*
                   gets a new textview
            */
    private fun get_textview(): TextView
    {
        var box = TextView(this)
        // box.layoutParams = ViewGroup.LayoutParams(resources.getDimension(R.dimen.divohi_takalot_horiz_dimen).toInt(),resources.getDimension(R.dimen.divohi_takalot_horiz_dimen).toInt())
        // box.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        var marginnum = resources.getDimension(R.dimen.divohi_takalot_horiz_dimen)
        box.setPadding(marginnum.toInt(),0,marginnum.toInt(),0)
        box.gravity = Gravity.CENTER
        return box
    }
}
