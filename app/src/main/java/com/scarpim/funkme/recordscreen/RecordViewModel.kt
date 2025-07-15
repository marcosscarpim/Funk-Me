package com.scarpim.funkme.recordscreen

import androidx.lifecycle.ViewModel
import com.scarpim.funkme.domain.model.AudioRecording
import com.scarpim.funkme.domain.recorder.RecorderProvider
import com.scarpim.funkme.domain.util.logD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class RecordScreenState(
    val recordings: List<AudioRecording> = emptyList(),
    val currentlyPlaying: String? = null,
    val isPlaying: Boolean = false,
)

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val recorderProvider: RecorderProvider,
): ViewModel() {

    private val _uiState = MutableStateFlow(RecordScreenState())
    val uiState: StateFlow<RecordScreenState> = _uiState.asStateFlow()

    init {
        val recordings = recorderProvider.getRecordings()
        _uiState.value = RecordScreenState(recordings)
    }

    fun onPlayClick(recording: AudioRecording) {
        logD { "onPlayClick - $recording, isPlaying = ${_uiState.value.currentlyPlaying}" }
        if (_uiState.value.currentlyPlaying == recording.name && recorderProvider.isPlaying()) {
            recorderProvider.stop()
            _uiState.value = _uiState.value.copy(currentlyPlaying = null)
        } else {
            recorderProvider.play(recording)
            recorderProvider.setOnCompletionListener {
                _uiState.value = _uiState.value.copy(currentlyPlaying = null)
            }
            _uiState.value = _uiState.value.copy(currentlyPlaying = recording.name)
        }
    }

    override fun onCleared() {
        super.onCleared()
        recorderProvider.stop()
    }
}