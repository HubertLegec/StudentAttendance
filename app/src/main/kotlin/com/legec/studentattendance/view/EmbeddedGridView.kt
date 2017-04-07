package com.legec.studentattendance.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.GridView


class EmbeddedGridView(context: Context, attrs: AttributeSet) : GridView(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val newHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MEASURED_SIZE_MASK, View.MeasureSpec.AT_MOST)

        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)
        layoutParams.height = measuredHeight
    }
}