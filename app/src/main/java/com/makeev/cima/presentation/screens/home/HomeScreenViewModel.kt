package com.makeev.cima.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeev.cima.domain.usecase.GetPopularMovieUseCase
import com.makeev.cima.domain.usecase.GetTrendingMovieUseCase
import com.makeev.cima.presentation.screens.model.MovieUiModel
import com.makeev.cima.utils.toMovieDetailUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getPopularMovieUseCase: GetPopularMovieUseCase,
    private val getTrendingMovieUseCase: GetTrendingMovieUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<HomeSideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        loadMovies()
    }

    fun retry() {
        loadMovies()
    }


    private fun loadMovies() {
        viewModelScope.launch {
            _uiState.value = HomeScreenUiState.Loading
            try {
                coroutineScope {

                    val popularDeferred = async { getPopularMovieUseCase() }
                    val trendingDeferred = async { getTrendingMovieUseCase() }

                    val popularMovies = popularDeferred.await().map { it.toMovieDetailUiModel() }
                    val trendingMovies = trendingDeferred.await().map { it.toMovieDetailUiModel() }

                    _uiState.value = HomeScreenUiState.Success(
                        popularMovie = popularMovies,
                        trendingMovie = trendingMovies
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (
                e: Exception
            ) {
                e.printStackTrace()
                _uiState.value = HomeScreenUiState.Error("Ошибка при загрузке данных $e")

                _sideEffect.send(HomeSideEffect.ShowToast("Ошибка загрузки данных"))
            }
        }
    }

}

sealed interface HomeScreenUiState {

    object Loading : HomeScreenUiState

    data class Success(
        val popularMovie: List<MovieUiModel>,
        val trendingMovie: List<MovieUiModel>,
        val isRefreshing: Boolean = false
    ) : HomeScreenUiState

    data class Error(val message: String) : HomeScreenUiState

}

sealed interface HomeSideEffect {

    data class ShowToast(val message: String) : HomeSideEffect

}