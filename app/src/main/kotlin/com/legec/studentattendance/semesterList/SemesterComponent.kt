package com.legec.studentattendance.semesterList

import com.legec.studentattendance.semesterList.MainActivity
import com.legec.studentattendance.AppModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AppModule::class))
interface SemesterComponent {
    fun inject(mainActivity: MainActivity)
}