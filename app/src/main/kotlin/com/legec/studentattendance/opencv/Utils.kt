package com.legec.studentattendance.opencv

import org.bytedeco.javacpp.opencv_core
import org.bytedeco.javacpp.opencv_imgcodecs.imread
import org.bytedeco.javacpp.opencv_imgproc.COLOR_BGR2GRAY
import org.bytedeco.javacpp.opencv_imgproc.cvtColor

fun loadGrayscaleImage(path : String): opencv_core.Mat {
    val img = imread(path)
    val grayImg = opencv_core.Mat()
    cvtColor(img, grayImg, COLOR_BGR2GRAY)
    return grayImg
}
