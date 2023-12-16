package com.dicoding.chickfarm.ui.screen.diseasedetector

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.InputStream

class TakeImageViewModel() : ViewModel(){
    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context
    }



    fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

//    input gambar ke model ML
    fun uriToBitmap(contentResolver: ContentResolver, uri: Uri): Bitmap? {
        var inputStream: InputStream? = null
        try {
            inputStream = contentResolver.openInputStream(uri)
            return BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
        }
        return null
    }

    fun resizeBitmap(bitmap: Bitmap, targetWidth: Int, targetHeight: Int): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true)
    }

    fun bitmapToArray(bitmap: Bitmap): Array<Array<FloatArray>> {
        val height = bitmap.height
        val width = bitmap.width
        val pixels = IntArray(224 * 224)
        bitmap.getPixels(pixels, 0, 224, 0, 0, 224, 224)

        // Tidak perlu normalisasi karena model sudah menangani normalisasi
        val inputArray = Array(224) { Array(224) { FloatArray(3) } }

        for (y in 0 until 224) {
            for (x in 0 until 224) {
                val pixelValue = pixels[y * 224 + x]
                inputArray[y][x][0] = ((pixelValue shr 16) and 0xFF).toFloat() // Red channel
                inputArray[y][x][1] = ((pixelValue shr 8) and 0xFF).toFloat()  // Green channel
                inputArray[y][x][2] = (pixelValue and 0xFF).toFloat()         // Blue channel
            }
        }

        return inputArray
    }

//    Sintak lain untuk input gambar ke model
//    fun bitmapToArray(bitmap: Bitmap): Array<Array<FloatArray>> {
//        val height = bitmap.height
//        val width = bitmap.width
//        val pixels = IntArray(224 * 224)
//        bitmap.getPixels(pixels, 0, 224, 0, 0, 224, 224)
//
//        // Normalize pixel values to be between 0 and 1
//        val inputArray = Array(224) { Array(224) { FloatArray(3) } }
//        for (y in 0 until height) {
//            for (x in 0 until width) {
//
//                val pixelValue = pixels[y * width + x]
//                inputArray[y][x][0] = ((pixelValue shr 16) and 0xFF) / 255.0f // Red channel
//                inputArray[y][x][1] = ((pixelValue shr 8) and 0xFF) / 255.0f  // Green channel
//                inputArray[y][x][2] = (pixelValue and 0xFF) / 255.0f         // Blue channel
//
//            }
//        }
//        return inputArray
//    }



}






