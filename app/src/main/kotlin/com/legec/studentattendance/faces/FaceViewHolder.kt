package com.legec.studentattendance.faces

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.legec.studentattendance.R


class FaceViewHolder(view: View): RecyclerView.ViewHolder(view) {
    @BindView(R.id.face_thumbnail)
    lateinit var thumbnail: ImageView

    init {
        ButterKnife.bind(this, view)
    }
}