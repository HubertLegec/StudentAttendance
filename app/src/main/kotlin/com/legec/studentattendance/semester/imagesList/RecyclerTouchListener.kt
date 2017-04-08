package com.legec.studentattendance.semester.imagesList

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import com.legec.studentattendance.semester.imagesList.ClickListener


class RecyclerTouchListener: RecyclerView.OnItemTouchListener {
    private val gestureDetector: GestureDetector
    private val clickListener: ClickListener

    constructor(context: Context, recyclerView: RecyclerView, clickListener: ClickListener) {
        this.clickListener = clickListener
        this.gestureDetector = GestureDetector(context, object: GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                return true
            }

            override fun onLongPress(e: MotionEvent?) {
                val child = recyclerView.findChildViewUnder(e?.x!!, e?.y)
                if (child != null) {
                    clickListener.onLongClick(child, recyclerView.getChildLayoutPosition(child))
                }
            }
        })
    }

    override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {}

    override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean {
        val child = rv?.findChildViewUnder(e?.x!!, e.y)
        if (child != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, rv.getChildLayoutPosition(child))
        }
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

}