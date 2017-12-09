package com.example.chaosruler.msa_manager.activies.testing_do_all_table_activities

import android.app.Activity
import android.os.Bundle
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_opr_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.opr_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_table_peolot_edit.*
import java.util.*

class table_peolot_edit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_peolot_edit)
        if(!global_variables_dataclass.GUI_MODE && !init_table())
            finish()
    }


    private fun init_table():Boolean
    {
        var arr: Vector<opr_data> =
                if (global_variables_dataclass.GUI_MODE)
                    Vector<opr_data>()
                else if (!global_variables_dataclass.GUI_MODE && global_variables_dataclass.isLocal)
                    global_variables_dataclass.DB_OPR!!.get_local_DB_by_projname(global_variables_dataclass.projid)
                else
                    global_variables_dataclass.DB_OPR!!.server_data_to_vector_by_projname(global_variables_dataclass.projid)

        for (item in arr)
        {
            var row = TableRow(baseContext)

            row.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT,1.0f)
            row.layoutDirection = TableRow.LAYOUT_DIRECTION_RTL


            var all_views = Vector<View>()

            val peolot_data: opr_data = item

            var id = themer.get_textview(baseContext)
            var dataaraeid = themer.get_textview(baseContext)
            var name = themer.get_edittext(baseContext)

            all_views.addElement(id)
            all_views.addElement(dataaraeid)
            all_views.addElement(name)

            id.text = (peolot_data.get_oprid()?:"").trim()

            dataaraeid.text = (peolot_data.get_DATAREAID()?:"").trim()

            name.hint = (peolot_data.get_opr_name()?:"").trim()


            name.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if(hasFocus || name.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = name.text.toString()
                Thread({
                    Looper.prepare()
                    var update_value: HashMap<String, String> = HashMap()
                    update_value[remote_opr_table_helper.NAME] = str
                    remote_opr_table_helper.push_update(peolot_data, update_value, baseContext)
                    peolot_data.set_oprname(str)
                    global_variables_dataclass.DB_OPR!!.add_opr(peolot_data)
                    themer.hideKeyboard(baseContext,name)
                }).start()
                name.hint = str.trim()
                name.text.clear()
            }

            for(view in all_views)
                row.addView(view)

            //center_all_views(all_views)
            themer.fix_size(baseContext,all_views)

            table_chooser_table_opr.addView(row)

        }
        return true
    }


}
