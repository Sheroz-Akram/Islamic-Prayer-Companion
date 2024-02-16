package com.ucp.project

import android.media.Image
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import org.json.JSONObject
import java.io.IOException

class DailyAyah : AppCompatActivity() {

    var mediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_ayah)

        // Get a Random Hadith from URL
        val rand = java.util.Random()
        val randomInt = rand.nextInt(6234) + 1
        var url = "https://api.alquran.cloud/v1/ayah/" + randomInt.toString() + "/en.asad"
        var mediaUrl = "https://cdn.islamic.network/quran/audio/128/ar.alafasy/" + randomInt.toString() + ".mp3"

        GetIslamicDateTask { result ->

            var data = JSONObject(result)
            var myData = data.getJSONObject("data")
            var Ayah = myData.getString("text")
            var ayahDisplay:TextView = findViewById(R.id.ayahDisplay)
            ayahDisplay.setText(Ayah)


        }.execute(url)

        var i = 0;
        // Play Media on Play Button
        var playButton:ImageView = findViewById(R.id.playButton)
        playButton.setOnClickListener{
            if(i == 0){
                // Setup Our Media Player
                mediaPlayer.setDataSource(mediaUrl)
                mediaPlayer.prepare()
                i++
            }
            if(!mediaPlayer.isPlaying){
                playMedia()
            }
        }

        // Stop the media on Stop Button
        var stopButton:ImageView = findViewById(R.id.stopButton)
        stopButton.setOnClickListener{
            if(mediaPlayer.isPlaying){
                mediaPlayer.pause()
            }
        }




    }

    override fun onBackPressed() {
        super.onBackPressed()
        mediaPlayer.stop()
    }

    fun  playMedia(){
        // Now We Play Our Media
        try {


            var progressBar:ProgressBar = findViewById(R.id.progressBar)

            mediaPlayer.start()
            var dateTime = DateTime()
            val totalDuration = mediaPlayer.duration
            var totalDurationTV:TextView = findViewById(R.id.endingTime)
            totalDurationTV.text = dateTime.timeInCorrectFormat(totalDuration).toString()

            val handler = Handler()
            val updateProgress = object : Runnable {
                override fun run() {
                    if (mediaPlayer.isPlaying) {
                        val currentPosition = mediaPlayer.currentPosition
                        val progress = (currentPosition.toFloat() / totalDuration.toFloat()) * 100
                        progressBar.progress = progress.toInt()
                        var currentDurationTV:TextView = findViewById(R.id.startingTime)
                        currentDurationTV .text = dateTime.timeInCorrectFormat(currentPosition).toString()

                        handler.postDelayed(this, 1000)
                    }
                }
            }

            handler.post(updateProgress)

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}