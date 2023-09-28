/**
 * Copyright @marcosscarpim.
 */

package com.scarpim.funkme.funkScreen

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.scarpim.funkme.R
import com.scarpim.funkme.domain.model.FunkAudio
import com.scarpim.funkme.domain.model.FunkType
import com.scarpim.funkme.ui.theme.FunkMeTheme
import com.scarpim.funkme.ui.theme.repeatButtonColor
import com.scarpim.funkme.ui.theme.soundButtonColor
import com.scarpim.funkme.ui.theme.voiceButtonColor

@Composable
fun FunkButton(
    modifier: Modifier = Modifier,
    audio: FunkAudio,
    onClick: () -> Unit
) {
    val iconResource = getIconResource(audio)
    val color = getColorResource(audio)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color = color)
            .clickable {
                onClick()
            },
    ) {
        FunkButtonContent(
            iconResource = iconResource,
            isPlaying = audio.isPlaying
        )
    }
}

@Composable
fun FunkButtonContent(
    modifier: Modifier = Modifier,
    @DrawableRes iconResource: Int,
    isPlaying: Boolean
) {

    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                0.5f at 500
            },
            repeatMode = RepeatMode.Reverse
        )
    )

    val boxModifier = if (isPlaying) {
        modifier.background(Color.White.copy(alpha = alpha))
    } else {
        modifier
    }

    Box(
        modifier = boxModifier.fillMaxSize()
    ) {
        if (isPlaying) {
            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .padding(8.dp),
                painter = painterResource(id = iconResource),
                tint = Color.Unspecified,
                contentDescription = null
            )
        } else {
            Icon(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp),
                painter = painterResource(id = iconResource),
                tint = Color.Unspecified,
                contentDescription = null
            )
        }
    }
}

@DrawableRes
private fun getIconResource(audio: FunkAudio): Int = when (audio.type) {
    FunkType.REPEAT -> R.drawable.ic_repeat
    FunkType.VOICE -> R.drawable.ic_voice
    FunkType.SOUND -> R.drawable.ic_sound
}

@ColorRes
private fun getColorResource(audio: FunkAudio): Color = when (audio.type) {
    FunkType.REPEAT -> repeatButtonColor
    FunkType.VOICE -> voiceButtonColor
    FunkType.SOUND -> soundButtonColor
}

@Preview(showBackground = true)
@Composable
fun RepeatButtonPreview() {
    FunkMeTheme {
        val funkAudio = FunkAudio(0, FunkType.REPEAT, 100)
        FunkButton(
            modifier = Modifier
                .width(200.dp)
                .height(200.dp),
            audio = funkAudio,
            onClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun VoiceButtonPreview() {
    FunkMeTheme {
        val funkAudio = FunkAudio(0, FunkType.VOICE, 100)
        FunkButton(
            modifier = Modifier
                .width(200.dp)
                .height(200.dp),
            audio = funkAudio,
            onClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SoundButtonPreview() {
    FunkMeTheme {
        val funkAudio = FunkAudio(0, FunkType.SOUND, 100)
        FunkButton(
            modifier = Modifier
                .width(200.dp)
                .height(200.dp),
            audio = funkAudio,
            onClick = { }
        )
    }
}
