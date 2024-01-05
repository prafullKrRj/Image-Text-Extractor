package com.prafull.imageTextExtractor.data

import android.content.Context
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.prafull.imageTextExtractor.data.local.HistoryDatabase

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
            historyDao = HistoryDatabase.getDatabase(context).historyDao()
        )
    }

}