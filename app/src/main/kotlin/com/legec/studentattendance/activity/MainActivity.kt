package com.legec.studentattendance.activity

import android.app.Activity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import butterknife.BindView
import butterknife.ButterKnife
import com.legec.studentattendance.R
import com.legec.studentattendance.adapter.SemesterListAdapter
import com.legec.studentattendance.callback.DeleteSemesterCallback
import com.legec.studentattendance.model.Semester

class MainActivity : Activity(), DeleteSemesterCallback {

    @BindView(R.id.list)
    lateinit var semesterList : ListView

    lateinit var semesterListAdapter : SemesterListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        semesterListAdapter = SemesterListAdapter(this, this)
        semesterList.adapter = semesterListAdapter
        semesterList.choiceMode = ListView.CHOICE_MODE_SINGLE
        semesterList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->  {
            val semester: Semester = semesterListAdapter.getItem(position) as Semester

        }}
    }

    override fun onDeleteSemester(id: Int) {
        semesterListAdapter.delete(id)
    }
}
