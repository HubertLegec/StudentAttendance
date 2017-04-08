package com.legec.studentattendance.semester.imagesList

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.legec.studentattendance.R


class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var thumbnail: ImageView = view.findViewById(R.id.thumbnail) as ImageView

}