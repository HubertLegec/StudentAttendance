package com.legec.studentattendance.semester

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.PersistableBundle
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.legec.studentattendance.R
import com.legec.studentattendance.faceApi.FaceApiService
import java.io.File
import java.io.IOException
import javax.inject.Inject

class SemesterActivity : AppCompatActivity() {
    private val SEMESTER_MESSAGE = "com.legec.StudentAttendance.SEMESTER_MESSAGE"
    private val TAG = "SemesterActivity"
    private val REQUEST_TAKE_PHOTO = 0
    private val REQUEST_PHOTO_FROM_GALLERY = 1

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.container)
    lateinit var mViewPager: ViewPager
    @BindView(R.id.tabs)
    lateinit var tabLayout: TabLayout

    lateinit private var mSectionsPagerAdapter: SectionsPagerAdapter
    lateinit private var semesterId: String
    private var takenPhotoUri: Uri? = null

    @Inject lateinit var faceApiService: FaceApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_semester)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        semesterId = intent.getStringExtra(SEMESTER_MESSAGE)
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, semesterId)
        mViewPager.adapter = mSectionsPagerAdapter
        tabLayout.setupWithViewPager(mViewPager)
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putParcelable("imageUri", takenPhotoUri)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        takenPhotoUri = savedInstanceState?.getParcelable("imageUri")
    }

    @OnClick(R.id.camera_button)
    fun onCameraButtonClick(view: View) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            try {
                val file = File.createTempFile("IMG_", ".jpg", storageDir)
                takenPhotoUri = Uri.fromFile(file)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, takenPhotoUri)
                startActivityForResult(intent, REQUEST_TAKE_PHOTO)
            } catch (e: IOException) {
                Log.e(TAG, e.message)
                Snackbar.make(view, "Problem with creating image file", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
            }
        }
    }

    @OnClick(R.id.gallery_button)
    fun onGalleryButtonClick() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_PHOTO_FROM_GALLERY)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_semester, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) true else super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if ((requestCode == REQUEST_TAKE_PHOTO || requestCode == REQUEST_PHOTO_FROM_GALLERY)
                && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data ?: takenPhotoUri!!
            mSectionsPagerAdapter.addImage(imageUri)
            /*val bitmap = loadSizeLimitedBitmap(imageUri, contentResolver)
            val imageInputStream = compressBitmapToJpeg(bitmap)
            faceApiService.detect(imageInputStream, object: DetectionCallback {
                override fun onPreExecute() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onProgressUpdate(value: String) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onPostExecute(result: List<Face>) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })*/
        }
    }
}
