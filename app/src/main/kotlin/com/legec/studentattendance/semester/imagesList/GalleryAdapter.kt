package com.legec.studentattendance.semester.imagesList

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.legec.studentattendance.R


class GalleryAdapter(
        val context: Context,
        val images: MutableList<Image>,
        val deleteCallback: (String) -> Unit
): RecyclerView.Adapter<ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ImageViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.gallery_thumbnail, parent, false)
        return ImageViewHolder(itemView, deleteCallback)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder?, position: Int) {
        val image = images[position]
        Glide.with(context).load(image.getUri())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder?.thumbnail)
        holder?.creationDate?.text = image.date
        holder?.id = image.id
    }

    fun deleteImage(id: String) {
        val img = images.find { img -> img.id == id }
        if (img != null) {
            images.remove(img)
            notifyDataSetChanged()
        }
    }

}