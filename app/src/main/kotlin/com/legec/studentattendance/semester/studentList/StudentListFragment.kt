package com.legec.studentattendance.semester.studentList

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.ListView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.legec.studentattendance.R
import com.legec.studentattendance.StudentAttendanceApp
import com.legec.studentattendance.faces.FaceDescription
import com.legec.studentattendance.faces.FacesActivity
import javax.inject.Inject


class StudentListFragment(private val semesterId: String) : Fragment() {
    private val STUDENT_MESSAGE = "com.legec.StudentAttendance.STUDENT_MESSAGE"
    private val TAG = "StudentListFragment"
    lateinit private var adapter: StudentListAdapter
    lateinit private var unbinder: Unbinder
    @Inject lateinit var studentListService: StudentListService

    @BindView(R.id.student_list)
    lateinit var studentList: ListView
    @BindView(R.id.progressbar_view)
    lateinit var loader: LinearLayout

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater!!.inflate(R.layout.fragment_student_list, container, false)
        unbinder = ButterKnife.bind(this, rootView)
        StudentAttendanceApp.semesterComponent.inject(this)
        adapter = StudentListAdapter(this.context)
        studentList.adapter = adapter
        studentList.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    onStudentClick(adapter.getStudent(position))
                }
        loader.visibility = View.GONE
        return rootView
    }

    override fun onStart() {
        super.onStart()
        loader.visibility = View.VISIBLE
        adapter.update(emptyMap())
        adapter.notifyDataSetChanged()
        studentListService.getSemesterFacesGroups(semesterId, object : StudentFacesCallback {
            override fun onSuccess(result: Map<Student, List<FaceDescription>>, ungrouped: List<FaceDescription>) {
                Log.i(TAG, "faces success")
                adapter.update(result)
                adapter.notifyDataSetChanged()
                loader.visibility = View.GONE
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }

    private fun onStudentClick(student: Student) {
        val intent = Intent(this.activity, FacesActivity::class.java)
        intent.putExtra(STUDENT_MESSAGE, student.id)
        startActivity(intent)
    }
}