package com.jys.catsapp.core

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jys.catsapp.presentation.HomeScreen
import com.jys.catsapp.core.theme.CatsAPPTheme
import com.jys.catsapp.navigation.Navigation
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI

class MainActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EnableTransparentStatusBar()
            CatsAPPTheme {

                KoinAndroidContext {
                    Navigation()
                }
            }
        }
    }
}




@Composable
private fun EnableTransparentStatusBar() {
    val view = LocalView.current
    val darkTheme = isSystemInDarkTheme()
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                !darkTheme
        }
    }
}


