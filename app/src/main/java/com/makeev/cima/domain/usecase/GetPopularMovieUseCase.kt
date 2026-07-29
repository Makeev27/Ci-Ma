package com.makeev.cima.domain.usecase

import com.makeev.cima.domain.model.PopularMovie
import com.makeev.cima.domain.repository.MovieRepository
import javax.inject.Inject

class GetPopularMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(): List<PopularMovie> {
        return repository.getPopularMovies()
    }

}