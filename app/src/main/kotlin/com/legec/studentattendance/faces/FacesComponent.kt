package com.legec.studentattendance.faces

import com.legec.studentattendance.AppModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(FacesModule::class, AppModule::class))
interface FacesComponent {
    fun inject(facesActivity: FacesActivity)
}