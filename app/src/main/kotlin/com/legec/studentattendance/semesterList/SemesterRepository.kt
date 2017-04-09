package com.legec.studentattendance.semesterList

import android.util.Log
import io.realm.Realm
import java.util.*



class SemesterRepository {
    private val TAG = "SemesterRepository"
    private val realm: Realm = Realm.getDefaultInstance()

    fun saveSemester(subjectName: String, semesterName: String) : Semester {
        realm.beginTransaction()
        val semester = realm.createObject(Semester::class.java, UUID.randomUUID().toString())
        Log.i(TAG, "Save semester: " + semester.id)
        semester.semesterName = semesterName
        semester.subjectName = subjectName
        realm.commitTransaction()
        return semester
    }

    fun getSavedSemesters() : List<Semester> {
        return realm
                .where(Semester::class.java)
                .findAll()
                .toList()
    }

    fun deleteSemester(id: String) {
        Log.i(TAG, "Delete semester: " + id)
        realm.beginTransaction()
        realm.where(Semester::class.java)
                .equalTo("id", id)
                .findFirst()
                ?.deleteFromRealm()
        realm.commitTransaction()
    }
}

