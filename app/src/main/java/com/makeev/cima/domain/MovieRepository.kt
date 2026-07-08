package com.makeev.cima.domain

import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getAllMovies(): Flow<List<MovieItem>>

}