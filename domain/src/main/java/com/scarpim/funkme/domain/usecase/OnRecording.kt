package com.scarpim.funkme.domain.usecase

import android.content.Intent
import com.scarpim.funkme.domain.model.RecordingResult
import com.scarpim.funkme.domain.recorder.AudioRecorder
import javax.inject.Inject

class OnRecording @Inject constructor(
    private val audioRecorder: AudioRecorder,
) {

    /**
     * Start or stop recording.
     *
     * @param recording if recording should be started or stopped
     * @param mediaProjectionIntent the media projection intent to properly start the recording
     *
     * @return the [RecordingResult] as feedback
     */
    operator fun invoke(recording: Boolean, mediaProjectionIntent: Intent?): RecordingResult {
        return try {
            if (recording) {
                audioRecorder.startRecording(mediaProjectionIntent)
                RecordingResult.Started
            } else {
                audioRecorder.stopRecording()
                RecordingResult.StoppingInProgress
            }
        } catch (e: Exception) {
            RecordingResult.Error(e.message ?: "Unknown error")
        }
    }
}