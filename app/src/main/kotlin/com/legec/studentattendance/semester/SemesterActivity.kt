package com.legec.studentattendance.semester

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentPagerAdapter
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
import java.io.File
import java.io.IOException

class SemesterActivity : AppCompatActivity() {
    private val TAG = "SemesterActivity"
    private val REQUEST_TAKE_PHOTO = 0
    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * [FragmentPagerAdapter] derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    /**
     * The [ViewPager] that will host the section contents.
     */
    private var mViewPager: ViewPager? = null

    private var takenPhotoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_semester)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container) as ViewPager
        mViewPager!!.adapter = mSectionsPagerAdapter

        val tabLayout = findViewById(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(mViewPager)
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
        //TODO
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


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
                val imageUri: Uri
                if (data == null || data.data == null) {
                    imageUri = takenPhotoUri!!
                } else {
                    imageUri = data.data
                }
        }
    }
}
