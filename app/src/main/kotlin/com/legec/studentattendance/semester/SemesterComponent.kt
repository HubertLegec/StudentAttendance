package com.legec.studentattendance.semester

import com.legec.studentattendance.faceApi.FaceApiModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(FaceApiModule::class))
interface SemesterComponent {
    fun inject(semesterActivity: SemesterActivity)
}