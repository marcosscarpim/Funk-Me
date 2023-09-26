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
    val state by viewModel.uiState.collectAsState()

    if (state.isLoading) {
        // TODO - Add some feedback for
    } else {
        FunkPad(
            modifier = Modifier.fillMaxSize(),
            audios = state.audios
        ) { audio ->
            viewModel.onAction(FunkScreenAction.AudioClicked(audio))
        }
    }
}
