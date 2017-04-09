package com.legec.studentattendance.utils

import android.graphics.Bitmap
import android.os.Environment
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URI


@Throws(IOException::class)
fun savebitmap(bmp: Bitmap, imageName: String): URI {
    val bytes = ByteArrayOutputStream()
    bmp.compress(Bitmap.CompressFormat.JPEG, 60, bytes)
    val f = File(Environment.getExternalStorageDirectory().toString() + File.separator + imageName + ".jpg")
    f.createNewFile()
    val fo = FileOutputStream(f)
    fo.write(bytes.toByteArray())
    fo.close()
    return f.toURI()
}