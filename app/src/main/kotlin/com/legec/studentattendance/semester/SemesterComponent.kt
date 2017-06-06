package com.legec.studentattendance.semester

import com.legec.studentattendance.semester.imagesList.ImageListFragment
import com.legec.studentattendance.semester.studentList.StudentListFragment
import com.legec.studentattendance.semester.ungroupedFaces.UngroupedFacesFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(SemesterModule::class))
interface SemesterComponent {
    fun inject(imageListFragment: ImageListFragment)
    fun inject(studentListFragment: StudentListFragment)
    fun inject(ungroupedFacesFragment: UngroupedFacesFragment)
}