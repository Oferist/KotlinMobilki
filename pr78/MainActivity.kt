package com.example.pr78

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pr78.R
import com.pr78.pr78.ImageRepository
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var urlEditText: EditText
    private lateinit var downloadButton: Button
    private lateinit var imageView: ImageView
    private val imageRepository = ImageRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        urlEditText = findViewById(R.id.urlEditText)
        downloadButton = findViewById(R.id.downloadButton)
        imageView = findViewById(R.id.imageView)

        downloadButton.setOnClickListener {
            val imageUrl = urlEditText.text.toString()
            if (imageUrl.isNotEmpty()) {
                downloadImage(imageUrl)
            }
        }
    }

    private fun downloadImage(imageUrl: String) {
        // Запуск двух потоков: загрузка и сохранение
        lifecycleScope.launch(Dispatchers.IO) {
            val imageBitmap = imageRepository.downloadImage(imageUrl)
            if (imageBitmap != null) {
                imageRepository.saveImageToDisk(applicationContext, imageBitmap)

                withContext(Dispatchers.Main) {
                    // Отображение изображения на ImageView
                    Picasso.get().load(imageUrl).into(imageView)
                }
            }
        }
    }
}