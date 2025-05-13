package com.scarpim.funkme.domain.usecase

import android.content.Intent
import com.scarpim.funkme.domain.recorder.AudioRecorder
import javax.inject.Inject

class OnRecording @Inject constructor(
    private val audioRecorder: AudioRecorder,
) {

    /**
     * Start or stop recording.
     *
     * TODO Add feedback to this use case
     *
     * @param recording if recording should be started or stopped
     * @param mediaProjectionIntent the media projection intent to properly start the recording
     */
    operator fun invoke(recording: Boolean, mediaProjectionIntent: Intent?) {
        if (recording) {
            audioRecorder.startRecording(mediaProjectionIntent)
        } else {
            audioRecorder.stopRecording()
        }
    }
}