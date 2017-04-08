package com.legec.studentattendance

import android.app.Application
import android.content.Context
import com.legec.studentattendance.semesterList.SemesterRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val mApplication: Application) {

    @Provides
    @Singleton
    fun providesContext(): Context {
        return mApplication
    }

    @Provides
    @Singleton
    fun providesApplication(): Application {
        return mApplication
    }

    @Provides
    @Singleton
    fun providesSemesterRepository(): SemesterRepository {
        return SemesterRepository()
    }
}
