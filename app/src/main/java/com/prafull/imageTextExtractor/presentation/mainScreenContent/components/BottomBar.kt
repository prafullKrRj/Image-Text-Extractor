package com.prafull.imageTextExtractor.presentation.mainScreenContent.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.prafull.imageTextExtractor.R
import com.prafull.imageTextExtractor.presentation.OcrViewModel

@Composable
fun BottomAppBar(viewModel: OcrViewModel, cameraClick: () -> Unit = {}, galleryClick: () -> Unit = {}) {
    val context = LocalContext.current
    BottomAppBar(
        actions = {
            BottomAppBarActionButtons(
                id = R.drawable.baseline_share_24,
                onClick = {
                    viewModel.uiState.value.text?.let { Utils.shareText(context, it) }
                }
            )
            BottomAppBarActionButtons(
                id = R.drawable.baseline_content_copy_24,
                onClick = {
                    viewModel.uiState.value.text?.let { Utils.copyToClipboard(context, it) }
                }
            )
        },

        floatingActionButton = {
            Row {
                BottomAppBarFloatingActionButtons(
                    id = R.drawable.baseline_browse_gallery_24,
                    onClick = galleryClick
                )
                Spacer(modifier = Modifier.width(12.dp))
                BottomAppBarFloatingActionButtons(
                    id = R.drawable.baseline_camera_24,
                    onClick = cameraClick
                )
            }
        }
    )
}

@Composable
private fun BottomAppBarFloatingActionButtons(
    @DrawableRes id: Int,
    onClick: () -> Unit = {}
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
    ) {
        Icon(
            painter = painterResource(id = id),
            contentDescription = "Image Selection Button"
        )
    }
}
@Composable
private fun BottomAppBarActionButtons(
    @DrawableRes id: Int,
    onClick: () -> Unit = {}
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = id),
            contentDescription = "Copy and Share"
        )
    }
}
object Utils {
    fun copyToClipboard(context: Context, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", text)
        clipboard.setPrimaryClip(clipData)
        Toast.makeText(context, "Copied!", Toast.LENGTH_SHORT).show()
    }
    fun shareText(context: Context, text: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, text)
        context.startActivity(Intent.createChooser(intent, "Share"))

    }
}