package com.legec.studentattendance.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.legec.studentattendance.R
import com.legec.studentattendance.callback.DeleteSemesterCallback
import com.legec.studentattendance.model.Semester
import com.legec.studentattendance.view.SemesterView


class SemesterListAdapter(context: Activity, val deleteSemCallback: DeleteSemesterCallback) : BaseAdapter() {
    private val inflater = LayoutInflater.from(context)
    private val semesters: MutableList<Semester> = ArrayList()

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val semester: Semester = getItem(position) as Semester
        var v = view
        var holder: SemesterView
        if (v != null) {
            holder = v.tag as SemesterView
        } else {
            v = inflater.inflate(R.layout.semester_view, parent, false)
            holder = SemesterView(v, deleteSemCallback, semester.id)
            v!!.tag = holder
        }

        holder.semesterName.text = semester.semesterName
        holder.subjectName.text = semester.subjectName
        return v
    }

    override fun getItem(position: Int): Any {
        return semesters[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return semesters.size
    }

    fun addElement(semester: Semester) {
        semesters.add(semester)
    }

    fun addAll(toAdd: List<Semester>) {
        semesters.addAll(toAdd)
    }

    fun delete(id: Int) {
        semesters.removeIf { el -> el.id == id }
    }
}