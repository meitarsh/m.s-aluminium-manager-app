package com.example.chaosruler.msa_manager.activies.testing_do_all_table_activities

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_projects_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.project_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_table_projects_edit.*
import java.util.*

class table_projects_edit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_projects_edit)
        if(!global_variables_dataclass.GUI_MODE && !init_table())
            finish()
    }

    private fun init_table():Boolean
    {
        var arr: Vector<project_data> =
                if (global_variables_dataclass.GUI_MODE)
                    Vector<project_data>()
                else if (!global_variables_dataclass.GUI_MODE && global_variables_dataclass.isLocal)
                    global_variables_dataclass.DB_project!!.get_local_DB_by_projname(global_variables_dataclass.projid)
                else
                    global_variables_dataclass.DB_project!!.server_data_to_vector_by_projid(global_variables_dataclass.projid)

        for (item in arr)
        {
            var row = TableRow(baseContext)

            row.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT,1.0f)
            row.layoutDirection = TableRow.LAYOUT_DIRECTION_RTL


            var all_views = Vector<View>()

            val proj_daa: project_data = item

            var id = themer.get_textview(baseContext)
            var dataaraeid = themer.get_textview(baseContext)
            var name = themer.get_edittext(baseContext)

            all_views.addElement(id)
            all_views.addElement(dataaraeid)
            all_views.addElement(name)

            id.text = (proj_daa.getProjID()?:"").trim()

            dataaraeid.text = (proj_daa.get_DATAREAID()?:"").trim()

           name.hint = (proj_daa.get_project_name()?:"").trim()


            name.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if(hasFocus || name.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = name.text.toString()
                Thread({
                    Looper.prepare()
                    var update_value: HashMap<String, String> = HashMap()
                    update_value[remote_projects_table_helper.NAME] = str
                    remote_projects_table_helper.push_update(proj_daa, update_value, baseContext)
                    proj_daa.set_project_name(str)
                    global_variables_dataclass.DB_project!!.add_project(proj_daa)
                    themer.hideKeyboard(baseContext,name)
                }).start()
                name.hint = str.trim()
                name.text.clear()
            }

            for(view in all_views)
                row.addView(view)
            table_chooser_project_table.addView(row)
            themer.fix_size(baseContext,all_views)

           // center_all_views(all_views)


        }
        return true
    }

}
