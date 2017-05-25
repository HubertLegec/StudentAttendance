package com.legec.studentattendance.faces

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.legec.studentattendance.R
import com.legec.studentattendance.StudentAttendanceApp
import com.legec.studentattendance.semester.imagesList.ImageRepository
import com.microsoft.projectoxford.face.contract.Face
import javax.inject.Inject


class FacesActivity: AppCompatActivity() {
    private val TAG = "FacesActivity"
    private val URI_MESSAGE = "com.legec.StudentAttendance.URI_MESSAGE"
    private val IMAGE_ID_MESSAGE = "com.legec.StudentAttendance.IMAGE_ID_MESSAGE"
    private val STUDENT_MESSAGE = "com.legec.StudentAttendance.STUDENT_MESSAGE"
    @Inject lateinit var faceService: FaceService
    @Inject lateinit var faceDescriptionRepository: FaceDescriptionRepository
    @Inject lateinit var imageRepository: ImageRepository

    lateinit private var adapter: FacesGridAdapter

    @BindView(R.id.recycler_view)
    lateinit var recyclerView: RecyclerView
    @BindView(R.id.progressbar_view)
    lateinit var loader: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faces)
        ButterKnife.bind(this)
        StudentAttendanceApp.facesComponent.inject(this)
        val layoutManager = GridLayoutManager(this, 2)
        adapter = FacesGridAdapter(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter
        loader.visibility = View.GONE
        val imageId = intent.getStringExtra(IMAGE_ID_MESSAGE)
        val imageUri = intent.getParcelableExtra<Uri>(URI_MESSAGE)
        if(imageUri != null) {
            detectFaces(imageUri, imageId)
        } else if (imageId != null) {
            loadFaces(imageId)
        } else {
            val studentId = intent.getStringExtra(STUDENT_MESSAGE)
            loadStudentFaces(studentId)
        }
    }

    private fun detectFaces(imageUri: Uri, imageId: String) {
        faceService.detectAndSave(imageUri, object: DetectionCallback {
            override fun onPreExecute() {
                loader.visibility = View.VISIBLE
            }

            override fun onProgressUpdate(value: String) {
                Log.i(TAG, value)
            }

            override fun onPostExecute(result: List<Face>) {
                val image = imageRepository.getImageById(imageId)
                val descriptions = faceDescriptionRepository.saveFaces(result, imageId, image!!.getUri())
                loader.visibility = View.GONE
                adapter.addFaces(descriptions)
            }

        })
    }

    private fun loadFaces(imageId: String) {
        val faces = faceDescriptionRepository.getSavedFacesForImage(imageId)
        adapter.addFaces(faces)
    }

    private fun loadStudentFaces(studentId: String) {
        val faces = faceDescriptionRepository.getSavedFacesForStudent(studentId)
        adapter.addFaces(faces)
    }
}