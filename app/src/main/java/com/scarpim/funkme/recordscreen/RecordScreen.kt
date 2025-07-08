package com.scarpim.funkme.recordscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.scarpim.funkme.R
import com.scarpim.funkme.domain.model.AudioRecording
import java.util.Locale

@Composable
fun RecordScreen(
    viewModel: RecordViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(
            items = state.recordings,
            key = { it.name }
        ) { recording ->
            val isPlaying = recording.name == state.currentlyPlaying
            RecordItem(
                recording = recording,
                isPlaying = isPlaying,
                onPlayClick = { viewModel.onPlayClick(recording) }
            )
            HorizontalDivider()
        }
    }
}

@Composable
fun RecordItem(
    recording: AudioRecording,
    isPlaying: Boolean = false,
    onPlayClick: () -> Unit,
) {
    val buttonIcon = if (isPlaying) {
        Icons.Default.Stop
    } else {
        Icons.Default.PlayArrow
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = recording.name,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = formatDuration(recording.length),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
            )
        }
        IconButton(onClick = onPlayClick) {
            Icon(
                imageVector = buttonIcon,
                contentDescription = stringResource(R.string.play_recording_button_description),
                tint = Color.White,
            )
        }
    }
}

private fun formatDuration(durationMillis: Long): String {
    val minutes = (durationMillis / 1000) / 60
    val seconds = (durationMillis / 1000) % 60
    return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
}