/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.funkplayer.mapper

import com.scarpim.funkme.domain.model.FunkAudio as DomainAudio
import com.scarpim.funkme.funkplayer.model.FunkAudio as PlayerAudio
import javax.inject.Inject

class FunkAudioMapper @Inject constructor() {

    fun toDomain(playerList: List<PlayerAudio>): List<DomainAudio> =
        playerList.map(::toDomain)

    private fun toDomain(playerAudio: PlayerAudio): DomainAudio =
        DomainAudio(playerAudio.id, playerAudio.type)
}