package com.jys.catsapp.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jys.catsapp.core.mock.mockValues
import com.jys.catsapp.presentation.components.CatListComponent
import com.jys.catsapp.presentation.components.TopBar
import com.jys.catsapp.data.network.model.Photo
import kotlinx.coroutines.flow.MutableStateFlow
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
        modifier = Modifier.fillMaxSize(),) { paddingValues ->
        CatListComponent(
            modifier = Modifier,
            paddingValues = paddingValues,
            catPhotoPagingItems = stateUI
        )
    }
}

@SuppressLint("RememberReturnType")
@Preview
@Composable
fun HomeScreenPreview() {
    val items = mockValues()
    val pagingData = PagingData.from(items)
    val lazyPagingItems = MutableStateFlow(pagingData).collectAsLazyPagingItems()

    HomeScreenContent(
        stateUI = lazyPagingItems
    )
}



