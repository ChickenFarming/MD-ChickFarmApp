package com.dicoding.chickfarm.ui.screen.diseasedetector

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.dicoding.chickfarm.databinding.ActivityTakeImageBinding
import com.dicoding.chickfarm.modelML.DiseaseDetector
import com.dicoding.chickfarm.ui.screen.diseasedetector.CameraActivity.Companion.CAMERAX_RESULT

class TakeImageActivity : AppCompatActivity() {

    private lateinit var model: DiseaseDetector

    private lateinit var takeImageViewModel: TakeImageViewModel

    private lateinit var binding: ActivityTakeImageBinding

    private var currentImageUri: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTakeImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
//      init model ML
        model = DiseaseDetector(this, "model.tflite")

        takeImageViewModel = ViewModelProvider(this).get(TakeImageViewModel::class.java)
        takeImageViewModel.init(this)

        if (!takeImageViewModel.allPermissionsGranted()) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

        binding.galleryButton.setOnClickListener {
            startGallery()
        }

        binding.cameraXButton.setOnClickListener {
            startCameraX()
        }

        binding.detectionButton.setOnClickListener {
            detection()

        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    fun detection() {
        currentImageUri?.let { uri ->
            // Konversi URI ke Bitmap
            val bitmap = takeImageViewModel.uriToBitmap(contentResolver, uri)
            // Ubah ukuran Bitmap ke dimensi yang diinginkan (224x224)
            val resizedBitmap = bitmap?.let { takeImageViewModel.resizeBitmap(it, 224, 224) }
            // Konversi Bitmap ke array dengan dimensi (224, 224, 3)
            val inputArray = resizedBitmap?.let { takeImageViewModel.bitmapToArray(it) }
            val resultIndex = model.predict(inputArray)
            when (resultIndex) {
                0 -> showResult("Koksidiosis (Coccidiosis)")
                1 -> showResult("Sehat")
                2 -> showResult("Tetelo (New Castle Disease)")
                3 -> showResult("Salmonelosis (Salmonella)")
            }

        }
    }

    private fun showResult(diseaseName: String) {
        Toast.makeText(this, "$diseaseName", Toast.LENGTH_SHORT).show()
    }


}



