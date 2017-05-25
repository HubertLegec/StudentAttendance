package com.legec.studentattendance.semesterList

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.AdapterView
import android.widget.ListView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.legec.studentattendance.R
import com.legec.studentattendance.StudentAttendanceApp
import com.legec.studentattendance.semester.SemesterActivity
import com.legec.studentattendance.semester.imagesList.ImageRepository
import javax.inject.Inject


class SemesterListActivity : AppCompatActivity() {
    private val SEMESTER_MESSAGE = "com.legec.StudentAttendance.SEMESTER_MESSAGE"
    @BindView(R.id.list)
    lateinit var semesterList: ListView

    lateinit var semesterListAdapter: SemesterListAdapter

    @Inject lateinit var semesterRepository: SemesterRepository
    @Inject lateinit var imageRepository: ImageRepository

    private val deleteSemesterCallback = { id: String ->
        semesterListAdapter.deleteElem(id)
        semesterRepository.deleteSemester(id)
        imageRepository.deleteImagesBySemester(id)
    }

    private val editSemesterCallback = { id: String, oldSubject: String, oldSemester: String ->
        onEditSemester(id, oldSubject, oldSemester)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        StudentAttendanceApp.semesterListComponent.inject(this)
        semesterListAdapter = SemesterListAdapter(
                LayoutInflater.from(this),
                deleteSemesterCallback,
                editSemesterCallback
        )
        semesterListAdapter.addAll(semesterRepository.getSavedSemesters())
        semesterList.adapter = semesterListAdapter
        semesterList.choiceMode = ListView.CHOICE_MODE_SINGLE
        semesterList.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            openSemesterActivity(position)
        }
    }

    @OnClick(R.id.add_semester_button)
    fun onAddSemester() {
        val dialog = NewSemesterDialog({ subject, semester ->
            val sem = semesterRepository.saveSemester(subject, semester)
            semesterListAdapter.addElement(sem)
        })
        dialog.show(fragmentManager, "NewSemesterDialogFragment")
    }

    private fun onEditSemester(id: String, oldSubject: String, oldSemester: String) {
        val dialog = NewSemesterDialog({ subject, semester ->
            if (oldSemester != semester || oldSubject != subject) {
                semesterListAdapter.editElement(id, subject, semester)
            }
        }, oldSubject, oldSemester)
        dialog.show(fragmentManager, "EditSemesterDialogFragment")
    }

    private fun openSemesterActivity(position: Int) {
        val semester: Semester = semesterListAdapter.getItem(position) as Semester
        val intent = Intent(this, SemesterActivity::class.java)
        intent.putExtra(SEMESTER_MESSAGE, semester.id)
        startActivity(intent)
    }
}
