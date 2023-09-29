/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.funkScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.forEach

@Composable
fun FunkScreen(viewModel: FunkViewModel) {
    val state by viewModel.uiState.collectAsState()

    if (state.isLoading) {
        // TODO - Add some feedback
    } else {
        Row {
            FunkPad(
                modifier = Modifier.weight(1f),
                audios = state.audios
            ) { audio ->
                viewModel.onAction(FunkScreenAction.AudioClicked(audio))
            }
        }
    }
}
