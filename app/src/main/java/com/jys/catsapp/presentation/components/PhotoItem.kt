package com.jys.catsapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jys.catsapp.core.common.DimensUtil.Dimens150Dp
import com.jys.catsapp.core.common.DimensUtil.Dimens16Dp
import com.jys.catsapp.core.common.DimensUtil.Dimens1Dp
import com.jys.catsapp.core.common.createCustomImageLoader
import com.jys.catsapp.data.network.model.Photo
import com.jys.catsapp.domain.model.PhotoDomain

@Composable
fun PhotoItem(
    modifier: Modifier = Modifier,
    photoItem: Photo,
    onClick: () -> Photo?
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens150Dp),
        shape = RoundedCornerShape(Dimens16Dp),
        border = BorderStroke(Dimens1Dp, Color.White),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                imageLoader = createCustomImageLoader(LocalContext.current),
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillWidth,
                model = photoItem.src.tiny,
                contentDescription = ""
            )
        }
    }
}

@Composable
fun PhotoItemDomain(
    modifier: Modifier = Modifier,
    photoItem: PhotoDomain,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens150Dp),
        shape = RoundedCornerShape(Dimens16Dp),
        border = BorderStroke(Dimens1Dp, Color.White),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                imageLoader = createCustomImageLoader(LocalContext.current),
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillWidth,
                model = photoItem.src.tiny,
                contentDescription = ""
            )
        }
    }
}