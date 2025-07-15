package com.scarpim.funkme.domain.recorder

import com.scarpim.funkme.domain.model.AudioRecording

/**
 * Interface to access recordings saved by the app.
 */
interface RecorderProvider {

    fun getRecordings(): List<AudioRecording>

    fun play(recording: AudioRecording)

    fun pause()

    fun stop()

    fun isPlaying(): Boolean

    fun setOnCompletionListener(listener: () -> Unit)
}