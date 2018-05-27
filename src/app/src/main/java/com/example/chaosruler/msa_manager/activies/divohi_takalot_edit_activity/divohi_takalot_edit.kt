package com.example.chaosruler.msa_manager.activies.divohi_takalot_edit_activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.object_types.big_table_data
import com.example.chaosruler.msa_manager.object_types.takala_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.divohi_takalot_edit.*
import java.io.File
import java.util.*
import android.view.MenuInflater
import android.view.MenuItem
import com.example.chaosruler.msa_manager.activies.MainActivity
import com.example.chaosruler.msa_manager.activies.divohi_takalot_new_takala_activity.NewTakala


/**
 *  the divohi takalot (edit type) activity logic class
 *  @author Chaosruler972
 *  @constructor as a activity class, this is a default constructor
 */
class divohi_takalot_edit : AppCompatActivity() {

    /**
     * part of the android activity lifecycle
     * will populate listview (or tableview)
     * @param savedInstanceState the last state of the activity
     * @author Chaosruler972
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.divohi_takalot_edit)
        if(!global_variables_dataclass.GUI_MODE && !init_table())
            finish()
    }

    /**
     * Inits a menu on this activity
     * @author Chaosruler972
     * @param menu the menu to inflate
     * @return on success
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        val inflater = menuInflater
        inflater.inflate(R.menu.takalot_edit_menu, menu)
        return true
    }

    /**
     * Selects a menu item
     * @author Chaosruler972
     * @param item the item selected
     * @return on success
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_new_takala -> {
                val aboutIntent = Intent(this@divohi_takalot_edit, NewTakala::class.java)
                startActivity(aboutIntent)
            }
            else ->
            {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * inits the table of the activity by getting the list of data (from server or client) and
     * opening a new adapter for it
     * @author Chaosruler972
     * @return true if successfull (to be frank, always true since data testing is done)
     */
    private fun init_table():Boolean
    {

        Thread({
            val arr: Vector<takala_data> =
                    if (global_variables_dataclass.GUI_MODE || global_variables_dataclass.DB_BIG == null)
                        Vector()
                    else if (!global_variables_dataclass.GUI_MODE && global_variables_dataclass.isLocal)
                        Vector(global_variables_dataclass.db_salprojtakala_vec.filter { it.get_projid() == global_variables_dataclass.projid })
                    else
                        global_variables_dataclass.DB_SALPROJTAKALA!!.server_data_to_vector_by_projname((global_variables_dataclass.projid ?: "").trim())
//            arr.sort()
            runOnUiThread({divohi_takalot_edit_listview.adapter = divohi_takalot_edit_arrayadapter(this, arr)
            })

        }).start()
        return true
    }


    /**
     * after event to get the file, this function handles that
     * @author Chaosruler972
     * @param data the file data
     * @param requestCode the request code to request the file
     * @param resultCode the result code (if we succeded or not)
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent)
    {
       if(resultCode == Activity.RESULT_OK)
       {

                val uri = data.data
           val parent = divohi_takalot_edit_listview.getChildAt(requestCode) as LinearLayout
           val button = parent.getChildAt(parent.childCount - 1) as Button
                button.isEnabled = false
                button.background = getDrawable(R.drawable.cell_shape)
                button.text = File(uri.path).absolutePath
                parent.refreshDrawableState()


       }
        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     *    Dispatch remove focus from all edit texts
     *    first attempt to remove soft keyboard
     *  @author Chaosruler972
     *  @return upon success, true
     *  @param event the event that triggered the request to hide soft keyboard
     */
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}
