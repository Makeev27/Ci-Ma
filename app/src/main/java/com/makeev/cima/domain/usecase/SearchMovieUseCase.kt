package com.makeev.cima.domain.usecase

import com.makeev.cima.domain.model.PopularMovie
import com.makeev.cima.domain.repository.MovieRepository
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(query: String): List<PopularMovie> {
        return repository.searchMovie(query)
    }

}