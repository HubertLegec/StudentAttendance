package com.legec.studentattendance.faceApi

import com.microsoft.projectoxford.face.FaceServiceClient
import java.io.InputStream
import java.util.*


class FaceApiService(val faceServiceClient: FaceServiceClient) {

    fun group(uuids: List<UUID>, callback: GroupingCallback) {
        val groupingTask = GroupingTask(faceServiceClient, callback)
        groupingTask.execute(*uuids.toTypedArray())
    }

    fun detect(inputStream: InputStream, callback: DetectionCallback) {
        val detectionTask = DetectionTask(faceServiceClient, callback)
        detectionTask.execute(inputStream)
    }
}