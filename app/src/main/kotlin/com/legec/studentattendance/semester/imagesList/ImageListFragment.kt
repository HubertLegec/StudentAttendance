package com.legec.studentattendance.semester.imagesList

import android.app.AlertDialog
import android.content.Intent
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
import com.legec.studentattendance.faces.FacesActivity
import java.util.*
import javax.inject.Inject


class ImageListFragment(private val semesterId: String) : Fragment() {
    private val IMAGE_ID_MESSAGE = "com.legec.StudentAttendance.IMAGE_ID_MESSAGE"

    @BindView(R.id.recycler_view)
    lateinit var recyclerView: RecyclerView

    @Inject lateinit var imageRepository: ImageRepository

    private val images: MutableList<Image> = ArrayList()
    lateinit private var adapter: GalleryAdapter
    lateinit private var unbinder: Unbinder

    val onDeleteImage: (String) -> Unit = { id: String ->
        val dialog = AlertDialog.Builder(this.context)
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", { _, _ ->
                    adapter.deleteImage(id)
                    imageRepository.deleteImage(id)
                })
                .setNegativeButton("No", { _, _ -> })
        dialog.show()
    }

    val onClickImage = { id: String ->
        val intent = Intent(this.context, FacesActivity::class.java)
        intent.putExtra(IMAGE_ID_MESSAGE, id)
        startActivity(intent)
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_images_list, container, false)
        unbinder = ButterKnife.bind(this, rootView)
        StudentAttendanceApp.semesterComponent.inject(this)
        val layoutManager = GridLayoutManager(this.context, 1)
        images.clear()
        images.addAll(imageRepository.getImagesForSemester(semesterId))
        adapter = GalleryAdapter(this.context, images, onDeleteImage, onClickImage)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }

    fun addImage(imageUri: Uri, fromCamera: Boolean): Image {
        val image = imageRepository.saveImage(imageUri, semesterId, fromCamera)
        images.add(image)
        adapter.notifyDataSetChanged()
        return image
    }
}