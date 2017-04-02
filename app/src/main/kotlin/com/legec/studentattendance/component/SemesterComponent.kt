package com.legec.studentattendance.component

import com.legec.studentattendance.activity.MainActivity
import com.legec.studentattendance.module.AppModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AppModule::class))
interface SemesterComponent {
    fun inject(mainActivity: MainActivity)
}