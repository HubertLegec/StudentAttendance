package com.legec.studentattendance.semester.studentList

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.legec.studentattendance.R
import com.legec.studentattendance.StudentAttendanceApp
import com.legec.studentattendance.faces.FaceDescription
import javax.inject.Inject


class StudentListFragment(private val semesterId: String) : Fragment() {
    private val TAG = "StudentListFragment"
    lateinit private var unbinder: Unbinder
    @Inject lateinit var studentListService: StudentListService

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater!!.inflate(R.layout.fragment_student_list, container, false)
        unbinder = ButterKnife.bind(this, rootView)
        StudentAttendanceApp.semesterComponent.inject(this)
        return rootView
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            studentListService.getSemesterFacesGroups(semesterId, object : StudentFacesCallback {
                override fun onSuccess(result: Map<Student, List<FaceDescription>>) {
                    Log.i(TAG, "faces success")
                }

            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }
}