package com.legec.studentattendance.faces

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.legec.studentattendance.semester.imagesList.Image
import com.legec.studentattendance.utils.generateFaceThumbnail
import com.legec.studentattendance.utils.loadSizeLimitedBitmap
import com.legec.studentattendance.utils.savebitmap
import com.microsoft.projectoxford.face.contract.Face
import io.realm.Realm
import java.util.*


class FaceDescriptionRepository(val contentResolver: ContentResolver) {
    private val TAG = "FacesRepository"
    private val realm: Realm = Realm.getDefaultInstance()

    fun getSavedFacesForImage(imageId: String): List<FaceDescription> {
        Log.i(TAG, "get faces for image: " + imageId)
        return realm.where(FaceDescription::class.java)
                .equalTo("imageId", imageId)
                .findAll()
    }

    fun getSavedFacesForSemester(semesterId: String): List<FaceDescription> {
        return realm.where(FaceDescription::class.java)
                .equalTo("semesterId", semesterId)
                .findAll()
    }

    fun getSavedFacesForStudent(studentId: String): List<FaceDescription> {
        return realm.where(FaceDescription::class.java)
                .equalTo("studentId", studentId)
                .findAll()
    }

    fun saveFaces(faces: List<Face>, imageId: String, imageUri: Uri): List<FaceDescription> {
        Log.i(TAG, "save faces for image: " + imageId)
        val bitmap = loadSizeLimitedBitmap(imageUri, contentResolver)
        realm.beginTransaction()
        val semesterId = realm.where(Image::class.java)
                .equalTo("id", imageId)
                .findFirst()
                .semesterId
        val faceDescriptions = faces.map { face -> createFaceDescription(face, imageId, semesterId, bitmap) }
        realm.commitTransaction()
        return faceDescriptions
    }

    fun deleteDescriptionsForImage(imageId: String) {
        Log.i(TAG, "delete faces for image: " + imageId)
        realm.beginTransaction()
        realm.where(FaceDescription::class.java)
                .equalTo("imageId", imageId)
                .findAll()
                .deleteAllFromRealm()
        realm.commitTransaction()
    }

    private fun createFaceDescription(face: Face, imageId: String, semesterId: String, originalImage: Bitmap): FaceDescription {
        val thumbnail = generateFaceThumbnail(originalImage, face.faceRectangle)
        val thumbnailUri = savebitmap(thumbnail, face.faceId.toString())
        val faceDesc: FaceDescription = realm.createObject(FaceDescription::class.java, UUID.randomUUID().toString())
        faceDesc.fillValuesFromFace(face, imageId, semesterId, thumbnailUri)
        return faceDesc
    }
}