package com.makeev.cima.data.remote.dto.response


import com.makeev.cima.data.remote.dto.TrendingMovieDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrendingMovieResponse(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val trendingMovies: List<TrendingMovieDto>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)