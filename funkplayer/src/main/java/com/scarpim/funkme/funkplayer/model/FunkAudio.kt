/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.funkplayer.model

import androidx.annotation.RawRes
import com.scarpim.funkme.domain.model.FunkType

data class FunkAudio(
    val id: Int,
    @RawRes val file: Int,
    val type: FunkType,
    val loadedId: Int = -1,
    var streamId: Int = -1,
    val duration: Int = 0, // in ms
    var isPlaying: Boolean = false,
    val name: String = ""
)