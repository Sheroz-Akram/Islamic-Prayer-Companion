package com.ucp.project

import android.util.Log

class PrayerTime(var StringTime:String, var PrayerName:String) {
    var Hour:Int = 0
    var Minute:Int = 0

    init {
        val (time, timezone) = StringTime.split(" (")
        val (hour, minute) = time.split(":").map { it.toInt() }
        Hour = hour
        Minute = minute
    }

    fun displayInDifferentForm():String{
        if(Hour > 12){
            var newHour = Hour - 12
            return "$newHour:$Minute PM";
        }
        else{
            return getString() + " AM"
        }
    }

    fun getString():String{
        return Hour.toString() + ":" + Minute.toString()
    }

    fun compare(otherTime: PrayerTime):Boolean{
        return if(Hour > otherTime.Hour){
            true
        } else if (Hour == otherTime.Hour){
            Minute > otherTime.Minute
        } else{
            false
        }
    }
}