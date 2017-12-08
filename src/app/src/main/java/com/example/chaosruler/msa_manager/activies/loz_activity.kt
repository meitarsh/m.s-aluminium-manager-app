package com.example.chaosruler.msa_manager.activies

import android.app.Activity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TableRow
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_big_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.*
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_loz_activity.*
import java.util.*



class loz_activity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loz_activity)
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

        for (item in arr)
        {
            var row = TableRow(baseContext)
            row.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT)
            row.layoutDirection = TableRow.LAYOUT_DIRECTION_RTL

            var bnian = get_edittext()
            var koma = get_edittext()
            var begin = get_edittext()
            var finish = get_edittext()
            var ahoz_bizoaa = get_edittext()
            var isFinished = get_checkbox()
            var haarot = get_edittext()

            var all_views = Vector<View>()
            all_views.add(bnian)
            all_views.add(koma)
            all_views.add(begin)
            all_views.add(finish)
            all_views.add(ahoz_bizoaa)
            all_views.add(isFinished)
            all_views.add(haarot)

            val big_item:big_table_data = item

            var ahoz_bizoa_str:String = big_item.get_MILESTONEPERCENT()?:0.toString()


            bnian.hint = (big_item.get_FLAT() ?: "").trim()
            koma.hint = (big_item.get_FLOOR() ?:  "").trim()
            begin.hint = ("No database value to grab").trim()
            begin.isEnabled = false
            finish.hint = "No database value to grab"
            finish.isEnabled = false

            isFinished.isEnabled = false
            ahoz_bizoaa.hint = (ahoz_bizoa_str + "%").trim()
            ahoz_bizoaa.setInputType(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD)
            haarot.hint = "No database value to grab"
            haarot.isEnabled = false

            bnian.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if(hasFocus || bnian.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = bnian.text.toString()
                var update_value:HashMap<String,String> = HashMap()
                update_value[remote_big_table_helper.FLAT] = str
                remote_big_table_helper.push_update(big_item,update_value,baseContext)
                bnian.hint = str.trim()
                bnian.text.clear()
                big_item.set_FLAT(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
            }

            koma.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if(hasFocus || koma.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = koma.text.toString()
                var update_value:HashMap<String,String> = HashMap()
                update_value[remote_big_table_helper.FLOOR] = str
                remote_big_table_helper.push_update(big_item,update_value,baseContext)
                koma.hint = str.trim()
                koma.text.clear()
                big_item.set_FLOOR(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
            }

            ahoz_bizoaa.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if(hasFocus || ahoz_bizoaa.text.isEmpty())
                    return@OnFocusChangeListener
                var str = ahoz_bizoaa.text.toString()
                Log.d("loz  ","mshoar str is : " + str)
                var update_value:HashMap<String,String> = HashMap()
                update_value[remote_big_table_helper.MILESTONEPERCENT] = str
                remote_big_table_helper.push_update(big_item,update_value,baseContext)
                big_item.set_MILESTONEPERCENT(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                Log.d("kbalan_mforat","done")
                ahoz_bizoaa.hint = (str + "%").trim()
                ahoz_bizoaa.text.clear()
            }


            for(view in all_views)
                row.addView(view)
            loz_activity_table.addView(row)
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
               gets a new edit text
        */
    private fun get_edittext(): EditText
    {
        var box = EditText(this)
        // box.layoutParams = ViewGroup.LayoutParams(resources.getDimension(R.dimen.divohi_takalot_horiz_dimen).toInt(),resources.getDimension(R.dimen.divohi_takalot_horiz_dimen).toInt())
        // box.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        var marginnum = resources.getDimension(R.dimen.divohi_takalot_horiz_dimen)
        box.setPadding(marginnum.toInt(),0,marginnum.toInt(),0)
        box.gravity = Gravity.CENTER
        return box
    }
    /*
                   gets a new checkbox
            */
    private fun get_checkbox(): CheckBox
    {
        var box = CheckBox(this)
        // box.layoutParams = ViewGroup.LayoutParams(resources.getDimension(R.dimen.divohi_takalot_horiz_dimen).toInt(),resources.getDimension(R.dimen.divohi_takalot_horiz_dimen).toInt())
        // box.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        var marginnum = resources.getDimension(R.dimen.divohi_takalot_horiz_dimen)
        box.setPadding(marginnum.toInt(),0,marginnum.toInt(),0)
        return box
    }
}
