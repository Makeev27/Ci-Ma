package com.makeev.cima.domain.model

import com.makeev.cima.data.remote.dto.MovieDetailDto.ProductionCountry

data class MovieDetails(
    val title: String,
    val releaseDate: String,
    val runtime: Int,
    val voteAverage: Double,
    val tagline: String,
    val overview: String,
    val productionCountries: List<ProductionCountry>,
    val id: Int,
    val budget: Long,
    val posterPath: String
)
