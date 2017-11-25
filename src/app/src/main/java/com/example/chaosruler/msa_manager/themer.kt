package com.example.chaosruler.msa_manager

import android.content.Context
import android.preference.PreferenceManager



/*
    general static class responsible for getting the right updated theme for every page based on the settings
    of the style chosen by the user, default being Light
 */
class themer {
    companion object {
        fun style(context: Context):Int
        {
            var isDark:String = ""
            var preferences = PreferenceManager.getDefaultSharedPreferences(context)
            isDark = preferences.getString(context.getString(R.string.style),"Light")
            return getResourceId(context,isDark,"style",context.packageName)
        }



        private fun getResourceId(context: Context, pVariableName: String, pResourcename: String, pPackageName: String): Int =
                context.resources.getIdentifier(pVariableName, pResourcename, pPackageName)
    }
}