package com.legec.studentattendance.semester.studentList

import android.app.Activity
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.legec.studentattendance.R
import com.legec.studentattendance.faces.FaceDescription


class StudentListAdapter(val context: Activity,
                         val onNameUpdate: (String, String) -> Unit) : BaseAdapter() {
    var students: List<Pair<Student, List<FaceDescription>>> = ArrayList()

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var v = view
        val holder: StudentViewHolder
        if (v != null) {
            holder = v.tag as StudentViewHolder
        } else {
            v = LayoutInflater.from(context).inflate(R.layout.student_view, parent, false)
            holder = StudentViewHolder(v, context)
            v!!.tag = holder
        }
        val student = students[position]
        holder.name.text = student.first.name
        val studentFaces = student.second
                .map { face -> face.imageUriPath }
                .map { uriString -> Uri.parse(uriString)}
        val uri = studentFaces.first()
        holder.occurences.text = "Occurences: " + studentFaces.size
        holder.onNameUpdate = {name -> this.onNameUpdate(name, student.first.id)}
        holder.loadImage(uri)
        return v
    }

    override fun getItem(position: Int): Any {
        return students[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return students.size
    }

    fun update(students: Map<Student, List<FaceDescription>>) {
        this.students = students.entries
                .map { e -> Pair(e.key, e.value) }
    }

    fun getStudent(position: Int): Student {
        return students[position].first
    }

}