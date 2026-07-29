package com.makeev.cima.data.mapper

import com.makeev.cima.data.remote.dto.MovieCastDto
import com.makeev.cima.data.remote.dto.MovieDetailDto
import com.makeev.cima.data.remote.dto.MovieSimilarDto
import com.makeev.cima.data.remote.dto.PopularMovieDto
import com.makeev.cima.data.remote.dto.TrendingMovieDto
import com.makeev.cima.data.remote.dto.response.PersonResponse
import com.makeev.cima.domain.model.MovieCast
import com.makeev.cima.domain.model.MovieDetails
import com.makeev.cima.domain.model.MovieSimilar
import com.makeev.cima.domain.model.Person
import com.makeev.cima.domain.model.PopularMovie
import com.makeev.cima.domain.model.TrendingMovie

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

fun PopularMovieDto.toPopularMovie(): PopularMovie {
    return PopularMovie(
        id = id,
        overview = overview,
        posterPath = posterPath.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        title = title,
        voteAverage = voteAverage,
        originalLanguage = originalLanguage,
        popularity = popularity,
        voteCount = voteCount
    )
}

fun TrendingMovieDto.toTrendingMovie(): TrendingMovie {
    return TrendingMovie(
        id = id,
        overview = overview,
        posterPath = posterPath.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        title = title,
        voteAverage = voteAverage
    )
}

fun MovieDetailDto.toMovieDetails(): MovieDetails {
    return MovieDetails(
        title = title,
        releaseDate = releaseDate.orEmpty(),
        runtime = runtime ?: 0,
        voteAverage = voteAverage ?: 0.0,
        tagline = tagline.orEmpty(),
        overview = overview.orEmpty(),
        productionCountries = productionCountries.orEmpty(),
        id = id,
        budget = budget ?: 0,
        posterPath = "${IMAGE_BASE_URL}${this.posterPath}"
    )
}

fun MovieCastDto.toMovieCast(): MovieCast {
    return MovieCast(
        character = character,
        name = name,
        popularity = popularity,
        profilePath = "${IMAGE_BASE_URL}${this.profilePath}",
        id = id
    )
}

fun MovieSimilarDto.toMovieSimilar(): MovieSimilar {
    return MovieSimilar(
        id = id,
        backdropPath = backdropPath,
        title = title,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
    )
}

fun PersonResponse.toPerson(): Person {
    return Person(
        adult = adult,
        alsoKnownAs = alsoKnownAs,
        biography = biography,
        birthday = birthday,
        deathday = deathday,
        gender = gender,
        homepage = homepage,
        id = id,
        imdbId = imdbId,
        knownForDepartment = knownForDepartment,
        name = name,
        placeOfBirth = placeOfBirth,
        popularity = popularity,
        profilePath = profilePath
    )
}