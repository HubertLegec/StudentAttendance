package com.legec.studentattendance.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class Semester : RealmObject() {
    @PrimaryKey
    var id: String = ""
    var subjectName: String = ""
    var semesterName: String = ""
}