package com.scarpim.funkme.domain.recorder

import android.content.Intent

/**
 * Interface representing audio recorder functionality.
 */
interface AudioRecorder {

    fun startRecording(mediaProjectionIntent: Intent?)

    fun stopRecording()
}