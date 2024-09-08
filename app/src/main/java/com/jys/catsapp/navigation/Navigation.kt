package com.jys.catsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jys.catsapp.presentation.HomeScreen
import com.jys.catsapp.presentation.HomeScreenWithRoom
import com.jys.catsapp.presentation.components.DashBoard

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home",
    ) {
        composable("home") {
            DashBoard(navController)
        }
        composable("homeWithOutRoom") {
            HomeScreen()
        }

        composable("homeWithRoom") {
            HomeScreenWithRoom()
        }
    }
}

