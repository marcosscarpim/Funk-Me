/**
 * Copyright ....
 */

package com.scarpim.funkme.domain.player

import com.scarpim.funkme.domain.model.FunkAudio

/**
 * Interface representing audio player functionality.
 */
interface FunkAudioPlayer {

    suspend fun prepare(): List<FunkAudio>

    fun play(audio: FunkAudio): Boolean

    fun stop(audio: FunkAudio): Boolean
}