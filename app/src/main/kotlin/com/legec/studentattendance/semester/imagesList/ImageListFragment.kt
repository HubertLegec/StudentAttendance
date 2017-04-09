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
import butterknife.Unbinder
import com.legec.studentattendance.R
import com.legec.studentattendance.StudentAttendanceApp
import java.util.*
import javax.inject.Inject


class ImageListFragment(private val semesterId: String) : Fragment() {
    @BindView(R.id.recycler_view)
    lateinit var recyclerView: RecyclerView

    @Inject lateinit var imageRepository: ImageRepository

    private val images: MutableList<Image> = ArrayList()
    lateinit private var adapter: GalleryAdapter
    lateinit private var unbinder: Unbinder


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_images_list, container, false)
        unbinder = ButterKnife.bind(this, rootView)
        StudentAttendanceApp.semesterComponent.inject(this)
        val layoutManager = GridLayoutManager(this.context, 1)
        images.addAll(imageRepository.getImagesForSemester(semesterId))
        adapter = GalleryAdapter(this.context, images)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }

    fun addImage(imageUri: Uri) {
        val image = imageRepository.saveImage(imageUri, Date(), semesterId)
        images.add(image)
        adapter.notifyDataSetChanged()
    }
}