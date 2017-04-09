package com.legec.studentattendance.faces

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.legec.studentattendance.R
import java.util.*


class FacesGridAdapter(val context: Context) : RecyclerView.Adapter<FaceViewHolder>(){
    private var faces: List<FaceDescription> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FaceViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.face_thumbnail, parent, false)
        return FaceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FaceViewHolder?, position: Int) {
        Glide.with(context).load(faces[position].getUri())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder?.thumbnail)
    }

    override fun getItemCount(): Int {
        return faces.size
    }

    fun addFaces(facesToAdd: List<FaceDescription>) {
        faces = facesToAdd
        notifyDataSetChanged()
    }

}