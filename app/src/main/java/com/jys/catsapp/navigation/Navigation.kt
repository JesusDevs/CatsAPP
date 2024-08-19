package com.jys.catsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jys.catsapp.presentation.HomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home",
    ) {
        composable("home") {
            HomeScreen()
        }
    }
}