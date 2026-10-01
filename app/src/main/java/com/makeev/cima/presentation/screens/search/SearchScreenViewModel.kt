package com.makeev.cima.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeev.cima.domain.model.Movie
import com.makeev.cima.domain.model.Person
import com.makeev.cima.domain.model.SearchCategory
import com.makeev.cima.domain.model.TvShow
import com.makeev.cima.domain.usecase.SearchUseCase
import com.makeev.cima.presentation.screens.model.MultiSearchUiItem
import com.makeev.cima.utils.toMovieUiModel
import com.makeev.cima.utils.toPersonUiModel
import com.makeev.cima.utils.toTvShowUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow(SearchCategory.ALL)
    val selectedCategory = _selectedCategory.asStateFlow()

    @OptIn(FlowPreview::class)
    private val debouncedQuery = _searchQuery
        .debounce { 500L }
        .distinctUntilChanged()

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<SearchUiState> = combine(
        debouncedQuery,
        _selectedCategory
    ) { query, category ->
        // Здесь мы просто упаковываем два значения в объект Pair (query to category)
        // и передаем их дальше по цепочке потока
        query to category
    }
        .flatMapLatest { (query, category) ->
            if (query.isBlank()) {
                flowOf<SearchUiState>(SearchUiState.Idle)
            } else {
                flow {
                    emit(SearchUiState.Loading)
                    try {
                        val result: List<MultiSearchUiItem> = when (category) {
                            SearchCategory.ALL -> {
                                searchUseCase(query, category)
                                    .map { it as MultiSearchUiItem }
                            }

                            SearchCategory.MOVIES -> {
                                searchUseCase(query, category)
                                    .map { (it as Movie).toMovieUiModel() }
                            }

                            SearchCategory.TV_SHOWS -> {
                                searchUseCase(query, category)
                                    .map { (it as TvShow).toTvShowUiModel() }
                            }

                            SearchCategory.PERSONS -> {
                                searchUseCase(query, category)
                                    .map { (it as Person).toPersonUiModel() }
                            }
                        }
                        if (result.isEmpty()) {
                            emit(SearchUiState.Empty)
                        } else {
                            emit(SearchUiState.Success(result, category))
                        }
                    } catch (e: Exception) {
                        if (e is CancellationException) throw e
                        emit(SearchUiState.Error(e.localizedMessage ?: "Ошибка загрузки"))
                    }
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchUiState.Idle
        )
//    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
//    val uiState: StateFlow<SearchUiState> = _searchQuery
//        .debounce { 500L }
//        .distinctUntilChanged()
//        .flatMapLatest { query ->
//            if (query.isBlank()) {
//                flowOf<SearchUiState>(SearchUiState.Idle)
//            } else {
//                flow {
//                    emit(SearchUiState.Loading)
//                    try {
//                        val result = searchUseCase(query)
//                            .map { it.toMovieUiModel() }
//                        if (result.isEmpty()) {
//                            emit(SearchUiState.Empty)
//                        } else {
//                            emit(SearchUiState.Success(result))
//                        }
//                    } catch (e: Exception) {
//                        if (e is CancellationException) throw e
//                        emit(SearchUiState.Error(e.localizedMessage ?: "Ошибка загрузки"))
//                    }
//
//                }
//
//            }
//        }
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5000),
//            initialValue = SearchUiState.Idle
//        )

    fun onQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onCategorySelected(category: SearchCategory) {
        _selectedCategory.value = category
    }

    fun clearQuery() {
        _searchQuery.value = ""
    }
}

sealed interface SearchUiState {
    data object Idle : SearchUiState
    data object Loading : SearchUiState
    data class Success(val items: List<MultiSearchUiItem>, val category: SearchCategory) : SearchUiState
    data object Empty : SearchUiState
    data class Error(val message: String) : SearchUiState
}