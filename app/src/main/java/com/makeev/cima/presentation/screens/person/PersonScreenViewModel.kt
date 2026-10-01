package com.makeev.cima.presentation.screens.person

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeev.cima.domain.usecase.GetPersonUseCase
import com.makeev.cima.presentation.screens.model.PersonDetailUiModel
import com.makeev.cima.utils.toPersonDetailUiModel
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
class PersonScreenViewModel @Inject constructor(
    private val getPersonUseCase: GetPersonUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<PersonScreenUiState>(PersonScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<HomeSideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    val personId: Int = checkNotNull(savedStateHandle["personId"])

    init {
        loadMovies()
    }

    fun retry() {
        loadMovies()
    }


    private fun loadMovies() {
        viewModelScope.launch {
            _uiState.value = PersonScreenUiState.Loading
            try {
                coroutineScope {

                    val personDeferred = async { getPersonUseCase(personId) }

                    val person = personDeferred.await().toPersonDetailUiModel()

                    _uiState.value = PersonScreenUiState.Success(
                        person
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (
                e: Exception
            ) {
                e.printStackTrace()
                _uiState.value = PersonScreenUiState.Error("Ошибка при загрузке данных $e")

                _sideEffect.send(HomeSideEffect.ShowToast("Ошибка загрузки данных"))
            }
        }
    }

}

sealed interface PersonScreenUiState {

    object Loading : PersonScreenUiState

    data class Success(
        val person: PersonDetailUiModel,
        val isRefreshing: Boolean = false
    ) : PersonScreenUiState

    data class Error(val message: String) : PersonScreenUiState

}

sealed interface HomeSideEffect {

    data class ShowToast(val message: String) : HomeSideEffect

}