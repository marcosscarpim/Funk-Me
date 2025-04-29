/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.funkScreen

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mscarpim.funkme.recorder.AudioCaptureService
import com.scarpim.funkme.domain.model.FunkAudio
import com.scarpim.funkme.helper.isLandscape

@Composable
fun FunkScreen(viewModel: FunkViewModel) {
    val state by viewModel.uiState.collectAsState()
    val isLandscape = isLandscape()
    var isRecording: Boolean by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // TODO move this to proper place
    val onRecordClicked: (Intent?) -> Unit = { mediaProjectionIntent ->
        val intent = Intent(context, AudioCaptureService::class.java)
        if (isRecording) {
            isRecording = false
            context.stopService(intent)
        } else {
            isRecording = true
            intent.putExtra("resultCode", RESULT_OK)
            AudioCaptureService.mediaProjectionData = mediaProjectionIntent
            context.startForegroundService(intent)
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
                onAudioClicked = { audio ->
                    viewModel.onAction(FunkScreenAction.AudioClicked(audio))
                }
            )
        } else {
            FunkScreenPortrait(
                state = state,
                isRecording = isRecording,
                onRecordClicked = onRecordClicked,
                onAudioClicked = { audio ->
                    viewModel.onAction(FunkScreenAction.AudioClicked(audio))
                }
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
) {
    Row {
        FunkPad(
            modifier = Modifier.weight(1f),
            audios = state.audios,
            onClick = onAudioClicked,
        )
        RecordButton(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 10.dp, end = 20.dp),
            isRecording = isRecording,
            onButtonClicked = onRecordClicked,
        )
    }
}

@SuppressLint("MissingPermission")
@Composable
fun FunkScreenPortrait(
    state: FunkScreenState,
    isRecording: Boolean,
    onRecordClicked: (Intent?) -> Unit,
    onAudioClicked: (FunkAudio) -> Unit,
) {
    Column {
        FunkPad(
            modifier = Modifier.weight(1f),
            audios = state.audios,
            onClick = onAudioClicked,
        )
        RecordButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 10.dp, bottom = 20.dp),
            isRecording = isRecording,
            onButtonClicked = onRecordClicked,
        )
    }
}
