package com.legec.studentattendance

import android.app.Application
import com.legec.studentattendance.component.DaggerSemesterComponent
import com.legec.studentattendance.component.SemesterComponent
import com.legec.studentattendance.module.AppModule


class StudentAttendanceApp : Application() {

    companion object {
        lateinit var semesterComponent: SemesterComponent
    }

    override fun onCreate() {
        super.onCreate()
        semesterComponent = DaggerSemesterComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}