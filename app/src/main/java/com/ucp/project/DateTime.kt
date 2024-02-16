package com.ucp.project

import android.os.AsyncTask
import android.util.Log
import android.widget.TextView
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


// This is Date and Time Class Which Manages Current Dates and Time both in Islamic and English
class DateTime {

    // get the current date in this "EEEE dd MMMM, yyyy" format
    fun getDate(): String {
        // Get the Current Date and Time in the Required Format
        val dateFormat = SimpleDateFormat("EEEE dd MMMM, yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        return currentDate.toString()
    }

    // get the current time in this "HH:mm" format
    fun getTime():String{
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val currentTime = timeFormat.format(Date())
        return currentTime.toString()
    }

    // Get Time in Prayer Time
    fun getTimePrayerTime():PrayerTime{
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val currentTime = timeFormat.format(Date()).toString() + " (PKT)"
        return PrayerTime(currentTime, "Current")
    }

    // This Function Will Parse
    fun parseTimeString(timeString: String): Pair<Int, Int> {
        val (time, timezone) = timeString.split(" (")
        val (hour, minute) = time.split(":").map { it.toInt() }
        return Pair(hour, minute)
    }

    // Returns the Current Islamic Date in Hijari
   fun getIslamicDate(callback: (String) -> Unit){

       val date = Date()
       val dateFormat = SimpleDateFormat("dd-MM-yyyy")
       val formattedDate = dateFormat.format(date)
        var getUrl = "https://api.aladhan.com/v1/gToH?date=" + formattedDate

       GetIslamicDateTask(callback).execute(getUrl)
   }

    // Convert the Time in Milliseconds to the Correct Format Like "MM:ss"
    fun timeInCorrectFormat(durationInMilliseconds:Int):String{
        val minutes = (durationInMilliseconds / 1000) / 60
        val seconds = (durationInMilliseconds / 1000) % 60

        return "$minutes:$seconds"
    }

}

public class GetIslamicDateTask(private val callback: (String) -> Unit) : AsyncTask<String, Void, String>() {
    override fun doInBackground(vararg params: String?): String {
        val dataUrl = params[0]
        val url = URL(dataUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        return connection.inputStream.bufferedReader().readText()
    }

    override fun onPostExecute(result: String?) {
        if (result != null) {
            callback(result ?: "")
        }
    }
}