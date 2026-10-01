package com.makeev.cima.utils

import com.makeev.cima.data.remote.dto.response.MovieDetailResponse
import com.makeev.cima.domain.model.Movie
import com.makeev.cima.domain.model.MovieDetails
import com.makeev.cima.domain.model.Person
import com.makeev.cima.domain.model.PopularMovie
import com.makeev.cima.domain.model.TrendingMovie
import com.makeev.cima.domain.model.TvShow
import com.makeev.cima.presentation.screens.detail.MovieRecommendationsUiModel
import com.makeev.cima.presentation.screens.model.MovieCastUiModel
import com.makeev.cima.presentation.screens.model.MovieDetailUiModel
import com.makeev.cima.presentation.screens.model.MovieUiModel
import com.makeev.cima.presentation.screens.model.PersonDetailUiModel
import com.makeev.cima.presentation.screens.model.PersonUiModel
import com.makeev.cima.presentation.screens.model.TvShowUiModel
import java.util.Locale


private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
private const val VIDEO_BASE_URL = "https://www.youtube.com/watch?v="
fun MovieDetails.toMovieUiModel(): MovieDetailUiModel {
    return MovieDetailUiModel(
        title = title,
        releaseDate = getReleaseYear(releaseDate),
        runtime = runtimeToHours(runtime),
        voteAverage = voteAverage.roundToOneDecimal(),
        tagline = tagline.orEmpty(),
        overview = overview,
        productionCountries = productionCountries,
        id = id,
        budget = "$budget $",
        posterPath = "${IMAGE_BASE_URL}${this.posterPath}",
        belongsToCollection = belongsToCollection,
        videos = "${VIDEO_BASE_URL}${this}",
        recommendations = recommendations,
        credits = credits,
        genres = genres,
        images = images
    )
}

fun TrendingMovie.toMovieUiModel(): MovieUiModel {
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

fun Movie.toMovieUiModel(): MovieUiModel {
    return MovieUiModel(
        title = title,
        releaseDate = getReleaseYear(releaseDate),
        runtime = runtimeToHours(runtime),
        voteAverage = voteAverage.roundToOneDecimal(),
        overview = overview,
        id = id,
        posterPath = "${IMAGE_BASE_URL}${this.posterPath}",
        genres = genres,
    )
}

fun Person.toPersonUiModel(): PersonUiModel {
    return PersonUiModel(
        birthday = birthday,
        deathday = deathday,
        gender = when (gender) {
            1 -> "Женский"
            2 -> "Мужской"
            3 -> "Небинарный"
            else -> "Н/Д"
        },
        id = id,
        knownForDepartment = when (knownForDepartment) {
            "Acting" -> "Актер"
            else -> ""
        },
        name = name,
        placeOfBirth = placeOfBirth,
        profilePath = "${IMAGE_BASE_URL}${this.profilePath}"
    )
}

fun TvShow.toTvShowUiModel(): TvShowUiModel {
    return TvShowUiModel(
        episodeRunTime = episodeRunTime,
        genres = genres,
        id = id,
        languages = languages,
        name = name,
        numberOfEpisodes = numberOfEpisodes,
        originalName = originalName,
        popularity = popularity,
        posterPath = "${IMAGE_BASE_URL}${this.posterPath}",
        voteAverage = voteAverage.roundToOneDecimal(),
        firstAirDate = firstAirDate,
        lastAirDate = lastAirDate,
        overview = overview
    )
}


fun Person.toPersonDetailUiModel(): PersonDetailUiModel {
    return PersonDetailUiModel(
        biography = biography,
        birthday = birthday,
        deathday = deathday,
        gender = when (gender) {
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

fun MovieDetailResponse.Credits.Cast.toMovieCastUiModel(): MovieCastUiModel {
    return MovieCastUiModel(
        character = character.orEmpty(),
        name = name.orEmpty(),
        popularity = popularity ?: 0.0,
        profilePath = profilePath?.let { path -> "${IMAGE_BASE_URL}$path" },
        id = id
    )
}

fun MovieDetailResponse.Recommendations.MovieRecommendations.toMovieRecommendationsUiModel(): MovieRecommendationsUiModel {
    return MovieRecommendationsUiModel(
        id = id,
        title = title.orEmpty(),
        posterPath = posterPath?.let { path -> "${IMAGE_BASE_URL}$path" },
        voteAverage = (voteAverage ?: 0.0).roundToOneDecimal(),
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