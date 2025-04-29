/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.funkScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.scarpim.funkme.domain.model.FunkAudio
import com.scarpim.funkme.domain.model.FunkType
import com.scarpim.funkme.helper.isLandscape

private const val GRID_ROWS_LANDSCAPE = 3
private const val GRID_COLS_PORTRAIT = 3

@Composable
fun FunkPad(
    modifier: Modifier = Modifier,
    audios: List<FunkAudio>,
    spacingStart: Dp = 0.dp,
    isLandscape: Boolean = isLandscape(),
    onClick: (FunkAudio) -> Unit,
) {
    if (isLandscape) {
        FunkPadLandscape(modifier, audios, spacingStart, onClick)
    } else {
        FunkPadPortrait(modifier, audios, spacingStart, onClick)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FunkPadLandscape(
    modifier: Modifier = Modifier,
    audios: List<FunkAudio>,
    spacingRight: Dp = 0.dp,
    onClick: (FunkAudio) -> Unit
) {
    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
        BoxWithConstraints(modifier = modifier) {
            val padding = PaddingValues(20.dp)
            val space = 5.dp
            val rowCount = GRID_ROWS_LANDSCAPE
            val columnCount = audios.size / rowCount + if (audios.size % rowCount > 0) 1 else 0
            val paddingStart = padding.calculateStartPadding(LayoutDirection.Ltr)
            val paddingEnd = padding.calculateEndPadding(LayoutDirection.Ltr)
            val gridWidth = maxWidth - spacingRight - paddingStart - paddingEnd
            val itemWidth = (gridWidth - space * (rowCount - 1)) / columnCount

            LazyHorizontalGrid(
                rows = GridCells.Fixed(GRID_ROWS_LANDSCAPE),
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FunkPadPortrait(
    modifier: Modifier = Modifier,
    audios: List<FunkAudio>,
    spacingBottom: Dp = 0.dp,
    onClick: (FunkAudio) -> Unit
) {
    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
        BoxWithConstraints(modifier = modifier) {
            val padding = PaddingValues(20.dp)
            val space = 5.dp
            val columnCount = GRID_COLS_PORTRAIT
            val rowCount = audios.size / columnCount + if (audios.size % columnCount > 0) 1 else 0
            val paddingTop = padding.calculateTopPadding()
            val paddingBottom = padding.calculateBottomPadding()
            val gridHeight = maxHeight - spacingBottom - paddingTop - paddingBottom
            val itemHeight = (gridHeight - space * (columnCount - 1)) / rowCount

            LazyVerticalGrid(
                columns = GridCells.Fixed(GRID_COLS_PORTRAIT),
                contentPadding = PaddingValues(5.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                content = {
                    items(audios, key = { it.id }) { audio ->
                        FunkButton(
                            modifier = Modifier
                                .fillMaxHeight()
                                .requiredHeight(itemHeight),
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

@Preview(showBackground = true)
@Composable
fun FunkPadPortraitPreview() {
    Column {
        FunkPadPortrait(
            audios = previewAudioList
        ) { }
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=840dp,height=384dp,dpi=320",
)
@Composable
fun FunkPadLandscapePreview() {
    Column {
        FunkPadLandscape(
            audios = previewAudioList
        ) { }
    }
}

private val previewAudioList: List<FunkAudio> = listOf(
    FunkAudio(0, FunkType.REPEAT), FunkAudio(1, FunkType.REPEAT),
    FunkAudio(2, FunkType.REPEAT), FunkAudio(3, FunkType.REPEAT),
    FunkAudio(4, FunkType.REPEAT), FunkAudio(5, FunkType.REPEAT),
    FunkAudio(6, FunkType.SOUND), FunkAudio(7, FunkType.SOUND),
    FunkAudio(9, FunkType.SOUND), FunkAudio(10, FunkType.SOUND),
    FunkAudio(11, FunkType.SOUND), FunkAudio(12, FunkType.SOUND),
    FunkAudio(13, FunkType.SOUND), FunkAudio(14, FunkType.SOUND),
    FunkAudio(15, FunkType.SOUND), FunkAudio(16, FunkType.VOICE),
    FunkAudio(17, FunkType.VOICE), FunkAudio(18, FunkType.VOICE),
    FunkAudio(19, FunkType.VOICE), FunkAudio(20, FunkType.VOICE),
    FunkAudio(21, FunkType.VOICE),
)