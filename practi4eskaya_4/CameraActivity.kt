package com.example.practi4eskaya_4

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


// CameraActivity: Handles camera preview and photo capturing
class CameraActivity : AppCompatActivity() {
    private val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private lateinit var previewView: PreviewView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        previewView = findViewById(R.id.preview_view)

        if (!hasPermissions()) {
            ActivityCompat.requestPermissions(this, permissions, 1)
        } else {
            startCamera()
        }

        val buttonCapture = findViewById<Button>(R.id.button_capture)
        buttonCapture.setOnClickListener {
            savePhotoTimestamp()
        }
    }

    private fun hasPermissions(): Boolean {
        return permissions.all { perm ->
            ContextCompat.checkSelfPermission(this, perm) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview)
            } catch (exc: Exception) {
                Log.e("CameraActivity", "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun savePhotoTimestamp() {
        val photosDir = File(getExternalFilesDir(null), "photos")
        if (!photosDir.exists()) {
            val created = photosDir.mkdirs()
            Log.d("CameraActivity", "Photos directory created: $created")
        }

        val file = File(photosDir, "date")
        if (!file.exists()) {
            val created = file.createNewFile()
            Log.d("CameraActivity", "Date file created: $created")
        }

        val currentTime = System.currentTimeMillis() + (3 * 60 * 60 * 1000) // Добавляем 3 часа
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(currentTime))
        file.appendText("$timestamp\n")
        Log.d("CameraActivity", "Saved timestamp: $timestamp at ${file.absolutePath}")
    }
}