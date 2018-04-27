package com.example.chaosruler.msa_manager.activies

import android.os.Build
import android.support.design.widget.TabLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.chaosruler.msa_manager.BuildConfig

import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.activies.project_chooser_fragments.KablaniMishneFragment
import com.example.chaosruler.msa_manager.activies.project_chooser_fragments.LozFragment
import com.example.chaosruler.msa_manager.activies.project_chooser_fragments.divohi_takalot_fragment
import com.example.chaosruler.msa_manager.services.global_variables_dataclass
import com.example.chaosruler.msa_manager.services.themer
import kotlinx.android.synthetic.main.activity_project_options2.*
import kotlinx.android.synthetic.main.fragment_project_options2.view.*

class ProjectOptions2Activity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(themer.style(baseContext))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_options2)

        if(!global_variables_dataclass.GUI_MODE)
            global_variables_dataclass.projid = intent.getStringExtra(getString(R.string.key_pass_main_to_options))
        else
            global_variables_dataclass.projid = ""
        if(global_variables_dataclass.projid == null)
            finish()

        if(BuildConfig.DEBUG)
        {
            val dbg_proj_id = "00315"
            global_variables_dataclass.projid = dbg_proj_id
            Log.d("projid","Changed to $dbg_proj_id (${global_variables_dataclass.projid})")
        }

//        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))


    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return when(position)
            {
                0 -> KablaniMishneFragment.newInstance()
                1 -> divohi_takalot_fragment.newInstance()
                2 -> LozFragment.newInstance()
                else -> KablaniMishneFragment.newInstance()
            }
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }
    }


}
