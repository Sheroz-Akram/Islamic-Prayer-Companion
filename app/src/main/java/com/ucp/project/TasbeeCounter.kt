package com.ucp.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import com.example.assignment03.DBHelper

class TasbeeCounter : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasbee_counter)

        // Adjust the Add Btn
        var addBtn: View = findViewById(R.id.fab)
        addBtn.setOnClickListener{
            var addIntent = Intent(this, AddTasbee::class.java)
            startActivity(addIntent)
        }

        // Load All the Tasbee
        loadTasbee()
    }

    override fun onResume() {
        super.onResume()
        loadTasbee()
    }
    fun loadTasbee(){

        // Connect to the Database and Retrieve All the Tasbee
        var dbHelper = DBHelper(this, null)
        var arrayList = dbHelper.readData("")

        // Display Into Our List View
        var ourListView: ListView = findViewById(R.id.tasbeeListView)

        // Set our Adapter and Begin
        ourListView.adapter = CustomAdapterTasbeeAdapter(this, arrayList)

        // When User Press on Tasbee
        ourListView.setOnItemClickListener{ parent, view, position, id ->

            var intent: Intent = Intent(this , TasbeeManager::class.java)
            intent.putExtra("ID" , arrayList.get(position).ID)
            intent.putExtra("Name" , arrayList.get(position).Name)
            intent.putExtra("Count" , arrayList.get(position).Count)
            startActivity(intent)

        }

    }
}