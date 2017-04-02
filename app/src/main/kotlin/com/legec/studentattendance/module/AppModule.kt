package com.legec.studentattendance.module

import android.app.Application
import android.content.Context
import com.legec.studentattendance.repository.SemesterRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule (val mApplication: Application){

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
