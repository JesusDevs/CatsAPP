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
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.jys.catsapp.R
import com.jys.catsapp.core.common.utils.DimensUtil.Dimens150Dp
import com.jys.catsapp.core.common.utils.DimensUtil.Dimens16Dp
import com.jys.catsapp.core.common.utils.DimensUtil.Dimens1Dp
import com.jys.catsapp.domain.model.PhotoDomain
import kotlinx.coroutines.Dispatchers

@Composable
fun PhotoItemDomain(
    modifier: Modifier = Modifier,
    onClick: (PhotoDomain) -> Unit,
    photoDomain: PhotoDomain,
) {
    val context = LocalContext.current
    Card(
        modifier = modifier
            .clickable { onClick(photoDomain) }
            .fillMaxWidth()
            .height(Dimens150Dp),
        shape = RoundedCornerShape(Dimens16Dp),
        border = BorderStroke(Dimens1Dp, Color.White),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val imageRequest = remember(photoDomain.src.tiny) {
                ImageRequest.Builder(context)
                    .dispatcher(Dispatchers.IO)
                    .data(photoDomain.src.tiny)
                    .placeholder(R.drawable.placeholder)
                    .crossfade(true)
                    .crossfade(300)
                    .diskCacheKey(photoDomain.src.tiny)
                    .memoryCacheKey(photoDomain.src.tiny)
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