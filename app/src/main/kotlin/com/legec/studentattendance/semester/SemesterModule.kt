package com.legec.studentattendance.semester

import android.app.Application
import com.legec.studentattendance.semester.imagesList.ImageRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class SemesterModule(val mApplication: Application) {

    @Provides
    @Singleton
    fun providesImagesRepository(): ImageRepository {
        return ImageRepository(mApplication.contentResolver)
    }
}