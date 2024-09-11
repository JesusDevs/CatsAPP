package com.jys.catsapp.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jys.catsapp.core.common.Constants.VIDEO_SAMURAI_CAT
import com.jys.catsapp.core.common.Integers.Int1000
import com.jys.catsapp.core.common.Integers.Int500
import com.jys.catsapp.core.common.VideoBackgroundScreen
import com.jys.catsapp.navigation.NavigationRoute.DASHBOARD
import com.jys.catsapp.navigation.NavigationRoute.HOME_WITHOUT_ROOM
import com.jys.catsapp.navigation.NavigationRoute.HOME_WITH_ROOM
import com.jys.catsapp.presentation.HomeScreen
import com.jys.catsapp.presentation.HomeScreenWithRoom
import com.jys.catsapp.presentation.components.DashBoard

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = DASHBOARD,
        popExitTransition = { defaultPopExitTransition() },
        popEnterTransition = { defaultPopEnterTransition() },
        exitTransition = { defaultExitTransition() },
        enterTransition = { defaultEnterTransition() }
    ) {
        composable(DASHBOARD) {
            VideoBackgroundScreen(
                pathVideo = VIDEO_SAMURAI_CAT,
                content = { DashBoard(navController = navController) }
            )
        }
        composable(HOME_WITHOUT_ROOM) {
            HomeScreen()
        }
        composable(HOME_WITH_ROOM) {
            HomeScreenWithRoom()
        }
    }
}



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
