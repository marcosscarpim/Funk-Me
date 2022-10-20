/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scarpim.funkme.domain.model.FunkAudio
import com.scarpim.funkme.domain.usecase.PlayAudio
import com.scarpim.funkme.domain.usecase.PreparePlayer
import com.scarpim.funkme.domain.usecase.StopAudio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val preparePlayer: PreparePlayer,
    private val playAudio: PlayAudio,
    private val stopAudio: StopAudio
): ViewModel() {

    val funkAudios = mutableStateListOf<FunkAudio>()

    fun prepare() {
        viewModelScope.launch {
            funkAudios.clear()
            funkAudios.addAll(preparePlayer())
        }
    }

    fun play(audioId: Int) {
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

    fun stop(audioId: Int) {
        val audio = funkAudios.find { it.id == audioId }
        audio?.let {
            val audioIndex = funkAudios.indexOf(it)
            stopAudio(it)
            funkAudios.removeAt(audioIndex)
            funkAudios.add(audioIndex, it.copy(isPlaying = false))
        }
    }
}