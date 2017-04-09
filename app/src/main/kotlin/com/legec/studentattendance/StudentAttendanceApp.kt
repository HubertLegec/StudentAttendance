package com.legec.studentattendance

import android.app.Application
import com.legec.studentattendance.faceApi.FaceApiModule
import com.legec.studentattendance.faces.DaggerFacesComponent
import com.legec.studentattendance.faces.FacesComponent
import com.legec.studentattendance.faces.FacesModule
import com.legec.studentattendance.semester.DaggerSemesterComponent
import com.legec.studentattendance.semester.SemesterComponent
import com.legec.studentattendance.semesterList.DaggerSemesterListComponent
import com.legec.studentattendance.semesterList.SemesterListComponent
import io.realm.Realm


class StudentAttendanceApp : Application() {

    companion object {
        lateinit var semesterComponent: SemesterComponent
        lateinit var semesterListComponent: SemesterListComponent
        lateinit var facesComponent: FacesComponent
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        semesterComponent = DaggerSemesterComponent.builder()
                .appModule(AppModule(this))
                .build()
        semesterListComponent = DaggerSemesterListComponent.builder()
                .appModule(AppModule(this))
                .build()
        facesComponent = DaggerFacesComponent.builder()
                .facesModule(FacesModule(this))
                .appModule(AppModule(this))
                .faceApiModule(FaceApiModule(this))
                .build()
    }
}