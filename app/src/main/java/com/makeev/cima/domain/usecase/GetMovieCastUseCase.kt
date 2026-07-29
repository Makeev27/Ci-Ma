package com.makeev.cima.domain.usecase

import com.makeev.cima.domain.model.MovieCast
import com.makeev.cima.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieCastUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(movieId: Int) : List<MovieCast> {
        return repository.getMovieCast(movieId)
    }

}