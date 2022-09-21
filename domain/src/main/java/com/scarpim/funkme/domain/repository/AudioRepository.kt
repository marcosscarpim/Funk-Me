/**
 * Copyright ....
 */

package com.scarpim.funkme.domain.repository

import com.scarpim.funkme.domain.model.FunkAudio

/**
 * Audio repository definition.
 */
interface AudioRepository {

    /**
     * Get available audios.
     *
     * @return the list of audios.
     */
    fun getAvailable(): List<FunkAudio>
}