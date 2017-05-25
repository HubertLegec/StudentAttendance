package com.legec.studentattendance.semester.imagesList

import android.net.Uri
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class Image : RealmObject() {
    @PrimaryKey
    var id: String = ""
    var uri: String = ""
    var date: String = ""
    var semesterId: String = ""
    var fromCamera: Boolean = false

    fun updateValues(uri: Uri, date: String, semesterId: String, fromCamera: Boolean) {
        this.uri = uri.toString()
        this.date = date
        this.semesterId = semesterId
        this.fromCamera = fromCamera
    }

    fun getUri(): Uri {
        return Uri.parse(uri)
    }
}