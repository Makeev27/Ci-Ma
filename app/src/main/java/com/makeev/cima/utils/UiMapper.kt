package com.makeev.cima.utils

import com.makeev.cima.domain.model.MovieCast
import com.makeev.cima.domain.model.MovieDetails
import com.makeev.cima.domain.model.MovieSimilar
import com.makeev.cima.domain.model.Person
import com.makeev.cima.domain.model.PopularMovie
import com.makeev.cima.domain.model.TrendingMovie
import com.makeev.cima.presentation.screens.detail.MovieSimilarUiModel
import com.makeev.cima.presentation.screens.model.MovieCastUiModel
import com.makeev.cima.presentation.screens.model.MovieDetailUiModel
import com.makeev.cima.presentation.screens.model.MovieUiModel
import com.makeev.cima.presentation.screens.model.PersonUiModel
import java.util.Locale


private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
fun MovieDetails.toMovieDetailUiModel(): MovieDetailUiModel {
    return MovieDetailUiModel(
        title = title,
        releaseDate = getReleaseYear(releaseDate),
        runtime = runtimeToHours(runtime),
        voteAverage = voteAverage.roundToOneDecimal(),
        tagline = tagline,
        overview = overview,
        productionCountries = productionCountries,
        id = id.toString(),
        budget = "$budget $",
        posterPath = posterPath
    )
}

fun TrendingMovie.toMovieDetailUiModel(): MovieUiModel {
    return MovieUiModel(
        id = id,
        posterPath = "${IMAGE_BASE_URL}${this.posterPath}}",
        releaseDate = getReleaseYear(releaseDate),
        title = title,
        voteAverage = voteAverage.roundToOneDecimal(),
        overview = ""
    )
}

fun PopularMovie.toMovieDetailUiModel(): MovieUiModel {
    return MovieUiModel(
        id = id,
        posterPath = "${IMAGE_BASE_URL}${this.posterPath}}",
        releaseDate = getReleaseYear(releaseDate),
        title = title,
        overview = overview,
        voteAverage = voteAverage.roundToOneDecimal()
    )
}

fun PopularMovie.toMovieUiModel(): MovieUiModel {
    return MovieUiModel(
        id = id,
        posterPath = "${IMAGE_BASE_URL}${this.posterPath}}",
        releaseDate = getReleaseYear(releaseDate),
        title = title,
        overview = overview,
        voteAverage = voteAverage.roundToOneDecimal(),
        voteCount = voteCount,
        popularity = popularity
    )
}

fun Person.toPersonUiModel(): PersonUiModel {
    return PersonUiModel(
        biography = biography,
        birthday = birthday,
        deathday = deathday,
        gender = when (gender) {
            0 -> "Н/Д"
            1 -> "Женский"
            2 -> "Мужской"
            3 -> "Небинарный"
            else -> "Н/Д"
        },
        homepage = homepage,
        id = id,
        imdbId = imdbId,
        knownForDepartment = when (knownForDepartment) {
            "Acting" -> "Актер"
            else -> ""
        },
        name = name,
        placeOfBirth = placeOfBirth,
        popularity = popularity,
        profilePath = "${IMAGE_BASE_URL}${this.profilePath}"
    )
}

fun MovieCast.toMovieCastUiModel(): MovieCastUiModel {
    return MovieCastUiModel(
        character = character,
        name = name,
        popularity = popularity,
        profilePath = "${IMAGE_BASE_URL}${this.profilePath}",
        id = id
    )
}

fun MovieSimilar.toMovieSimilarUiModel(): MovieSimilarUiModel {
    return MovieSimilarUiModel(
        id = id,
        title = title,
        posterPath = "${IMAGE_BASE_URL}${this.posterPath}",
        voteAverage = voteAverage.roundToOneDecimal(),
    )

}

fun getReleaseYear(date: String): String {
    return date.split("-").first()
}

fun runtimeToHours(time: Int): String {
    val hours = time / 60
    val minutes = time % 60
    return "${hours}H ${minutes}M"
}

fun Double.roundToOneDecimal(): String {
    return String.format(Locale.US, "%.1f", this)
}