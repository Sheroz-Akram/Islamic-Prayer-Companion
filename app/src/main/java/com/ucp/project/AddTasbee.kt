package com.ucp.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.assignment03.DBHelper

class AddTasbee : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tasbee)

        // Logic for Saving New Tasbee to the DataBase
        var TasbeeName:TextView = findViewById(R.id.tasbeeName)
        var TasbeCount:TextView = findViewById(R.id.tasbeeCount)

        // Save Btn Functionality
        var saveBtn: Button = findViewById(R.id.saveBtn)
        saveBtn.setOnClickListener{
            var Name:String = TasbeeName.text.toString()
            var Count:Int = TasbeCount.text.toString().toInt()

            // Store the Data On DataBase
            var dbHelper = DBHelper(this , null)
            dbHelper.addName(Tasbee(0 , Name, Count) , this)

            // Display a Message
            Toast.makeText(this, "New Tasbee is Saved" , Toast.LENGTH_LONG).show()

            // Close the Activity
            super.finish()
        }

        // Cancel Btn Functionality
        var cancelBtn:Button = findViewById(R.id.cancelBtn)
        cancelBtn.setOnClickListener{

            super.finish()

        }
    }
}