package com.dicoding.chickfarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.dicoding.chickfarm.ui.ChickFarmApp
import com.dicoding.chickfarm.ui.theme.ChickFarmTheme
import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class MainActivity : ComponentActivity() {

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    private var shouldShowCamera: MutableState<Boolean> = mutableStateOf(false)

    private val requestPermisiionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){isGranted ->
        if(isGranted){
            Log.i("kilo", "Permission granted")
            shouldShowCamera.value = true
        }else{
            Log.i("kilo", "Permission denied")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            ChickFarmTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if(shouldShowCamera.value){

                    ChickFarmApp(outputDirectory = outputDirectory,
                        executor = cameraExecutor ,
                        onImageCaptured =::handleImageCaptur ,
                        onError = {Log.e("kilo", "View error", it)},
                        context = this,
                    )
                    }
//                    ChickFarmApp( context = this,  )
                }

            }
        }

        requestCameraPermission()
        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun requestCameraPermission(){
        when{
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA
            )== PackageManager.PERMISSION_GRANTED -> {
                Log.i("kilo", "Permission previously granted")
                shouldShowCamera.value = true
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) -> Log.i("kilo", "Show camera permissions dialog")

            else -> requestPermisiionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun handleImageCaptur(uri : Uri){
        Log.i("kilo", "Image captured: $uri")
        shouldShowCamera.value = false
    }

    private fun getOutputDirectory(): File{
        val mediaDir = externalMediaDirs.firstOrNull()?.let{
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }

        }
        return if (mediaDir !=null && mediaDir.exists()) mediaDir else filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }


}

