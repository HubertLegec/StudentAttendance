package com.legec.studentattendance.semester.studentList

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.legec.studentattendance.R
import com.legec.studentattendance.faces.FaceDescription


class StudentListAdapter(val context: Context) : RecyclerView.Adapter<StudentViewHolder>() {
    var students: List<Student> = ArrayList()
    var faces: List<FaceDescription> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.student_view, parent, false)
        return StudentViewHolder(itemView, context)
    }

    override fun getItemCount(): Int {
        return students.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder?, position: Int) {
        val student = students[position]
        holder?.name?.text = student.name
        val studentFaces = faces
                .filter { face -> face.studentId == student.id }
                .map { face -> face.imageUriPath }
                .map { uriString -> Uri.parse(uriString)}
        val uri = studentFaces.first()
        holder?.occurences?.text = "Occurences: " + studentFaces.size
        holder?.loadImage(uri)
    }

    fun update(students: Map<Student, List<FaceDescription>>) {
        this.students = students.keys.toList()
        this.faces = students.values.flatten()
    }

}