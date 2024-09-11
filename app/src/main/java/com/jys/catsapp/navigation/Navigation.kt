package com.jys.catsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jys.catsapp.core.common.animation.defaultEnterTransition
import com.jys.catsapp.core.common.animation.defaultExitTransition
import com.jys.catsapp.core.common.animation.defaultPopEnterTransition
import com.jys.catsapp.core.common.animation.defaultPopExitTransition
import com.jys.catsapp.core.common.ui.VideoBackgroundScreen
import com.jys.catsapp.core.common.utils.ConstantsAsset.VIDEO_SAMURAI_CAT
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



