package com.makeev.cima.data.mapper

import com.makeev.cima.data.remote.dto.MovieCastDto
import com.makeev.cima.data.remote.dto.MovieSimilarDto
import com.makeev.cima.data.remote.dto.PopularMovieDto
import com.makeev.cima.data.remote.dto.TrendingMovieDto
import com.makeev.cima.data.remote.dto.response.MovieDetailResponse
import com.makeev.cima.data.remote.dto.response.PersonResponseNew
import com.makeev.cima.data.remote.dto.search.MovieDto
import com.makeev.cima.data.remote.dto.search.PersonDto
import com.makeev.cima.data.remote.dto.search.TvDto
import com.makeev.cima.domain.model.Movie
import com.makeev.cima.domain.model.MovieCast
import com.makeev.cima.domain.model.MovieDetails
import com.makeev.cima.domain.model.MovieSimilar
import com.makeev.cima.domain.model.Person
import com.makeev.cima.domain.model.PopularMovie
import com.makeev.cima.domain.model.TrendingMovie
import com.makeev.cima.domain.model.TvShow

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

fun MovieDetailResponse.toMovieDetails(): MovieDetails {
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
        posterPath = "${IMAGE_BASE_URL}${this.posterPath}",
        credits = credits,
        belongsToCollection = belongsToCollection,
        genres = genres.map { it.name },
        images = images.posters.mapNotNull { poster ->
            poster.filePath?.let { path -> "${IMAGE_BASE_URL}$path" }
        },
        recommendations = recommendations,
        videos = videos.videoResults.map { it.key }
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

fun PersonResponseNew.toPerson(): Person {
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

fun MovieDto.toPopularMovie(): PopularMovie {
    return PopularMovie(
        id = id,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        originalLanguage = originalLanguage,
        popularity = popularity,
        voteCount = voteCount
    )
}

fun MovieDto.toMovie(): Movie {
    return Movie(
        id = id,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        originalLanguage = originalLanguage,
        popularity = popularity,
        voteCount = voteCount,
        runtime = runtime,
        genres = genres.map { it.toString() }
    )
}

fun TvDto.toTvShows(): TvShow {
    return TvShow(
        backdropPath = backdropPath,
        createdBy = createdBy,
        episodeRunTime = episodeRunTime,
        firstAirDate = firstAirDate,
        genres = genres,
        id = id,
        inProduction = inProduction,
        languages = languages,
        name = name,
        numberOfEpisodes = numberOfEpisodes,
        numberOfSeasons = numberOfSeasons,
        originalName = originalName,
        popularity = popularity,
        posterPath = posterPath,
        voteAverage = voteAverage,
        lastAirDate = lastAirDate,
        overview = overview
    )
}

fun PersonDto.toPerson(): Person {
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