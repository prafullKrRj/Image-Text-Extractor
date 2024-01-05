package com.prafull.imageTextExtractor.data

import android.content.Context
import androidx.compose.ui.platform.isDebugInspectorInfoEnabled
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.prafull.imageTextExtractor.data.local.HistoryDB

interface AppContainer {
    val ocrRepository: OcrRepository
}
class AppContainerImpl(
    private val context: Context
): AppContainer {

    // Dependency Injection
    override val ocrRepository: OcrRepository by lazy {
        OcrRepositoryImpl(
            context = context,
            textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS),
            historyDao = HistoryDB.getDatabase(context).historyDao()
        )
    }

}