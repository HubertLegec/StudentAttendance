package com.legec.studentattendance.semesterList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.legec.studentattendance.R
import io.realm.Realm


class SemesterListAdapter(
        val inflater: LayoutInflater,
        val deleteSemCallback: (String) -> Unit,
        val editSemCallback: (String, String, String) -> Unit
) : BaseAdapter() {
    private val semesters: MutableList<Semester> = ArrayList()

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val semester: Semester = getItem(position) as Semester
        var v = view
        val holder: SemesterView
        if (v != null) {
            holder = v.tag as SemesterView
        } else {
            v = inflater.inflate(R.layout.semester_view, parent, false)
            holder = SemesterView(v, deleteSemCallback, editSemCallback, semester.id)
            v!!.tag = holder
        }

        holder.semesterName.text = semester.semesterName
        holder.subjectName.text = semester.subjectName
        holder.id = semester.id
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
        notifyDataSetChanged()
    }

    fun addAll(toAdd: List<Semester>) {
        semesters.addAll(toAdd)
    }

    fun editElement(id: String, subjectName: String, semesterName: String) {
        val sem = semesters.find { e -> e.id == id}
        if (sem != null) {
            Realm.getDefaultInstance().beginTransaction()
            sem.subjectName = subjectName
            sem.semesterName = semesterName
            Realm.getDefaultInstance().commitTransaction()
            notifyDataSetChanged()
        }
    }

    fun deleteElem(id: String) {
        val elem = semesters.find { e -> e.id == id }
        if (elem != null) {
            semesters.remove(elem)
            notifyDataSetChanged()
        }
    }
}