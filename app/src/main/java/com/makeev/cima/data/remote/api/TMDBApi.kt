package com.makeev.cima.data.remote.api

import com.makeev.cima.data.remote.dto.response.MovieCreditsResponse
import com.makeev.cima.data.remote.dto.response.MovieDetailResponse
import com.makeev.cima.data.remote.dto.response.MovieSimilarResponse
import com.makeev.cima.data.remote.dto.response.PersonResponse
import com.makeev.cima.data.remote.dto.response.PopularMovieResponse
import com.makeev.cima.data.remote.dto.response.TrendingMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {

    @GET("movie/popular")
    suspend fun getPopularMovie(): PopularMovieResponse

    @GET("trending/movie/week")
    suspend fun getTrendingMovie(): TrendingMovieResponse

    //Movies
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("append_to_response") appendToResponse: String = "credits,recommendations,images,videos"
    ): MovieDetailResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int
    ): MovieCreditsResponse

    @GET("movie/{movie_id}/recommendations")
    suspend fun getSimilarMovie(
        @Path("movie_id") movieId: Int
    ): MovieSimilarResponse

    //Persons
    @GET("person/{person_id}")
    suspend fun getPerson(
        @Path("person_id") personId: Int
    ): PersonResponse




    //Search
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): PopularMovieResponse

}