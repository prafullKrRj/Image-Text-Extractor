package com.prafull.imageTextExtractor.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognizer
import com.prafull.imageTextExtractor.MainActivity
import com.prafull.imageTextExtractor.data.local.HistoryDao
import com.prafull.imageTextExtractor.model.HistoryEntity
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.processNextEventInCurrentThread
import java.lang.Exception

interface OcrRepository {
    fun extractTextFromImage(uri: Uri): Flow<String>
    suspend fun insertHistory(history: HistoryEntity)
    fun getHistory(): Flow<List<HistoryEntity>>
    suspend fun deleteHistory(history: HistoryEntity)
}

class OcrRepositoryImpl (
    private val context: Context,
    private val textRecognizer: TextRecognizer,
    private val historyDao: HistoryDao
) : OcrRepository {
    override fun extractTextFromImage(uri: Uri): Flow<String> {
        return callbackFlow {
            val inputImage = InputImage.fromFilePath(context, uri)
            textRecognizer.process(inputImage)
                .addOnSuccessListener {
                    launch {
                        send(it.text)
                    }
                }
                .addOnFailureListener {
                    Log.d("textimage", "extractTextFromImage: ${it.message}")
                }
            awaitClose { }
        }
    }

    override suspend fun insertHistory(history: HistoryEntity) = historyDao.insertHistory(history)

    override fun getHistory(): Flow<List<HistoryEntity>> = historyDao.getHistory()

    override suspend fun deleteHistory(history: HistoryEntity) = historyDao.deleteHistory(history)
}