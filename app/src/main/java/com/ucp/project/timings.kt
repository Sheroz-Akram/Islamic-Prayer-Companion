package com.ucp.project

import android.Manifest.permission.SET_ALARM
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.core.content.ContextCompat
import android.Manifest
import android.os.Build
import androidx.core.app.ActivityCompat

class timings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timings)


        setAlarmsListView()



    }



    fun setAlarmsListView(){


        var cityOperations = CityOperations()

        cityOperations.getAllPrayerTimmings(this) { list ->
            var listView:ListView = findViewById(R.id.prayerAlarmSetListView)
            listView.adapter = CustomAdapterPrayerTime(this, list)
        }
    }
}