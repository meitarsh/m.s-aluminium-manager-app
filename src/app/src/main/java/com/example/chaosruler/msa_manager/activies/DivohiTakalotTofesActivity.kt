package com.example.chaosruler.msa_manager.activies

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.*
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.*
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.divohi_takalot_tofes.*
import java.util.*

/*
    divohi takalot form activity
 */
class DivohiTakalotTofesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.divohi_takalot_tofes)
        if(!global_variables_dataclass.GUI_MODE && !init_table())
            finish()
    }
    /*
                   inits table
            */
    private fun init_table():Boolean
    {
        var arr: Vector<big_table_data> =
        if (global_variables_dataclass.GUI_MODE || global_variables_dataclass.DB_BIG == null)
            Vector<big_table_data>()
        else if (!global_variables_dataclass.GUI_MODE && global_variables_dataclass.isLocal)
            global_variables_dataclass.DB_BIG!!.get_local_DB_by_projname(global_variables_dataclass.projid)
        else
            global_variables_dataclass.DB_BIG!!.server_data_to_vector_by_projname(global_variables_dataclass.projid)

        for(item in arr)
        {
            var row = TableRow(baseContext)

            row.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT,1.0f)
            row.layoutDirection = TableRow.LAYOUT_DIRECTION_RTL


            var mispar_parit = themer.get_textview(baseContext)
            var shem_parit = themer.get_textview(baseContext)
            var mispar_project = themer.get_textview(baseContext)
            var shem_project = themer.get_textview(baseContext)
            var kamot = themer.get_textview(baseContext)
            var sog_takala = themer.get_textview(baseContext)
            var koma = themer.get_textview(baseContext)
            var bnian = themer.get_textview(baseContext)
            var dira = themer.get_textview(baseContext)
            var tiaor_takala = themer.get_textview(baseContext)
            var peolot_ltikon = themer.get_textview(baseContext)
            var peolot_monoot = themer.get_textview(baseContext)
            var tgovat_mnaal = themer.get_textview(baseContext)
            var alot_takala = themer.get_textview(baseContext)

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

            val big_item:big_table_data = item

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



            for(box in all_txtviews)
            {
                row.addView(box)
            }
            divohi_takalot_tofes_table.addView(row)
            themer.fix_size(baseContext,all_txtviews)

            //center_all_views(all_txtviews)
        }
        return true
    }




}
