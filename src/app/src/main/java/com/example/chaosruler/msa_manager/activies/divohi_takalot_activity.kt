package com.example.chaosruler.msa_manager.activies

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.activies.flat_chooser.flat_chooser
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_divohi_takalot_activity.*

/**
 * class for the logic of the activity of divohi takalot
 * @author Chaosruler972
 * @constructor a default constructor for an activity constructor
 */
class divohi_takalot_activity : Activity() {

    /**
     * part of the activity lifecycle, initates the button logic
     * @author Chaosruler972
     * @param savedInstanceState the last state of the activity
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_divohi_takalot_activity)
        init_buttons()
    }

    /**
     *   inits buttons
     *   @author Chaosruler972
     */
    private fun init_buttons()
    {
        divohi_takalot_form.setOnClickListener {
            global_variables_dataclass.floor_moving_to =1
            startActivity(Intent(this, flat_chooser::class.java))
        }

        divohi_takalot_edit.setOnClickListener({startActivity(Intent(this, com.example.chaosruler.msa_manager.activies.divohi_takalot_edit_activity.divohi_takalot_edit::class.java)) })


    }


}
