package com.jys.catsapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.jys.catsapp.core.common.utils.DimensUtil.Dimens16Dp
import com.jys.catsapp.core.common.utils.DimensUtil.Dimens8Dp
import com.jys.catsapp.domain.model.PhotoDomain

@Composable
fun CatListComponentWithRoom(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    catPhotoPagingItems: LazyPagingItems<PhotoDomain>,
) {

    LazyColumn(
        modifier = modifier
            .background(color = Color.Black)
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = Dimens16Dp)
    ) {
        val loadState = catPhotoPagingItems.loadState.mediator

        items(
            count = catPhotoPagingItems.itemCount,
            key = catPhotoPagingItems.itemKey { it.id?: 0 }
        ) { index ->
            catPhotoPagingItems[index]?.let { photoDomain ->
                PhotoItemDomain(
                    modifier = Modifier.padding(top = Dimens8Dp),
                    onClick = { catPhotoPagingItems[index] },
                    photoDomain= photoDomain
                )
            }
        }
        catPhotoPagingItems.run {
            when {
                loadState?.refresh is LoadState.Loading -> {
                    item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
                }

                loadState?.refresh is LoadState.Error -> {
                    val error = catPhotoPagingItems.loadState.refresh as LoadState.Error
                    item {
                        ErrorMessageFullScreen(
                            modifier = Modifier.fillParentMaxSize(),
                            message = error.error.localizedMessage!!,
                            onClickRetry = { retry() })
                    }
                }

                loadState?.append is LoadState.Loading -> {
                    item { LoadingNextPageItem(modifier = Modifier) }
                }

                loadState?.append is LoadState.Error -> {
                    val errorBottom = catPhotoPagingItems.loadState.append as LoadState.Error
                    item {
                        ErrorMessageNextPageItem(
                            modifier = Modifier.padding(vertical = Dimens8Dp),
                            message = errorBottom.error.localizedMessage!!,
                            onClickRetry = { retry() })
                    }
                }
            }
        }
    }
}