package com.legec.studentattendance.semester.studentList

import com.microsoft.projectoxford.face.contract.GroupResult


interface GroupingCallback {
    fun onPreExecute()
    fun onProgressUpdate(value: String)
    fun onPostExecute(result: GroupResult)
}