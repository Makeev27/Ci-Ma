package com.makeev.cima.data.remote.dto.response


import com.makeev.cima.data.remote.dto.MovieSimilarDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieSimilarResponse(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<MovieSimilarDto>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)