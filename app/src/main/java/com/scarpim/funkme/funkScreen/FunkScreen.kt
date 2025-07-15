/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.funkScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.scarpim.funkme.domain.model.FunkAudio
import com.scarpim.funkme.domain.model.RecordingResult
import com.scarpim.funkme.helper.isLandscape

@Composable
fun FunkScreen(
    viewModel: FunkViewModel = hiltViewModel(),
    navigateToFilesScreen: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    val isLandscape = isLandscape()

    var isRecording: Boolean by remember { mutableStateOf(false) }

    val onRecordClicked: (Intent?) -> Unit = { mediaProjectionIntent ->
        isRecording = !isRecording
        viewModel.onAction(FunkScreenAction.OnRecordClicked(isRecording, mediaProjectionIntent))
    }

    val onAudioClicked: (FunkAudio) -> Unit = { audio ->
        viewModel.onAction(FunkScreenAction.AudioClicked(audio))
    }

    val context = LocalContext.current
    LaunchedEffect(state.recordingResult) {
        val result = state.recordingResult
        if (result is RecordingResult.Stopped) {
            Toast.makeText(
                context,
                "Recording saved as: ${result.fileName}",
                Toast.LENGTH_LONG
            ).show()
            viewModel.onAction(FunkScreenAction.ClearRecordingResult)
        }
    }

    if (state.isLoading) {
        // TODO - Add some feedback
    } else {
        if (isLandscape) {
            FunkScreenLandscape(
                state = state,
                isRecording = isRecording,
                onRecordClicked = onRecordClicked,
                onAudioClicked = onAudioClicked,
                onFilesClicked = navigateToFilesScreen,
            )
        } else {
            FunkScreenPortrait(
                state = state,
                isRecording = isRecording,
                onRecordClicked = onRecordClicked,
                onAudioClicked = onAudioClicked,
                onFilesClicked = navigateToFilesScreen,
            )
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun FunkScreenLandscape(
    state: FunkScreenState,
    isRecording: Boolean,
    onRecordClicked: (Intent?) -> Unit,
    onAudioClicked: (FunkAudio) -> Unit,
    onFilesClicked: () -> Unit,
) {
    Row {
        FunkPad(
            modifier = Modifier.weight(1f),
            audios = state.audios,
            onClick = onAudioClicked,
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 10.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            RecordButton(
                isRecording = isRecording,
                onButtonClicked = onRecordClicked,
            )
            FilesButton(onClick = onFilesClicked)
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun FunkScreenPortrait(
    state: FunkScreenState,
    isRecording: Boolean,
    onRecordClicked: (Intent?) -> Unit,
    onAudioClicked: (FunkAudio) -> Unit,
    onFilesClicked: () -> Unit,
) {
    Column {
        FunkPad(
            modifier = Modifier.weight(1f),
            audios = state.audios,
            onClick = onAudioClicked,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            RecordButton(
                isRecording = isRecording,
                onButtonClicked = onRecordClicked,
            )
            FilesButton(onClick = onFilesClicked)
        }
    }
}
