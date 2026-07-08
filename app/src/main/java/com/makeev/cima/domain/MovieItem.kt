package com.makeev.cima.domain

data class MovieItem(
    val movieId: Int = 0,
    val title: String,
    val description: String,
    val rating: Double,
    val year: Int,
    val imageURL: String
)
