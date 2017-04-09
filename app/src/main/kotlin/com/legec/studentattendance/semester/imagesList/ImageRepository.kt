package com.legec.studentattendance.semester.imagesList

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import com.legec.studentattendance.semester.getImageTakenDate
import io.realm.Realm
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton


@Singleton
class ImageRepository(private val contentResolver: ContentResolver) {
    private val TAG = "ImageRepository"
    private val realm = Realm.getDefaultInstance()

    fun saveImage(imageUri: Uri, semesterId: String, fromCamera: Boolean): Image {
        Log.i(TAG, "Save image")
        val f = SimpleDateFormat("dd-MM-yyyy HH:mm")
        val dateTaken = f.format(getImageTakenDate(imageUri, contentResolver))
        realm.beginTransaction()
        val image = realm.createObject(Image::class.java, UUID.randomUUID().toString())
        image.updateValues(imageUri, dateTaken, semesterId, fromCamera)
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
        val img = realm.where(Image::class.java)
                .equalTo("id", imageId)
                .findFirst()
        if (img != null) {
            deleteImageIfFromCamera(img)
            img.deleteFromRealm()
        }
        realm.commitTransaction()
    }

    fun deleteImagesBySemester(semesterId: String) {
        Log.i(TAG, "Delete images associated with semester: " + semesterId)
        realm.where(Image::class.java)
                .equalTo("semesterId", semesterId)
                .findAll()
                .map {img -> img.id }
                .forEach { id -> deleteImage(id) }
    }

    private fun deleteImageIfFromCamera(img: Image) {
        if (img.fromCamera) {
            try {
            val file = File(img.uri)
            file.delete()
            } catch (e: IOException) {
                Log.e(TAG, e.message)
            }
        }
    }
}