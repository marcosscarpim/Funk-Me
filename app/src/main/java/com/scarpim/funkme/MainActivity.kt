package com.scarpim.funkme

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.scarpim.funkme.domain.model.FunkAudio
import com.scarpim.funkme.domain.model.FunkType
import com.scarpim.funkme.ui.theme.FunkMeTheme
import com.scarpim.funkme.ui.theme.repeatButtonColor
import com.scarpim.funkme.ui.theme.soundButtonColor
import com.scarpim.funkme.ui.theme.voiceButtonColor
import dagger.hilt.android.AndroidEntryPoint

private const val GRID_CELLS = 3
private const val COLUMN_HEIGHT_FRACTION = 1f / GRID_CELLS

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FunkMeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    FunkPad(viewModel = viewModel)
                }
            }
        }
        viewModel.prepare()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FunkPad(viewModel: MainViewModel) {
    val audios = viewModel.funkAudios
    val widthFraction = 1f / (audios.size / 3)
    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
        LazyHorizontalGrid(
            modifier = Modifier.fillMaxSize(),
            rows = GridCells.Fixed(GRID_CELLS),
            contentPadding = PaddingValues(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            content = {
                items(audios) { audio ->
                    FunkButton(audio = audio, widthFraction = widthFraction) { audioId, isPlaying ->
                        if (isPlaying) {
                            viewModel.stop(audioId)
                        } else {
                            viewModel.play(audioId)
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun FunkButton(audio: FunkAudio, widthFraction: Float, onClick: (Int, Boolean) -> Unit) {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0.8f,
        // `infiniteRepeatable` repeats the specified duration-based `AnimationSpec` infinitely.
        animationSpec = infiniteRepeatable(
            // The `keyframes` animates the value by specifying multiple timestamps.
            animation = keyframes {
                // One iteration is 1000 milliseconds.
                durationMillis = 1000
                // 0.7f at the middle of an iteration.
                0.5f at 500
            },
            // When the value finishes animating from 0f to 1f, it repeats by reversing the
            // animation direction.
            repeatMode = RepeatMode.Reverse
        )
    )

    val iconResource = when (audio.type) {
        FunkType.REPEAT -> R.drawable.ic_repeat
        FunkType.VOICE -> R.drawable.ic_voice
        FunkType.SOUND -> R.drawable.ic_sound
    }
    val color = when (audio.type) {
        FunkType.REPEAT -> repeatButtonColor
        FunkType.VOICE -> voiceButtonColor
        FunkType.SOUND -> soundButtonColor
    }

    val boxModifier = if (audio.isPlaying) {
        Modifier.background(Color.White.copy(alpha = alpha))
    } else {
        Modifier
    }
    Button(
        modifier = Modifier
            .width(((LocalConfiguration.current.screenWidthDp - 60) * widthFraction).dp)
            .fillMaxHeight(COLUMN_HEIGHT_FRACTION),
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        onClick = {
            Log.d("Marcos", "onClick - id = ${audio.id}, isPlaying = ${audio.isPlaying}")
            onClick(audio.id, audio.isPlaying)
        }
    ) {
        Box(
            modifier = boxModifier.fillMaxSize()
        ) {
            if (audio.isPlaying) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    painter = painterResource(id = iconResource),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
            } else {
                Icon(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    painter = painterResource(id = iconResource),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FunkMeTheme {
        //TestButton()
    }
}