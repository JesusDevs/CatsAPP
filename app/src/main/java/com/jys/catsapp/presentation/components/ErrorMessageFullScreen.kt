package com.jys.catsapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jys.catsapp.R
import com.jys.catsapp.core.common.DimensUtil.Dimens1Dp
import com.jys.catsapp.core.common.DimensUtil.Dimens32Dp
import com.jys.catsapp.core.common.DimensUtil.Dimens8Dp
import com.jys.catsapp.core.theme.Orange

@Composable
fun ErrorMessageFullScreen(
    modifier: Modifier = Modifier,
    message: String,
    onClickRetry: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(id = R.drawable.splash_cat_samu),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.height(Dimens32Dp))
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(bottom = Dimens8Dp),
            maxLines = 2,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier,
            onClick = onClickRetry,
            border = BorderStroke(Dimens1Dp, Color.Cyan)
        ) {
            Text(
                text = stringResource(id = R.string.retry),
                modifier = Modifier,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(Dimens32Dp))
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreenState() {
    Column {
        ErrorMessageFullScreen(message = "Error message", onClickRetry = {})
    }
}