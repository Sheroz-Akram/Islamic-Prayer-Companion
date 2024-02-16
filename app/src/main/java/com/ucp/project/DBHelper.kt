package com.example.assignment03

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.ucp.project.Tasbee


class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = ("CREATE TABLE $TABLE_NAME ($ID_COL INTEGER PRIMARY KEY, $NAME_COl TEXT,$Count_COl INTEGER)")
        db.execSQL(createTableQuery) // Executing the query
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    // Add a New tasbee to The DataBase
    fun addName(tasbee: Tasbee , context: Context ){

        // Getting the Values
        val values = ContentValues()
        values.put(NAME_COl, tasbee.Name)
        values.put(Count_COl, tasbee.Count)

        // Store the Data into Database
        val db = this.writableDatabase
        var Result = db.insert(TABLE_NAME, null, values)

        if(Result == -1.toLong()){
            Toast.makeText(context , "Contact Can not be Saved!" , Toast.LENGTH_LONG ).show()
        }

        db.close()
    }

    // Update a Tasbee Data by Its ID
    fun updateStudent(tasbee: Tasbee  , ID:Int, context: Context ){

        // Getting the Values
        val values = ContentValues()
        values.put(NAME_COl, tasbee.Name)
        values.put(Count_COl, tasbee.Count)

        // Store the Data into Database
        val db = this.writableDatabase
        var Result = db.update(TABLE_NAME,values , ID_COL+"=?" , arrayOf(ID.toString()))
        Toast.makeText(context , "Contact is Updated!" , Toast.LENGTH_SHORT).show()

        db.close()
    }

    // This Function will remove only the Select Tasbee
    fun deleteData(ID:Int , context: Context){
        var db = this.writableDatabase

        db.delete(TABLE_NAME , ID_COL+"=?" , arrayOf(ID.toString()))
        db.close()
    }

    // This Function will remove all the Contacts in Data Base
    fun deleteAllData( context: Context){
        var db = this.writableDatabase
        db.delete(TABLE_NAME , null , null)
        db.close()
    }


    // Read all Data and Return into Arraylist
    @SuppressLint("Range")
    fun readData(keywordSearch : String) : ArrayList<Tasbee> {

        var tasbeeList = ArrayList<Tasbee>()

        val db = this.readableDatabase

        var query = ""

        if(keywordSearch.length == 0){
            query = "SELECT * FROM $TABLE_NAME ORDER BY $NAME_COl"
        }
        else{
            query =
                "SELECT * FROM $TABLE_NAME WHERE $NAME_COl LIKE '%$keywordSearch%' ORDER BY $NAME_COl"
        }

        var cursor = db.rawQuery(query , null)

        if(cursor.moveToFirst()){
            do {
                var ID: Int = cursor.getString(cursor.getColumnIndex(ID_COL)).toInt()
                var Name: String = cursor.getString(cursor.getColumnIndex(NAME_COl)).toString()
                var Count:Int = cursor.getString(cursor.getColumnIndex(Count_COl)).toInt()
                var newTasbee:Tasbee = Tasbee(ID, Name, Count)


                tasbeeList.add(newTasbee)

            }
            while (cursor.moveToNext())
        }

        cursor.close()


        return tasbeeList

    }

    companion object{
        //TODO: Database
        private val DATABASE_NAME = "IslamicCompanionApp"
        private val DATABASE_VERSION = 1

        //TODO: Table
        val TABLE_NAME = "Tasbee_Data"

        //TODO: Columns
        val ID_COL = "Id"
        val NAME_COl = "Name"
        val Count_COl = "Roll"
    }
}
