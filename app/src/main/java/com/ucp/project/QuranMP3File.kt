package com.ucp.project

class QuranMP3File(var ID:Int , var Name:String) {
    var URL:String = ""

    init {
        val correctFormatID = "%03d".format(ID)
        URL = "https://server6.mp3quran.net/download/thubti/$correctFormatID.mp3"
    }
}