package com.legec.studentattendance.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.legec.studentattendance.R
import com.legec.studentattendance.callback.DeleteSemesterCallback


class SemesterView(view: View, deleteCallback: DeleteSemesterCallback, id: Int) {
    @BindView(R.id.subject_name)
    lateinit var subjectName: TextView
    @BindView(R.id.semester_name)
    lateinit var semesterName: TextView
    val id = id
    val deleteCallback = deleteCallback

    init {
        ButterKnife.bind(this, view)
    }

    @OnClick(R.id.delete_button)
    fun deleteItem(elem: ImageView) {
        deleteCallback.onDeleteSemester(id)
    }
}