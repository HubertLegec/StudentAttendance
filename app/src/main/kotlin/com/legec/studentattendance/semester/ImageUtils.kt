package com.legec.studentattendance.semester

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Rect
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import com.microsoft.projectoxford.face.contract.FaceRectangle
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.*

// The maximum side length of the image to detect, to keep the size of image less than 4MB.
// Resize the image if its side length is larger than the maximum.
private val IMAGE_MAX_SIDE_LENGTH = 1280

// Ratio to scale a detected face rectangle, the face rectangle scaled up looks more natural.
private val FACE_RECT_SCALE_RATIO = 1.3

/**
 * Decode image from imageUri, and resize according to the expectedMaxImageSideLength
 * If expectedMaxImageSideLength is
 *     (1) less than or equal to 0,
 *     (2) more than the actual max size length of the bitmap
 *     then return the original bitmap
 * Else, return the scaled bitmap
 */
fun loadSizeLimitedBitmap(imageUri: Uri, contentResolver: ContentResolver): Bitmap {
    var imageInputStream = contentResolver.openInputStream(imageUri)

    //only decode the image meta and get the side length
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    val outPadding = Rect()
    BitmapFactory.decodeStream(imageInputStream, outPadding, options)

    // Calculate shrink rate when loading the image into memory
    var maxSideLength = if (options.outWidth > options.outHeight) options.outWidth else options.outHeight
    options.inSampleSize = calculateSampleSize(maxSideLength, IMAGE_MAX_SIDE_LENGTH)
    options.inJustDecodeBounds = false

    imageInputStream?.close()

    //load bitmap and resize to expected size
    imageInputStream = contentResolver.openInputStream(imageUri)
    var bitmap = BitmapFactory.decodeStream(imageInputStream, outPadding, options)
    maxSideLength = if (bitmap.width > bitmap.height) bitmap.width else bitmap.height
    val ratio = IMAGE_MAX_SIDE_LENGTH / maxSideLength as Double
    if (ratio < 1) {
        bitmap = Bitmap.createScaledBitmap(bitmap, (bitmap.width * ratio).toInt(), (bitmap.height * ratio).toInt(), false)
    }
    val angle = getImageRotationAngle(imageUri, contentResolver)
    return rotateBitmap(bitmap, angle)
}

/**
 * Crop the face thumbnail out from the original image.
 * For better view for human, face rectangles are resized to the rate faceRectEnlargeRatio.
 */
fun generateFaceThumbnail(image: Bitmap, faceRectangle: FaceRectangle): Bitmap {
    val faceRect = calculateFaceRectangle(image, faceRectangle, FACE_RECT_SCALE_RATIO)
    return Bitmap.createBitmap(image, faceRect.left, faceRect.top, faceRect.width, faceRect.height)
}

fun compressBitmapToJpeg(bitmap: Bitmap) : InputStream {
    val output = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
    return ByteArrayInputStream(output.toByteArray())
}

/**
 * Return the number of times for the image to shrink when loading it into memory.
 */
private fun calculateSampleSize(maxSideLength: Int, expectedMaxImageSideLength: Int): Int {
    var inSampleSize = 1
    var maxL = maxSideLength

    while (maxL > 2 * expectedMaxImageSideLength) {
        maxL /= 2
        inSampleSize *= 2
    }

    return inSampleSize
}

private fun rotateBitmap(bitmap: Bitmap, angle: Int): Bitmap {
    if (angle == 0) {
        return bitmap
    }
    val matrix = Matrix()
    matrix.postRotate(angle.toFloat())
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}

private fun getImageRotationAngle(imageUri: Uri, contentResolver: ContentResolver): Int {
    var angle = 0
    val cursor = contentResolver
            .query(imageUri, arrayOf(MediaStore.Images.ImageColumns.ORIENTATION), null, null, null)
    if(cursor?.count == 1) {
        cursor.moveToFirst()
        angle = cursor.getInt(0)
        cursor.close()
    } else {
        val exif = ExifInterface(imageUri.path)
        val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_270 -> angle = 270
            ExifInterface.ORIENTATION_ROTATE_180 -> angle = 180
            ExifInterface.ORIENTATION_ROTATE_90 -> angle = 90
            else -> {
            }
        }
    }
    return angle
}

fun getImageTakenDate(imageUri: Uri, contentResolver: ContentResolver): Date {
    val cursor = contentResolver
            .query(imageUri, arrayOf(MediaStore.Images.ImageColumns.DATE_TAKEN), null, null, null)
    if(cursor?.count == 1) {
        cursor.moveToFirst()
        val date = cursor.getLong(0)
        cursor.close()
        return Date(date)
    } else {
        val exif = ExifInterface(imageUri.path)
        val date = exif.getAttribute(
                ExifInterface.TAG_DATETIME)
        return Date(date.toLong())
    }
}

private fun calculateFaceRectangle(bitmap: Bitmap, faceRectangle: FaceRectangle, enlargeRatio: Double): FaceRectangle {
    //get the new side length
    val sideLength = Math.min(
            (faceRectangle.width * enlargeRatio).toInt(),
            Math.min(bitmap.width, bitmap.height)
    )
    //make the left edge to left more
    val left = Math.min(
            Math.max(faceRectangle.left - faceRectangle.width * (enlargeRatio - 1.0) * 0.5, 0.0).toInt(),
            bitmap.width - sideLength
    )
    //make the top edge to top more
    var top = Math.min(
            Math.max(faceRectangle.top - faceRectangle.height * (enlargeRatio - 1.0) * 0.5, 0.0).toInt(),
            bitmap.height - sideLength
    )
    //shift top edge to the top more
    val shiftTop = Math.min(Math.max(enlargeRatio - 1.0, 0.0), 1.0).toInt()
    top = Math.max(top - 0.15 * shiftTop * faceRectangle.height, 0.0).toInt()
    //set result
    val result = FaceRectangle()
    result.left = left
    result.top = top
    result.width = sideLength
    result.height = sideLength
    return result
}