package com.scarpim.funkme.domain.model

import java.io.File

/**
 * Class representing a funk audio recorded by the app.
 *
 * @param name the recording name saved
 * @param length the length in seconds of the audio
 * @param file the [File] to access later
 */
data class AudioRecording(
    val name: String,
    val length: Long,
    val file: File,
)