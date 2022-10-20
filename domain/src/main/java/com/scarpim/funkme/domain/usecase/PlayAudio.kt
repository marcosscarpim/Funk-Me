/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.domain.usecase

import com.scarpim.funkme.domain.model.FunkAudio
import com.scarpim.funkme.domain.model.FunkType
import com.scarpim.funkme.domain.player.FunkAudioPlayer
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.timerTask

class PlayAudio @Inject constructor(
    private val funkAudioPlayer: FunkAudioPlayer
) {

    /**
     * Play an audio.
     *
     * @param audio the [FunkAudio] to play
     * @param onFinish function called when audio is finished, boolean represents if audio should
     * be stopped
     */
    operator fun invoke(audio: FunkAudio, onFinish: (Boolean) -> Unit) {
        funkAudioPlayer.play(audio)
        if (audio.type != FunkType.REPEAT) {
            val timer = Timer()
            val myTask = timerTask {
                onFinish(true)
            }
            timer.schedule(myTask, audio.duration.toLong())
        } else {
            onFinish(false)
        }
    }
}