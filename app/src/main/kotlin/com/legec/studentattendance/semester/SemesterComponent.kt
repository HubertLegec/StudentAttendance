package com.legec.studentattendance.semester

import com.legec.studentattendance.AppModule
import com.legec.studentattendance.semester.imagesList.ImageListFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AppModule::class))
interface SemesterComponent {
    fun inject(imageListFragment: ImageListFragment)
}