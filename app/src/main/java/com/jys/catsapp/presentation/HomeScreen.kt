package com.jys.catsapp.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jys.core.network.model.Photo
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen() {
        val viewModel: HomeViewModel = koinViewModel()
        val stateUI = viewModel.catPhotoState.collectAsLazyPagingItems()
        LaunchedEffect(Unit) {
            viewModel.getListCatPhoto()
        }
        HomeScreenContent(stateUI)
}

@Composable
private fun HomeScreenContent(
    stateUI: LazyPagingItems<Photo>
) {
    Scaffold(
        topBar = {  }
    ) { paddingValues ->
        Text(modifier = Modifier.padding(paddingValues), text ="Hello Cats ${stateUI.itemCount}")
    }
}