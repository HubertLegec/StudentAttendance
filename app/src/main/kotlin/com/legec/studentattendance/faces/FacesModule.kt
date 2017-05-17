package com.legec.studentattendance.faces

import android.app.Application
import com.legec.studentattendance.AppModule
import com.legec.studentattendance.R
import com.microsoft.projectoxford.face.FaceServiceClient
import com.microsoft.projectoxford.face.FaceServiceRestClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = arrayOf(AppModule::class))
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

    @Provides
    @Singleton
    fun providesFaceServiceClient(): FaceServiceClient {
        return FaceServiceRestClient(mApplication.getString(R.string.face_api_subscription_key))
    }
}