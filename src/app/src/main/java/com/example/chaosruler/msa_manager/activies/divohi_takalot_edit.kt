package com.example.chaosruler.msa_manager.activies

import android.app.Activity
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
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
import android.content.Intent
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.widget.*
import java.io.File


class divohi_takalot_edit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.divohi_takalot_edit)
        if(!global_variables_dataclass.GUI_MODE && !init_table())
            finish()
    }
    /*
               inits table
        */
    private fun init_table():Boolean
    {
        var arr: Vector<big_table_data> =
                if (global_variables_dataclass.GUI_MODE || global_variables_dataclass.DB_BIG == null)
                    Vector<big_table_data>()
                else if (!global_variables_dataclass.GUI_MODE && global_variables_dataclass.isLocal)
                    global_variables_dataclass.DB_BIG!!.get_local_DB_by_projname(global_variables_dataclass.projid)
                else
                    global_variables_dataclass.DB_BIG!!.server_data_to_vector_by_projname(global_variables_dataclass.projid)

        for(item in arr)
        {
            var row = TableRow(baseContext)

            row.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT,1.0f)
            row.layoutDirection = TableRow.LAYOUT_DIRECTION_RTL


            var mispar_parit = themer.get_edittext(baseContext)
            var shem_parit = themer.get_edittext(baseContext)
            var mispar_project = themer.get_edittext(baseContext)
            var shem_project = themer.get_edittext(baseContext)
            var kamot = themer.get_edittext(baseContext)
            var sog_takala = themer.get_edittext(baseContext)
            var koma = themer.get_edittext(baseContext)
            var bnian = themer.get_edittext(baseContext)
            var dira = themer.get_edittext(baseContext)
            var tiaor_takala = themer.get_edittext(baseContext)
            var peolot_ltikon = themer.get_edittext(baseContext)
            var peolot_monoot = themer.get_edittext(baseContext)
            var tgovat_mnaal = themer.get_edittext(baseContext)
            var alot_takala = themer.get_edittext(baseContext)
            var upload_btn = themer.get_button(baseContext)

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
            all_txtviews.addElement(upload_btn)

            val big_item: big_table_data = item
            val project_item: project_data = global_variables_dataclass.DB_project!!.get_project_by_id(big_item.get_PROJECT_ID()?:"")!!
            val inventory: inventory_data = global_variables_dataclass.DB_INVENTORY!!.get_inventory_by_id(big_item.get_INVENTORY_ID()?:"")!!

            mispar_parit.hint = (big_item.get_ITEMNUMBER() ?: "").trim()
            mispar_parit.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
            shem_parit.hint = (inventory.get_itemname() ?: "").trim()
            mispar_project.hint = (project_item.getProjID() ?: "").trim()
            mispar_project.isEnabled = false
            shem_project.hint = (project_item.get_project_name() ?: "").trim()
            kamot.hint = (big_item.get_QTY() ?: "").trim()
            kamot.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
            sog_takala.hint = "No value from database"
            sog_takala.isEnabled = false
            koma.hint = (big_item.get_FLOOR() ?: "").trim()
            bnian.hint = (big_item.get_FLAT() ?: "").trim()
            dira.hint = (big_item.get_DIRANUM() ?: "").trim()
            dira.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
            tiaor_takala.hint = "No value from database"
            tiaor_takala.isEnabled = false
            peolot_ltikon.hint = "No value from database"
            peolot_ltikon.isEnabled = false
            peolot_monoot.hint = "No value from database"
            peolot_monoot.isEnabled = false
            tgovat_mnaal.hint = "No value from database"
            tgovat_mnaal.isEnabled = false
            alot_takala.hint = (big_item.get_TOTALSUM() ?: "").trim()
            alot_takala.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED

          //  upload_btn.text= getString(R.string.divohi_takalot_upload)
            upload_btn.background = getDrawable(R.drawable.filesend)

            mispar_parit.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if(hasFocus || mispar_parit.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = mispar_parit.text.toString()
                Thread({
                    Looper.prepare()
                    var update_value: HashMap<String, String> = HashMap()
                    update_value[remote_big_table_helper.ITEMNUMBER] = str
                    remote_big_table_helper.push_update(big_item, update_value, baseContext)
                    big_item.set_FLAT(str)
                    global_variables_dataclass.DB_BIG!!.add_big(big_item)
                    themer.hideKeyboard(baseContext,mispar_parit)
                }).start()
                mispar_parit.hint = str.trim()
                mispar_parit.text.clear()
            }

            shem_parit.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if(hasFocus || shem_parit.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = shem_parit.text.toString()
                Thread({
                    Looper.prepare()
                    var update_value: HashMap<String, String> = HashMap()
                    update_value[remote_inventory_table_helper.NAME] = str
                    remote_inventory_table_helper.push_update(inventory, update_value, baseContext)
                    inventory.set_itemname(str)
                    global_variables_dataclass.DB_INVENTORY!!.add_inventory(inventory)
                    themer.hideKeyboard(baseContext,shem_parit)
                }).start()
                shem_parit.hint = str.trim()
                shem_parit.text.clear()
            }

            shem_project.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if(hasFocus || shem_project.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = shem_project.text.toString()
                Thread({
                    Looper.prepare()
                    var update_value: HashMap<String, String> = HashMap()
                    update_value[remote_projects_table_helper.NAME] = str
                    remote_projects_table_helper.push_update(project_item, update_value, baseContext)
                    project_item.set_project_name(str)
                    global_variables_dataclass.DB_project!!.add_project(project_item)
                    themer.hideKeyboard(baseContext,shem_project)
                }).start()
                shem_project.hint = str.trim()
                shem_project.text.clear()
            }

            kamot.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if(hasFocus || kamot.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = kamot.text.toString()
                Thread({
                    Looper.prepare()
                    var update_value: HashMap<String, String> = HashMap()
                    update_value[remote_big_table_helper.QTY] = str
                    remote_big_table_helper.push_update(big_item, update_value, baseContext)
                    big_item.set_QTY(str)
                    global_variables_dataclass.DB_BIG!!.add_big(big_item)
                    themer.hideKeyboard(baseContext,kamot)
                }).start()
                kamot.hint = str.trim()
                kamot.text.clear()
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

            dira.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if(hasFocus || dira.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = dira.text.toString()
                Thread({
                    Looper.prepare()
                    var update_value: HashMap<String, String> = HashMap()
                    update_value[remote_big_table_helper.DIRANUM] = str
                    remote_big_table_helper.push_update(big_item, update_value, baseContext)
                    big_item.set_DIRANUM(str)
                    global_variables_dataclass.DB_BIG!!.add_big(big_item)
                    themer.hideKeyboard(baseContext,dira)
                }).start()
                dira.hint = str.trim()
                dira.text.clear()
            }

            alot_takala.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if(hasFocus || alot_takala.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = alot_takala.text.toString()
                Thread({
                    Looper.prepare()
                    var update_value: HashMap<String, String> = HashMap()
                    update_value[remote_big_table_helper.TOTALSUM] = str
                    remote_big_table_helper.push_update(big_item, update_value, baseContext)
                    big_item.set_TOTALSUM(str)
                    global_variables_dataclass.DB_BIG!!.add_big(big_item)
                    themer.hideKeyboard(baseContext,alot_takala)
                }).start()
                alot_takala.hint = str.trim()
                alot_takala.text.clear()
            }

            upload_btn.setOnClickListener({
                var index = divohi_takalot_tofes_table.indexOfChild(row)
                if(index==-1)
                    return@setOnClickListener
                showFileChooser(index)
            })

            for(box in all_txtviews)
            {
                row.addView(box)
            }
            divohi_takalot_tofes_table.addView(row)
            themer.fix_size(baseContext,all_txtviews)

           // center_all_views(all_txtviews)
        }

        return true
    }


    /*
        shows the file chooser
     */

    private fun showFileChooser(code:Int)
    {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)

        try
        {
            startActivityForResult(
                    Intent.createChooser(intent, getString(R.string.divohi_takalot_choose_file)),
                    code)
        } catch (ex: android.content.ActivityNotFoundException)
        {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, getString(R.string.divohi_takalot_please_install),
                    Toast.LENGTH_SHORT).show()
        }

    }

    /*
        get the file
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent)
    {
       if(resultCode == Activity.RESULT_OK)
       {
                // Get the Uri of the selected file
                val uri = data.data
                // Get the path
                var path_text = themer.get_textview(baseContext)
                var row = divohi_takalot_tofes_table.getChildAt(requestCode) as TableRow
                var btn = row.getChildAt(row.childCount-1) as Button
                btn.isEnabled = false
                btn.visibility = View.GONE
                path_text.text = File(uri.path).absolutePath
                row.addView(path_text)
                row.refreshDrawableState()
           // Get the file instance
                // File file = new File(path);
                // Initiate the upload
       }
        super.onActivityResult(requestCode, resultCode, data)
    }


}
