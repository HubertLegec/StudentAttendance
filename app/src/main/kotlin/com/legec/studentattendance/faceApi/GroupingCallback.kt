package com.legec.studentattendance.faceApi

import com.microsoft.projectoxford.face.contract.GroupResult


interface GroupingCallback {
    fun onPreExecute()
    fun onProgressUpdate(value: String)
    fun onPostExecute(result: GroupResult)
}