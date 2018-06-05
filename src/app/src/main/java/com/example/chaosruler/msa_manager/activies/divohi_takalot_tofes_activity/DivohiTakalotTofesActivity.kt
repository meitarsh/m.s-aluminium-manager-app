package com.example.chaosruler.msa_manager.activies.divohi_takalot_tofes_activity

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.object_types.takala_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.divohi_takalot_tofes.*
import java.util.*

/**
 *  the divohi takalot (form type) activity logic class
 *  @author Chaosruler972
 *  @constructor as a activity class, this is a default constructor
 */
class DivohiTakalotTofesActivity : Activity() {

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
        setContentView(R.layout.divohi_takalot_tofes)
        if(!global_variables_dataclass.GUI_MODE && !init_table())
            finish()
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
                        global_variables_dataclass.DB_SALPROJTAKALA!!.server_data_to_vector<takala_data>()
            runOnUiThread({
//                val filtered_list = arr.filter { it.get_BINYAN() == global_variables_dataclass.flat && it.get_KOMA() == global_variables_dataclass.floor }
                divohi_takalot_tofes_listview.adapter = divohi_takalot_tofes_arrayadapter(this, arr)
            })
        }).start()

        return true
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
