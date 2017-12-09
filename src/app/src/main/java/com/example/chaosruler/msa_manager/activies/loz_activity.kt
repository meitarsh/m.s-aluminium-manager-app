package com.example.chaosruler.msa_manager.activies

import android.app.Activity
import android.os.Bundle
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_big_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.*
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_loz_activity.*
import java.util.*


class loz_activity : AppCompatActivity() {

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

            row.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT,1.0f)
            row.layoutDirection = TableRow.LAYOUT_DIRECTION_RTL

            loz_activity_table.addView(row)
            var bnian = themer.get_edittext(baseContext)
            var koma = themer.get_edittext(baseContext)
            var begin = themer.get_edittext(baseContext)
            var finish = themer.get_edittext(baseContext)
            var ahoz_bizoaa = themer.get_edittext(baseContext)
            var isFinished = themer.get_checkbox(baseContext)
            var haarot = themer.get_edittext(baseContext)

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
            ahoz_bizoaa.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
            haarot.hint = "No database value to grab"
            haarot.isEnabled = false

            bnian.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if(hasFocus || bnian.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = bnian.text.toString()
                Thread({
                    Looper.prepare()
                    var update_value: HashMap<String, String> = HashMap()
                    update_value[remote_big_table_helper.FLAT] = str
                    remote_big_table_helper.push_update(big_item, update_value, baseContext)
                    big_item.set_FLAT(str)
                    global_variables_dataclass.DB_BIG!!.add_big(big_item)
                    themer.hideKeyboard(baseContext,bnian)
                }).start()
                bnian.hint = str.trim()
                bnian.text.clear()
            }

            koma.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if(hasFocus || koma.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = koma.text.toString()
                Thread({
                    Looper.prepare()
                    var update_value: HashMap<String, String> = HashMap()
                    update_value[remote_big_table_helper.FLOOR] = str
                    remote_big_table_helper.push_update(big_item, update_value, baseContext)
                    big_item.set_FLOOR(str)
                    global_variables_dataclass.DB_BIG!!.add_big(big_item)
                    themer.hideKeyboard(baseContext,koma)
                }).start()
                koma.hint = str.trim()
                koma.text.clear()
            }

            ahoz_bizoaa.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if(hasFocus || ahoz_bizoaa.text.isEmpty())
                    return@OnFocusChangeListener
                var str = ahoz_bizoaa.text.toString()
                Thread({
                    Looper.prepare()
                    var update_value: HashMap<String, String> = HashMap()
                    update_value[remote_big_table_helper.MILESTONEPERCENT] = str
                    remote_big_table_helper.push_update(big_item, update_value, baseContext)
                    big_item.set_MILESTONEPERCENT(str)
                    global_variables_dataclass.DB_BIG!!.add_big(big_item)
                    themer.hideKeyboard(baseContext,ahoz_bizoaa)
                }).start()
                ahoz_bizoaa.hint = (str + "%").trim()
                ahoz_bizoaa.text.clear()
            }


            for(view in all_views)
                row.addView(view)
            themer.fix_size(baseContext,all_views)
          //  center_all_views(all_views)
        }
        loz_activity_table.isStretchAllColumns = true
        return true
    }

}
