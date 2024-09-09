package com.jys.catsapp.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jys.catsapp.domain.model.PhotoDomain
import com.jys.catsapp.presentation.components.CatListComponentWithRoom
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenWithRoom() {
    val viewModel: HomeViewModel = koinViewModel()
    val stateUI = viewModel.catPhotoStateRoom.collectAsLazyPagingItems()
    LaunchedEffect(stateUI.refresh()) {
        viewModel.getListCatPhotoWithRoom()
    }
    HomeScreenContentWithRoom(stateUI)
}

@Composable
private fun HomeScreenContentWithRoom(
    stateUI: LazyPagingItems<PhotoDomain>
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
      CatListComponentWithRoom(
            modifier = Modifier,
            paddingValues = paddingValues,
            catPhotoPagingItems = stateUI
        )
    }
}






