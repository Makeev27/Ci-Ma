package com.makeev.cima.domain.usecase

import com.makeev.cima.domain.repository.MovieRepository
import javax.inject.Inject

class GetAllMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {

//    operator fun invoke() : Flow<List<MovieItem>> {
//        return repository.getAllMovies()
//    }

}