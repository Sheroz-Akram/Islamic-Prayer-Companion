package com.ucp.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.assignment03.DBHelper

class TasbeeManager : AppCompatActivity() {

    var pCount:Int = 0
    var Name:String = ""
    var pSet:Int = 0
    var Count:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasbee_manager)

        // get All teh Data from Intent
        var intent: Intent = getIntent()
        var ID:Int = intent.getIntExtra("ID" , 0)
        Name = intent.getStringExtra("Name").toString()
        Count = intent.getIntExtra("Count", 0)

        // Save the Data Up
        DisplayTasbeedata()

        // Tap on Screen
        var clicker:ConstraintLayout = findViewById(R.id.clicker)
        clicker.setOnClickListener{
            pCount++;

            if(pCount % Count == 0){
                pCount = 0
                pSet++
            }

            DisplayTasbeedata()
        }

        // Reset the Tasbee
        var resetBtn:Button = findViewById(R.id.resetBtn)
        resetBtn.setOnClickListener{
            pCount = 0;
            pSet = 0
            DisplayTasbeedata()
        }

        // Set the Delete Btn
        var deleteBtn:ImageView = findViewById(R.id.deleteBtn)
        deleteBtn.setOnClickListener{

            // open Data and delete the Data
            var dbHelper = DBHelper(this, null)
            dbHelper.deleteData(ID , this)
            super.finish()
        }



    }

    fun DisplayTasbeedata(){

        // This wil get All the text vies needed
        var tasbeeName:TextView = findViewById(R.id.tasbeeNameDisplay)
        var tasbeeSet:TextView = findViewById(R.id.setInfo)
        var tasbeeCounter:TextView = findViewById(R.id.counterTasbee)
        var progressBar:ProgressBar = findViewById(R.id.progressBar)

        progressBar.progress = ( ( (pCount * 1.0) / Count )  * 100.0 ).toInt()

        tasbeeName.setText(Name)
        tasbeeSet.setText("Set: $pSet")
        tasbeeCounter.setText("Count: $pCount")
    }
}