package com.ucp.project

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import java.io.IOException

class PlayMP3 : AppCompatActivity() {

    var mediaPlayer = MediaPlayer()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_mp3)

        // Get Our intent Data
        var intent: Intent = getIntent()
        var ID:Int = intent.getIntExtra("ID", 1)
        var Name:String = intent.getStringExtra("Name").toString()
        var URL: String = intent.getStringExtra("URL").toString()

        // Display Our Data\
        var appTitlePage:TextView = findViewById(R.id.appTitlePage)
        appTitlePage.text = "Play $Name"


        var i  = 0;
        // Play Media on Play Button
        var playButton: ImageView = findViewById(R.id.playButton)
        playButton.setOnClickListener{
            if(i == 0){
                // Setup Our Media Player
                mediaPlayer.setDataSource(URL)
                mediaPlayer.prepare()
                i++
            }
            if(!mediaPlayer.isPlaying){
                playMedia()
            }
        }

        // Stop the media on Stop Button
        var stopButton: ImageView = findViewById(R.id.stopButton)
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


            var progressBar: ProgressBar = findViewById(R.id.progressBar)

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
