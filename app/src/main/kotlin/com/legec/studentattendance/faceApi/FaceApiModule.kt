package com.legec.studentattendance.faceApi

import android.app.Application
import com.legec.studentattendance.R
import com.microsoft.projectoxford.face.FaceServiceClient
import com.microsoft.projectoxford.face.FaceServiceRestClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FaceApiModule(val mApplication: Application) {

    @Provides
    @Singleton
    fun providesFaceServiceClient(): FaceServiceClient {
        return FaceServiceRestClient(mApplication.getString(R.string.face_api_subscription_key))
    }

    @Provides
    @Singleton
    fun providesGroupingService(faceServiceClient: FaceServiceClient): FaceApiService {
        return FaceApiService(faceServiceClient)
    }
}