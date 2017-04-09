package com.legec.studentattendance.semester.imagesList

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.legec.studentattendance.R


class ImageViewHolder(view: View, val deleteCallback: (String) -> Unit, val clickCallback: (String) -> Unit) : RecyclerView.ViewHolder(view) {
    @BindView(R.id.thumbnail)
    lateinit var thumbnail: ImageView
    @BindView(R.id.img_date)
    lateinit var creationDate: TextView
    var id: String? = null

    init {
        ButterKnife.bind(this, view)
    }

    @OnClick(R.id.img_delete)
    fun onDeleteClick() {
        if (id != null) {
            deleteCallback(id!!)
        }
    }

    @OnClick(R.id.thumbnail)
    fun onImageClick() {
        clickCallback(id!!)
    }

}