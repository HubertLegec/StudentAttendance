package com.legec.studentattendance.faceApi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.legec.studentattendance.R
import com.microsoft.projectoxford.face.contract.GroupResult
import java.util.*
import kotlin.collections.ArrayList


class FaceGroupsAdapter(
        val inflater: LayoutInflater,
        val faceListAdapter: FaceListAdapter,
        result: GroupResult?
) : BaseAdapter() {

    var faceGroups: List<List<UUID>> = ArrayList()

    override fun getCount(): Int {
        return faceGroups.size
    }

    override fun getItem(position: Int): Any {
        return faceGroups[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView ?: inflater.inflate(R.layout.item_face_group, parent, false)
        convertView!!.id = position

        var faceGroupName = "Group " + position + ": " + faceGroups[position].size + " face(s)"
        if (position == faceGroups.size - 1) {
            faceGroupName = "Messy Group: " + faceGroups[position].size + " face(s)"
        }
        (convertView.findViewById(R.id.face_group_name) as TextView).text = faceGroupName

        val facesAdapter = FacesAdapter(inflater, faceGroups[position], faceListAdapter)
        val gridView = convertView.findViewById(R.id.faces) as EmbeddedGridView
        gridView.adapter = facesAdapter

        return convertView
    }

    init {
        if (result != null) {
            faceGroups = result.groups
                    .map { group -> group.toList() }
                    .plus(listOf(result.messyGroup))
        }
    }
}