/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.funkScreen

import com.scarpim.funkme.domain.model.FunkAudio

sealed class FunkScreenAction {

    object LoadAudios: FunkScreenAction()

    data class AudioClicked(val audio: FunkAudio): FunkScreenAction()
}