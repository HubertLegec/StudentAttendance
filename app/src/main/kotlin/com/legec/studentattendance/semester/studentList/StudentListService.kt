package com.legec.studentattendance.semester.studentList

import android.util.Log
import com.legec.studentattendance.faces.FaceDescription
import com.legec.studentattendance.faces.FaceDescriptionRepository
import com.legec.studentattendance.semesterList.SemesterRepository
import com.microsoft.projectoxford.face.FaceServiceClient
import com.microsoft.projectoxford.face.contract.GroupResult
import java.util.*


class StudentListService(
        private val semesterRepository: SemesterRepository,
        private val faceRepository: FaceDescriptionRepository,
        private val faceServiceClient: FaceServiceClient,
        private val studentRepository: StudentRepository
) {
    private val TAG = "StudentListService"

    fun getSemesterFacesGroups(semesterId: String, callback: StudentFacesCallback) {
        val semester = semesterRepository.getSemesterById(semesterId)
        if (!semester.upToDate) {

            groupFaces(semesterId, object : GroupingCallback {
                override fun onPreExecute() {}

                override fun onProgressUpdate(value: String) {
                    Log.d(TAG, "grouping progress: " + value)
                }

                override fun onPostExecute(result: GroupResult) {
                    studentRepository.saveStudentsFaces(result.groups, semesterId)
                    studentRepository.saveUngroupedFaces(result.messyGroup, semesterId)
                    val groupedFaces = getGroupedFaces(semesterId)
                    val ungroupedFaces = getUngroupedFaces(semesterId)
                    callback.onSuccess(groupedFaces, ungroupedFaces)
                }

            })
        } else {
            val groupedFaces = getGroupedFaces(semesterId)
            val ungroupedFaces = getUngroupedFaces(semesterId)
            callback.onSuccess(groupedFaces, ungroupedFaces)
        }
    }

    fun getUngroupedFaces(semesterId: String, callback: StudentFacesCallback) {
        val groupedFaces = getGroupedFaces(semesterId)
        val ungroupedFaces = getUngroupedFaces(semesterId)
        callback.onSuccess(groupedFaces, ungroupedFaces)
    }

    private fun groupFaces(semesterId: String, callback: GroupingCallback) {
        val faces = faceRepository.getSavedFacesForSemester(semesterId)
        if (faces.isEmpty()) {
            return
        }
        val facesUUIDs = faces
                .map { face -> face.faceId }
                .map { id -> UUID.fromString(id) }
        val groupingTask = GroupingTask(faceServiceClient, callback)
        groupingTask.execute(*facesUUIDs.toTypedArray())
    }

    private fun getGroupedFaces(semesterId: String): Map<Student, List<FaceDescription>> {
        return studentRepository.getStudentFaces(semesterId)
    }

    private fun getUngroupedFaces(semesterId: String): List<FaceDescription> {
        return studentRepository.getUngroupedFaces(semesterId)
    }
}