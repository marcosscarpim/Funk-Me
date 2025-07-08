package com.mscarpim.funkme.recorder

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import com.scarpim.funkme.domain.recorder.AudioRecorder
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AudioRecorderImpl @Inject constructor(
    @ApplicationContext private val context: Context
): AudioRecorder {

    override fun startRecording(mediaProjectionIntent: Intent?) {
        val intent = Intent(context, AudioCaptureService::class.java)
        intent.putExtra("resultCode", RESULT_OK)
        AudioCaptureService.mediaProjectionData = mediaProjectionIntent
        context.startForegroundService(intent)
    }

    override fun stopRecording() {
        val intent = Intent(context, AudioCaptureService::class.java)
        context.stopService(intent)
    }
}