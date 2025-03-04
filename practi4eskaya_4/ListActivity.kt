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

// ListActivity: Displays timestamps in a RecyclerView
class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val photosDir = File(getExternalFilesDir(null), "photos")
        val file = File(photosDir, "date")

        val timestamps = if (file.exists()) {
            file.readLines().sortedDescending()
        } else {
            emptyList()
        }

        recyclerView.adapter = TimestampsAdapter(timestamps)
    }
}

// TimestampsAdapter: Binds timestamps to RecyclerView
class TimestampsAdapter(private val timestamps: List<String>) : RecyclerView.Adapter<TimestampsAdapter.ViewHolder>() {

    class ViewHolder(val view: android.widget.TextView) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewHolder {
        val textView = android.widget.TextView(parent.context)
        textView.setPadding(16, 16, 16, 16)
        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.text = timestamps[position]
    }

    override fun getItemCount(): Int = timestamps.size
}