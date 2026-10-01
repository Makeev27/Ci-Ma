package com.makeev.cima.domain.model

import com.makeev.cima.data.remote.dto.search.TvDto.Genre

data class TvShow(
    val backdropPath: String,
    val createdBy: List<String?>,
    val episodeRunTime: List<Int>,
    val firstAirDate: String,
    val genres: List<Genre>,
    val id: Int,
    val inProduction: Boolean,
    val languages: List<String>,
    val name: String,
    val numberOfEpisodes: Int,
    val numberOfSeasons: Int,
    val originalName: String,
    val popularity: Double,
    val posterPath: String,
    val voteAverage: Double,
    val lastAirDate: String,
    val overview: String
) : MultiSearchItem
