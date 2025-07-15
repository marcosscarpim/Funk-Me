package com.scarpim.funkme.domain.recorder

import android.content.Intent
import com.scarpim.funkme.domain.model.RecordingResult
import kotlinx.coroutines.flow.SharedFlow

/**
 * Interface representing audio recorder functionality.
 */
interface AudioRecorder {

    val events: SharedFlow<RecordingResult>

    fun startRecording(mediaProjectionIntent: Intent?)

    fun stopRecording()
}