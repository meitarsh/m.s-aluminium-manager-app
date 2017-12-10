package com.example.chaosruler.msa_manager.services

import android.app.Activity
import android.content.Context
import android.preference.PreferenceManager
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.chaosruler.msa_manager.R
import java.util.*


/*
    general static class responsible for getting the right updated theme for every page based on the settings
    of the style chosen by the user, default being Light
 */
class themer {
    companion object
    {
        /*
            whats the style?
         */
        fun style(context: Context):Int
        {
            var isDark:String
            var preferences = PreferenceManager.getDefaultSharedPreferences(context)
            isDark = preferences.getString(context.getString(R.string.style),"Light")
            return getResourceId(context, isDark, "style", context.packageName)
        }
        /*
                      gets a new edit text
               */
        public fun get_edittext(context: Context): EditText
        {
            var box = EditText(context)
            box.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)
            (box.layoutParams as TableRow.LayoutParams).weight = 1.toFloat()
            box.background = context.getDrawable(R.drawable.cell_shape)
            box.gravity = Gravity.CENTER
            return box
        }

        /*
                     gets textview
              */
        public fun get_textview(context: Context): TextView
        {
            var box = TextView(context)
            box.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)
            (box.layoutParams as TableRow.LayoutParams).weight = 1.toFloat()
            box.background = context.getDrawable(R.drawable.cell_shape)
            box.gravity = Gravity.CENTER
            return box
        }
        /*
                            centers all views
                     */
        public fun center_all_views(vector: Vector<View>)
        {
            for(item in vector)
            {
                (item.layoutParams as LinearLayout.LayoutParams).gravity = Gravity.CENTER
            }
        }

        /*
                   gets a new checkbox
            */
        public fun get_checkbox(context: Context): CheckBox
        {
            var box = CheckBox(context)
            box.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT)
            (box.layoutParams as TableRow.LayoutParams).weight = 1.toFloat()
            box.background = context.getDrawable(R.drawable.cell_shape)
            box.gravity = Gravity.CENTER
            return box
        }

    /*
            predefined size
         */
    public fun fix_size(context: Context,vector: Vector<View>)
    {
        for(item in vector)
        {
            if(item is TextView)
            {
                item.width = context.resources.getInteger(R.integer.table_width)
                item.height = context.resources.getInteger(R.integer.table_height)
            }
            if(item is EditText)
            {
                item.width = context.resources.getInteger(R.integer.table_width)
                item.height = context.resources.getInteger(R.integer.table_height)
            }
            if(item is CheckBox)
            {
                item.width = context.resources.getInteger(R.integer.table_width)
                item.height = context.resources.getInteger(R.integer.table_height)
            }

        }
    }
        /*
                   gets box
            */
        public fun get_button(context: Context): Button
        {
            var box = Button(context)
            box.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT)
            (box.layoutParams as TableRow.LayoutParams).weight = 1.toFloat()
            box.background = context.getDrawable(R.drawable.cell_shape)
            box.gravity = Gravity.CENTER
            return box
        }
        /*
    hides softkeyboard from specific view
 */
        fun hideKeyboard(context: Context,view: View)
        {
            val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
        /*
        gets resourceid from context
        */
        public fun getResourceId(context: Context, pVariableName: String, pResourcename: String, pPackageName: String): Int =
                context.resources.getIdentifier(pVariableName, pResourcename, pPackageName)

        /*
            gets a resource from context, without sending context
         */
        public fun get_view(convertView: View,id:Int) : View = convertView.findViewById(id) // grabs the correpsonding view by id from layout


    } // companion object
}