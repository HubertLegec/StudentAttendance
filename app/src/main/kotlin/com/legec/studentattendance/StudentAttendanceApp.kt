package com.legec.studentattendance

import android.app.Application
import com.legec.studentattendance.semesterList.DaggerSemesterComponent
import com.legec.studentattendance.semesterList.SemesterComponent
import io.realm.Realm


class StudentAttendanceApp : Application() {

    companion object {
        lateinit var semesterComponent: SemesterComponent
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        semesterComponent = DaggerSemesterComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}