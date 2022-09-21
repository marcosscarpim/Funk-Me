/**
 * Copyright ....
 */

package com.scarpim.funkme.domain.model

/**
 * Class representing an audio saved in app.
 *
 * @param id the unique id of the audio
 * @param type the [FunkType] of the audio
 * @param name the optional name of the audio
 */
data class FunkAudio(
    val id: Int,
    val type: FunkType,
    val name: String = ""
)