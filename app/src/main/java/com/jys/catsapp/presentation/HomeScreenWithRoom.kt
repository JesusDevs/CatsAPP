package com.jys.catsapp.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jys.catsapp.domain.model.PhotoDomain
import com.jys.catsapp.presentation.components.AnimatedBottomBar
import com.jys.catsapp.presentation.components.AnimatedCircularNavigationBar
import com.jys.catsapp.presentation.components.CatListComponentWithRoom
import com.jys.catsapp.presentation.components.LiquidBottomBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenWithRoom() {
    val viewModel: HomeViewModel = koinViewModel()
    val stateUI = viewModel.catPhotoStateRoom.collectAsLazyPagingItems()
    val stateIn = viewModel.catPhotoStateRoomStateIn.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        //viewModel.getListCatPhotoWithRoom()
    }
    HomeScreenContentWithRoom(stateIn)
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






