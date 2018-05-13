package com.example.chaosruler.msa_manager.activies.testing_do_all_table_activities.table_salproj_edit

import android.content.Context
import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.object_types.salprojluz_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import kotlinx.android.synthetic.main.activity_table_salrpoj_edit.*
import java.util.*

/**
 * Display salproj data
 * @author Chaosruler972
 */
class table_salrpoj_edit : AppCompatActivity() {

    /**
     * Activity to display data
     * @author Chaosruler972
     * @param savedInstanceState last state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_salrpoj_edit)
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
        val arr: Vector<salprojluz_data> =
                if (global_variables_dataclass.GUI_MODE)
                    Vector()
                else if (!global_variables_dataclass.GUI_MODE && global_variables_dataclass.isLocal)
                    global_variables_dataclass.db_salproj_vec
                else
                    global_variables_dataclass.DB_SALPROJ!!.server_data_to_vector()

        table_salproj_listview.adapter = table_salproj_arrayadapter(this,arr)
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
