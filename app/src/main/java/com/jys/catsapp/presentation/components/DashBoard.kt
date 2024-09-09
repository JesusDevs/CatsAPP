package com.jys.catsapp.presentation.components

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.jys.catsapp.core.theme.Gold

@Composable
fun DashBoard(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { navController.navigate("homeWithOutRoom") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2E2E2E),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(2.dp , Gold)
        ) {
            Text(
                text = "Navigate to Home Without Room",
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            onClick = { navController.navigate("homeWithRoom") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4A148C),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(2.dp, Color(0xFFC04040))
        ) {
            Text(
                text = "Navigate to Home with Room",
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.weight(0.2f))
    }
}

@Composable
fun VideoBackgroundScreen( content : @Composable () -> Unit) {
    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(Uri.parse("asset:///samurai_cat.mp4")) // Uri de tu video
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
            repeatMode = ExoPlayer.REPEAT_MODE_ALL
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            factory = {
                StyledPlayerView(context).apply {
                    player = exoPlayer
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                }
            },
            modifier = Modifier.fillMaxSize()
        )
        content()

    }

    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer.release()
        }
    }
}

