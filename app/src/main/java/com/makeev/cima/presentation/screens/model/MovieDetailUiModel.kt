package com.makeev.cima.presentation.screens.model

import com.makeev.cima.data.remote.dto.response.MovieDetailResponse
import com.makeev.cima.data.remote.dto.response.MovieDetailResponse.BelongsToCollection
import com.makeev.cima.data.remote.dto.response.MovieDetailResponse.Credits

data class MovieDetailUiModel(
    val title: String = "",
    val releaseDate: String = "",
    val runtime: String = "",
    val voteAverage: String = "",
    val tagline: String = "",
    val overview: String = "",
    val productionCountries: List<MovieDetailResponse.ProductionCountry>,
    val credits: Credits? = null,
    val belongsToCollection: BelongsToCollection? = null,
    val images: List<String> = emptyList(),
    val genres: List<String> = emptyList(),
    val recommendations: MovieDetailResponse.Recommendations? = null,
    val videos: String = "",
    val id: Int = 0,
    val budget: String = "",
    val posterPath: String = ""
)
