package com.jys.catsapp.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jys.catsapp.R

@Composable
fun TopBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {}
        ) {
            Icon(
                Icons.Default.Menu,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(25.dp)
            )
        }

        Text(
            text = "",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .weight(1.0f),
            textAlign = TextAlign.Center
        )

    }
}