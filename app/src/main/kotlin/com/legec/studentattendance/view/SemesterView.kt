package com.legec.studentattendance.view

import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.legec.studentattendance.R


class SemesterView(view: View, val deleteCallback: (String) -> Unit, var id: String) {
    @BindView(R.id.subject_name)
    lateinit var subjectName: TextView
    @BindView(R.id.semester_name)
    lateinit var semesterName: TextView

    init {
        ButterKnife.bind(this, view)
    }

    @OnClick(R.id.delete_button)
    fun deleteItem() {
        deleteCallback(id)
    }
}