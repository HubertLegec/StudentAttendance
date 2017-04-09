package com.legec.studentattendance.semester

import com.legec.studentattendance.semester.imagesList.ImageRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class SemesterModule {

    @Provides
    @Singleton
    fun providesImagesRepository(): ImageRepository {
        return ImageRepository()
    }
}