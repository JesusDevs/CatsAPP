package com.jys.catsapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.util.DebugLogger
import com.jys.catsapp.R
import com.jys.catsapp.core.common.DimensUtil.Dimens150Dp
import com.jys.catsapp.core.common.DimensUtil.Dimens16Dp
import com.jys.catsapp.core.common.DimensUtil.Dimens1Dp
import com.jys.catsapp.data.network.model.Photo
import com.jys.catsapp.domain.model.PhotoDomain
import kotlinx.coroutines.Dispatchers

@Composable
fun PhotoItem(
    modifier: Modifier = Modifier,
    photoItem: Photo,
    onClick: () -> Photo?
) {
    val context = LocalContext.current
    Card(
        modifier = modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .height(Dimens150Dp),
        shape = RoundedCornerShape(Dimens16Dp),
        border = BorderStroke(Dimens1Dp, Color.White),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val imageRequest = remember(photoItem.url) {
                ImageRequest.Builder(context)
                    .data(photoItem.url)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.msjcat)
                    .crossfade(true)
                    .crossfade(300)
                    .dispatcher(Dispatchers.IO)
                    .diskCacheKey(photoItem.src.tiny)
                    .memoryCacheKey(photoItem.src.tiny)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build()
            }

            AsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillWidth,
                model = imageRequest,
                contentDescription = ""
            )
        }
    }
}

@Composable
fun PhotoItemDomain(
    modifier: Modifier = Modifier,
    photoItem: PhotoDomain,
    onClick: (PhotoDomain) -> Unit,
) {
    val context = LocalContext.current
    Card(
        modifier = modifier
            .clickable { onClick(photoItem) }
            .fillMaxWidth()
            .height(Dimens150Dp),
        shape = RoundedCornerShape(Dimens16Dp),
        border = BorderStroke(Dimens1Dp, Color.White),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val imageRequest = remember(photoItem.src.tiny) {
                ImageRequest.Builder(context)
                    .data(photoItem.url)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.msjcat)
                    .crossfade(true)
                    .crossfade(300)
                    .dispatcher(Dispatchers.IO)
                    .diskCacheKey(photoItem.src.tiny)
                    .memoryCacheKey(photoItem.src.tiny)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build()
            }

            AsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillWidth,
                model = imageRequest,
                contentDescription = ""
            )
        }
    }
}


