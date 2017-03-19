package com.legec.studentattendance.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.legec.studentattendance.R
import com.legec.studentattendance.adapter.SemesterListAdapter
import com.legec.studentattendance.dialog.NewSemesterDialog
import com.legec.studentattendance.model.Semester

class MainActivity : Activity() {
    val SEMESTER_MESSAGE = "com.legec.StudentAttendance.SEMESTER_MESSAGE"
    @BindView(R.id.list)
    lateinit var semesterList : ListView

    lateinit var semesterListAdapter : SemesterListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        semesterListAdapter = SemesterListAdapter(this, {id ->
            semesterListAdapter.deleteElem(id)
        })
        semesterList.adapter = semesterListAdapter
        semesterList.choiceMode = ListView.CHOICE_MODE_SINGLE
        semesterList.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            run {
                val semester: Semester = semesterListAdapter.getItem(position) as Semester
                val intent = Intent(this, SemesterActivity::class.java)
                intent.putExtra(SEMESTER_MESSAGE, semester.id)
                startActivity(intent)
            }
        }
    }

    @OnClick(R.id.add_semester_button)
    fun onAddSemester() {
        val dialog = NewSemesterDialog({subject, semester ->
            val sem = Semester(subject, semester, 1)
            semesterListAdapter.addElement(sem)
        })
        dialog.show(fragmentManager, "NewSemesterDialogFragment")
    }
}
