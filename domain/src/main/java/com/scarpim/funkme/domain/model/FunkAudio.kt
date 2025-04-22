/**
 * Copyright ....
 */

package com.scarpim.funkme.domain.model

/**
 * Class representing an audio saved in app.
 *
 * @param id the unique id of the audio
 * @param type the [FunkType] of the audio
 * @param duration the duration of the audio in ms
 * @param isPlaying if audio isPlaying or not
 * @param name the optional name of the audio
 */
data class FunkAudio(
    val id: Int,
    val type: FunkType,
    val duration: Int = 0,
    var isPlaying: Boolean = false,
    val name: String = ""
)