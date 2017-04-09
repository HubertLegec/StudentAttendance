package com.legec.studentattendance.semester

import com.legec.studentattendance.faceApi.FaceApiModule
import com.legec.studentattendance.semester.imagesList.ImageListFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(FaceApiModule::class, SemesterModule::class))
interface SemesterComponent {
    fun inject(semesterActivity: SemesterActivity)
    fun inject(imageListFragment: ImageListFragment)
}