package com.ucp.project

import android.content.Context
import android.content.Intent
import android.provider.AlarmClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text

class CustomAdapterQuranMP3File(var context: Context, var arrayList: java.util.ArrayList<QuranMP3File>) : BaseAdapter() {
    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = LayoutInflater.from(context).inflate(R.layout.simple_selector, parent, false)

        var simpleTitle:TextView = convertView.findViewById(R.id.simpleTitle)
        simpleTitle.setText((arrayList.get(position).ID).toString() + "- " + arrayList.get(position).Name)
        return convertView
    }


}