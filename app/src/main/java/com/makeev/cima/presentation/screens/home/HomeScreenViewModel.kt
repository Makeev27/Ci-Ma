package com.makeev.cima.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeev.cima.domain.GetAllMoviesUseCase
import com.makeev.cima.domain.MovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getAllMoviesUseCase: GetAllMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<HomeSideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()


    fun loadMovies() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            getAllMoviesUseCase()
                .collect { resultData ->
                    _uiState.value = UiState.Success(items = resultData)
                }
        }
    }

}

sealed interface UiState {

    object Loading : UiState

    data class Success(val items: List<MovieItem>, val isRefreshing: Boolean = false) : UiState

    data class Error(val message: String) : UiState

}

sealed interface HomeSideEffect {

    data class ShowToast(val message: String) : HomeSideEffect

}