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
import android.content.ContentValues.TAG
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.view.ViewGroup
import android.widget.*
import java.io.File
import java.net.URISyntaxException


class divohi_takalot_edit : Activity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.divohi_takalot_tofes)
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
            row.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT)
            row.layoutDirection = TableRow.LAYOUT_DIRECTION_RTL
            var mispar_parit = get_editext()
            var shem_parit = get_editext()
            var mispar_project = get_editext()
            var shem_project = get_editext()
            var kamot = get_editext()
            var sog_takala = get_editext()
            var koma = get_editext()
            var bnian = get_editext()
            var dira = get_editext()
            var tiaor_takala = get_editext()
            var peolot_ltikon = get_editext()
            var peolot_monoot = get_editext()
            var tgovat_mnaal = get_editext()
            var alot_takala = get_editext()
            var upload_btn = get_button()

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
                var update_value:HashMap<String,String> = HashMap()
                update_value[remote_big_table_helper.ITEMNUMBER] = str
                remote_big_table_helper.push_update(big_item,update_value,baseContext)
                mispar_parit.hint = str.trim()
                mispar_parit.text.clear()
                big_item.set_FLAT(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                hideKeyboard(mispar_parit)
            }

            shem_parit.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
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
                hideKeyboard(shem_parit)
            }

            shem_project.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
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
                hideKeyboard(shem_project)
            }

            kamot.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
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
                hideKeyboard(kamot)
            }


            koma.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
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
                hideKeyboard(koma)
            }

            bnian.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
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
                hideKeyboard(bnian)
            }

            dira.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
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
                hideKeyboard(dira)
            }

            alot_takala.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
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
                hideKeyboard(alot_takala)
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

            center_all_views(all_txtviews)
        }
        return true
    }

    /*
            center all views
     */
    private fun center_all_views(vector: Vector<View>)
    {
        for(item in vector)
        {
            (item.layoutParams as TableRow.LayoutParams).gravity = Gravity.CENTER
        }
    }

    /*
              gets box
       */
    private fun get_button(): Button
    {
        var box = Button(this)
        // box.layoutParams = ViewGroup.LayoutParams(resources.getDimension(R.dimen.divohi_takalot_horiz_dimen).toInt(),resources.getDimension(R.dimen.divohi_takalot_horiz_dimen).toInt())
        var marginnum = resources.getDimension(R.dimen.divohi_takalot_horiz_dimen)
        box.setPadding(marginnum.toInt(),0,marginnum.toInt(),0)
        box.gravity = Gravity.CENTER
        return box
    }
    /*
              gets box
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
               gets box
        */
    private fun get_editext(): EditText
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
           hides softkeyboard from specific view
        */
    fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager!!.hideSoftInputFromWindow(view.windowToken, 0)
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
                var path_text = get_textview()
                var row = divohi_takalot_tofes_table.getChildAt(requestCode) as TableRow
                var btn = row.getChildAt(row.childCount-1) as Button
                btn.isEnabled = false
                btn.visibility = View.GONE
                path_text.text = File(uri.path).absolutePath
                row.addView(path_text)
                row.refreshDrawableState()
                (path_text.layoutParams as TableRow.LayoutParams).gravity = Gravity.CENTER
                // Get the file instance
                // File file = new File(path);
                // Initiate the upload
       }
        super.onActivityResult(requestCode, resultCode, data)
    }


}
