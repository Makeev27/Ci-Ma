package com.makeev.cima.presentation.screens.model

data class PersonUiModel(
    val birthday: String,
    val deathday: String?,
    val gender: String,
    val id: Int,
    val knownForDepartment: String,
    val name: String,
    val placeOfBirth: String,
    val profilePath: String
) : MultiSearchUiItem
