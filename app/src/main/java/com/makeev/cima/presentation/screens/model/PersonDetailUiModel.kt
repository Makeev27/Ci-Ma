package com.makeev.cima.presentation.screens.model

data class PersonDetailUiModel(
    val biography: String,
    val birthday: String,
    val deathday: String?,
    val gender: String,
    val homepage: String?,
    val id: Int,
    val imdbId: String,
    val knownForDepartment: String,
    val name: String,
    val placeOfBirth: String,
    val popularity: Double,
    val profilePath: String
)
