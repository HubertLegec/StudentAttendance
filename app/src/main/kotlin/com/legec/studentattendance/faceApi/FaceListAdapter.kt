package com.legec.studentattendance.faceApi

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.legec.studentattendance.R
import com.legec.studentattendance.semester.generateFaceThumbnail
import com.microsoft.projectoxford.face.contract.Face
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class FaceListAdapter(
        val inflater: LayoutInflater,
        var faces: List<Face> = ArrayList(),
        var faceThumbnails: List<Bitmap> = ArrayList(),
        var faceIdThumbnailMap: Map<UUID, Bitmap> = HashMap()
) : BaseAdapter() {


    fun addFaces(mBitmap: Bitmap, detectionResult: List<Face>) {
        faces = detectionResult
        faceThumbnails = detectionResult
                .map { face -> generateFaceThumbnail(mBitmap, face.faceRectangle) }
        faceIdThumbnailMap = faceThumbnails
                .mapIndexed { index, bitmap -> Pair<UUID, Bitmap>(faces[index].faceId, bitmap) }
                .toMap()
    }

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
        var convertView = convertView
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_face, parent, false)
        }
        convertView!!.id = position

        // Show the face thumbnail.
        (convertView.findViewById(R.id.image_face) as ImageView).setImageBitmap(faceThumbnails[position])
        return convertView
    }
}