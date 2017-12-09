package com.example.chaosruler.msa_manager.activies


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.*
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_kablan_pashot.*
import java.util.*

class kablan_pashot : AppCompatActivity()
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

            row.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT,1.0f)
            row.layoutDirection = TableRow.LAYOUT_DIRECTION_RTL

            var hoza = themer.get_textview(baseContext)
            var peola = themer.get_textview(baseContext)
            var ahoz = themer.get_textview(baseContext)

            val big_item:big_table_data = item
            val vendor_item: vendor_data = global_variables_dataclass.DB_VENDOR!!.get_vendor_by_id(big_item.get_VENDOR_ID()!!)!!

            hoza.text = (vendor_item.get_accountnum() ?: "").trim()
            peola.text = (vendor_item.get_accountname() ?: "").trim()
            var parcent:String = big_item.get_PERCENTFORACCOUNT()?:0.toString().trim()
            ahoz.text = ((parcent.toDouble()*1).toInt().toString() + "%").trim()
            ahoz.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
            var all_views = Vector<View>()
            all_views.addElement(hoza)
            all_views.add(peola)
            all_views.add(ahoz)


            for(view in all_views)
                row.addView(view)
            kablan_pashot_table.addView(row)
            themer.fix_size(baseContext,all_views)
        }

        return true
    }


}
