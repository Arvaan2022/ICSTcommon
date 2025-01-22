package com.icst.commonmodule.design.activity.addScheduleEvent.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckedTextView
import android.widget.TextView

open class SecondDemoAdapter(
    context: Context,
    resource: Int,
    objects: ArrayList<String>
) : ArrayAdapter<String>(context, resource, objects as List<String>) {
    private var data: ArrayList<String>? = null
    private var dataID: ArrayList<String>? = null
    fun setItems(data: ArrayList<String>?, dataID: ArrayList<String>?) {
        this.data = data
        this.dataID = dataID
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view =
                LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
            val textView = view.findViewById<TextView>(android.R.id.text1)
            textView.text = data!![position]
        }
        return view!!
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            if (position == 0) {
                view =
                    LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
                val textView = view?.findViewById<TextView>(android.R.id.text1)
                if (textView != null) {
                    textView.text = data!![position]
                }
            } else {
                view = LayoutInflater.from(context)
                    .inflate(android.R.layout.simple_list_item_checked, parent, false)
                val textView = view.findViewById<CheckedTextView>(android.R.id.text1)
                textView.text = data!![position]
            }
        }
        return view!!
    }

    override fun isEnabled(position: Int): Boolean {
        return position != 0
    }
}