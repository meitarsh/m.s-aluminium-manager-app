package com.example.chaosruler.msa_manager.activies.loz_activity

import android.content.Context
import android.os.Looper
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import com.example.chaosruler.msa_manager.MSSQL_helpers.remote_big_table_helper
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.object_types.big_table_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import java.util.*


class loz_activity_arrayadapter (context: Context, arr: Vector<big_table_data>) : ArrayAdapter<big_table_data>(context, R.layout.item_loz,arr.toTypedArray())
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {

        @Suppress("NAME_SHADOWING")
        var convertView:View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_loz,parent,false)

        var bnian = themer.get_view(convertView,R.id.item_loz_bnian) as EditText
        var koma = themer.get_view(convertView,R.id.item_loz_koma) as EditText
        var begin = themer.get_view(convertView,R.id.item_loz_begin) as EditText
        var finish = themer.get_view(convertView,R.id.item_loz_end) as EditText
        var ahoz_bizoaa = themer.get_view(convertView,R.id.item_loz_ahoz_bizoaa) as EditText
        var isFinished = themer.get_view(convertView,R.id.item_loz_isfinished) as CheckBox
        var haarot = themer.get_view(convertView,R.id.item_loz_haarot) as EditText

        var all_views = Vector<View>()
        all_views.add(bnian)
        all_views.add(koma)
        all_views.add(begin)
        all_views.add(finish)
        all_views.add(ahoz_bizoaa)
        all_views.add(isFinished)
        all_views.add(haarot)


        val big_item:big_table_data = getItem(position)

        var ahoz_bizoa_str:String = big_item.get_MILESTONEPERCENT()?:0.toString()


        bnian.hint = (big_item.get_FLAT() ?: "").trim()

        koma.hint = (big_item.get_FLOOR() ?:  "").trim()
        begin.hint = ("No database value to grab").trim()
        begin.isEnabled = false
        finish.hint = "No database value to grab"
        finish.isEnabled = false

        isFinished.isEnabled = false

        ahoz_bizoaa.hint = (ahoz_bizoa_str + "%").trim()
        ahoz_bizoaa.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
        haarot.hint = "No database value to grab"
        haarot.isEnabled = false

        bnian.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if(hasFocus || bnian.text.isEmpty() )
                return@OnFocusChangeListener
            var str = bnian.text.toString()
            Thread({
                Looper.prepare()
                var update_value: HashMap<String, String> = HashMap()
                update_value[remote_big_table_helper.FLAT] = str
                remote_big_table_helper.push_update(big_item, update_value, context)
                big_item.set_FLAT(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                themer.hideKeyboard(context,bnian)
            }).start()
            bnian.hint = str.trim()
            bnian.text.clear()
        }

        koma.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if(hasFocus || koma.text.isEmpty() )
                return@OnFocusChangeListener
            var str = koma.text.toString()
            Thread({
                Looper.prepare()
                var update_value: HashMap<String, String> = HashMap()
                update_value[remote_big_table_helper.FLOOR] = str
                remote_big_table_helper.push_update(big_item, update_value, context)
                big_item.set_FLOOR(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                themer.hideKeyboard(context,koma)
            }).start()
            koma.hint = str.trim()
            koma.text.clear()
        }

        ahoz_bizoaa.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if(hasFocus || ahoz_bizoaa.text.isEmpty())
                return@OnFocusChangeListener
            var str = ahoz_bizoaa.text.toString()
            Thread({
                Looper.prepare()
                var update_value: HashMap<String, String> = HashMap()
                update_value[remote_big_table_helper.MILESTONEPERCENT] = str
                remote_big_table_helper.push_update(big_item, update_value, context)
                big_item.set_MILESTONEPERCENT(str)
                global_variables_dataclass.DB_BIG!!.add_big(big_item)
                themer.hideKeyboard(context,ahoz_bizoaa)
            }).start()
            ahoz_bizoaa.hint = (str + "%").trim()
            ahoz_bizoaa.text.clear()
        }


        themer.center_all_views(all_views)

        return convertView


    }
}