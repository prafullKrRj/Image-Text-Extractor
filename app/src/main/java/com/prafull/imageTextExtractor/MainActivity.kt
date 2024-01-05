package com.prafull.imageTextExtractor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prafull.imageTextExtractor.presentation.MainScreen
import com.prafull.imageTextExtractor.presentation.OcrViewModel
import com.prafull.imageTextExtractor.ui.theme.ImageTextExtractorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            ImageTextExtractorTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(viewModel = viewModel(factory = OcrViewModel.Factory))
                }
            }
        }
    }
}