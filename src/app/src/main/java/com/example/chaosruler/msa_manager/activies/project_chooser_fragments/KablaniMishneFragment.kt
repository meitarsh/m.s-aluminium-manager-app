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
import com.example.chaosruler.msa_manager.activies.kablan_pashot_activity.kablan_pashot
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer


/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the [KablaniMishneFragment.newInstance] factory method to
 * create an instance of this fragment.
 * @author Chaosruler972
 */
class KablaniMishneFragment : Fragment() {

    /**
     * Creates the view of the fragment
     * @author Chaosruler972
     * @param inflater the layout inflater that I can use
     * @param container the viewgroup to add our view into
     * @param savedInstanceState our last state
     * @return a view of this fragment
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val tree_view =  inflater.inflate(R.layout.fragment_kablani_mishne, container, false)

        val pashot = themer.get_view(tree_view, R.id.frag_kablni_mishne_pshot)
        val pirot = themer.get_view(tree_view, R.id.frag_kablni_mishne_pirot)
        //val ntonim_hodshiim = themer.get_view(tree_view, R.id.frag_kablni_mishne_ntonim_hodshiim)

        pashot.setOnClickListener {
            startActivity(Intent(activity, kablan_pashot::class.java))
        }

        pirot.setOnClickListener {
            global_variables_dataclass.floor_moving_to = 0
            startActivity(Intent(activity, flat_and_floor_chooser::class.java))
        }

        return tree_view
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment KablaniMishneFragment.
         */
        @JvmStatic
        fun newInstance() = KablaniMishneFragment()
    }
}
