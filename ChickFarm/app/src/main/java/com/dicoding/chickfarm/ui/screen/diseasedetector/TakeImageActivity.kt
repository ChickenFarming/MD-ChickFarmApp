package com.dicoding.chickfarm.ui.screen.diseasedetector

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dicoding.chickfarm.R
import com.dicoding.chickfarm.databinding.ActivityTakeImageBinding
import com.dicoding.chickfarm.modelML.DiseaseDetector
import com.dicoding.chickfarm.ui.screen.diseasedetector.CameraActivity.Companion.CAMERAX_RESULT
import com.dicoding.chickfarm.ui.screen.diseasedetector.result.HealtyResultFragment
import com.dicoding.chickfarm.ui.screen.diseasedetector.result.ResultFragment

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
            Log.d("TakeImageActivity", "Image URI: $currentImageUri")
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
            Log.d("TakeImageActivity", "CameraX Image URI: $currentImageUri")
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            Glide.with(this)
                .load(it)
                .apply(RequestOptions().transform(RoundedCorners(100)))
                .into( binding.previewImageView)

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
                0 -> showResult(getString(R.string.disease_1), getString(R.string.disease_desc_1))
                1 -> showHealtyResult(getString(R.string.healty))
                2 -> showResult(getString(R.string.disease_2),getString(R.string.disease_desc_2))
                3 -> showResult(getString(R.string.disease_3),getString(R.string.disease_desc_3))
            }

        }
    }

    private fun showResult(diseaseName: String, desc:String) {
        val resultFragment = ResultFragment.newInstance(diseaseName, desc)
        resultFragment.show(supportFragmentManager, ResultFragment::class.java.simpleName)
    }

    private fun showHealtyResult(text : String){
        val resultFragment = HealtyResultFragment.newInstance(text)
        resultFragment.show(supportFragmentManager, ResultFragment::class.java.simpleName)
    }


}



