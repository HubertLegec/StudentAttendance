package com.legec.studentattendance.semester.imagesList

import android.view.View


interface ClickListener {
    fun onClick(view: View, position: Int)
    fun onLongClick(view: View, position: Int)
}