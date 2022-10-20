/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.domain

import com.scarpim.funkme.domain.model.FunkAudio
import com.scarpim.funkme.domain.model.FunkType
import com.scarpim.funkme.domain.player.FunkAudioPlayer
import com.scarpim.funkme.domain.usecase.PlayAudio
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class PlayAudioTest {

    private val audioPlayer: FunkAudioPlayer = mock()
    private lateinit var playAudio: PlayAudio

    @Before
    fun setup() {
        playAudio = PlayAudio(audioPlayer)
        whenever(audioPlayer.play(any())).thenReturn(true)
    }

    @Test
    fun `Check when Sound audio is not repeatable if onFinish is called with false`() {
        val funkAudio = FunkAudio(1, FunkType.SOUND, 100)
        playAudio(funkAudio) { shouldStop ->
            assert(shouldStop)
        }
    }

    @Test
    fun `Check when Voice audio is not repeatable if onFinish is called with false`() {
        val funkAudio = FunkAudio(1, FunkType.VOICE, 100)
        playAudio(funkAudio) { shouldStop ->
            assert(shouldStop)
        }
    }

    @Test
    fun `Check when Repeat audio is not repeatable if onFinish is called with false`() {
        val funkAudio = FunkAudio(1, FunkType.REPEAT, 100)
        playAudio(funkAudio) { shouldStop ->
            assert(!shouldStop)
        }
    }
}