package com.example.chaosruler.msa_manager

import android.content.Context
import android.preference.PreferenceManager




class themer {
    companion object {
        fun style(context: Context):Int
        {
            var isDark:String = ""
            var preferences = PreferenceManager.getDefaultSharedPreferences(context)
            try
            {
                isDark = preferences.getString(context.getString(R.string.style),"Light")
            }
            catch (e:Exception){}
            return getResourceId(context,isDark,"style",context.packageName)
        }



        fun getResourceId(context: Context,pVariableName: String, pResourcename: String, pPackageName: String): Int {
            try {
                return context.resources.getIdentifier(pVariableName, pResourcename, pPackageName)
            } catch (e: Exception) {
                e.printStackTrace()
                return -1
            }

        }
    }
}