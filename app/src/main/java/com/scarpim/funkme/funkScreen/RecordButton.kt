/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.funkScreen

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.media.projection.MediaProjectionManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.scarpim.funkme.R

// TODO move permission logic somewhere else
@Composable
fun RecordButton(
    modifier: Modifier,
    isRecording: Boolean,
    onButtonClicked: (Intent?) -> Unit,
) {
    val buttonColor = if (isRecording) Color.Red else Color.Gray
    val icon = if (isRecording) Icons.Default.Stop else Icons.Default.Mic
    var resultCode: Int? by rememberSaveable { mutableStateOf(null) }
    var intentData: Intent? by rememberSaveable { mutableStateOf(null) }

    val context = LocalContext.current
    val mediaProjectionManager = remember {
        context.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
    }

    val activityResultLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            resultCode = result.resultCode
            intentData = result.data ?: return@rememberLauncherForActivityResult
            onButtonClicked(intentData)
        }
    }

    val buttonDescription = if (isRecording) {
        context.getText(R.string.record_button_stop_description)
    } else {
        context.getText(R.string.record_button_start_description)
    }

    IconButton(
        modifier = modifier.size(60.dp),
        colors = IconButtonDefaults.iconButtonColors(containerColor = buttonColor),
        onClick = {
            if (resultCode == RESULT_OK && intentData != null) {
                onButtonClicked(intentData)
            } else {
                val intent = mediaProjectionManager.createScreenCaptureIntent()
                activityResultLauncher.launch(intent)
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