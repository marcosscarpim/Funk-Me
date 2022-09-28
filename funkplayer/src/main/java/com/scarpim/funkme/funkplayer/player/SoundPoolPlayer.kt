/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.funkplayer.player

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import com.scarpim.funkme.domain.model.FunkType
import com.scarpim.funkme.funkplayer.model.FunkAudio
import com.scarpim.funkme.funkplayer.source.FunkAudioDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundPoolPlayer @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private lateinit var soundPool: SoundPool
    private val loadedAudios = mutableListOf<FunkAudio>()

    suspend fun prepare(): List<FunkAudio> = withContext(Dispatchers.IO) {
        val audios = FunkAudioDataSource.getAvailable()
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(15)
            .setAudioAttributes(attributes)
            .build()

        audios.forEach { audio ->
            val loadedId = soundPool.load(context, audio.file, 1)
            val duration = getAudioDuration(audio)
            loadedAudios.add(audio.copy(loadedId = loadedId, duration = duration))
        }
        loadedAudios
    }

    fun play(audioId: Int) {
        val audio = loadedAudios.find { audioId == it.id }
        audio?.let {
            val loop = if (it.type == FunkType.REPEAT) -1 else 0
            val streamId = soundPool.play(it.loadedId, 1f, 1f, 0, loop, 1f)
            it.streamId = streamId
        }
    }

    fun stop(audioId: Int) {
        val audio = loadedAudios.find { audioId == it.id }
        audio?.let {
            soundPool.stop(it.streamId)
            it.streamId = -1
        }
    }

    private fun getAudioDuration(audio: FunkAudio): Int {
        val player = MediaPlayer.create(context, audio.file)
        return player.duration
    }
}