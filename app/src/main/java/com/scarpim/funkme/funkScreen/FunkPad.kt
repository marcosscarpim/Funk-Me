/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.funkScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.scarpim.funkme.domain.model.FunkAudio

private const val GRID_CELLS = 3
private const val COLUMN_HEIGHT_FRACTION = 1f / GRID_CELLS

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FunkPad(
    modifier: Modifier = Modifier,
    audios: List<FunkAudio>,
    onClick: (FunkAudio) -> Unit
) {
    val widthFraction = 1f / (audios.size / 3)

    // Measure Modifier for funk buttons, width measurement is because it was getting above navbar
    // TODO - provides a better measurement
    val buttonModifier = Modifier
        .width(((LocalConfiguration.current.screenWidthDp - 60) * widthFraction).dp)
        .fillMaxHeight(COLUMN_HEIGHT_FRACTION)

    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
        LazyHorizontalGrid(
            modifier = modifier,
            rows = GridCells.Fixed(GRID_CELLS),
            contentPadding = PaddingValues(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            content = {
                items(audios) { audio ->
                    FunkButton(
                        modifier = buttonModifier,
                        audio = audio
                    ) { isPlaying ->
                        onClick(audio)
                    }
                }
            }
        )
    }
}