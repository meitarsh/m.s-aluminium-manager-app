package com.example.chaosruler.msa_manager.activies.project_chooser_fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.activies.flat_and_floor_chooser
import com.example.chaosruler.msa_manager.activies.flat_chooser.flat_chooser
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_divohi_takalot_activity.*
import kotlinx.android.synthetic.main.fragment_divohi_takalot_fragment.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the [divohi_takalot_fragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class divohi_takalot_fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val tree_height = inflater.inflate(R.layout.fragment_divohi_takalot_fragment, container, false)

        val edit_btn = themer.get_view(tree_height, R.id.frag_divohi_takalot_edit)

        val form_btn = themer.get_view(tree_height, R.id.frag_divohi_takalot_form)

        edit_btn.setOnClickListener {
            startActivity(Intent(activity, com.example.chaosruler.msa_manager.activies.divohi_takalot_edit_activity.divohi_takalot_edit::class.java))
        }

        form_btn.setOnClickListener {
            global_variables_dataclass.floor_moving_to = 1
            startActivity(Intent(activity, flat_and_floor_chooser::class.java))
        }
        return tree_height
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment divohi_takalot_fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = divohi_takalot_fragment()
    }
}
