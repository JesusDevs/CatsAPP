package com.jys.catsapp.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.jys.catsapp.data.network.model.Photo
import com.jys.catsapp.domain.model.PhotoDomain

@Composable
fun CatListComponent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    catPhotoPagingItems: LazyPagingItems<Photo>,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
    ) {
        items(catPhotoPagingItems.itemCount) { index ->
            catPhotoPagingItems[index]?.let {
                PhotoItem(
                    modifier = Modifier.padding(top = 8.dp),
                    photoItem = it,
                    onClick = { catPhotoPagingItems[index] })
            }
        }
        catPhotoPagingItems.run {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = catPhotoPagingItems.loadState.refresh as LoadState.Error
                    item {
                        ErrorMessageFullScreen(
                            modifier = Modifier.fillParentMaxSize(),
                            message = error.error.localizedMessage!!,
                            onClickRetry = { retry() })
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item { LoadingNextPageItem(modifier = Modifier) }
                }

                loadState.append is LoadState.Error -> {
                    val errorBottom = catPhotoPagingItems.loadState.append as LoadState.Error
                    item {
                        ErrorMessageNextPageItem(
                            modifier = Modifier.padding(vertical = 8.dp),
                            message = errorBottom.error.localizedMessage!!,
                            onClickRetry = { retry() })
                    }
                }
            }
        }
    }
}

@Composable
fun CatListComponentWithRoom(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    catPhotoPagingItems: LazyPagingItems<PhotoDomain>,
) {
    LazyColumn(
        modifier = modifier
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
    ) {
        val loadState = catPhotoPagingItems.loadState.mediator

        items(
            catPhotoPagingItems.itemCount,
            key = { index -> catPhotoPagingItems[index]?.id ?: index }) { index ->
            catPhotoPagingItems[index]?.let {
                PhotoItemDomain(
                    modifier = Modifier.padding(top = 8.dp),
                    photoItem = it
                )
            }
        }
        catPhotoPagingItems.apply {
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
                            modifier = Modifier.padding(vertical = 8.dp),
                            message = errorBottom.error.localizedMessage!!,
                            onClickRetry = { retry() })
                    }
                }
            }
        }
    }
}

