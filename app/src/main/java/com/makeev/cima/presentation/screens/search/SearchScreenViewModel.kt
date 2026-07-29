package com.makeev.cima.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeev.cima.domain.usecase.SearchMovieUseCase
import com.makeev.cima.presentation.screens.model.MovieUiModel
import com.makeev.cima.utils.toMovieUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()


    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<SearchUiState> = _searchQuery
        .debounce { 500L }
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isBlank()) {
                flowOf<SearchUiState>(SearchUiState.Idle)
            } else {
                flow {
                    emit(SearchUiState.Loading)
                    try {
                        val result = searchMovieUseCase(query)
                            .sortedByDescending { it.popularity }
                            .map { it.toMovieUiModel() }
                        if (result.isEmpty()) {
                            emit(SearchUiState.Empty)
                        } else {
                            emit(SearchUiState.Success(result))
                        }
                    } catch (e: Exception) {
                        emit(SearchUiState.Error(e.localizedMessage ?: "Ошибка загрузки"))
                    }

                }

            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchUiState.Idle
        )

    fun onQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun clearQuery(query: String) {
        _searchQuery.value = ""
    }
}

sealed interface SearchUiState {
    data object Idle : SearchUiState
    data object Loading : SearchUiState
    data class Success(val movies: List<MovieUiModel>) : SearchUiState
    data object Empty : SearchUiState
    data class Error(val message: String) : SearchUiState
}