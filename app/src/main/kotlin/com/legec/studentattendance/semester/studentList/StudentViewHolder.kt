package com.legec.studentattendance.semester.studentList

import android.app.Activity
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.legec.studentattendance.R




class StudentViewHolder(view: View, val context: Activity) : RecyclerView.ViewHolder(view) {
    @BindView(R.id.student_thumbnail)
    lateinit var thumbnail: ImageView
    @BindView(R.id.student_name)
    lateinit var name: TextView
    @BindView(R.id.student_occurrences)
    lateinit var occurences: TextView
    var onNameUpdate: ((String) -> Unit)? = null

    init {
        ButterKnife.bind(this, view)
        name.setOnLongClickListener{ _ ->
            val dialog = StudentNameDialog(
                    {newName ->
                        name.text = newName
                        this.onNameUpdate?.let { it(newName) } },
                    name.text.toString()
            )
            dialog.show(context.fragmentManager, "StudentNameDialog")
            true
        }
    }

    fun loadImage(uri: Uri) {
        Glide.with(context).load(uri)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(thumbnail)
    }
}