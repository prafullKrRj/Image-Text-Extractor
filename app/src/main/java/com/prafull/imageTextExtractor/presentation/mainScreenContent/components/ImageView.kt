package com.prafull.imageTextExtractor.presentation.mainScreenContent.components

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext


@Composable
fun ImageWindow(image: Uri?) {
    /*if (image != null) {
        Image(bitmap = image.asImageBitmap(), contentDescription = null, contentScale = ContentScale.FillWidth)
    }*/
    val context = LocalContext.current
    val bitmap: ImageBitmap? = image?.let { loadBitmapFromUri(it, context) }
    if (bitmap != null) {
        Image(bitmap = bitmap, contentDescription = null)
    }
}
private fun loadBitmapFromUri(uri: Uri, context: Context): ImageBitmap? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        bitmap?.asImageBitmap()
    } catch (e: Exception) {
        // Handle exceptions, such as IOException or SecurityException
        null
    }
}