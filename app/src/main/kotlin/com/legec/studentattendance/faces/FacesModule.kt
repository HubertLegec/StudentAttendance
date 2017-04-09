package com.legec.studentattendance.faces

import android.app.Application
import com.legec.studentattendance.AppModule
import com.legec.studentattendance.faceApi.FaceApiModule
import com.microsoft.projectoxford.face.FaceServiceClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = arrayOf(FaceApiModule::class, AppModule::class))
class FacesModule(val mApplication: Application) {

    @Singleton
    @Provides
    fun providesFaceDescriptionRepository(): FaceDescriptionRepository {
        return FaceDescriptionRepository(mApplication.contentResolver)
    }

    @Singleton
    @Provides
    fun providesFaceService(faceServiceClient: FaceServiceClient): FaceService {
        return FaceService(
                faceServiceClient, mApplication.contentResolver
        )
    }
}