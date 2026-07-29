package com.makeev.cima.presentation.screens.model

import com.makeev.cima.data.remote.dto.MovieDetailDto.ProductionCountry

data class MovieDetailUiModel(
    val title: String,
    val releaseDate: String,
    val runtime: String,
    val voteAverage: String,
    val tagline: String,
    val overview: String,
    val productionCountries: List<ProductionCountry>,
    val id: String,
    val budget: String,
    val posterPath: String,
)
