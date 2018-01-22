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


/**
 * general static class responsible for getting the right updated theme for every page based on the settings
 * of the style chosen by the user, default being Light
 * @author Chaosruler972
 * @constructor since this is a singleton, this doesn't need to be constructed
 */
object themer {

    /**
     * whats the style?
     * @author Chaosruler
     * @param context the conext to work with
     * @return the resource id of our style
     */
    fun style(context: Context): Int {
            val isDark: String
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        isDark = preferences.getString(context.getString(R.string.style), "Light")
            return getResourceId(context, isDark, "style", context.packageName)
        }

    /**
     * generates edittext
     * @author Chaosruler972
     * @param context the context to work with
     * @return a new edittext
     */
    @Suppress("unused")
    fun get_edittext(context: Context): EditText {
        val box = EditText(context)
        box.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)
        (box.layoutParams as TableRow.LayoutParams).weight = 1.toFloat()
        box.background = context.getDrawable(R.drawable.cell_shape)
        box.gravity = Gravity.CENTER
        return box
    }

    /**
     * generates textview
     * @author Chaosruler972
     * @param context the context to work with
     * @return a new textview
     */
    @Suppress("unused")
    fun get_textview(context: Context): TextView {
        val box = TextView(context)
        box.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)
        (box.layoutParams as TableRow.LayoutParams).weight = 1.toFloat()
        box.background = context.getDrawable(R.drawable.cell_shape)
        box.gravity = Gravity.CENTER
        return box
    }

    /**
     * centers all views in a vector of views
     * @author Chaosruler972
     * @param vector a vector of views that we should center
     */
    fun center_all_views(vector: Vector<View>) {
        for (item in vector) {
                (item.layoutParams as LinearLayout.LayoutParams).gravity = Gravity.CENTER
            }
        }

    /**
     * generates checkbox
     * @author Chaosruler972
     * @param context the context to work with
     * @return a new checkbox
     */
    @Suppress("unused")
    fun get_checkbox(context: Context): CheckBox {
        val box = CheckBox(context)
        box.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)
        (box.layoutParams as TableRow.LayoutParams).weight = 1.toFloat()
        box.background = context.getDrawable(R.drawable.cell_shape)
        box.gravity = Gravity.CENTER
        return box
    }


    /**
     * generates button
     * @author Chaosruler972
     * @param context the context to work with
     * @return a new button
     */
    @Suppress("unused")
    fun get_button(context: Context): Button {
        val box = Button(context)
        box.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)
        (box.layoutParams as TableRow.LayoutParams).weight = 1.toFloat()
        box.background = context.getDrawable(R.drawable.cell_shape)
        box.gravity = Gravity.CENTER
        return box
    }


    /**
     * hides the soft keyboard if it is focused by specific view
     * @author Chaosruler972
     * @param context the context to work with
     * @param view the view that the softkeyboard is focused on
     */
    fun hideKeyboard(context: Context, view: View) {
            val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }


    /**
     * gets resource id from specified viewgroup and resourceID
     * @author Chaosruler972
     * @param context the context to work with
     * @param pPackageName the current package name
     * @param pResourcename the resourcee name
     * @param pVariableName gets the variable name on the resource
     * @return the resource ID we wanted
     */
    @Suppress("MemberVisibilityCanPrivate", "MemberVisibilityCanBePrivate")
        fun getResourceId(context: Context, pVariableName: String, pResourcename: String, pPackageName: String): Int =
                context.resources.getIdentifier(pVariableName, pResourcename, pPackageName)

    /**
     * gets a resource from context, without sending context
     * @author Chaosruler972
     * @param convertView the viewgroup parent
     * @param id the id of the view to get
     * @return the view we waned
     */
    fun get_view(convertView: View, id: Int): View = convertView.findViewById(id) // grabs the correpsonding view by id from layout


    /**
     * gets a resource from context, without sending context, with casting
     * @author Chaosruler972
     * @param id the id of the view to get
     * @return the view we waned, casted
     */
    @Suppress("unused")
    fun <T : View> Activity.find(id: Int): T = this.findViewById(id) as T
}

