package com.legec.studentattendance.faces

import android.content.ContentResolver
import android.net.Uri
import com.legec.studentattendance.faceApi.DetectionCallback
import com.legec.studentattendance.faceApi.DetectionTask
import com.legec.studentattendance.utils.compressBitmapToJpeg
import com.legec.studentattendance.utils.loadSizeLimitedBitmap
import com.microsoft.projectoxford.face.FaceServiceClient


class FaceService(val faceServiceClient: FaceServiceClient, val contentResolver: ContentResolver) {

    fun detectAndSave(imageUri: Uri, callback: DetectionCallback) {
        val bitmap = loadSizeLimitedBitmap(imageUri, contentResolver)
        val inputStream = compressBitmapToJpeg(bitmap)
        val detectionTask = DetectionTask(faceServiceClient, callback)
        detectionTask.execute(inputStream)
    }
}