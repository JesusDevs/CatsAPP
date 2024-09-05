package com.jys.catsapp.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
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
            .padding(horizontal = 16.dp)
        ,
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
                            modifier =Modifier.padding(vertical = 8.dp),
                            message = errorBottom.error.localizedMessage!!,
                            onClickRetry = { retry() })
                    }
                }
            }
        }
    }
}

/*
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
            .padding(horizontal = 16.dp)
        ,
    ) {
       // val loadState = catPhotoPagingItems.loadState.mediator
        items(catPhotoPagingItems.itemCount,key = { index -> catPhotoPagingItems[index]?.id ?: index }  ) { index ->
            catPhotoPagingItems[index]?.let {
                PhotoItemDomain(
                    modifier = Modifier.padding(top = 8.dp),
                    photoItem = it
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
                            modifier =Modifier.padding(vertical = 8.dp),
                            message = errorBottom.error.localizedMessage!!,
                            onClickRetry = { retry() })
                    }
                }
            }
        }
    }
}*/

@Composable
fun CatListComponentWithRoom(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    catPhotoPagingItems: LazyPagingItems<PhotoDomain>,
    listState: LazyListState = rememberLazyListState(), // Estado del LazyColumn
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
        state = listState // Pasamos el LazyListState
    ) {
        // Mostrar los elementos de la lista
        items(
            count = catPhotoPagingItems.itemCount,
            key = { index -> catPhotoPagingItems[index]?.id ?: index }
        ) { index ->
            catPhotoPagingItems[index]?.let {
                PhotoItemDomain(
                    modifier = Modifier.padding(top = 8.dp),
                    photoItem = it
                )
            }
        }

        // Manejar el estado de carga y errores en la misma LazyColumn
        when {
            // Si se está cargando la primera página
            catPhotoPagingItems.loadState.refresh is LoadState.Loading -> {
                item {
                    PageLoader(modifier = Modifier.fillParentMaxSize())
                }
            }

            // Si hay un error al cargar la primera página
            catPhotoPagingItems.loadState.refresh is LoadState.Error -> {
                val error = catPhotoPagingItems.loadState.refresh as LoadState.Error
                item {
                    ErrorMessageFullScreen(
                        modifier = Modifier.fillParentMaxSize(),
                        message = error.error.localizedMessage ?: "Unknown error",
                        onClickRetry = { catPhotoPagingItems.retry() }
                    )
                }
            }

            // Si se está cargando la siguiente página (scroll infinito)
            catPhotoPagingItems.loadState.append is LoadState.Loading -> {
                item {
                    LoadingNextPageItem(modifier = Modifier)
                }
            }

            // Si hubo un error al cargar la siguiente página
            catPhotoPagingItems.loadState.append is LoadState.Error -> {
                val errorBottom = catPhotoPagingItems.loadState.append as LoadState.Error
                item {
                    ErrorMessageNextPageItem(
                        modifier = Modifier.padding(vertical = 8.dp),
                        message = errorBottom.error.localizedMessage ?: "Unknown error",
                        onClickRetry = { catPhotoPagingItems.retry() }
                    )
                }
            }
        }
    }
}
