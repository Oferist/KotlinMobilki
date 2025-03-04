package com.example.practi4eskaya1011_test

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class ImageRepository(private val context: Context) {

    suspend fun downloadImage(imageUrl: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL(imageUrl)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val inputStream: InputStream = connection.inputStream
                BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                Log.e("ImageRepository", "Error downloading image", e)
                null
            }
        }
    }

    suspend fun saveImageToInternalStorage(bitmap: Bitmap, filename: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val file = File(context.filesDir, "$filename.jpg")
                val fos = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()
                true
            } catch (e: Exception) {
                Log.e("ImageRepository", "Error saving image", e)
                false
            }
        }
    }
}