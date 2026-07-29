package com.makeev.cima.domain.model

data class MovieCast(
    val character: String,
    val name: String,
    val popularity: Double,
    val profilePath: String?,
    val id: Int,
)
