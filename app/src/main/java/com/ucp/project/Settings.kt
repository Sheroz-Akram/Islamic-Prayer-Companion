package com.ucp.project

import android.content.Context
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val cities = arrayOf("Karachi", "Lahore", "Islamabad", "Rawalpindi", "Multan", "Faisalabad", "Gujranwala", "Hyderabad", "Peshawar", "Quetta")

        val arrayAdapter = ArrayAdapter(this, androidx.appcompat.R.layout.select_dialog_item_material, cities)
        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.cityAutoCompleteTextView)
        autocompleteTV.setAdapter(arrayAdapter)

        autocompleteTV.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->

            val selectedCity = cities[position]
            val preferences = getSharedPreferences("App",Context.MODE_PRIVATE)
            preferences.edit().putString("city", selectedCity).apply()

            Toast.makeText(applicationContext, "You have Selected " + cities.get(position), Toast.LENGTH_SHORT).show()
        })
    }
}