package com.legec.studentattendance.semester.imagesList

import android.net.Uri
import android.util.Log
import io.realm.Realm
import java.util.*
import javax.inject.Singleton


@Singleton
class ImageRepository {
    private val TAG = "ImageRepository"
    private val realm = Realm.getDefaultInstance()

    fun saveImage(imageUri: Uri, date: Date, semesterId: String): Image {
        Log.i(TAG, "Save image")
        realm.beginTransaction()
        val image = realm.createObject(Image::class.java, UUID.randomUUID().toString())
        image.updateValues(imageUri, date, semesterId)
        realm.commitTransaction()
        return image
    }

    fun getImagesForSemester(semesterId: String): List<Image> {
        return realm
                .where(Image::class.java)
                .equalTo("semesterId", semesterId)
                .findAll()
                .toList()
    }

    fun deleteImage(imageId: String) {
        Log.i(TAG, "Delete image with id: " + imageId)
        realm.beginTransaction()
        realm.where(Image::class.java)
                .equalTo("id", imageId)
                .findFirst()
                ?.deleteFromRealm()
        realm.commitTransaction()
    }
}