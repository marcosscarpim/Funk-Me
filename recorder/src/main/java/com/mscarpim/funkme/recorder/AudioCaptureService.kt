package com.mscarpim.funkme.recorder

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PROJECTION
import android.media.projection.MediaProjectionManager
import android.os.IBinder
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AudioCaptureService : Service() {

    @Inject
    lateinit var fileController: FileController

    private var recorder: SoundRecorder? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onDestroy() {
        recorder?.stopRecording()
        super.onDestroy()
    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(
            NOTIFICATION_ID,
            createNotification(),
            FOREGROUND_SERVICE_TYPE_MEDIA_PROJECTION // needed in documentation
        )

        val resultCode = intent?.getIntExtra("resultCode", Activity.RESULT_CANCELED) ?: return START_NOT_STICKY
        val data = mediaProjectionData ?: return START_NOT_STICKY

        val mediaProjectionManager = getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        val mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data)

        recorder = SoundRecorder(this, mediaProjection)
        val outputFile = fileController.createFileWithDateName()
        recorder?.startRecording(outputFile)

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotification(): Notification {
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Recording Audio Output")
            .setContentText("Capturing sound playback")
//            .setSmallIcon(R.drawable.ic_notification)
            .setOngoing(true)

        return notificationBuilder.build()
    }

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "Audio Capture",
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(serviceChannel)
    }

    companion object {
        const val CHANNEL_ID = "AudioCaptureChannel"
        const val NOTIFICATION_ID = 1001
        var mediaProjectionData: Intent? = null
    }
}
