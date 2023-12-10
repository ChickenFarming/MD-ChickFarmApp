package com.dicoding.chickfarm.ui.screen.camera

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.chickfarm.R
import com.dicoding.chickfarm.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.dicoding.chickfarm.ui.ChickFarmApp
import com.dicoding.chickfarm.ui.theme.ChickFarmTheme
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toFile
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CameraViewModel : ViewModel() {

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var context: Context
    private val _shouldShowCamera = MutableLiveData<Boolean>()


    fun init(requestPermissionLauncher: ActivityResultLauncher<String>, context: Context) {
        this.requestPermissionLauncher = requestPermissionLauncher
        this.context = context
    }

    fun requestCameraPermission(){
        when{
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.CAMERA
            )== PackageManager.PERMISSION_GRANTED -> {
                Log.i("kilo", "Permission previously granted")
                _shouldShowCamera.value = true
            }
            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

   suspend fun getCameraProvider(): ProcessCameraProvider =
        suspendCoroutine { continuation ->
            ProcessCameraProvider.getInstance(context).also { cameraProvider ->
                cameraProvider.addListener({
                    continuation.resume(cameraProvider.get())
                }, ContextCompat.getMainExecutor(context))
            }
        }


    fun takePhoto(
        filenameFormat: String,
        imageCapture: ImageCapture,
        executor: Executor,
        onImageCaptured: (Uri) -> Unit,
        onError: (Exception) -> Unit
    ) {
        // Proses pengambilan gambar oleh ImageCapture
//        val photoFile = createImageFile(filenameFormat)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(createTempFile()).build()

        imageCapture.takePicture(
            outputOptions,
            executor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exception: ImageCaptureException) {
                    onError(exception)
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(outputFileResults.savedUri?.toFile())
                    onImageCaptured(savedUri)
                }
            }
        )
    }
    private fun createTempFile(): File {
        return File.createTempFile("temp_image", ".jpg", context.cacheDir)
    }
    private fun createImageFile(filenameFormat: String): File {
        val timeStamp: String = SimpleDateFormat(filenameFormat, Locale.getDefault()).format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }

    // LiveData atau State untuk menyimpan URI gambar yang diambil
    private val _capturedImageUri = MutableLiveData<Uri?>()
    val capturedImageUri get() = _capturedImageUri

    // LiveData yang di-post di latar belakang saat gambar diambil
    fun handleCaptur(uri: Uri) {
        _capturedImageUri.postValue(uri)
    }




}
