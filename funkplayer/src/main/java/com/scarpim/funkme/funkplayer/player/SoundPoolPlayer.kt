/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.funkplayer.player

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.scarpim.funkme.domain.model.FunkType
import com.scarpim.funkme.funkplayer.model.FunkAudio
import com.scarpim.funkme.funkplayer.source.FunkAudioDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundPoolPlayer @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private lateinit var soundPool: SoundPool
    private val loadedAudios = mutableListOf<FunkAudio>()

    val stateFlow = MutableStateFlow<Int>(0)

    fun prepare() {
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
            loadedAudios.add(audio.copy(loadedId = loadedId))
        }
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
}