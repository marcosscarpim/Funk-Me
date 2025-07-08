package com.scarpim.funkme.domain.recorder

import com.scarpim.funkme.domain.model.AudioRecording

/**
 * Interface to access recordings saved by the app.
 */
interface RecorderProvider {

    fun getRecordings(): List<AudioRecording>

    fun playRecording(recording: AudioRecording)
}