package com.scarpim.funkme.domain.model

/**
 * Class representing a funk audio recorded by the app.
 *
 * @param name the recording name saved
 * @param length the length in seconds of the audio
 */
data class AudioRecording(
    val name: String,
    val length: Long,
)