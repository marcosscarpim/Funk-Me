/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.funkplayer.source

import com.scarpim.funkme.domain.model.FunkType
import com.scarpim.funkme.funkplayer.R
import com.scarpim.funkme.funkplayer.model.FunkAudio

/**
 * Representation of our internal source of Data for Funk sounds.
 */
object FunkAudioDataSource {

    /**
     * Get all available sounds.
     *
     * @return list of [FunkAudio] available
     */
    fun getAvailable(): List<FunkAudio> {
        val funkList = mutableListOf<FunkAudio>()
        var index = 0

        getRepeatableFiles().forEach { file ->
            funkList.add(FunkAudio(
                id = index,
                file = file,
                type = FunkType.REPEAT
            ))
            index++
        }
        getAudioSamples().forEach { file ->
            funkList.add(FunkAudio(
                id = index,
                file = file,
                type = FunkType.SOUND
            ))
            index++
        }
        getVoiceFiles().forEach { file ->
            funkList.add(FunkAudio(
                id = index,
                file = file,
                type = FunkType.VOICE
            ))
            index++
        }
        return funkList
    }

    private fun getRepeatableFiles(): List<Int> = listOf(
        R.raw.beat_mix,
        R.raw.eletronico,
        R.raw.palma_hu,
        R.raw.pum_bum,
        R.raw.ah_vai_ok,
        R.raw.padrao_ok
    )

    private fun getAudioSamples(): List<Int> = listOf(
        R.raw.virada,
        R.raw.boomber,
        R.raw.gaitinha,
        R.raw.grana,
        R.raw.ohh,
        R.raw.sax,
        R.raw.scratch,
        R.raw.shot,
        R.raw.crash
    )

    private fun getVoiceFiles(): List<Int> = listOf(
        R.raw.gemido,
        R.raw.ah_que_delicia,
        R.raw.sarra_ah,
        R.raw.safadinha,
        R.raw.vai_sentando,
        R.raw.vem_sentando
    )
}