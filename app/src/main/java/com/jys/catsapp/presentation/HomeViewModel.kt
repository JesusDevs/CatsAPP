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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

    //Opcion usando state in para controlar inicio de data y estados
    val catPhotoStateRoomStateIn: StateFlow<PagingData<PhotoDomain>> = flow {
        getCatUseCaseWithRoom.execute(Unit)
            .cachedIn(viewModelScope)
            .distinctUntilChanged()
            .catch { error -> println("Error: ${error.message}") }
            .buffer(
                capacity = 5,
                onBufferOverflow = BufferOverflow.DROP_OLDEST
            )
            .collect { value ->
                emit(value)
            }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = PagingData.empty()
    )

    suspend fun getListCatPhotoWithRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            getCatUseCaseWithRoom.execute(Unit)
                .cachedIn(viewModelScope)
                .distinctUntilChanged()
                .catch { error -> println("Error: ${error.message}") }
                .buffer(
                    capacity = 5,
                    onBufferOverflow = BufferOverflow.DROP_OLDEST
                )
                .collect { value ->
                    _catPhotoStateRoom.value = value
                }
        }
    }
}


sealed class HomeState {
    object Loading : HomeState()
    data class Success(val data: PagingData<PhotoDomain>) : HomeState()
    data class Error(val message: String) : HomeState()
    object Empty : HomeState()
}
