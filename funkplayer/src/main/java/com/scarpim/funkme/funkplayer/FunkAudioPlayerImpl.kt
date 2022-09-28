/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.funkplayer

import com.scarpim.funkme.domain.model.FunkAudio
import com.scarpim.funkme.domain.player.FunkAudioPlayer
import com.scarpim.funkme.funkplayer.mapper.FunkAudioMapper
import com.scarpim.funkme.funkplayer.player.SoundPoolPlayer
import javax.inject.Inject

class FunkAudioPlayerImpl @Inject constructor(
    private val poolPlayer: SoundPoolPlayer,
    private val audioMapper: FunkAudioMapper
): FunkAudioPlayer {

    override suspend fun prepare(): List<FunkAudio> =
        audioMapper.toDomain(poolPlayer.prepare())

    override fun play(audio: FunkAudio): Boolean {
        poolPlayer.play(audio.id)
        return true
    }

    override fun stop(audio: FunkAudio): Boolean {
        poolPlayer.stop(audio.id)
        return true
    }
}