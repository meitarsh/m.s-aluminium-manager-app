package com.example.chaosruler.msa_manager.activies

import android.app.Activity
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.TableRow
import android.widget.TextView
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_big_table_helper
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_inventory_table_helper
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_projects_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.big_table_data
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.inventory_data
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.project_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.divohi_takalot_tofes.*
import java.util.*

class divohi_takalot_edit : Activity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.divohi_takalot_tofes)
        if(!global_variables_dataclass.GUI_MODE && !init_table())
            finish()
    }

    private fun init_table():Boolean
    {
        var arr: Vector<big_table_data> =
                if (global_variables_dataclass.GUI_MODE || global_variables_dataclass.DB_BIG == null)
                    Vector<big_table_data>()
                else if (!global_variables_dataclass.GUI_MODE && global_variables_dataclass.isLocal)
                    global_variables_dataclass.DB_BIG!!.get_local_DB_by_projname(global_variables_dataclass.projid,global_variables_dataclass.DB_project!!)
                else
                    global_variables_dataclass.DB_BIG!!.server_data_to_vector_by_projname(global_variables_dataclass.projid,global_variables_dataclass.DB_project!!)

        for(item in arr)
        {
            var row = TableRow(baseContext)
            row.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT)
            row.layoutDirection = TableRow.LAYOUT_DIRECTION_RTL
            var mispar_parit = get_box()
            var shem_parit = get_box()
            var mispar_project = get_box()
            var shem_project = get_box()
            var kamot = get_box()
            var sog_takala = get_box()
            var koma = get_box()
            var bnian = get_box()
            var dira = get_box()
            var tiaor_takala = get_box()
            var peolot_ltikon = get_box()
            var peolot_monoot = get_box()
            var tgovat_mnaal = get_box()
            var alot_takala = get_box()

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

            val big_item: big_table_data = item
            val project_item: project_data = global_variables_dataclass.DB_project!!.get_project_by_id(big_item.get_PROJECT_ID()!!)!!
            val inventory: inventory_data = global_variables_dataclass.DB_INVENTORY!!.get_inventory_by_id(big_item.get_INVENTORY_ID()!!)!!

            mispar_parit.hint = (big_item.get_ITEMNUMBER() ?: "").trim()
            mispar_parit.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
            shem_parit.hint = (inventory.get_itemname() ?: "").trim()
            mispar_project.hint = (project_item.getProjID() ?: "").trim()
            mispar_project.isEnabled = false
            shem_project.hint = (project_item.get_project_name() ?: "").trim()
            kamot.hint = (big_item.get_QTY() ?: "").trim()
            kamot.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
            sog_takala.hint = "No value from database"
            sog_takala.isEnabled = false
            koma.hint = (big_item.get_FLOOR() ?: "").trim()
            bnian.hint = (big_item.get_FLAT() ?: "").trim()
            dira.hint = (big_item.get_DIRANUM() ?: "").trim()
            dira.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
            tiaor_takala.hint = "No value from database"
            tiaor_takala.isEnabled = false
            peolot_ltikon.hint = "No value from database"
            peolot_ltikon.isEnabled = false
            peolot_monoot.hint = "No value from database"
            peolot_monoot.isEnabled = false
            tgovat_mnaal.hint = "No value from database"
            tgovat_mnaal.isEnabled = false
            alot_takala.hint = (big_item.get_TOTALSUM() ?: "").trim()
            alot_takala.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD


            mispar_parit.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if(hasFocus || mispar_parit.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = mispar_parit.text.toString()
                var update_value:HashMap<String,String> = HashMap()
                update_value[remote_big_table_helper.ITEMNUMBER] = str
                remote_big_table_helper.push_update(big_item,update_value,baseContext)
                mispar_parit.hint = str.trim()
                mispar_parit.text.clear()
                big_item.set_FLAT(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
            }

            shem_parit.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if(hasFocus || shem_parit.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = shem_parit.text.toString()
                var update_value:HashMap<String,String> = HashMap()
                update_value[remote_inventory_table_helper.NAME] = str
                remote_inventory_table_helper.push_update(inventory,update_value,baseContext)
                shem_parit.hint = str.trim()
                shem_parit.text.clear()
                inventory.set_itemname(str)
                global_variables_dataclass.DB_INVENTORY!!.add_inventory(inventory)
            }

            shem_project.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if(hasFocus || shem_project.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = shem_project.text.toString()
                var update_value:HashMap<String,String> = HashMap()
                update_value[remote_projects_table_helper.NAME] = str
                remote_projects_table_helper.push_update(project_item,update_value,baseContext)
                shem_project.hint = str.trim()
                shem_project.text.clear()
                project_item.set_project_name(str)
                global_variables_dataclass.DB_project!!.add_project(project_item)
            }

            kamot.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if(hasFocus || kamot.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = kamot.text.toString()
                var update_value:HashMap<String,String> = HashMap()
                update_value[remote_big_table_helper.QTY] = str
                remote_big_table_helper.push_update(big_item,update_value,baseContext)
                kamot.hint = str.trim()
                kamot.text.clear()
                big_item.set_QTY(str)
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

            dira.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if(hasFocus || dira.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = dira.text.toString()
                var update_value:HashMap<String,String> = HashMap()
                update_value[remote_big_table_helper.DIRANUM] = str
                remote_big_table_helper.push_update(big_item,update_value,baseContext)
                dira.hint = str.trim()
                dira.text.clear()
                big_item.set_DIRANUM(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
            }

            alot_takala.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if(hasFocus || alot_takala.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = alot_takala.text.toString()
                var update_value:HashMap<String,String> = HashMap()
                update_value[remote_big_table_helper.TOTALSUM] = str
                remote_big_table_helper.push_update(big_item,update_value,baseContext)
                alot_takala.hint = str.trim()
                alot_takala.text.clear()
                big_item.set_TOTALSUM(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
            }

            for(box in all_txtviews)
            {
                row.addView(box)
            }
            divohi_takalot_tofes_table.addView(row)

            center_all_views(all_txtviews)
        }
        return true
    }


    private fun center_all_views(vector: Vector<View>)
    {
        for(item in vector)
        {
            (item.layoutParams as TableRow.LayoutParams).gravity = Gravity.CENTER
        }
    }

    private fun get_box(): EditText
    {
        var box = EditText(this)
        // box.layoutParams = ViewGroup.LayoutParams(resources.getDimension(R.dimen.divohi_takalot_horiz_dimen).toInt(),resources.getDimension(R.dimen.divohi_takalot_horiz_dimen).toInt())
        // box.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        var marginnum = resources.getDimension(R.dimen.divohi_takalot_horiz_dimen)
        box.setPadding(marginnum.toInt(),0,marginnum.toInt(),0)
        box.gravity = Gravity.CENTER
        return box
    }
}
