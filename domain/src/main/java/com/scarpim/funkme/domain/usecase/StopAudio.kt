/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.domain.usecase

import com.scarpim.funkme.domain.model.FunkAudio
import com.scarpim.funkme.domain.player.FunkAudioPlayer
import javax.inject.Inject

class StopAudio @Inject constructor(
    private val audioPlayer: FunkAudioPlayer
) {

    operator fun invoke(audio: FunkAudio) {
        audioPlayer.stop(audio)
    }
}