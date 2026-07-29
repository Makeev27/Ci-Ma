package com.makeev.cima.presentation.screens.model

data class MovieCastUiModel(
    val character: String,
    val name: String,
    val popularity: Double,
    val profilePath: String?,
    val id: Int,
)
