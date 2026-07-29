package com.makeev.cima.domain.usecase

import com.makeev.cima.domain.model.MovieDetails
import com.makeev.cima.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(movieId: Int): MovieDetails {
        return repository.getMovieDetail(movieId)
    }

}