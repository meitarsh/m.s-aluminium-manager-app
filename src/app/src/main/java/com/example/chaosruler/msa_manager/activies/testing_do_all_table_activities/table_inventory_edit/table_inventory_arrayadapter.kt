package com.example.chaosruler.msa_manager.activies.testing_do_all_table_activities.table_inventory_edit

import android.content.Context
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_inventory_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.object_types.inventory_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import java.util.*


class table_inventory_arrayadapter(context: Context, arr: Vector<inventory_data>) : ArrayAdapter<inventory_data>(context, R.layout.item_inventory,arr.toTypedArray())
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {

        @Suppress("NAME_SHADOWING")
        var convertView:View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_inventory,parent,false)

        val inventory: inventory_data = getItem(position)
        var all_views = Vector<View>()

        var id = themer.get_view(convertView,R.id.item_inventory_id) as TextView
        var dataaraeid = themer.get_view(convertView,R.id.item_inventory_dataaraeid) as TextView
        var name = themer.get_view(convertView,R.id.item_inventory_name) as EditText



        all_views.addElement(id)
        all_views.addElement(dataaraeid)
        all_views.addElement(name)

        id.text = (inventory.get_itemid()?:"").trim()

        dataaraeid.text = (inventory.get_DATAREAID()?:"").trim()

        name.hint = (inventory.get_itemname()?:"").trim()


        name.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if(hasFocus || name.text.isEmpty() )
                return@OnFocusChangeListener
            var str = name.text.toString()
            Thread({
                Looper.prepare()
                var update_value: HashMap<String, String> = HashMap()
                update_value[remote_inventory_table_helper.NAME] = str
                remote_inventory_table_helper.push_update(inventory, update_value, context)
                inventory.set_itemname(str)
                global_variables_dataclass.DB_INVENTORY!!.add_inventory(inventory)
                themer.hideKeyboard(context,name)
            }).start()
            name.hint = str.trim()
            name.text.clear()
        }

        themer.center_all_views(all_views)

        return convertView


    }
}