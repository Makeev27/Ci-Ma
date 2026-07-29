package com.makeev.cima.data.remote.dto.response

import com.makeev.cima.data.remote.dto.PopularMovieDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PopularMovieResponse(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val popularMovies: List<PopularMovieDto>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)