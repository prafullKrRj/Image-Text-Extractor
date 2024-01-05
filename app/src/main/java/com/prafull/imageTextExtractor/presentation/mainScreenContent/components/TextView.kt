package com.prafull.imageTextExtractor.presentation.mainScreenContent.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun TextWindow(text: String?) {
    if (!text.isNullOrEmpty()) {
        Text(text = text)
    }
}


