package com.icdominguez.socialmediagamerkotlin.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.icdominguez.socialmediagamerkotlin.R
import com.icdominguez.socialmediagamerkotlin.common.Constants
import com.icdominguez.socialmediagamerkotlin.common.Utils
import com.icdominguez.socialmediagamerkotlin.databinding.ActivityCameraXactivityBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class CameraXActivity : AppCompatActivity() {

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var outputDirectory: File

    private var cameraProvider: ProcessCameraProvider? = null

    private var cameraFront: Boolean = false
    private var cameraBack: Boolean = false

    private var camera: Camera? = null
    private var imageCapture: ImageCapture? = null
    private var preview: Preview? = null
    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK
    
    private lateinit var binding: ActivityCameraXactivityBinding

    companion object {
        private const val TAG = "CameraX"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraXactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpView()
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = File(outputDirectory, SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis()) + ".jpg")

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions, ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo capture succeeded: $savedUri"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)

                    val intent = this@CameraXActivity.intent
                    intent.putExtra(Constants.INTENT_CAMARAX, savedUri.toString())
                    setResult(RESULT_OK, intent)
                    finish()
                }
            })
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {

            cameraProvider = cameraProviderFuture.get()

            lensFacing = when {
                hasBackCamera() -> CameraSelector.LENS_FACING_BACK
                hasFrontCamera() -> CameraSelector.LENS_FACING_FRONT
                else -> throw IllegalStateException("Back and front camera are unavailable")
            }

            updateCameraSwitchButton()
            bindCameraUseCases()

        }, ContextCompat.getMainExecutor(this))
    }

    private fun setUpView() {

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()

        binding.photoViewButton.setOnClickListener {
            openGallery()
        }

        binding.cameraSwitchButton.setOnClickListener {
            lensFacing = if (CameraSelector.LENS_FACING_FRONT == lensFacing) {
                CameraSelector.LENS_FACING_BACK
            } else {
                CameraSelector.LENS_FACING_FRONT
            }
            bindCameraUseCases()
        }

        binding.captureImage.setOnClickListener {
            takePhoto()
        }

        binding.viewFinder.post {
            startCamera()
        }
    }

    private fun openGallery() {
        var galeryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        galeryIntent.type = "image/*"
        startActivityForResult(galeryIntent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK && requestCode == 100) {
            var photofile = Utils().createFileFromURI(this, data!!.data!!)
            Toast.makeText(this, "Image path: ${photofile.absolutePath}", Toast.LENGTH_SHORT).show()

            val intent = this@CameraXActivity.intent
            intent.putExtra(Constants.INTENT_CAMARAX, photofile.absolutePath)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let { File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun bindCameraUseCases() {

        try {
            val metrics = windowManager.currentWindowMetrics.bounds
            Log.d(TAG, "Screen metrics: ${metrics.width()} x ${metrics.height()}")

            val screenAspectRatio = aspectRatio(metrics.width(), metrics.height())
            Log.d(TAG, "Preview aspect ratio: $screenAspectRatio")

            val rotation = binding.viewFinder.display.rotation

            val cameraProvider = cameraProvider
                ?: throw IllegalStateException("Camera initialization failed.")

            if (cameraFront) lensFacing = CameraSelector.LENS_FACING_FRONT
            if (cameraBack) lensFacing = CameraSelector.LENS_FACING_BACK
            val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

            preview = Preview.Builder()
                .setTargetAspectRatio(screenAspectRatio)
                .setTargetRotation(rotation)
                .build()

            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .setTargetResolution(Size(1080,1920))
                .setTargetRotation(rotation)
                .build()

            cameraProvider.unbindAll()

            camera = cameraProvider.bindToLifecycle( this, cameraSelector, preview, imageCapture)

            preview?.setSurfaceProvider(binding.viewFinder.surfaceProvider)

        } catch (exc: Exception) {
            Log.e(TAG, "Use case binding failed", exc)
            exc.printStackTrace()
        }
    }

    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    private fun hasBackCamera(): Boolean {
        return cameraProvider?.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA) ?: false
    }

    private fun hasFrontCamera(): Boolean {
        return cameraProvider?.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA) ?: false
    }

    private fun updateCameraSwitchButton() {
        try {
            if (cameraBack || cameraFront) {
                binding.cameraSwitchButton.isEnabled = false
                binding.cameraSwitchButton.visibility = View.GONE
            }
            else binding.cameraSwitchButton.isEnabled = hasBackCamera() && hasFrontCamera()
        } catch (exception: CameraInfoUnavailableException) {
            binding.cameraSwitchButton.isEnabled = false
        }
    }
}