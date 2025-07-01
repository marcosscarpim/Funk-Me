package com.scarpim.funkme.funkScreen

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.scarpim.funkme.R

@Composable
fun FilesButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier.size(60.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        onClick = onClick,
    ) {
        Icon(
            imageVector = Icons.Default.Folder,
            contentDescription = stringResource(R.string.file_button_description),
            tint = Color.White,
        )
    }
}