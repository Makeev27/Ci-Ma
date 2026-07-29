package com.makeev.cima.domain.usecase

import com.makeev.cima.domain.model.TrendingMovie
import com.makeev.cima.domain.repository.MovieRepository
import javax.inject.Inject

class GetTrendingMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(): List<TrendingMovie> {
        return repository.getTrendingMovies()
    }

}