package com.example.chaosruler.msa_manager.activies.testing_do_all_table_activities

import android.app.Activity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TableRow
import android.widget.TextView
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_big_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.dataclass_for_SQL_representation.big_table_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_table_big_edit.*
import java.util.*

class table_big_edit : Activity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_big_edit)
        if(!global_variables_dataclass.GUI_MODE && !init_table())
            finish()
    }

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
            row.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT)
            row.layoutDirection = TableRow.LAYOUT_DIRECTION_RTL

            var accountnum = get_textview()
            var dataaraeid = get_textview()
            var recversion = get_edittext()
            var recid = get_edittext()
            var projid = get_textview()
            var itemid = get_textview()
            var flat = get_edittext()
            var floor = get_edittext()
            var qty = get_edittext()
            qty.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
            var salesprice = get_edittext()
            salesprice.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
            var oprid = get_textview()
            var milestone_percent =get_edittext()
            milestone_percent.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
            var qtyforaccount = get_edittext()
            qtyforaccount.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
            var percentforaccount = get_edittext()
            percentforaccount.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
            var totalsum = get_edittext()
            totalsum.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
            var salprog = get_edittext()
            salprog.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
            var printorder = get_edittext()
            printorder.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
            var itemnumber = get_edittext()
            itemnumber.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
            var komanum = get_edittext()
            komanum.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
            var diranum = get_edittext()
            diranum.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED

            var all_views = Vector<View>()
            all_views.addElement(accountnum)
            all_views.addElement(dataaraeid)
            all_views.addElement(recversion)
            all_views.addElement(recid)
            all_views.addElement(projid)
            all_views.addElement(itemid)
            all_views.addElement(flat)
            all_views.addElement(floor)
            all_views.addElement(qty)
            all_views.addElement(salesprice)
            all_views.addElement(oprid)
            all_views.addElement(milestone_percent)
            all_views.addElement(qtyforaccount)
            all_views.addElement(percentforaccount)
            all_views.addElement(totalsum)
            all_views.addElement(salprog)
            all_views.addElement(printorder)
            all_views.addElement(itemnumber)
            all_views.addElement(komanum)
            all_views.addElement(diranum)


            val big_item: big_table_data = item

            var milpercent:String = big_item.get_MILESTONEPERCENT()?:0.toString()
            var percentacc:String = big_item.get_PERCENTFORACCOUNT()?:0.toString()


            accountnum.text = (big_item.get_VENDOR_ID()?:"").trim()
            dataaraeid.text = (big_item.get_DATAAREAID()?:"").trim()
            recversion.hint = (big_item.get_RECVERSION()?:"").trim()
            recid.hint = (big_item.get_RECID()?:"").trim()
            projid.text = (big_item.get_PROJECT_ID()?:"").trim()
            itemid.text = (big_item.get_INVENTORY_ID()?:"").trim()
            flat.hint = (big_item.get_FLAT()?:"").trim()
            floor.hint = (big_item.get_FLOOR()?:"").trim()
            qty.hint = (big_item.get_QTY()?:"").trim()
            salesprice.hint = (big_item.get_SALESPRICE()?:"").trim()
            oprid.text = (big_item.get_OPRID()?:"").trim()
            milestone_percent.hint = (big_item.get_MILESTONEPERCENT()?:"0").trim()+"%"
            qtyforaccount.hint = (big_item.get_QTYFORACCOUNT()?:"").trim()
            percentforaccount.hint = (big_item.get_PERCENTFORACCOUNT()?:"0").trim()+"%"
            totalsum.hint = (big_item.get_TOTALSUM()?:"").trim()
            salprog.hint = (big_item.get_SALPROG()?:"").trim()
            printorder.hint = (big_item.get_PRINTORDER()?:"").trim()
            itemnumber.hint = (big_item.get_ITEMNUMBER()?:"").trim()
            komanum.hint = (big_item.get_KOMANUM()?:"").trim()
            diranum.hint = (big_item.get_DIRANUM()?:"").trim()

            recversion.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if(hasFocus || recversion.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = recversion.text.toString()
                var update_value:HashMap<String,String> = HashMap()
                update_value[remote_big_table_helper.RECVERSION] = str
                remote_big_table_helper.push_update(big_item,update_value,baseContext)
                recversion.hint = str.trim()
                recversion.text.clear()
                big_item.set_RECVERSION(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                hideKeyboard(recversion)
            }

            recid.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if(hasFocus || recid.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = recid.text.toString()
                var update_value:HashMap<String,String> = HashMap()
                update_value[remote_big_table_helper.RECID] = str
                remote_big_table_helper.push_update(big_item,update_value,baseContext)
                recid.hint = str.trim()
                recid.text.clear()
                big_item.set_RECID(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                hideKeyboard(recid)
            }

            flat.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if(hasFocus || flat.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = flat.text.toString()
                var update_value:HashMap<String,String> = HashMap()
                update_value[remote_big_table_helper.FLAT] = str
                remote_big_table_helper.push_update(big_item,update_value,baseContext)
                flat.hint = str.trim()
                flat.text.clear()
                big_item.set_FLAT(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                hideKeyboard(flat)
            }

            floor.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if(hasFocus || floor.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = floor.text.toString()
                var update_value:HashMap<String,String> = HashMap()
                update_value[remote_big_table_helper.FLOOR] = str
                remote_big_table_helper.push_update(big_item,update_value,baseContext)
                floor.hint = str.trim()
                floor.text.clear()
                big_item.set_FLOOR(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                hideKeyboard(floor)
            }

            qty.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if(hasFocus || qty.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = qty.text.toString()
                var update_value:HashMap<String,String> = HashMap()
                update_value[remote_big_table_helper.QTY] = str
                remote_big_table_helper.push_update(big_item,update_value,baseContext)
                qty.hint = str.trim()
                qty.text.clear()
                big_item.set_QTY(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                hideKeyboard(qty)
            }

            salesprice.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if(hasFocus || salesprice.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = salesprice.text.toString()
                var update_value:HashMap<String,String> = HashMap()
                update_value[remote_big_table_helper.SALESPRICE] = str
                remote_big_table_helper.push_update(big_item,update_value,baseContext)
                salesprice.hint = str.trim()
                salesprice.text.clear()
                big_item.set_SALESPRICE(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                hideKeyboard(salesprice)
            }



            milestone_percent.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if(hasFocus || milestone_percent.text.isEmpty())
                    return@OnFocusChangeListener
                var str = milestone_percent.text.toString()
                Log.d("loz  ","mshoar str is : " + str)
                var update_value:HashMap<String,String> = HashMap()
                update_value[remote_big_table_helper.MILESTONEPERCENT] = str
                remote_big_table_helper.push_update(big_item,update_value,baseContext)
                big_item.set_MILESTONEPERCENT(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                Log.d("kbalan_mforat","done")
                milestone_percent.hint = (str + "%").trim()
                milestone_percent.text.clear()
                hideKeyboard(milestone_percent)
            }

            qtyforaccount.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if(hasFocus || qtyforaccount.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = qtyforaccount.text.toString()
                var update_value:HashMap<String,String> = HashMap()
                update_value[remote_big_table_helper.QTYFORACCOUNT] = str
                remote_big_table_helper.push_update(big_item,update_value,baseContext)
                qtyforaccount.hint = str.trim()
                qtyforaccount.text.clear()
                big_item.set_QTYFORACCOUNT(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                hideKeyboard(qtyforaccount)
            }

            percentforaccount.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if(hasFocus || percentforaccount.text.isEmpty())
                    return@OnFocusChangeListener
                var str = percentforaccount.text.toString()
                var update_value:HashMap<String,String> = HashMap()
                update_value[remote_big_table_helper.PERCENTFORACCOUNT] = str
                remote_big_table_helper.push_update(big_item,update_value,baseContext)
                big_item.set_PERCENTFORACCOUNT(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                percentforaccount.hint = (str + "%").trim()
                percentforaccount.text.clear()
                hideKeyboard(percentforaccount)
            }

            totalsum.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if(hasFocus || qtyforaccount.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = totalsum.text.toString()
                var update_value:HashMap<String,String> = HashMap()
                update_value[remote_big_table_helper.TOTALSUM] = str
                remote_big_table_helper.push_update(big_item,update_value,baseContext)
                totalsum.hint = str.trim()
                totalsum.text.clear()
                big_item.set_TOTALSUM(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                hideKeyboard(totalsum)
            }


            salprog.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if(hasFocus || salprog.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = salprog.text.toString()
                var update_value:HashMap<String,String> = HashMap()
                update_value[remote_big_table_helper.SALPROG] = str
                remote_big_table_helper.push_update(big_item,update_value,baseContext)
                salprog.hint = str.trim()
                salprog.text.clear()
                big_item.set_SALPROG(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                hideKeyboard(salprog)
            }

            printorder.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if(hasFocus || printorder.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = printorder.text.toString()
                var update_value:HashMap<String,String> = HashMap()
                update_value[remote_big_table_helper.PRINTORDER] = str
                remote_big_table_helper.push_update(big_item,update_value,baseContext)
                printorder.hint = str.trim()
                printorder.text.clear()
                big_item.set_PRINTORDER(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                hideKeyboard(printorder)
            }

            itemnumber.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if(hasFocus || itemnumber.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = itemnumber.text.toString()
                var update_value:HashMap<String,String> = HashMap()
                update_value[remote_big_table_helper.ITEMNUMBER] = str
                remote_big_table_helper.push_update(big_item,update_value,baseContext)
                itemnumber.hint = str.trim()
                itemnumber.text.clear()
                big_item.set_ITEMNUMBER(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                hideKeyboard(itemnumber)
            }

            komanum.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if(hasFocus || komanum.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = komanum.text.toString()
                var update_value:HashMap<String,String> = HashMap()
                update_value[remote_big_table_helper.KOMANUM] = str
                remote_big_table_helper.push_update(big_item,update_value,baseContext)
                komanum.hint = str.trim()
                komanum.text.clear()
                big_item.set_KOMANUM(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                hideKeyboard(komanum)
            }

            diranum.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if(hasFocus || diranum.text.isEmpty() )
                    return@OnFocusChangeListener
                var str = diranum.text.toString()
                var update_value:HashMap<String,String> = HashMap()
                update_value[remote_big_table_helper.DIRANUM] = str
                remote_big_table_helper.push_update(big_item,update_value,baseContext)
                diranum.hint = str.trim()
                diranum.text.clear()
                big_item.set_DIRANUM(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                hideKeyboard(diranum)
            }




            for(view in all_views)
                row.addView(view)
            table_chooser_table_big.addView(row)
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
