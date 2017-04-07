package com.legec.studentattendance.faceApi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.legec.studentattendance.R
import com.legec.studentattendance.adapter.FaceListAdapter
import java.util.*


class FacesAdapter(
        val inflater: LayoutInflater,
        val faces: List<UUID>,
        val faceListAdapter: FaceListAdapter
): BaseAdapter() {

    override fun getCount(): Int {
        return faces.size
    }

    override fun getItem(position: Int): Any {
        return faces[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView ?: inflater.inflate(R.layout.item_face, parent, false)
        convertView!!.id = position
        // Show the face thumbnail.
        (convertView.findViewById(R.id.image_face) as ImageView)
                .setImageBitmap(faceListAdapter.faceIdThumbnailMap[faces[position]])
        return convertView
    }
}