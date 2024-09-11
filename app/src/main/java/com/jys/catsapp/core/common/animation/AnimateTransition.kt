package com.jys.catsapp.core.common.animation


import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import com.jys.catsapp.core.common.utils.Integers.Int1000
import com.jys.catsapp.core.common.utils.Integers.Int500

fun defaultEnterTransition(): EnterTransition {
    return slideInHorizontally(initialOffsetX = { Int1000 }) + fadeIn(animationSpec = tween(Int500))
}

fun defaultExitTransition(): ExitTransition {
    return slideOutHorizontally(targetOffsetX = { -Int1000 }) + fadeOut(animationSpec = tween(Int500))
}

fun defaultPopEnterTransition(): EnterTransition {
    return slideInHorizontally(initialOffsetX = { -Int1000 }) + fadeIn(animationSpec = tween(Int500))
}

fun defaultPopExitTransition(): ExitTransition {
    return slideOutHorizontally(targetOffsetX = { Int1000 }) + fadeOut(animationSpec = tween(Int500))
}
