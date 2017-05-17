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
            val studentId = UUID.randomUUID().toString()
            val student = realm.createObject(Student::class.java, studentId)
            student.semesterId = semesterId
            assignFacesToStudent(studentId, faceGroup)
        }
        realm.where(Semester::class.java)
                .equalTo("id", semesterId)
                .findFirst()
                .upToDate = true
        realm.commitTransaction()
    }

    private fun assignFacesToStudent(studentId: String, faceUUIDs: Array<UUID>) {
        val faceIds = faceUUIDs.map { uuid -> uuid.toString()}.toTypedArray()
        realm.where(FaceDescription::class.java)
                .`in`("faceId", faceIds)
                .findAll()
                .forEach { f -> f.studentId = studentId }
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
}