/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scarpim.funkme.domain.model.FunkAudio
import com.scarpim.funkme.domain.model.FunkType
import com.scarpim.funkme.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject
import kotlin.concurrent.timer
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
}