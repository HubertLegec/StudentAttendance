package com.legec.studentattendance.semester.studentList

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.legec.studentattendance.R


class StudentListFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater!!.inflate(R.layout.fragment_student_list, container, false)
        ButterKnife.bind(this, rootView)

        return rootView
    }
}