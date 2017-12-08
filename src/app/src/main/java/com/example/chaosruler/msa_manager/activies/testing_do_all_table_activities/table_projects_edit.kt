package com.example.chaosruler.msa_manager.activies.testing_do_all_table_activities

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TableRow
import android.widget.TextView
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
            row.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT)
            row.layoutDirection = TableRow.LAYOUT_DIRECTION_RTL


            var all_views = Vector<View>()

            val proj_daa: project_data = item

            var id = get_textview()
            var dataaraeid = get_textview()
            var name = get_edittext()

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
                var update_value: HashMap<String, String> = HashMap()
                update_value[remote_projects_table_helper.NAME] = str
                remote_projects_table_helper.push_update(proj_daa,update_value,baseContext)
                name.hint = str.trim()
                name.text.clear()
                proj_daa.set_project_name(str)
                global_variables_dataclass.DB_project!!.add_project(proj_daa)
                hideKeyboard(name)
            }

            for(view in all_views)
                row.addView(view)
            table_chooser_project_table.addView(row)
            center_all_views(all_views)


        }
        return true
    }


    /*
                      centers all views
               */
    private fun center_all_views(vector: Vector<View>)
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
                 gets textview
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
    /*
           hides softkeyboard from specific view
        */
    fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager!!.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
