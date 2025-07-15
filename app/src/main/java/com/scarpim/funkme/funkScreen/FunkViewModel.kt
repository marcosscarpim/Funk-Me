/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.funkScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scarpim.funkme.domain.model.FunkAudio
import com.scarpim.funkme.domain.model.RecordingResult
import com.scarpim.funkme.domain.recorder.AudioRecorder
import com.scarpim.funkme.domain.usecase.OnRecording
import com.scarpim.funkme.domain.usecase.PlayAudio
import com.scarpim.funkme.domain.usecase.PreparePlayer
import com.scarpim.funkme.domain.usecase.StopAudio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

data class FunkScreenState(
    val isLoading: Boolean = false,
    val audios: List<FunkAudio> = emptyList(),
    val recordingResult: RecordingResult? = null,
)

@HiltViewModel
class FunkViewModel @Inject constructor(
    private val preparePlayer: PreparePlayer,
    private val playAudio: PlayAudio,
    private val stopAudio: StopAudio,
    private val onRecording: OnRecording,
    private val audioRecorder: AudioRecorder,
) : ViewModel() {

    private val _uiState = MutableStateFlow(FunkScreenState())
    val uiState: StateFlow<FunkScreenState> = _uiState.asStateFlow()

    init {
        prepare()
        viewModelScope.launch {
            audioRecorder.events.collect { result ->
                _uiState.update { it.copy(recordingResult = result) }
            }
        }
    }

    fun onAction(action: FunkScreenAction) {
        when (action) {
            FunkScreenAction.LoadAudios -> {} //prepare()
            is FunkScreenAction.AudioClicked -> onAudioClicked(action.audio)
            is FunkScreenAction.OnRecordClicked -> onRecordClicked(action)
            FunkScreenAction.ClearRecordingResult -> clearRecordingFeedback()
        }
    }

    private fun prepare() {
        viewModelScope.launch {
            val audios = preparePlayer()
            _uiState.update {
                it.copy(audios = audios)
            }
        }
    }

    private fun onAudioClicked(audio: FunkAudio) {
        if (audio.isPlaying) {
            stop(audio)
        } else {
            play(audio)
        }

        _uiState.update { currentState ->
            currentState.copy(audios = updateList(currentState, audio.id, !audio.isPlaying))
        }
    }

    private fun play(audio: FunkAudio) {
        playAudio(audio) { shouldFinish ->
            if (shouldFinish) {
                _uiState.update { currentState ->
                    currentState.copy(audios = updateList(currentState, audio.id, false))
                }
            }
        }
    }

    private fun stop(audio: FunkAudio) {
        stopAudio(audio)
    }

    private fun updateList(
        currentState: FunkScreenState,
        audioId: Int,
        isPlaying: Boolean
    ): List<FunkAudio> = currentState.audios.map {
        if (it.id == audioId) {
            it.copy(isPlaying = isPlaying)
        } else {
            it
        }
    }

    private fun onRecordClicked(action: FunkScreenAction.OnRecordClicked) {
        onRecording(action.recording, action.mediaProjectionIntent)
    }

    private fun clearRecordingFeedback() {
        _uiState.update { it.copy(recordingResult = null) }
    }
}