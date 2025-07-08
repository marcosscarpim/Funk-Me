package com.scarpim.funkme.recordscreen

import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import com.scarpim.funkme.domain.model.AudioRecording
import com.scarpim.funkme.domain.recorder.RecorderProvider
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
    recorderProvider: RecorderProvider,
): ViewModel() {

    private var mediaPlayer: MediaPlayer? = null

    private val _uiState = MutableStateFlow(RecordScreenState())
    val uiState: StateFlow<RecordScreenState> = _uiState.asStateFlow()

    init {
        val recordings = recorderProvider.getRecordings()
        _uiState.value = RecordScreenState(recordings)
    }

    fun onPlayClick(recording: AudioRecording) {
        val current = _uiState.value

        if (current.currentlyPlaying == recording.name) {
            stopPlayback()
        } else {
            startPlayback(recording)
        }
    }

    private fun startPlayback(recording: AudioRecording) {
        stopPlayback() // Stop any current playing

        mediaPlayer = MediaPlayer().apply {
            setDataSource(recording.file.absolutePath)
            prepare()
            start()
            setOnCompletionListener {
                stopPlayback()
            }
        }

        _uiState.value = _uiState.value.copy(
            currentlyPlaying = recording.name,
            isPlaying = true
        )
    }

    private fun stopPlayback() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null

        _uiState.value = _uiState.value.copy(
            currentlyPlaying = null,
            isPlaying = false
        )
    }

    override fun onCleared() {
        super.onCleared()
        stopPlayback()
    }
}