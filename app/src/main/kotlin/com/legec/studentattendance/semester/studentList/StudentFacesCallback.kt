package com.legec.studentattendance.semester.studentList

import com.legec.studentattendance.faces.FaceDescription


interface StudentFacesCallback {
    fun onSuccess(result: Map<Student, List<FaceDescription>>, ungrouped: List<FaceDescription>)
}