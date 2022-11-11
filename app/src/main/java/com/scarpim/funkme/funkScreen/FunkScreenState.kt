/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.funkScreen

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.scarpim.funkme.domain.model.FunkAudio

sealed class FunkScreenState {

    object Loading: FunkScreenState()

    data class Loaded(val audios: SnapshotStateList<FunkAudio>): FunkScreenState()
}