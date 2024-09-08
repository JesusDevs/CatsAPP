package com.jys.catsapp.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jys.catsapp.presentation.HomeScreen
import com.jys.catsapp.presentation.HomeScreenWithRoom

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home",
    ) {
        composable("home") {
           // HomeScreen()

            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.weight(1f))

                Button( modifier = Modifier.fillMaxWidth(), onClick ={ navController.navigate("homeRoom")}) {
                    Text("Navigate to Home Room")
                }
                Spacer(modifier = Modifier.weight(1f))
            }



        }

        composable("homeRoom") {
            // HomeScreen()
            HomeScreenWithRoom()

        }
    }
}