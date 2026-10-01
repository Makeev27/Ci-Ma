package com.makeev.cima.presentation.screens.model

import com.makeev.cima.data.remote.dto.search.TvDto.Genre

data class TvShowUiModel(
    val episodeRunTime: List<Int>,
    val genres: List<Genre>,
    val id: Int,
    val languages: List<String>,
    val name: String,
    val numberOfEpisodes: Int,
    val originalName: String,
    val popularity: Double,
    val posterPath: String,
    val voteAverage: String,
    val firstAirDate: String,
    val lastAirDate: String,
    val overview: String
) : MultiSearchUiItem
