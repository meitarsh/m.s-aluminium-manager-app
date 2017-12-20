package com.example.chaosruler.msa_manager.activies.settings_activity

import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceActivity
import android.support.annotation.LayoutRes
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.Toolbar
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup

/**
 * A [android.preference.PreferenceActivity] which implements and proxies the necessary calls
 * to be used with AppCompat.
 */
abstract class AppCompatPreferenceActivity : PreferenceActivity() {

    /*
        override activity to initate perference activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        delegate.installViewFactory()
        delegate.onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
    }
    /*
           override activity to initate perference activity
        */
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        delegate.onPostCreate(savedInstanceState)
    }

    val supportActionBar: ActionBar?
        get() = delegate.supportActionBar

    @Suppress("unused")
/*
           override activity to initate perference activity
        */
    fun setSupportActionBar(toolbar: Toolbar?) {
        delegate.setSupportActionBar(toolbar)
    }
    /*
           override activity to initate perference activity
        */
    override fun getMenuInflater(): MenuInflater {
        return delegate.menuInflater
    }
    /*
           override activity to initate perference activity
        */
    override fun setContentView(@LayoutRes layoutResID: Int) {
        delegate.setContentView(layoutResID)
    }
    /*
           override activity to initate perference activity
        */
    override fun setContentView(view: View) {
        delegate.setContentView(view)
    }
    /*
           override activity to initate perference activity
        */
    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        delegate.setContentView(view, params)
    }
    /*
           override activity to initate perference activity
        */
    override fun addContentView(view: View, params: ViewGroup.LayoutParams) {
        delegate.addContentView(view, params)
    }
    /*
           override activity to initate perference activity
        */
    override fun onPostResume() {
        super.onPostResume()
        delegate.onPostResume()
    }
    /*
           override activity to initate perference activity
        */
    override fun onTitleChanged(title: CharSequence, color: Int) {
        super.onTitleChanged(title, color)
        delegate.setTitle(title)
    }
    /*
           override activity to initate perference activity
        */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        delegate.onConfigurationChanged(newConfig)
    }
    /*
           override activity to initate perference activity
        */
    override fun onStop() {
        super.onStop()
        delegate.onStop()
    }
    /*
           override activity to initate perference activity
        */
    override fun onDestroy() {
        super.onDestroy()
        delegate.onDestroy()
    }
    /*
           override activity to initate perference activity
        */
    override fun invalidateOptionsMenu() {
        delegate.invalidateOptionsMenu()
    }
    /*
           override activity to initate perference activity
        */
    private val delegate: AppCompatDelegate by lazy {
        AppCompatDelegate.create(this, null)
    }
}
