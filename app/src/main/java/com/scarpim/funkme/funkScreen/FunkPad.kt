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
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.scarpim.funkme.domain.model.FunkAudio

private const val GRID_ROWS = 3

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FunkPad(
    modifier: Modifier = Modifier,
    audios: List<FunkAudio>,
    onClick: (FunkAudio) -> Unit
) {
    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
        BoxWithConstraints {
            // TODO - Hardcoded value, find a better way to calculate the safe area
            val padding = PaddingValues(20.dp)
            val space = 5.dp
            val rowCount = GRID_ROWS
            val columnCount = audios.size / rowCount + if (audios.size % rowCount > 0) 1 else 0
            val gridWidth = maxWidth - padding.calculateLeftPadding(LayoutDirection.Ltr) - padding.calculateRightPadding(LayoutDirection.Ltr)
            val itemWidth = (gridWidth - space * (rowCount - 1)) / columnCount

            LazyHorizontalGrid(
                modifier = modifier,
                rows = GridCells.Fixed(GRID_ROWS),
                contentPadding = PaddingValues(5.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                content = {
                    items(audios, key = { it.id }) { audio ->
                        FunkButton(
                            modifier = Modifier
                                .fillMaxHeight()
                                .requiredWidth(itemWidth),
                            audio = audio
                        ) {
                            onClick(audio)
                        }
                    }
                }
            )
        }
    }
}