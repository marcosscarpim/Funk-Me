/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.funkScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun FunkScreen(viewModel: FunkViewModel) {
    val state by viewModel.state.collectAsState()

    if (state is FunkScreenState.Loaded) {
        FunkPad(
            modifier = Modifier.fillMaxSize(),
            audios = (state as FunkScreenState.Loaded).audios
        ) { audio ->
            viewModel.onAction(FunkScreenAction.AudioClicked(audio))
        }
    } else {
        // TODO - Add some feedback for user
    }
}
