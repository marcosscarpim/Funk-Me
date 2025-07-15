package com.mscarpim.funkme.recorder

import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import com.scarpim.funkme.domain.model.AudioRecording
import com.scarpim.funkme.domain.recorder.RecorderProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecorderProviderImpl @Inject constructor(
    private val fileController: FileController,
) : RecorderProvider {

    private var mediaPlayer: MediaPlayer? = null
    private var onCompletionListener: (() -> Unit)? = null

    override fun getRecordings(): List<AudioRecording> {
        val retriever = MediaMetadataRetriever()
        val recordingList = mutableListOf<AudioRecording>()

        val files = fileController.getFilesSaved()
        files.forEach { file ->
            if (file.extension.equals(WAV_EXTENSION, ignoreCase = true)) {
                try {
                    retriever.setDataSource(file.absolutePath)
                    val durationStr =
                        retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                    val duration = durationStr?.toLongOrNull() ?: 0L
                    recordingList.add(AudioRecording(file.name, duration, file))
                } catch (e: Exception) {
                    // TODO provide feedback
                }
            }
        }

        return recordingList.toList()
    }

    override fun play(recording: AudioRecording) {
        stop() // Stop any current playback
        mediaPlayer = MediaPlayer().apply {
            setDataSource(recording.file.absolutePath)
            prepare()
            start()
            setOnCompletionListener {
                onCompletionListener?.invoke()
            }
        }
    }

    override fun pause() {
        mediaPlayer?.pause()
    }

    override fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun isPlaying(): Boolean = mediaPlayer?.isPlaying == true

    override fun setOnCompletionListener(listener: () -> Unit) {
        onCompletionListener = listener
    }

    companion object {
        private const val WAV_EXTENSION = "wav"
    }
}