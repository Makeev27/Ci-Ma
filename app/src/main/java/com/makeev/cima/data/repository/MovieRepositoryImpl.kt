package com.makeev.cima.data.repository

import com.makeev.cima.data.mapper.toMovie
import com.makeev.cima.data.mapper.toMovieCast
import com.makeev.cima.data.mapper.toMovieDetails
import com.makeev.cima.data.mapper.toMovieSimilar
import com.makeev.cima.data.mapper.toPerson
import com.makeev.cima.data.mapper.toPopularMovie
import com.makeev.cima.data.mapper.toTrendingMovie
import com.makeev.cima.data.mapper.toTvShows
import com.makeev.cima.data.remote.api.TMDBApi
import com.makeev.cima.domain.model.Movie
import com.makeev.cima.domain.model.MovieCast
import com.makeev.cima.domain.model.MovieDetails
import com.makeev.cima.domain.model.MovieSimilar
import com.makeev.cima.domain.model.MultiSearchItem
import com.makeev.cima.domain.model.Person
import com.makeev.cima.domain.model.PopularMovie
import com.makeev.cima.domain.model.TrendingMovie
import com.makeev.cima.domain.model.TvShow
import com.makeev.cima.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: TMDBApi
) : MovieRepository {
    override suspend fun getPopularMovies(): List<PopularMovie> {
        val response = api.getPopularMovie()
        return response.popularMovies
            .filter { it.originalLanguage != "ja" && it.overview.isNotBlank() }
            .map { it.toPopularMovie() }
    }

    override suspend fun getTrendingMovies(): List<TrendingMovie> {
        val response = api.getTrendingMovie()
        return response.trendingMovies
            .filter { it.originalLanguage != "ja" && it.overview.isNotBlank() }
            .map { it.toTrendingMovie() }
    }

    override suspend fun getPerson(personId: Int): Person {
        val response = api.getPerson(personId)
        return response.toPerson()
    }

    override suspend fun searchMulti(query: String): List<MultiSearchItem> {
        val response = api.searchMulti(query = query)
        return response.results
            .map { it as MultiSearchItem }
    }

    override suspend fun searchMovie(query: String): List<Movie> {
        val response = api.searchMovies(query = query)
        return response.results
            .sortedByDescending{it.popularity}
            .map { it.toMovie() }
    }

    override suspend fun searchPerson(query: String): List<Person> {
        val response = api.searchPerson(query = query)
        return response.results
            .sortedByDescending { it.popularity }
            .map { it.toPerson() }
    }

    override suspend fun searchTv(query: String): List<TvShow> {
        val response = api.searchTvShows(query = query)
        return response.results
            .sortedByDescending { it.popularity }
            .map { it.toTvShows() }
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetails {
        val response = api.getMovieDetails(movieId)
        return response.toMovieDetails()
    }

    override suspend fun getMovieCast(movieId: Int): List<MovieCast> {
        val response = api.getMovieCredits(movieId)
        return response.cast.map { it.toMovieCast() }
    }

    override suspend fun getSimilarMovie(movieId: Int): List<MovieSimilar> {
        val response = api.getSimilarMovie(movieId = movieId)
        return response.results
            .filter { it.voteAverage > 6.0 && it.originalLanguage != "ja" && it.popularity > 1 }
            .map { it.toMovieSimilar() }
    }
}