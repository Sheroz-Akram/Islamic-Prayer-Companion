package com.ucp.project

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import org.json.JSONObject
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        runTasks()


    }

    override fun onResume() {
        super.onResume()
        runTasks()
    }

    fun runTasks(){
        CheckInternetTask(this) {
            super.finish()
        }.execute()

        // Set the Current Date and Time in the Main Page
        setDateTimeMainTextView()

        var settinsButton:ImageButton = findViewById(R.id.settingsButton)
        settinsButton.setOnClickListener{
            var settingsIntent:Intent = Intent(this , Settings::class.java)
            startActivity(settingsIntent)
        }

        // Populate the Content Header
        setPrayerTimings()

        // Set the Alarm Button
        var timingButton:LinearLayout = findViewById(R.id.timingButton)
        timingButton.setOnClickListener{
            var settingsIntent:Intent = Intent(this , timings::class.java)
            startActivity(settingsIntent)
        }


        // open the Ayah Activity
        var ayahButton:LinearLayout = findViewById(R.id.AyahRandomButton)
        ayahButton.setOnClickListener{
            var ayahIntent:Intent = Intent(this , DailyAyah::class.java)
            startActivity(ayahIntent)
        }

        // open the Quran Recitation Activity
        var quranRecitationButton:LinearLayout = findViewById(R.id.quranRecitationButton)
        quranRecitationButton.setOnClickListener{
            var quranIntent:Intent = Intent(this , QuranRecitation::class.java)
            startActivity(quranIntent)
        }

        // Open Tasbee Btn
        var TasbeBtn:LinearLayout = findViewById(R.id.tasbeeBTN)
        TasbeBtn.setOnClickListener{
            var tasbeeIntent:Intent = Intent(this , TasbeeCounter::class.java)
            startActivity(tasbeeIntent)
        }
    }





    fun setPrayerTimings(){

        // Our Our City Class To Get Prayer Timmings
        var city = CityOperations()

        // Next Prayer and Current Prayer
        var Next = 0;
        var NextPrayer: PrayerTime = PrayerTime("00:00 (PKT)", "NULL")
        var Current = 0;
        var CurrentPrayer:PrayerTime = PrayerTime("00:00 (PKT)", "NULL")

        // All Prayer Timmings
        city.getAllPrayerTimmings(this) { list ->

            // Get Current Date and Time
            var dateTime = DateTime()
            var currentTime:PrayerTime = dateTime.getTimePrayerTime()

            // Compare All the Timmings
            var Num = 0;
            for ( i in list){

                if(i.compare(currentTime) == true){

                    if(Num == 0){
                        Current = list.size - 1
                        Next = Num
                    }
                    else{
                        Current = Num - 1;
                        Next = Num;
                    }
                    break;
                }

                Num++;

            }

            NextPrayer = list.get(Next)
            CurrentPrayer = list.get(Current)


            // Display the Current And Next Prayer
            var currentPrayerTv:TextView = findViewById(R.id.currentPrayer)
            var nextPrayerTV:TextView = findViewById(R.id.nextPrayerDetails)

            currentPrayerTv.setText(CurrentPrayer.PrayerName)
            nextPrayerTV.setText("Next Prayer (" + NextPrayer.PrayerName+"-Hanafi) " + NextPrayer.displayInDifferentForm())


        }


    }



    // Set the Date and Time in our Text View
    fun setDateTimeMainTextView(){

        // Set the Current date and time
        var dateTimeManager = DateTime()

        // get our Text Views
        var dateTextView:TextView = findViewById(R.id.dataTextView)
        var timeTextView:TextView = findViewById(R.id.timeDisplay)

        // Get the Islamic Date
        dateTimeManager.getIslamicDate { result ->
            val json = JSONObject(result)
            val hijri = json.getJSONObject("data").getJSONObject("hijri")

            val hijriDay = hijri.getString("day")
            val hijriMonth = hijri.getJSONObject("month").getString("en")
            val hijriYear = hijri.getString("year")

            val formattedHijriDate = "$hijriDay $hijriMonth HIJJAH $hijriYear"

            var islamicDateTextView:TextView = findViewById(R.id.islamicDateDisplayMainPage)
            islamicDateTextView.setText(formattedHijriDate.toString())
        }

        // Set the Current Date and Time
        dateTextView.setText(dateTimeManager.getDate())
        timeTextView.setText(dateTimeManager.getTime())
    }
}

class CheckInternetTask(var context: Context, var callback: () -> Unit) : AsyncTask<Void, Void, Boolean>() {
    override fun doInBackground(vararg params: Void?): Boolean {
        return try {
            val timeoutMs = 1500
            val socket = Socket()
            val socketAddress = InetSocketAddress("8.8.8.8", 53)
            socket.connect(socketAddress, timeoutMs)
            socket.close()

            true
        } catch (e: IOException) {

            false
        }
    }

    override fun onPostExecute(result: Boolean) {
        super.onPostExecute(result)

        if(!result){
            Toast.makeText(context, "Internet Not Working!" , Toast.LENGTH_LONG ).show()
            callback()
        }
    }
}



