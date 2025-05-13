/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.funkScreen

import android.content.Intent
import com.scarpim.funkme.domain.model.FunkAudio

sealed class FunkScreenAction {

    data object LoadAudios: FunkScreenAction()

    data class AudioClicked(val audio: FunkAudio): FunkScreenAction()

    data class OnRecordClicked(
        val recording: Boolean,
        val mediaProjectionIntent: Intent?,
    ): FunkScreenAction()
}