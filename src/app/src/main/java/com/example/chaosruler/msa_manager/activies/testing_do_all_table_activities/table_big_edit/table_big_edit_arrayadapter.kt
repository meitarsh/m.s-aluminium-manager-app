package com.example.chaosruler.msa_manager.activies.testing_do_all_table_activities.table_big_edit

import android.content.Context
import android.os.Looper
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_big_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.object_types.big_table_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import java.util.*


class table_big_edit_arrayadapter (context: Context, arr: Vector<big_table_data>) : ArrayAdapter<big_table_data>(context, R.layout.item_big_edit,arr.toTypedArray())
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {

        @Suppress("NAME_SHADOWING")
        val convertView: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_big_edit, parent, false)

        val accountnum = themer.get_view(convertView, R.id.item_big_accountnum) as TextView
        val dataaraeid = themer.get_view(convertView, R.id.item_big_dataaraeid) as TextView
        val recversion = themer.get_view(convertView, R.id.item_big_recversion) as EditText
        val recid = themer.get_view(convertView, R.id.item_big_recid) as EditText
        val projid = themer.get_view(convertView, R.id.item_big_projid) as TextView
        val itemid = themer.get_view(convertView, R.id.item_big_itemid) as TextView
        val flat = themer.get_view(convertView, R.id.item_big_flat) as EditText
        val floor = themer.get_view(convertView, R.id.item_big_floor) as EditText
        val qty = themer.get_view(convertView, R.id.item_big_qty) as EditText
        qty.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
        val salesprice = themer.get_view(convertView, R.id.item_big_salesprice) as EditText
        salesprice.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
        val oprid = themer.get_view(convertView, R.id.item_big_oprid) as TextView
        val milestone_percent = themer.get_view(convertView, R.id.item_big_milestone_percent) as EditText
        milestone_percent.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
        val qtyforaccount = themer.get_view(convertView, R.id.item_big_qtyforaccount) as EditText
        qtyforaccount.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
        val percentforaccount = themer.get_view(convertView, R.id.item_big_percentforaccount) as EditText
        percentforaccount.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
        val totalsum = themer.get_view(convertView, R.id.item_big_totalsum) as EditText
        totalsum.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
        val salprog = themer.get_view(convertView, R.id.item_big_salprog) as EditText
        salprog.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
        val printorder = themer.get_view(convertView, R.id.item_big_printorder) as EditText
        printorder.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
        val itemnumber = themer.get_view(convertView, R.id.item_big_itemnumber) as EditText
        itemnumber.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
        val komanum = themer.get_view(convertView, R.id.item_big_komanum) as EditText
        komanum.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
        val diranum = themer.get_view(convertView, R.id.item_big_diranum) as EditText
        diranum.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED

        val all_views = Vector<View>()
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


        val big_item: big_table_data = getItem(position)

        @Suppress("UNUSED_VARIABLE")
        var milpercent:String = big_item.get_MILESTONEPERCENT()?:0.toString()
        @Suppress("UNUSED_VARIABLE")
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
            val str = recversion.text.toString()
            Thread({
                Looper.prepare()
                val update_value: HashMap<String, String> = HashMap()
                update_value[remote_big_table_helper.RECVERSION] = str
                remote_big_table_helper.push_update(big_item, update_value, context)
                big_item.set_RECVERSION(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                themer.hideKeyboard(context,recversion)
            }).start()

            recversion.hint = str.trim()
            recversion.text.clear()
        }

        recid.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if(hasFocus || recid.text.isEmpty() )
                return@OnFocusChangeListener
            val str = recid.text.toString()
            Thread({
                Looper.prepare()
                val update_value: HashMap<String, String> = HashMap()
                update_value[remote_big_table_helper.RECID] = str
                remote_big_table_helper.push_update(big_item, update_value, context)
                big_item.set_RECID(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                themer.hideKeyboard(context,recid)
            }).start()
            recid.hint = str.trim()
            recid.text.clear()
        }

        flat.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if(hasFocus || flat.text.isEmpty() )
                return@OnFocusChangeListener
            val str = flat.text.toString()
            Thread({
                Looper.prepare()
                val update_value: HashMap<String, String> = HashMap()
                update_value[remote_big_table_helper.FLAT] = str
                remote_big_table_helper.push_update(big_item, update_value, context)
                big_item.set_FLAT(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                themer.hideKeyboard(context,flat)
            }).start()
            flat.hint = str.trim()
            flat.text.clear()
        }

        floor.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus || floor.text.isEmpty())
                return@OnFocusChangeListener
            val str = floor.text.toString()
            Thread({
                Looper.prepare()
                val update_value: HashMap<String, String> = HashMap()
                update_value[remote_big_table_helper.FLOOR] = str
                remote_big_table_helper.push_update(big_item, update_value, context)
                big_item.set_FLOOR(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                themer.hideKeyboard(context,floor)
            }).start()

            floor.hint = str.trim()
            floor.text.clear()
        }

        qty.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if(hasFocus || qty.text.isEmpty() )
                return@OnFocusChangeListener
            val str = qty.text.toString()
            Thread({
                Looper.prepare()
                val update_value: HashMap<String, String> = HashMap()
                update_value[remote_big_table_helper.QTY] = str
                remote_big_table_helper.push_update(big_item, update_value, context)
                big_item.set_QTY(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                themer.hideKeyboard(context,qty)
            }).start()
            qty.hint = str.trim()
            qty.text.clear()
        }

        salesprice.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if(hasFocus || salesprice.text.isEmpty() )
                return@OnFocusChangeListener
            val str = salesprice.text.toString()
            Thread({
                Looper.prepare()
                val update_value: HashMap<String, String> = HashMap()
                update_value[remote_big_table_helper.SALESPRICE] = str
                remote_big_table_helper.push_update(big_item, update_value, context)
                big_item.set_SALESPRICE(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                themer.hideKeyboard(context,salesprice)
            }).start()
            salesprice.hint = str.trim()
            salesprice.text.clear()
        }



        milestone_percent.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if(hasFocus || milestone_percent.text.isEmpty())
                return@OnFocusChangeListener
            val str = milestone_percent.text.toString()
            Thread({
                Looper.prepare()
                val update_value: HashMap<String, String> = HashMap()
                update_value[remote_big_table_helper.MILESTONEPERCENT] = str
                remote_big_table_helper.push_update(big_item, update_value, context)
                big_item.set_MILESTONEPERCENT(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                themer.hideKeyboard(context,milestone_percent)
            }).start()
            milestone_percent.hint = (str + "%").trim()
            milestone_percent.text.clear()
        }

        qtyforaccount.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if(hasFocus || qtyforaccount.text.isEmpty() )
                return@OnFocusChangeListener
            val str = qtyforaccount.text.toString()
            Thread({
                Looper.prepare()
                val update_value: HashMap<String, String> = HashMap()
                update_value[remote_big_table_helper.QTYFORACCOUNT] = str
                remote_big_table_helper.push_update(big_item, update_value, context)
                big_item.set_QTYFORACCOUNT(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                themer.hideKeyboard(context,qtyforaccount)
            }).start()
            qtyforaccount.hint = str.trim()
            qtyforaccount.text.clear()
        }

        percentforaccount.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if(hasFocus || percentforaccount.text.isEmpty())
                return@OnFocusChangeListener
            val str = percentforaccount.text.toString()
            Thread({
                Looper.prepare()
                val update_value: HashMap<String, String> = HashMap()
                update_value[remote_big_table_helper.PERCENTFORACCOUNT] = str
                remote_big_table_helper.push_update(big_item, update_value, context)
                big_item.set_PERCENTFORACCOUNT(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                themer.hideKeyboard(context,percentforaccount)
            }).start()
            percentforaccount.hint = (str + "%").trim()
            percentforaccount.text.clear()
        }

        totalsum.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if(hasFocus || qtyforaccount.text.isEmpty() )
                return@OnFocusChangeListener
            val str = totalsum.text.toString()
            Thread({
                Looper.prepare()
                val update_value: HashMap<String, String> = HashMap()
                update_value[remote_big_table_helper.TOTALSUM] = str
                remote_big_table_helper.push_update(big_item, update_value, context)
                big_item.set_TOTALSUM(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                themer.hideKeyboard(context,totalsum)
            }).start()
            totalsum.hint = str.trim()
            totalsum.text.clear()
        }


        salprog.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if(hasFocus || salprog.text.isEmpty() )
                return@OnFocusChangeListener
            val str = salprog.text.toString()
            Thread({
                Looper.prepare()
                val update_value: HashMap<String, String> = HashMap()
                update_value[remote_big_table_helper.SALPROG] = str
                remote_big_table_helper.push_update(big_item, update_value, context)
                big_item.set_SALPROG(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                themer.hideKeyboard(context,salprog)
            }).start()
            salprog.hint = str.trim()
            salprog.text.clear()
        }

        printorder.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if(hasFocus || printorder.text.isEmpty() )
                return@OnFocusChangeListener
            val str = printorder.text.toString()
            Thread({
                Looper.prepare()
                val update_value: HashMap<String, String> = HashMap()
                update_value[remote_big_table_helper.PRINTORDER] = str
                remote_big_table_helper.push_update(big_item, update_value, context)
                big_item.set_PRINTORDER(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                themer.hideKeyboard(context,printorder)
            }).start()
            printorder.hint = str.trim()
            printorder.text.clear()
        }

        itemnumber.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if(hasFocus || itemnumber.text.isEmpty() )
                return@OnFocusChangeListener
            val str = itemnumber.text.toString()
            Thread({
                Looper.prepare()
                val update_value: HashMap<String, String> = HashMap()
                update_value[remote_big_table_helper.ITEMNUMBER] = str
                remote_big_table_helper.push_update(big_item, update_value, context)
                big_item.set_ITEMNUMBER(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                themer.hideKeyboard(context,itemnumber)
            }).start()
            itemnumber.hint = str.trim()
            itemnumber.text.clear()
        }

        komanum.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if(hasFocus || komanum.text.isEmpty() )
                return@OnFocusChangeListener
            val str = komanum.text.toString()
            Thread({
                Looper.prepare()
                val update_value: HashMap<String, String> = HashMap()
                update_value[remote_big_table_helper.KOMANUM] = str
                remote_big_table_helper.push_update(big_item, update_value, context)
                big_item.set_KOMANUM(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                themer.hideKeyboard(context,komanum)
            }).start()
            komanum.hint = str.trim()
            komanum.text.clear()
        }

        diranum.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if(hasFocus || diranum.text.isEmpty() )
                return@OnFocusChangeListener
            val str = diranum.text.toString()
            Thread({
                Looper.prepare()
                val update_value: HashMap<String, String> = HashMap()
                update_value[remote_big_table_helper.DIRANUM] = str
                remote_big_table_helper.push_update(big_item, update_value, context)
                big_item.set_DIRANUM(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                themer.hideKeyboard(context,diranum)
            }).start()
            diranum.hint = str.trim()
            diranum.text.clear()
        }


        themer.center_all_views(all_views)

        return convertView


    }
}