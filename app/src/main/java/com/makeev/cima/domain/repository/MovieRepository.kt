package com.makeev.cima.domain.repository

import com.makeev.cima.domain.model.Movie
import com.makeev.cima.domain.model.MovieCast
import com.makeev.cima.domain.model.MovieDetails
import com.makeev.cima.domain.model.MovieSimilar
import com.makeev.cima.domain.model.MultiSearchItem
import com.makeev.cima.domain.model.Person
import com.makeev.cima.domain.model.PopularMovie
import com.makeev.cima.domain.model.TrendingMovie
import com.makeev.cima.domain.model.TvShow

interface MovieRepository {

    suspend fun getPopularMovies(): List<PopularMovie>

    suspend fun getTrendingMovies(): List<TrendingMovie>

    suspend fun getMovieDetail(movieId: Int): MovieDetails

    suspend fun getMovieCast(movieId: Int): List<MovieCast>

    suspend fun getSimilarMovie(movieId: Int): List<MovieSimilar>

    suspend fun getPerson(personId: Int): Person


    //Search
    suspend fun searchMulti(query: String): List<MultiSearchItem>

    suspend fun searchMovie(query: String): List<Movie>

    suspend fun searchPerson(query: String): List<Person>

    suspend fun searchTv(query: String): List<TvShow>
}