package com.legec.studentattendance.semester.studentList

import android.os.AsyncTask
import android.util.Log
import com.microsoft.projectoxford.face.FaceServiceClient
import com.microsoft.projectoxford.face.contract.GroupResult
import java.util.*


class GroupingTask(
        private val faceServiceClient: FaceServiceClient,
        private val callback: GroupingCallback
) : AsyncTask<UUID, String, GroupResult>() {
    private val TAG = "GroupingTask"

    override fun doInBackground(vararg params: UUID): GroupResult? {
        Log.i(TAG, "Request: Grouping " + params.size + " face(s)")
        try {
            publishProgress("Grouping...")

            // Start grouping, params are face IDs.
            return faceServiceClient.group(params)
        } catch (e: Exception) {
            Log.i(TAG, e.message)
            //publishProgress(e.message)
            return null
        }

    }

    override fun onPreExecute() {
        callback.onPreExecute()
    }

    override fun onProgressUpdate(vararg values: String) {
        callback.onProgressUpdate(values[0])
    }

    override fun onPostExecute(result: GroupResult?) {
        if (result != null) {
            Log.i(TAG, "Response: Success. Grouped into " + result.groups.size + " face group(s).")
        }
        callback.onPostExecute(result!!)
    }
}