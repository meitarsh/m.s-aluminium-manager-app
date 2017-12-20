package com.example.chaosruler.msa_manager.activies.testing_do_all_table_activities.table_projects_edit

import android.content.Context
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_projects_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.object_types.project_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import java.util.*


class table_projects_arrayadapter(context: Context, arr: Vector<project_data>) : ArrayAdapter<project_data>(context, R.layout.item_projects,arr.toTypedArray())
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {

        @Suppress("NAME_SHADOWING")
        var convertView:View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_projects,parent,false)

        var all_views = Vector<View>()

        val proj_daa: project_data = getItem(position)


        var id = themer.get_view(convertView,R.id.item_project_id) as TextView
        var dataaraeid = themer.get_view(convertView,R.id.item_project_dataaraeid) as TextView
        var name = themer.get_view(convertView,R.id.item_project_name) as EditText



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
                remote_projects_table_helper.push_update(proj_daa, update_value, context)
                proj_daa.set_project_name(str)
                global_variables_dataclass.DB_project!!.add_project(proj_daa)
                themer.hideKeyboard(context,name)
            }).start()
            name.hint = str.trim()
            name.text.clear()
        }


        themer.center_all_views(all_views)

        return convertView


    }
}