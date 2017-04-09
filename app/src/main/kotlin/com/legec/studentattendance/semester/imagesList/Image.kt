package com.legec.studentattendance.semester.imagesList

import android.net.Uri
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*


open class Image: RealmObject() {
    @PrimaryKey
    var id: String = ""
    var uri: String = ""
    var date: Date = Date()
    var semesterId: String = ""

    fun updateValues(uri: Uri, date: Date, semesterId: String){
        this.uri = uri.toString()
        this.date = date
        this.semesterId = semesterId
    }

    fun getUri(): Uri {
        return Uri.parse(uri)
    }
}