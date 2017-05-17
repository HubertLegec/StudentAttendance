package com.legec.studentattendance.faces

import android.net.Uri
import com.microsoft.projectoxford.face.contract.Face
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.net.URI


open class FaceDescription: RealmObject() {
    @PrimaryKey
    var id: String = ""
    var imageId: String = ""
    var faceId: String = ""
    var imageUriPath: String = ""
    var semesterId: String = ""
    var width: Int = 0
    var height: Int = 0
    var left: Int = 0
    var top: Int = 0
    var studentId: String = ""

    fun fillValuesFromFace(face: Face, imageId: String, semesterId: String, thumbnailUri: URI) {
        faceId = face.faceId.toString()
        width = face.faceRectangle.width
        height = face.faceRectangle.height
        left = face.faceRectangle.left
        top = face.faceRectangle.top
        imageUriPath = thumbnailUri.toString()
        this.imageId = imageId
        this.semesterId = semesterId
    }

    fun getUri(): Uri {
        return Uri.parse(imageUriPath)
    }
}