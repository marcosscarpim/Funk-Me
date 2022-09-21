/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scarpim.funkme.domain.model.FunkAudio
import com.scarpim.funkme.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAvailableAudios: GetAvailableAudios,
    private val preparePlayer: PreparePlayer,
    private val playAudio: PlayAudio,
    private val stopAudio: StopAudio
): ViewModel() {

    private val funkAudios: List<FunkAudio> = getAvailableAudios()

    fun prepare() {
        viewModelScope.launch {
            preparePlayer()
        }
    }

    fun getAudios(): List<FunkAudio> = funkAudios

    fun play(audioId: Int) {
        val audio = funkAudios.find { it.id == audioId }
        audio?.let { playAudio(it) }
    }

    fun stop(audioId: Int) {
        val audio = funkAudios.find { it.id == audioId }
        audio?.let { stopAudio(it) }
    }
}