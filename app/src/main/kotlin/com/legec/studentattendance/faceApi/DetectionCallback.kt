package com.legec.studentattendance.faceApi

import com.microsoft.projectoxford.face.contract.Face


interface DetectionCallback {
    fun onPreExecute()
    fun onProgressUpdate(value: String)
    fun onPostExecute(result: List<Face>)
}