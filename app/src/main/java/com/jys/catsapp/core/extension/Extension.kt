package com.jys.catsapp.core.extension

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer

fun String.toColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}


fun Modifier.shimmer(): Modifier = composed {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(translateAnim, translateAnim),
        tileMode = TileMode.Mirror
    )

    this.graphicsLayer {
        clip = true
    }.drawWithCache {
        onDrawWithContent {
            drawContent()
            drawRect(brush = brush)
        }
    }
}

@Composable
fun Modifier.rainbowBackground(): Modifier {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val colors = listOf(
        Color.Red,
        Color.Blue,
        Color.Cyan,
        Color.Yellow
    )

    val animatedColors = colors.map { color ->
        infiniteTransition.animateColor(
            initialValue = color,
            targetValue = color.copy(alpha = 0.5f),
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 2000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        )
    }

    return this.background(
        brush = Brush.horizontalGradient(
            colors = animatedColors.map { it.value },
            tileMode = TileMode.Mirror
        )
    )
}
