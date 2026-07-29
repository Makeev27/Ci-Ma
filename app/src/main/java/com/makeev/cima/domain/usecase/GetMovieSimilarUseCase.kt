package com.makeev.cima.domain.usecase

import com.makeev.cima.domain.model.MovieSimilar
import com.makeev.cima.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieSimilarUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(movieId: Int): List<MovieSimilar> {
        return repository.getSimilarMovie(movieId)
    }

}