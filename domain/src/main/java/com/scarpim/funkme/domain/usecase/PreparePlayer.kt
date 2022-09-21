/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.domain.usecase

import com.scarpim.funkme.domain.player.FunkAudioPlayer
import javax.inject.Inject

class PreparePlayer @Inject constructor(
    private val audioPlayer: FunkAudioPlayer
) {
    suspend operator fun invoke() {
        audioPlayer.prepare()
    }
}