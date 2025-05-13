package com.scarpim.funkme.permission

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.media.projection.MediaProjectionManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

/**
 * Composable to acquire Media Projection permission. Implementation for other explicit permissions
 * is not done yet.
 *
 * @param askPermission if permission should be asked
 * @param onResult the permission result callback with the intent needed to call media projection
 */
@Composable
fun MediaProjectionPermissionHandler(
    askPermission: Boolean = true,
    onResult: (Boolean, Intent?) -> Unit,
) {
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
            onResult(true, intentData)
        }
    }

    LaunchedEffect(askPermission) {
        if (askPermission) {
            if (resultCode == RESULT_OK && intentData != null) {
                onResult(true, intentData)
            } else {
                val intent = mediaProjectionManager.createScreenCaptureIntent()
                activityResultLauncher.launch(intent)
            }
        }
    }
}