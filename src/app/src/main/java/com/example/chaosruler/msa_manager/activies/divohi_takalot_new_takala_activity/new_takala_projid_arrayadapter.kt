package com.example.chaosruler.msa_manager.activies.divohi_takalot_new_takala_activity

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.chaosruler.msa_manager.R
import com.example.chaosruler.msa_manager.object_types.project_data
import org.jetbrains.anko.textColor

class new_takala_projid_arrayadapter(context: Context, resourceID: Int, list:List<project_data>):
        ArrayAdapter<project_data>(context, resourceID, list.toTypedArray())
{
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val txtview = TextView(context)
        txtview.text = getItem(position).get_project_name() ?: ""
        txtview.setTextColor(Color.BLACK)
        return txtview
    }
}