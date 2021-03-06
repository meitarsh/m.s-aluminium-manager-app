package com.example.chaosruler.msa_manager.activies.project_chooser_fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.activies.loz_activity.loz_activity_arrayadapter
import com.example.chaosruler.msa_manager.object_types.salprojluz_data.salprojluz_data
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import org.jetbrains.anko.support.v4.act
import java.util.*


/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the [LozFragment.newInstance] factory method to
 * create an instance of this fragment.
 * @author Chaosruler972
 */
class LozFragment : Fragment() {

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
        val tree_view =  inflater.inflate(R.layout.fragment_loz, container, false)

        val listview = themer.get_view(tree_view, R.id.frag_loz_activity_listview) as ListView
        Thread({
            val arr: Vector<salprojluz_data> =
                    if (global_variables_dataclass.GUI_MODE)
                        Vector()
                    else if (!global_variables_dataclass.GUI_MODE && global_variables_dataclass.isLocal)
                        Vector(global_variables_dataclass.db_salproj_vec.filter { it.get_projid() == global_variables_dataclass.projid })
                    else
                        global_variables_dataclass.DB_SALPROJ!!.server_data_to_vector<salprojluz_data>()

                activity?.runOnUiThread {
                    listview.adapter = loz_activity_arrayadapter(act,arr)
                }

        }).start()
        return tree_view
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment LozFragment.
         */
        @JvmStatic
        fun newInstance() = LozFragment()
    }
}
