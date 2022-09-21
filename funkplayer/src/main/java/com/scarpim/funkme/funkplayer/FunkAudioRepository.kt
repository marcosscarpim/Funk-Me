/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.funkplayer

import com.scarpim.funkme.domain.model.FunkAudio
import com.scarpim.funkme.domain.repository.AudioRepository
import com.scarpim.funkme.funkplayer.mapper.FunkAudioMapper
import com.scarpim.funkme.funkplayer.source.FunkAudioDataSource
import javax.inject.Inject

class FunkAudioRepository @Inject constructor(
    private val audioMapper: FunkAudioMapper
): AudioRepository {

    override fun getAvailable(): List<FunkAudio> =
        audioMapper.toDomain(FunkAudioDataSource.getAvailable())
}