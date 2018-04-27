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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the [KablaniMishneFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class KablaniMishneFragment : Fragment() {

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
        @JvmStatic
        fun newInstance() = KablaniMishneFragment()
    }
}
