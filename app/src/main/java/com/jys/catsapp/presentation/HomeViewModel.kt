package com.jys.catsapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.jys.catsapp.data.network.model.Photo
import com.jys.catsapp.domain.model.PhotoDomain
import com.jys.catsapp.domain.usecase.GetCatUseCase
import com.jys.catsapp.domain.usecase.GetCatUseCaseWithRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class HomeViewModel(
    private val getCatUseCase: GetCatUseCase,
    private val getCatUseCaseWithRoom: GetCatUseCaseWithRoom
) : ViewModel() {

    private val _catPhotoState: MutableStateFlow<PagingData<Photo>> =
        MutableStateFlow(value = PagingData.empty())
    val catPhotoState: MutableStateFlow<PagingData<Photo>> get() = _catPhotoState

    private val _catPhotoStateRoom: MutableStateFlow<PagingData<PhotoDomain>> =
        MutableStateFlow(value = PagingData.empty())
    val catPhotoStateRoom: MutableStateFlow<PagingData<PhotoDomain>> get() = _catPhotoStateRoom


    init {
       viewModelScope.launch {
           //getListCatPhoto()
          // getListCatPhotoWithRoom()
       }
    }

    suspend fun getListCatPhoto() {
        getCatUseCase.execute(Unit)
            .cachedIn(viewModelScope)
            .flowOn(Dispatchers.IO)
            .map {
                it.filter { photo -> photo.src.tiny != null }
            }
            .catch {}
            .collect {
                _catPhotoState.value = it
            }
    }

     suspend fun getListCatPhotoWithRoom() {
        getCatUseCaseWithRoom.execute(Unit)
            .cachedIn(viewModelScope)
            .flowOn(Dispatchers.IO)
            .map {
                it.filter { photo -> photo.src.tiny != null }
            }
            .catch {}
            .collect {
                _catPhotoStateRoom.value = it
            }
    }
}
