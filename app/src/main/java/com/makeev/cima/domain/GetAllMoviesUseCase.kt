package com.makeev.cima.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    operator fun invoke() : Flow<List<MovieItem>> {
        return repository.getAllMovies()
    }

}