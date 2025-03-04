package com.example.practi4eskaya1011_test

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.ui.res.painterResource
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val context = LocalContext.current
    val imageRepository = remember { ImageRepository(context) }
    var imageUrl by remember { mutableStateOf(TextFieldValue("")) }
    val coroutineScope = rememberCoroutineScope()

    // Список изображений
    var imageList by remember { mutableStateOf<List<android.graphics.Bitmap>>(emptyList()) }

    // Состояние текущего выбранного экрана
    var selectedScreen by remember { mutableStateOf("Home") }

    // Состояние для открытия/закрытия Drawer
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScopeDrawer = rememberCoroutineScope()

    // Основной Scaffold
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Navigation",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                NavigationDrawerItem(
                    label = { Text("Home") },
                    selected = selectedScreen == "Home",
                    onClick = {
                        selectedScreen = "Home"
                        coroutineScopeDrawer.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Gallery") },
                    selected = selectedScreen == "Gallery",
                    onClick = {
                        selectedScreen = "Gallery"
                        coroutineScopeDrawer.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Dynamic Image List") },
                    navigationIcon = {
                        IconButton(onClick = { coroutineScopeDrawer.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBar {
                    NavigationBar {
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                            label = { Text("Home") },
                            selected = selectedScreen == "Home",
                            onClick = { selectedScreen = "Home" }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.List, contentDescription = "Home") },
                            label = { Text("Gallery") },
                            selected = selectedScreen == "Gallery",
                            onClick = { selectedScreen = "Gallery" }
                        )
                    }
                }
            },
            content = {
                when (selectedScreen) {
                    "Home" -> HomeScreen(
                        imageUrl = imageUrl,
                        onImageUrlChange = { imageUrl = it },
                        imageList = imageList,
                        onAddImage = { bitmap ->
                            imageList = imageList + bitmap
                            imageUrl = TextFieldValue("") // Очищаем поле ввода
                        },
                        imageRepository = imageRepository
                    )
                    "Gallery" -> GalleryScreen(imageList = imageList)
                }
            }
        )
    }

    // Добавление WorkManager
    WorkManager.getInstance(context).enqueue(
        OneTimeWorkRequestBuilder<ImageTaskWorker>()
            .setInitialDelay(15, TimeUnit.MINUTES) // Выполнение задачи через 15 минут после запуска
            .build()
    )
}

@Composable
fun HomeScreen(
    imageUrl: TextFieldValue,
    onImageUrlChange: (TextFieldValue) -> Unit,
    imageList: List<android.graphics.Bitmap>,
    onAddImage: (android.graphics.Bitmap) -> Unit,
    imageRepository: ImageRepository
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        BasicTextField(
            value = imageUrl,
            onValueChange = { onImageUrlChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(50.dp)
                .border(1.dp, MaterialTheme.colorScheme.primary)
                .padding(8.dp),
            decorationBox = { innerTextField ->
                Box(Modifier.fillMaxSize()) {
                    if (imageUrl.text.isEmpty()) {
                        Text(
                            "Enter image URL",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    innerTextField()
                }
            }
        )

        Button(
            onClick = {
                if (imageUrl.text.isNotEmpty()) {
                    coroutineScope.launch {
                        val loadedImage = imageRepository.downloadImage(imageUrl.text)
                        if (loadedImage != null) {
                            onAddImage(loadedImage)
                            Toast.makeText(context, "Image added to list", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Error loading image", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Add Image to List")
        }
    }
}

@Composable
fun GalleryScreen(imageList: List<android.graphics.Bitmap>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(imageList.size) { index ->
            val bitmap = imageList[index]
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

class ImageTaskWorker(appContext: android.content.Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        // Задача, которую будет выполнять WorkManager
        return Result.success()
    }
}

//@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}