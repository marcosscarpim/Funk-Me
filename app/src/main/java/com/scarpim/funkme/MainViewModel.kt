/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scarpim.funkme.domain.model.FunkAudio
import com.scarpim.funkme.domain.model.FunkType
import com.scarpim.funkme.domain.usecase.PlayAudio
import com.scarpim.funkme.domain.usecase.PreparePlayer
import com.scarpim.funkme.domain.usecase.StopAudio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.timerTask

@HiltViewModel
class MainViewModel @Inject constructor(
    private val preparePlayer: PreparePlayer,
    private val playAudio: PlayAudio,
    private val stopAudio: StopAudio
): ViewModel() {

    val funkAudios = mutableStateListOf<FunkAudio>()

    fun prepare() {
        viewModelScope.launch {
            funkAudios.addAll(preparePlayer())
        }
    }

    fun play(audioId: Int) {
        val audio = funkAudios.find { it.id == audioId }
        audio?.let {
            val audioIndex = funkAudios.indexOf(it)
            playAudio(it)
            funkAudios.removeAt(audioIndex)
            funkAudios.add(audioIndex, it.copy(isPlaying = true))
            if (it.type != FunkType.REPEAT) {
                val timer = Timer()
                val myTask = timerTask {
                    funkAudios.removeAt(audioIndex)
                    funkAudios.add(audioIndex, it.copy(isPlaying = false))
                }
                timer.schedule(myTask, it.duration.toLong())
            }
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

    private fun updateItemState(audio: FunkAudio, isPlaying: Boolean) {
        val audioIndex = funkAudios.indexOf(audio)
        funkAudios.removeAt(audioIndex)
        funkAudios.add(audioIndex, audio.copy(isPlaying = isPlaying))
    }
}