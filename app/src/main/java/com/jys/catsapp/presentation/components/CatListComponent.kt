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
import com.jys.catsapp.core.common.DimensUtil.Dimens16Dp
import com.jys.catsapp.core.common.DimensUtil.Dimens8Dp
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
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = Dimens16Dp)
    ) {
        val loadState = catPhotoPagingItems.loadState.mediator

        items(
            count = catPhotoPagingItems.itemCount,
            key = { index ->  catPhotoPagingItems.itemSnapshotList[index].hashCode()}
        ) { index ->
            catPhotoPagingItems[index]?.let { photoDomain ->
                PhotoItemDomain(
                    modifier = Modifier.padding(top = Dimens8Dp),
                    photoItem = photoDomain,
                    onClick = {
                        println( "PhotoItemDomain onClick index: $it")
                        catPhotoPagingItems[index]
                    }
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
                            modifier = Modifier.padding(vertical = Dimens8Dp),
                            message = errorBottom.error.localizedMessage!!,
                            onClickRetry = { retry() })
                    }
                }
            }
        }
    }
}

