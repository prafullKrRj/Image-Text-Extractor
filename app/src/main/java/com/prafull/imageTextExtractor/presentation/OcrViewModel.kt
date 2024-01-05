package com.prafull.imageTextExtractor.presentation

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OcrViewModel(
    private val repository: OcrRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(State())
    val uiState = _uiState.asStateFlow()

    val history: StateFlow<List<HistoryModel>> = repository.getHistory().map { list ->
        list.map {
            HistoryModel(
                image = Uri.parse(it.image),
                text = it.text
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    private fun getText() {
        if (uiState.value.image != null) {
            viewModelScope.launch {
                repository.extractTextFromImage(uiState.value.image!!).collect { generatedText ->
                    _uiState.update {
                        it.copy(
                            text = uiState.value.text + generatedText
                        )
                    }
                }
            }
        }
    }
    fun startExtraction(image: Uri?) {
        _uiState.update {
            it.copy(
                image = image,
                text = ""
            )
        }
        getText()
        updateDatabase()
    }
    private fun updateDatabase() {
        if (!_uiState.value.text.isNullOrEmpty() && _uiState.value.text != "")
        viewModelScope.launch {
            repository.insertHistory(
                HistoryEntity(
                    image = uiState.value.image.toString(),
                    text = uiState.value.text!!
                )
            )
        }
    }
    private fun getAllFromDb() {
        viewModelScope.launch {
            val x = repository.getHistory().collect { list ->
                list.map {
                    HistoryModel(
                        image = Uri.parse(it.image),
                        text = it.text
                    )
                }
            }
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
    val text: String? = null
)