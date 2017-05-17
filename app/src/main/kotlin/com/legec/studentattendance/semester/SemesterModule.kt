package com.legec.studentattendance.semester

import com.legec.studentattendance.AppModule
import com.legec.studentattendance.faces.FaceDescriptionRepository
import com.legec.studentattendance.faces.FacesModule
import com.legec.studentattendance.semester.studentList.StudentListService
import com.legec.studentattendance.semester.studentList.StudentRepository
import com.legec.studentattendance.semesterList.SemesterRepository
import com.microsoft.projectoxford.face.FaceServiceClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = arrayOf(FacesModule::class, AppModule::class))
class SemesterModule {

    @Singleton
    @Provides
    fun providesStudentListService(
            semesterRepository: SemesterRepository,
            faceRepository: FaceDescriptionRepository,
            faceServiceClient: FaceServiceClient,
            studentRepository: StudentRepository): StudentListService {
        return StudentListService(semesterRepository, faceRepository, faceServiceClient, studentRepository)
    }

    @Singleton
    @Provides
    fun providesStudentRepository(): StudentRepository {
        return StudentRepository()
    }
}