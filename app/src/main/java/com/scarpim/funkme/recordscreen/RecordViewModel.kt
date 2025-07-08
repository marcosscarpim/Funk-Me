package com.scarpim.funkme.recordscreen

import androidx.lifecycle.ViewModel
import com.scarpim.funkme.domain.model.AudioRecording
import com.scarpim.funkme.domain.recorder.RecorderProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class RecordScreenState(
    val recordings: List<AudioRecording> = emptyList()
)

@HiltViewModel
class RecordViewModel @Inject constructor(
    recorderProvider: RecorderProvider,
): ViewModel() {

    private val _uiState = MutableStateFlow(RecordScreenState())
    val uiState: StateFlow<RecordScreenState> = _uiState.asStateFlow()

    init {
        val recordings = recorderProvider.getRecordings()
        _uiState.value = RecordScreenState(recordings)
    }
}