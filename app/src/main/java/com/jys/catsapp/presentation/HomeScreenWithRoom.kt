package com.jys.catsapp.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.map
import com.jys.catsapp.core.mock.mockValues
import com.jys.catsapp.data.mapper.toEntity
import com.jys.catsapp.domain.model.PhotoDomain
import com.jys.catsapp.domain.model.toDomain
import com.jys.catsapp.presentation.components.CatListComponentWithRoom
import kotlinx.coroutines.flow.MutableStateFlow
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






