package com.makeev.cima.presentation.screens.model

data class MovieUiModel(
    val id: Int,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: String,
    val voteCount: Int = 0,
    val popularity: Double = 0.0
)