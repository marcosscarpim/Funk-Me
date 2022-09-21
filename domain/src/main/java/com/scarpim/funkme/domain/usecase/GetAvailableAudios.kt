/**
 * Copyright ....
 */

package com.scarpim.funkme.domain.usecase

import com.scarpim.funkme.domain.model.FunkAudio
import com.scarpim.funkme.domain.repository.AudioRepository
import javax.inject.Inject

/**
 * Use-case to get available audios in app.
 */
class GetAvailableAudios @Inject constructor(
    private val audioRepository: AudioRepository
) {
    /**
     * Get list of available [FunkAudio] ordered by Funk Type enum order.
     *
     * @return the ordered list of [FunkAudio]
     */
    operator fun invoke(): List<FunkAudio> = audioRepository.getAvailable().sortedBy { it.type }
}