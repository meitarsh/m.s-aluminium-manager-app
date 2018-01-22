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

/**
 * array adpater responsible for the logic behind the project table listview
 * @author Chaosruler972
 * @constructor the context we work with and the list of data we work with
 */
class table_projects_arrayadapter(context: Context, arr: Vector<project_data>) : ArrayAdapter<project_data>(context, R.layout.item_projects,arr.toTypedArray())
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
        val convertView: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_projects, parent, false)

        val all_views = Vector<View>()

        val proj_daa: project_data = getItem(position)


        val id = themer.get_view(convertView, R.id.item_project_id) as TextView
        val dataaraeid = themer.get_view(convertView, R.id.item_project_dataaraeid) as TextView
        val name = themer.get_view(convertView, R.id.item_project_name) as EditText



        all_views.addElement(id)
        all_views.addElement(dataaraeid)
        all_views.addElement(name)


        id.text = (proj_daa.getProjID()?:"").trim()

        dataaraeid.text = (proj_daa.get_DATAREAID()?:"").trim()

        name.hint = (proj_daa.get_project_name()?:"").trim()


        name.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if(hasFocus || name.text.isEmpty() )
                return@OnFocusChangeListener
            val str = name.text.toString()
            Thread({
                Looper.prepare()
                val update_value: HashMap<String, String> = HashMap()
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