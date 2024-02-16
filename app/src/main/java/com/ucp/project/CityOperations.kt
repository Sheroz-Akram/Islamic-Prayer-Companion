package com.ucp.project

import android.content.Context
import android.util.Log
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class CityOperations {

    fun getCity(context: Context):String {
        val preferences = context.getSharedPreferences("App",Context.MODE_PRIVATE)
        val selectedCity = preferences.getString("city", "Lahore")

        Log.d("AliBaba" , selectedCity.toString())

        return selectedCity.toString()
    }

    fun getPrayerTimes(context: Context, callback: (String) -> Unit){
        val calendar = Calendar.getInstance()
        val currentMonth = (calendar.get(Calendar.MONTH) + 1).toString()
        val currentYear = calendar.get(Calendar.YEAR).toString()

        var getUrl = "https://api.aladhan.com/v1/calendarByCity?city= " + getCity(context) + "&country=Pakistan&method=1&month=" +currentMonth+ "&year=" + currentYear
        GetIslamicDateTask(callback).execute(getUrl)
    }

    fun getAllPrayerTimmings(context: Context, callback: (ArrayList<PrayerTime>) -> Unit){
        var cityManager = CityOperations()
        var listTimmings = ArrayList<PrayerTime>()
        cityManager.getPrayerTimes(context) { result ->

            // Parse the Json data to Find Relevant Information
            val json = JSONObject(result)
            var data = json.getJSONArray("data")
            val calendar = Calendar.getInstance()
            val currentDate= calendar.get(Calendar.DATE) - 1
            val currentDateData = data.getJSONObject(currentDate).getJSONObject("timings")

            // All Five Prayers
            var Fajr = currentDateData.getString("Fajr")
            var Dhuhr = currentDateData.getString("Dhuhr")
            var Asr = currentDateData.getString("Asr")
            var Maghrib = currentDateData.getString("Maghrib")
            var Isha = currentDateData.getString("Isha")

            // Add to ArrayList
            listTimmings.add(PrayerTime(Fajr, "Fajr"))
            listTimmings.add(PrayerTime(Dhuhr, "Dhuhr"))
            listTimmings.add(PrayerTime(Asr, "Asr"))
            listTimmings.add(PrayerTime(Maghrib, "Maghrib"))
            listTimmings.add(PrayerTime(Isha, "Isha"))

            callback(listTimmings)

        }
    }

}