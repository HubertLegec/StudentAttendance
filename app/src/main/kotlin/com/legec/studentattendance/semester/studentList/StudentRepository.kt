package com.legec.studentattendance.semester.studentList

import android.util.Log
import com.legec.studentattendance.faces.FaceDescription
import com.legec.studentattendance.semesterList.Semester
import io.realm.Realm
import java.util.*


class StudentRepository {
    private val TAG = "StudentRepository"
    private val realm = Realm.getDefaultInstance()

    fun saveStudentsFaces(faces: List<Array<UUID>>, semesterId: String) {
        Log.i(TAG, "Detected students: " + faces.size)
        realm.beginTransaction()
        realm.where(Student::class.java)
                .equalTo("semesterId", semesterId)
                .findAll()
                .deleteAllFromRealm()
        faces.forEach { faceGroup ->
            createStudent(faceGroup, semesterId)
        }
        realm.where(Semester::class.java)
                .equalTo("id", semesterId)
                .findFirst()
                .upToDate = true
        realm.commitTransaction()
    }

    fun saveUngroupedFaces(ungrouped: List<UUID>, semesterId: String) {
        Log.i(TAG, "Ungrouped faces: " + ungrouped.size)
        val faceIds = ungrouped.map { uuid -> uuid.toString()}.toTypedArray()
        realm.beginTransaction()
        realm.where(FaceDescription::class.java)
                .equalTo("semesterId", semesterId)
                .`in`("faceId", faceIds)
                .findAll()
                .forEach { f -> f.studentId = "" }
        realm.commitTransaction()
    }

    fun getStudentFaces(semesterId: String): Map<Student, List<FaceDescription>> {
        Log.i(TAG, "get faces for semester: " + semesterId)
        return realm.where(Student::class.java)
                .equalTo("semesterId", semesterId)
                .findAll()
                .associateBy({student -> student}, {student ->
                    realm.where(FaceDescription::class.java)
                            .equalTo("studentId", student.id)
                            .findAll()
                            .toList()
                })
    }

    fun getUngroupedFaces(semesterId: String): List<FaceDescription> {
        Log.i(TAG, "get ungrouped faces for semester: " + semesterId)
        return realm.where(FaceDescription::class.java)
                .equalTo("semesterId", semesterId)
                .isEmpty("studentId")
                .findAll()
                .toList()
    }

    fun updateStudentName(name: String, id: String) {
        realm.beginTransaction()
        realm.where(Student::class.java)
                .equalTo("id", id)
                .findFirst()
                .name = name
        realm.commitTransaction()
    }

    private fun createStudent(faceGroup: Array<UUID>, semesterId: String) {
        val studentId = UUID.randomUUID().toString()
        val student = realm.createObject(Student::class.java, studentId)
        student.semesterId = semesterId
        assignFacesToStudent(studentId, faceGroup)
    }

    private fun assignFacesToStudent(studentId: String, faceUUIDs: Array<UUID>) {
        val faceIds = faceUUIDs.map { uuid -> uuid.toString()}.toTypedArray()
        realm.where(FaceDescription::class.java)
                .`in`("faceId", faceIds)
                .findAll()
                .forEach { f -> f.studentId = studentId }
    }
}