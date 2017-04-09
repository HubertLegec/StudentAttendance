package com.legec.studentattendance.faceApi

import android.content.ContentResolver
import com.microsoft.projectoxford.face.FaceServiceClient
import java.util.*


class FaceApiService(val faceServiceClient: FaceServiceClient, val contentResolver: ContentResolver) {

    fun group(uuids: List<UUID>, callback: GroupingCallback) {
        val groupingTask = GroupingTask(faceServiceClient, callback)
        groupingTask.execute(*uuids.toTypedArray())
    }
}