package com.jys.catsapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.jys.catsapp.data.network.model.Photo
import com.jys.catsapp.domain.usecase.GetCatUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map


class HomeViewModel(private val getCatUseCase: GetCatUseCase) : ViewModel() {

    private val _catPhotoState: MutableStateFlow<PagingData<Photo>> =
        MutableStateFlow(value = PagingData.empty())
    val catPhotoState: MutableStateFlow<PagingData<Photo>> get() = _catPhotoState

    suspend fun getListCatPhoto() {
        getCatUseCase.execute(Unit)
            .cachedIn(viewModelScope)
            .flowOn(Dispatchers.IO)
            .map {
                it.filter { photo -> photo.photographerId != null }
            }
            .catch {}
            .collect {
                Log.d("HomeViewModel", "getListCatPhoto: $it")
                _catPhotoState.value = it
            }
    }
}
