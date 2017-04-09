package com.legec.studentattendance.semester

import android.net.Uri
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.legec.studentattendance.semester.imagesList.ImageListFragment
import com.legec.studentattendance.semester.studentList.StudentListFragment


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fm: FragmentManager, semesterId: String) : FragmentPagerAdapter(fm) {
    private val fragments: Map<Int, Fragment>

    init {
        fragments = HashMap()
        fragments[0] = ImageListFragment(semesterId)
        fragments[1] = StudentListFragment()
    }

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        return fragments[position]!!
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Photos"
            1 -> return "Students"
        }
        return null
    }

    fun addImage(imageUri: Uri) {
        val imgListFragment = fragments[0] as ImageListFragment
        imgListFragment.addImage(imageUri)
    }
}