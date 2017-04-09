package com.legec.studentattendance

import android.app.Application
import com.legec.studentattendance.faceApi.FaceApiModule
import com.legec.studentattendance.semester.DaggerSemesterComponent
import com.legec.studentattendance.semester.SemesterComponent
import com.legec.studentattendance.semester.SemesterModule
import com.legec.studentattendance.semesterList.DaggerSemesterListComponent
import com.legec.studentattendance.semesterList.SemesterListComponent
import io.realm.Realm


class StudentAttendanceApp : Application() {

    companion object {
        lateinit var semesterComponent: SemesterComponent
        lateinit var semesterListComponent: SemesterListComponent
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        semesterComponent = DaggerSemesterComponent.builder()
                .faceApiModule(FaceApiModule(this))
                .semesterModule(SemesterModule())
                .build()
        semesterListComponent = DaggerSemesterListComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}