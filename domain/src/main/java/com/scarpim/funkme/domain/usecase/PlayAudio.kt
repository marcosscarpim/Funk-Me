/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.domain.usecase

import com.scarpim.funkme.domain.model.FunkAudio
import com.scarpim.funkme.domain.player.FunkAudioPlayer
import javax.inject.Inject

class PlayAudio @Inject constructor(
    private val funkAudioPlayer: FunkAudioPlayer
) {

    operator fun invoke(audio: FunkAudio) {
        funkAudioPlayer.play(audio)
    }
}