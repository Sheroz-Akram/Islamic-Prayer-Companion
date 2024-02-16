package com.ucp.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import org.json.JSONObject

class QuranRecitation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quran_recitation)

        var quranRecitationListView:ListView = findViewById(R.id.quranRecitationListView)

        GetIslamicDateTask { result ->
            var arrayList:ArrayList<QuranMP3File> = ArrayList<QuranMP3File>()
            var data = JSONObject(result)
            var ourArray = data.getJSONArray("suwar")

            (0 until ourArray.length()).forEach { i ->

                var itemData = ourArray.getJSONObject(i)
                arrayList.add(QuranMP3File(itemData.getInt("id") , itemData.getString("name")))

            }

            var quranRecitationListView:ListView = findViewById(R.id.quranRecitationListView)
            quranRecitationListView.adapter = CustomAdapterQuranMP3File(this , arrayList)

            quranRecitationListView.setOnItemClickListener{ parent, view, position, id ->

                var intent: Intent = Intent(this , PlayMP3::class.java)
                intent.putExtra("ID" , arrayList.get(position).ID)
                intent.putExtra("Name" , arrayList.get(position).Name)
                intent.putExtra("URL" , arrayList.get(position).URL)
                startActivity(intent)

            }


        }.execute("https://mp3quran.net/api/v3/suwar?language=eng")
    }
}
