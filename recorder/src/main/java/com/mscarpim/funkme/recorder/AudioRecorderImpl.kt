package com.mscarpim.funkme.recorder

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import com.scarpim.funkme.domain.model.RecordingResult
import com.scarpim.funkme.domain.recorder.AudioRecorder
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioRecorderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val scope: CoroutineScope,
): AudioRecorder {

    private val _events = MutableSharedFlow<RecordingResult>()
    override val events: SharedFlow<RecordingResult> = _events.asSharedFlow()

    override fun startRecording(mediaProjectionIntent: Intent?) {
        val intent = Intent(context, AudioCaptureService::class.java)
        intent.putExtra("resultCode", RESULT_OK)
        AudioCaptureService.mediaProjectionData = mediaProjectionIntent
        context.startForegroundService(intent)

        scope.launch {
            _events.emit(RecordingResult.Started)
        }
    }

    override fun stopRecording() {
        val intent = Intent(context, AudioCaptureService::class.java)
        context.stopService(intent)
    }

    fun notifyRecordingStopped(fileName: String) {
        scope.launch {
            _events.emit(RecordingResult.Stopped(fileName))
        }
    }
}