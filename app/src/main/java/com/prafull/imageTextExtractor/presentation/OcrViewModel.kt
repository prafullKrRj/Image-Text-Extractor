package com.prafull.imageTextExtractor.presentation

import android.net.Uri
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.prafull.imageTextExtractor.OcrApplication
import com.prafull.imageTextExtractor.data.OcrRepository
import com.prafull.imageTextExtractor.model.HistoryEntity
import com.prafull.imageTextExtractor.model.HistoryModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OcrViewModel(
    private val repository: OcrRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(State())
    val uiState = _uiState.asStateFlow()

    val history: StateFlow<List<HistoryEntity>> =
        repository.getHistory().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun startExtraction(image: Uri?) {
        if (image == null) return
        _uiState.update {
            it.copy(
                image = image,
                text = ""
            )
        }
        viewModelScope.launch {
            repository.extractTextFromImage(image).collect { generatedText ->
                _uiState.update {
                    it.copy(
                        text = uiState.value.text + generatedText
                    )
                }
            }
            repository.insertHistory(
                HistoryEntity(
                    text = uiState.value.text?: "",
                    image = uiState.value.image.toString()
                )
            )
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as OcrApplication)
                val ocrRepository = application.appContainer.ocrRepository
                OcrViewModel(repository = ocrRepository)
            }
        }
    }
}
@Stable
data class State(
    val image: Uri? = null,
    val text: String? = null,
)
data class HistoryState(
    val history: List<HistoryModel> = emptyList()
)