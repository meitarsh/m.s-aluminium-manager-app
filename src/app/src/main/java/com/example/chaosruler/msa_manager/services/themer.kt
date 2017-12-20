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
            val isDark: String
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            isDark = preferences.getString(context.getString(R.string.style),"Light")
            return getResourceId(context, isDark, "style", context.packageName)
        }

        @Suppress("unused")
/*
                      gets a new edit text
               */
        fun get_edittext(context: Context): EditText
        {
            val box = EditText(context)
            box.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)
            (box.layoutParams as TableRow.LayoutParams).weight = 1.toFloat()
            box.background = context.getDrawable(R.drawable.cell_shape)
            box.gravity = Gravity.CENTER
            return box
        }

        @Suppress("unused")
/*
                     gets textview
              */
        fun get_textview(context: Context): TextView
        {
            val box = TextView(context)
            box.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)
            (box.layoutParams as TableRow.LayoutParams).weight = 1.toFloat()
            box.background = context.getDrawable(R.drawable.cell_shape)
            box.gravity = Gravity.CENTER
            return box
        }

        /*
                            centers all views
                     */
        fun center_all_views(vector: Vector<View>)
        {
            for(item in vector)
            {
                (item.layoutParams as LinearLayout.LayoutParams).gravity = Gravity.CENTER
            }
        }

        @Suppress("unused")
/*
                   gets a new checkbox
            */
        fun get_checkbox(context: Context): CheckBox
        {
            val box = CheckBox(context)
            box.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT)
            (box.layoutParams as TableRow.LayoutParams).weight = 1.toFloat()
            box.background = context.getDrawable(R.drawable.cell_shape)
            box.gravity = Gravity.CENTER
            return box
        }

        @Suppress("unused")
/*
                   gets box
            */
        fun get_button(context: Context): Button
        {
            val box = Button(context)
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

        @Suppress("MemberVisibilityCanPrivate")
/*
        gets resourceid from context
        */
        fun getResourceId(context: Context, pVariableName: String, pResourcename: String, pPackageName: String): Int =
                context.resources.getIdentifier(pVariableName, pResourcename, pPackageName)

        /*
            gets a resource from context, without sending context
         */
        fun get_view(convertView: View, id: Int): View = convertView.findViewById(id) // grabs the correpsonding view by id from layout

        @Suppress("unused")
        fun <T : View> Activity.find(id: Int): T = this.findViewById(id) as T

    } // companion object
}

