package com.legec.studentattendance.semester.studentList

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class Student : RealmObject() {
    @PrimaryKey
    var id: String = ""
    var semesterId: String = ""
    var name: String = "student name"
}