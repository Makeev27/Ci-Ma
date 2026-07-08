package com.makeev.cima.data

import com.makeev.cima.domain.MovieItem
import com.makeev.cima.domain.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class TestMovieRepositoryImpl @Inject constructor() : MovieRepository {

    private val testData = mutableListOf<MovieItem>().apply {
        repeat(10) {
            add(
                MovieItem(
                    movieId = it,
                    title = "Title $it",
                    description = "Description $it",
                    rating = it.toDouble(),
                    year = it,
                    imageURL = "https://www.themoviedb.org/assets/2/v4/logos/v2/blue_square_2-d537fb228cf3ded904ef09b136fe3fec72548ebc1fea3fbbd1ad9e36364db38b.svg"
                )
            )
        }
    }

    private val flowData = MutableStateFlow<List<MovieItem>>(
        testData
    )

    override fun getAllMovies(): Flow<List<MovieItem>> {
        return flowData.asStateFlow()
    }


}