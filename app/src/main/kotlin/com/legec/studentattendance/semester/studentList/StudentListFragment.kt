package com.legec.studentattendance.semester.studentList

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.legec.studentattendance.R
import com.legec.studentattendance.StudentAttendanceApp
import com.legec.studentattendance.faces.FaceDescription
import javax.inject.Inject


class StudentListFragment(private val semesterId: String) : Fragment() {
    private val TAG = "StudentListFragment"
    lateinit private var adapter: StudentListAdapter
    lateinit private var unbinder: Unbinder
    @Inject lateinit var studentListService: StudentListService

    @BindView(R.id.student_recycler_view)
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater!!.inflate(R.layout.fragment_student_list, container, false)
        unbinder = ButterKnife.bind(this, rootView)
        StudentAttendanceApp.semesterComponent.inject(this)
        val layoutManager = GridLayoutManager(this.context, 1)
        adapter = StudentListAdapter(this.context)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        return rootView
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            studentListService.getSemesterFacesGroups(semesterId, object : StudentFacesCallback {
                override fun onSuccess(result: Map<Student, List<FaceDescription>>) {
                    Log.i(TAG, "faces success")
                    adapter.update(result)
                    adapter.notifyDataSetChanged()
                }

            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }
}