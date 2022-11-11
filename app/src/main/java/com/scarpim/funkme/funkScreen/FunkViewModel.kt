/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.funkScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scarpim.funkme.domain.model.FunkAudio
import com.scarpim.funkme.domain.usecase.PlayAudio
import com.scarpim.funkme.domain.usecase.PreparePlayer
import com.scarpim.funkme.domain.usecase.StopAudio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FunkViewModel @Inject constructor(
    private val preparePlayer: PreparePlayer,
    private val playAudio: PlayAudio,
    private val stopAudio: StopAudio
): ViewModel() {

    private val _state: MutableStateFlow<FunkScreenState> = MutableStateFlow(FunkScreenState.Loading)
    val state: StateFlow<FunkScreenState>
        get() = _state

    private val funkAudios = mutableStateListOf<FunkAudio>()

    fun onAction(action: FunkScreenAction) {
        when (action) {
            FunkScreenAction.LoadAudios -> prepare()
            is FunkScreenAction.AudioClicked -> {
                if (action.audio.isPlaying) {
                    stop(action.audio.id)
                } else {
                    play(action.audio.id)
                }
            }
        }
    }

    private fun prepare() {
        viewModelScope.launch {
            funkAudios.clear()
            funkAudios.addAll(preparePlayer())
            _state.tryEmit(FunkScreenState.Loaded(funkAudios))
        }
    }

    private fun play(audioId: Int) {
        val audio = funkAudios.find { it.id == audioId }
        audio?.let {
            val audioIndex = funkAudios.indexOf(it)
            playAudio(it) { shouldFinish ->
                if (shouldFinish) {
                    funkAudios.removeAt(audioIndex)
                    funkAudios.add(audioIndex, it.copy(isPlaying = false))
                }
            }
            funkAudios.removeAt(audioIndex)
            funkAudios.add(audioIndex, it.copy(isPlaying = true))
        }
    }

    private fun stop(audioId: Int) {
        val audio = funkAudios.find { it.id == audioId }
        audio?.let {
            val audioIndex = funkAudios.indexOf(it)
            stopAudio(it)
            funkAudios.removeAt(audioIndex)
            funkAudios.add(audioIndex, it.copy(isPlaying = false))
        }
    }
}