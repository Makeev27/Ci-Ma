package com.makeev.cima.data.remote.dto.response


import com.makeev.cima.data.remote.dto.MovieCastDto
import com.makeev.cima.data.remote.dto.MovieCrewDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieCreditsResponse(
    @SerialName("cast")
    val cast: List<MovieCastDto>,
    @SerialName("crew")
    val crew: List<MovieCrewDto>,
    @SerialName("id")
    val id: Int
)