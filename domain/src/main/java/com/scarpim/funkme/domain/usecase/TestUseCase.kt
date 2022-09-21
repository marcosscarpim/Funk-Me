/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.domain.usecase

import android.util.Log
import com.scarpim.funkme.domain.model.FunkAudio
import com.scarpim.funkme.domain.model.FunkType
import com.scarpim.funkme.domain.player.FunkAudioPlayer
import javax.inject.Inject

class TestUseCase @Inject constructor(
    private val audioPlayer: FunkAudioPlayer
) {
    operator fun invoke(testcase: Int) {
        Log.d("Marcos", "invoke: test")
        /*if (testcase == 1) {
            audioPlayer.prepare()
        } else {
            audioPlayer.play(
                FunkAudio(
                1,
                FunkType.REPEAT
                )
            )
        }*/
    }
}