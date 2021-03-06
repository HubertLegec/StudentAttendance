package com.legec.studentattendance.semester

import android.net.Uri
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.legec.studentattendance.semester.imagesList.Image
import com.legec.studentattendance.semester.imagesList.ImageListFragment
import com.legec.studentattendance.semester.studentList.StudentListFragment
import com.legec.studentattendance.semester.ungroupedFaces.UngroupedFacesFragment


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fm: FragmentManager, semesterId: String) : FragmentPagerAdapter(fm) {
    private val fragments: Map<Int, Fragment>

    init {
        fragments = HashMap()
        fragments[0] = ImageListFragment(semesterId)
        fragments[1] = StudentListFragment(semesterId)
        fragments[2] = UngroupedFacesFragment(semesterId)
    }

    /**
     * getItem is called to instantiate the fragment for the given page.
     */
    override fun getItem(position: Int): Fragment {
        return fragments[position]!!
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Photos"
            1 -> return "Students"
            2 -> return "Ungrouped"
        }
        return null
    }

    fun addImage(imageUri: Uri, fromCamera: Boolean): Image {
        val imgListFragment = fragments[0] as ImageListFragment
        return imgListFragment.addImage(imageUri, fromCamera)
    }
}