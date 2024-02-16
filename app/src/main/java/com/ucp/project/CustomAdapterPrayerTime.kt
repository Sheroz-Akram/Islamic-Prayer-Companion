package com.ucp.project

import android.content.Context
import android.content.Intent
import android.provider.AlarmClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity

class CustomAdapterPrayerTime(var context: Context, var arrayList: java.util.ArrayList<PrayerTime>) : BaseAdapter() {
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
        var convertView = LayoutInflater.from(context).inflate(R.layout.alarm_item_prayer_set, parent, false)
        var prayerTitle: TextView = convertView.findViewById(R.id.prayerTitle)
        var prayerTime: TextView = convertView.findViewById(R.id.prayerTime)
        var prayerButton: Button = convertView.findViewById(R.id.prayerAlarmButton)

        prayerTitle.setText(arrayList.get(position).PrayerName)
        prayerTime.setText(arrayList.get(position).StringTime)
        prayerButton.setOnClickListener{

            val alarmIntent:Intent = Intent(AlarmClock.ACTION_SET_ALARM)
            alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, arrayList.get(position).Hour)
            alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, arrayList.get(position).Minute)
            alarmIntent.putExtra(AlarmClock.EXTRA_MESSAGE, arrayList.get(position).PrayerName + " Alarm")
            alarmIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, true)
            context.startActivity(alarmIntent)


        }
        return convertView
    }


}