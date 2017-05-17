package com.legec.studentattendance.faces

import android.os.AsyncTask
import android.util.Log
import com.microsoft.projectoxford.face.FaceServiceClient
import com.microsoft.projectoxford.face.contract.Face
import java.io.InputStream


class DetectionTask(
        private val faceServiceClient: FaceServiceClient,
        private val callback: DetectionCallback
) : AsyncTask<InputStream, String, List<Face>>() {
    private val TAG = "DetectionTask"
    private var mSucceed = true

    override fun doInBackground(vararg params: InputStream): List<Face>? {
        try {
            Log.i(TAG, "Detecting...")

            // Start detection.
            return faceServiceClient.detect(
                    params[0], /* Input stream of image to detectAndSave */
                    true, /* Whether to return face ID */
                    false, /* Whether to return face landmarks */
                    /* Which face attributes to analyze, currently we support:
                           age,gender,headPose,smile,facialHair */
                    null).toList()

        } catch (e: Exception) {
            mSucceed = false
            publishProgress(e.message)
            Log.e(TAG, e.message)
            return null
        }

    }

    override fun onPreExecute() {
        callback.onPreExecute()
    }

    override fun onProgressUpdate(vararg values: String) {
        callback.onProgressUpdate(values[0])
    }

    override fun onPostExecute(result: List<Face>) {
        if (mSucceed) {
            Log.i(TAG, "Response: Success. Detected " + result.size + " Face(s) in image")
        }
        callback.onPostExecute(result)
    }
}