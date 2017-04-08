package com.legec.studentattendance.semester.imagesList

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.legec.studentattendance.R


class ImageListFragment : Fragment() {
    @BindView(R.id.recycler_view)
    lateinit var recyclerView: RecyclerView
    private val images: MutableList<Uri> = ArrayList()
    lateinit private var adapter: GalleryAdapter


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_images_list, container, false)
        ButterKnife.bind(this, rootView)
        val layoutManager = GridLayoutManager(this.context, 1)
        adapter = GalleryAdapter(this.context, images)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter
        //val textView = rootView.findViewById(R.id.section_label) as TextView
        //textView.text = getString(R.string.section_format, tabNumber)
        return rootView
    }

    fun addImage(imageUri: Uri) {
        images.add(imageUri)
        adapter.notifyDataSetChanged()
    }
}