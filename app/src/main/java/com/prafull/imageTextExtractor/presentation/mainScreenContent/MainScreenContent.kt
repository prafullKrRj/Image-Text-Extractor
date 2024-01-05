package com.prafull.imageTextExtractor.presentation.mainScreenContent

import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.prafull.imageTextExtractor.presentation.OcrViewModel
import com.prafull.imageTextExtractor.presentation.createImageUri
import com.prafull.imageTextExtractor.presentation.mainScreenContent.components.BottomAppBar
import com.prafull.imageTextExtractor.presentation.mainScreenContent.components.ImageWindow
import com.prafull.imageTextExtractor.presentation.mainScreenContent.components.MainScreenTopBar
import com.prafull.imageTextExtractor.presentation.mainScreenContent.components.TextWindow


@Composable
fun MainScreenContent(drawerIcon: () -> Unit, viewModel: OcrViewModel) {
    val context = LocalContext.current
    val photoUri = createImageUri(context = context)
    val state by viewModel.uiState.collectAsState()
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = {
            if (it) {
                viewModel.startExtraction(photoUri)
            }
            else {
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
            }
        }
    )
    val getImages = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {
        viewModel.startExtraction(it)
    }
    val perMissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
        if (it) {
            cameraLauncher.launch(photoUri)
        }
        else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }
    Scaffold (
        topBar = { MainScreenTopBar(drawerIcon = drawerIcon) },
        bottomBar = {
            BottomAppBar(
                viewModel = viewModel,
                cameraClick = {
                    if (checkPermission(context)) {
                        cameraLauncher.launch(photoUri)
                    }
                    else {
                        perMissionLauncher.launch(android.Manifest.permission.CAMERA)
                    }
                },
                galleryClick = {
                    getImages.launch(
                        PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = (paddingValues),
            modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp)
        ) {
            item {
                ImageWindow(
                    image = state.image
                )
            }
            item {
                TextWindow(
                    text = state.text
                )
            }
        }
    }
}

fun checkPermission(context: Context): Boolean {
    val permissionChecked = ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)
    return permissionChecked == PackageManager.PERMISSION_GRANTED
}