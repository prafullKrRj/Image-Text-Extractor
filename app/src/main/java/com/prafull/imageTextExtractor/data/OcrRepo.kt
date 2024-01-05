package com.prafull.imageTextExtractor.data

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognizer
import com.prafull.imageTextExtractor.data.local.HistoryDao
import com.prafull.imageTextExtractor.model.HistoryEntity
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

interface OcrRepository {
    fun extractTextFromImage(uri: Uri): Flow<String>
    fun getHistory(): Flow<List<HistoryEntity>>
    suspend fun insertHistory(historyEntity: HistoryEntity)
    suspend fun deleteHistory(historyEntity: HistoryEntity)
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
    override fun getHistory(): Flow<List<HistoryEntity>> {
        return callbackFlow {
            historyDao.getAllHistory().collect {
                launch {
                    send(it)
                }
            }
            awaitClose { }
        }
    }
    override suspend fun insertHistory(historyEntity: HistoryEntity) {
        historyDao.updateHistory(historyEntity)
    }
    override suspend fun deleteHistory(historyEntity: HistoryEntity) {
        historyDao.deleteHistory(historyEntity)
    }

}