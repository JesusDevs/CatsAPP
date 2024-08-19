package com.jys.catsapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jys.catsapp.R

@Composable
fun ErrorMessageNextPageItem(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.msjcat),
                contentDescription = ""
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 8.dp),
                    maxLines = 2
                )
                Button(
                    modifier = Modifier,
                    onClick = onClickRetry,
                    border = BorderStroke(1.dp, Color.Cyan)
                ) {
                    Text(
                        text = stringResource(id = R.string.retry),
                        modifier = Modifier,
                        color = Color.White
                    )
                }
            }
        }
    }
}