package com.legec.studentattendance.semester.ungroupedFaces

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.legec.studentattendance.R
import com.legec.studentattendance.StudentAttendanceApp
import com.legec.studentattendance.faces.FaceDescription
import com.legec.studentattendance.faces.FacesGridAdapter
import com.legec.studentattendance.semester.studentList.Student
import com.legec.studentattendance.semester.studentList.StudentFacesCallback
import com.legec.studentattendance.semester.studentList.StudentListService
import javax.inject.Inject


class UngroupedFacesFragment(private val semesterId: String) : Fragment() {
    private val TAG = "UngroupedFacesFragment"
    lateinit private var unbinder: Unbinder
    lateinit private var adapter: FacesGridAdapter
    @Inject lateinit var studentListService: StudentListService

    @BindView(R.id.ungrouped_recycler_view)
    lateinit var recyclerView: RecyclerView
    @BindView(R.id.ungrouped_progressbar_view)
    lateinit var loader: LinearLayout

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater!!.inflate(R.layout.fragment_ungrouped, container, false)
        unbinder = ButterKnife.bind(this, rootView)
        StudentAttendanceApp.semesterComponent.inject(this)
        val layoutManager = GridLayoutManager(this.context, 2)
        adapter = FacesGridAdapter(this.context)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter
        loader.visibility = View.GONE
        return rootView
    }

    override fun onStart() {
        super.onStart()
        loader.visibility = View.VISIBLE
        studentListService.getUngroupedFaces(semesterId, object : StudentFacesCallback {
            override fun onSuccess(result: Map<Student, List<FaceDescription>>, ungrouped: List<FaceDescription>) {
                Log.i(TAG, "faces success")
                adapter.addFaces(ungrouped)
                loader.visibility = View.GONE
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }
}