package com.makeev.cima.presentation.screens.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeev.cima.domain.usecase.GetMovieCastUseCase
import com.makeev.cima.domain.usecase.GetMovieDetailUseCase
import com.makeev.cima.domain.usecase.GetMovieSimilarUseCase
import com.makeev.cima.presentation.screens.model.MovieCastUiModel
import com.makeev.cima.presentation.screens.model.MovieDetailUiModel
import com.makeev.cima.utils.toMovieCastUiModel
import com.makeev.cima.utils.toMovieDetailUiModel
import com.makeev.cima.utils.toMovieSimilarUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieCastUseCase: GetMovieCastUseCase,
    private val getMovieSimilarUseCase: GetMovieSimilarUseCase,
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
                val movieDetails = getMovieDetailUseCase(movieId).toMovieDetailUiModel()
                val movieCast = getMovieCastUseCase(movieId).map { it.toMovieCastUiModel() }
                val movieSimilar = getMovieSimilarUseCase(movieId).map { it.toMovieSimilarUiModel() }
                Log.d("Detail", "loadMovieDetails: $movieCast $movieDetails ${_uiState.value}")
                _uiState.value = DetailsUiState.Success(movieDetails, movieCast, movieSimilar)
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
        val similarMovie: List<MovieSimilarUiModel>,
        val isRefreshing: Boolean = false
    ) : DetailsUiState

    data class Error(val message: String) : DetailsUiState

}