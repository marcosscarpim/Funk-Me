package com.mscarpim.funkme.recorder

import android.content.Context
import android.media.MediaMetadataRetriever
import com.scarpim.funkme.domain.model.AudioRecording
import com.scarpim.funkme.domain.recorder.RecorderProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RecorderProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fileController: FileController,
) : RecorderProvider {

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

    override fun playRecording(recording: AudioRecording) {
        // TODO implement this
    }

    companion object {
        private const val WAV_EXTENSION = "wav"
    }
}