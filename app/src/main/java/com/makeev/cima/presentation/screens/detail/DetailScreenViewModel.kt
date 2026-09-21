package com.makeev.cima.presentation.screens.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeev.cima.domain.usecase.GetMovieDetailUseCase
import com.makeev.cima.presentation.screens.model.MovieCastUiModel
import com.makeev.cima.presentation.screens.model.MovieDetailUiModel
import com.makeev.cima.utils.toMovieCastUiModel
import com.makeev.cima.utils.toMovieRecommendationsUiModel
import com.makeev.cima.utils.toMovieUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsUiState>(
        DetailsUiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])

    init {
        loadMovieDetails()
    }

    fun loadMovieDetails() {
        viewModelScope.launch {
            _uiState.value = DetailsUiState.Loading
            try {
                val movieDetails = getMovieDetailUseCase(movieId)
                val movieDetailUiModel = movieDetails.toMovieUiModel()
                val movieCast = movieDetails.credits.cast.map { it.toMovieCastUiModel() }
                val movieRecommendations = movieDetails.recommendations.movieRecommendations.map { it.toMovieRecommendationsUiModel() }
                Log.d("Detail", "loadMovieDetails: $movieCast $movieDetails ${_uiState.value}")
                _uiState.value =
                    DetailsUiState.Success(movieDetailUiModel, movieCast, movieRecommendations)
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = DetailsUiState.Error(e.localizedMessage ?: "Ошибка")
            }
        }
    }

}

sealed interface DetailsUiState {

    object Loading : DetailsUiState

    data class Success(
        val movie: MovieDetailUiModel,
        val cast: List<MovieCastUiModel>,
        val similarMovie: List<MovieRecommendationsUiModel>,
        val isRefreshing: Boolean = false
    ) : DetailsUiState

    data class Error(val message: String) : DetailsUiState

}