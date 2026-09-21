package com.makeev.cima.domain.model

import com.makeev.cima.data.remote.dto.response.MovieDetailResponse
import com.makeev.cima.data.remote.dto.response.MovieDetailResponse.BelongsToCollection
import com.makeev.cima.data.remote.dto.response.MovieDetailResponse.Credits
import com.makeev.cima.data.remote.dto.response.MovieDetailResponse.ProductionCountry


data class MovieDetails(
    val title: String,
    val releaseDate: String,
    val runtime: Int,
    val voteAverage: Double,
    val tagline: String? = null,
    val overview: String,
    val productionCountries: List<ProductionCountry>,
    val credits: Credits,
    val belongsToCollection: BelongsToCollection? = null,
    val genres: List<String>,
    val images: List<String>,
    val recommendations: MovieDetailResponse.Recommendations,
    val videos: List<String?> = emptyList(),
    val id: Int,
    val budget: Long,
    val posterPath: String
)
