/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.funkScreen

import android.content.Intent
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.scarpim.funkme.R
import com.scarpim.funkme.permission.MediaProjectionPermissionHandler

// TODO move permission logic somewhere else
@Composable
fun RecordButton(
    modifier: Modifier = Modifier,
    isRecording: Boolean,
    onButtonClicked: (Intent?) -> Unit,
) {
    val buttonColor = if (isRecording) Color.Red else Color.Gray
    val icon = if (isRecording) Icons.Default.Stop else Icons.Default.Mic

    var shouldAskPermission: Boolean by rememberSaveable { mutableStateOf(false) }
    var intentData: Intent? by rememberSaveable { mutableStateOf(null) }

    MediaProjectionPermissionHandler(
        askPermission = shouldAskPermission
    ) { isPermissionGranted, intent ->
        if (isPermissionGranted) {
            intentData = intent
            onButtonClicked(intent)
        }
    }

    val buttonDescription = if (isRecording) {
        stringResource(R.string.record_button_stop_description)
    } else {
        stringResource(R.string.record_button_start_description)
    }

    IconButton(
        modifier = modifier.size(60.dp),
        colors = IconButtonDefaults.iconButtonColors(containerColor = buttonColor),
        onClick = {
            if (intentData != null) {
                onButtonClicked(intentData)
            } else {
                shouldAskPermission = true
            }
        },
    ) {
        Icon(
            imageVector = icon,
            contentDescription = buttonDescription.toString(),
            tint = Color.White,
        )
    }
}