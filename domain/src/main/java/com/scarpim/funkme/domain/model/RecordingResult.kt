package com.scarpim.funkme.domain.model

/**
 * Sealed class with possible results when trying to record a file.
 */
sealed class RecordingResult {
    object Started : RecordingResult()
    data class Stopped(val fileName: String) : RecordingResult()
    object StoppingInProgress: RecordingResult()
    data class Error(val message: String) : RecordingResult()
}