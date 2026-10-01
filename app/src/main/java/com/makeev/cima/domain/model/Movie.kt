package com.makeev.cima.domain.model

data class Movie(
    val id: Int,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val runtime: Int,
    val title: String,
    val voteAverage: Double,
    val originalLanguage: String,
    val popularity: Double = 0.0,
    val voteCount: Int = 0,
    val genres: List<String>
) : MultiSearchItem
