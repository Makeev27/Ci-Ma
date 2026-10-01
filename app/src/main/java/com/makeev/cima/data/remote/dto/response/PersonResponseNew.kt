package com.makeev.cima.data.remote.dto.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonResponseNew(
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("also_known_as")
    val alsoKnownAs: List<String>,
    @SerialName("biography")
    val biography: String,
    @SerialName("birthday")
    val birthday: String,
    @SerialName("deathday")
    val deathday: String?,
    @SerialName("gender")
    val gender: Int,
    @SerialName("homepage")
    val homepage: String?,
    @SerialName("id")
    val id: Int,
    @SerialName("imdb_id")
    val imdbId: String,
    @SerialName("known_for_department")
    val knownForDepartment: String,
    @SerialName("movie_credits")
    val movieCredits: MovieCredits,
    @SerialName("name")
    val name: String,
    @SerialName("place_of_birth")
    val placeOfBirth: String,
    @SerialName("popularity")
    val popularity: Double,
    @SerialName("profile_path")
    val profilePath: String,
    @SerialName("tv_credits")
    val tvCredits: TvCredits
) {
    @Serializable
    data class MovieCredits(
        @SerialName("cast")
        val cast: List<Cast>,
        @SerialName("crew")
        val crew: List<Crew>
    ) {
        @Serializable
        data class Cast(
            @SerialName("adult")
            val adult: Boolean,
            @SerialName("backdrop_path")
            val backdropPath: String?,
            @SerialName("character")
            val character: String,
            @SerialName("credit_id")
            val creditId: String,
            @SerialName("genre_ids")
            val genreIds: List<Int>,
            @SerialName("id")
            val id: Int,
            @SerialName("order")
            val order: Int,
            @SerialName("original_language")
            val originalLanguage: String,
            @SerialName("original_title")
            val originalTitle: String,
            @SerialName("overview")
            val overview: String,
            @SerialName("popularity")
            val popularity: Double,
            @SerialName("poster_path")
            val posterPath: String?,
            @SerialName("release_date")
            val releaseDate: String,
            @SerialName("softcore")
            val softcore: Boolean,
            @SerialName("title")
            val title: String,
            @SerialName("video")
            val video: Boolean,
            @SerialName("vote_average")
            val voteAverage: Double,
            @SerialName("vote_count")
            val voteCount: Int
        )

        @Serializable
        data class Crew(
            @SerialName("adult")
            val adult: Boolean,
            @SerialName("backdrop_path")
            val backdropPath: String?,
            @SerialName("credit_id")
            val creditId: String,
            @SerialName("department")
            val department: String,
            @SerialName("genre_ids")
            val genreIds: List<Int>,
            @SerialName("id")
            val id: Int,
            @SerialName("job")
            val job: String,
            @SerialName("original_language")
            val originalLanguage: String,
            @SerialName("original_title")
            val originalTitle: String,
            @SerialName("overview")
            val overview: String,
            @SerialName("popularity")
            val popularity: Double,
            @SerialName("poster_path")
            val posterPath: String?,
            @SerialName("release_date")
            val releaseDate: String,
            @SerialName("softcore")
            val softcore: Boolean,
            @SerialName("title")
            val title: String,
            @SerialName("video")
            val video: Boolean,
            @SerialName("vote_average")
            val voteAverage: Double,
            @SerialName("vote_count")
            val voteCount: Int
        )
    }

    @Serializable
    data class TvCredits(
        @SerialName("cast")
        val cast: List<Cast>,
        @SerialName("crew")
        val crew: List<Crew>
    ) {
        @Serializable
        data class Cast(
            @SerialName("adult")
            val adult: Boolean,
            @SerialName("backdrop_path")
            val backdropPath: String?,
            @SerialName("character")
            val character: String,
            @SerialName("credit_id")
            val creditId: String,
            @SerialName("episode_count")
            val episodeCount: Int,
            @SerialName("first_air_date")
            val firstAirDate: String,
            @SerialName("first_credit_air_date")
            val firstCreditAirDate: String,
            @SerialName("genre_ids")
            val genreIds: List<Int>,
            @SerialName("id")
            val id: Int,
            @SerialName("name")
            val name: String,
            @SerialName("origin_country")
            val originCountry: List<String>,
            @SerialName("original_language")
            val originalLanguage: String,
            @SerialName("original_name")
            val originalName: String,
            @SerialName("overview")
            val overview: String,
            @SerialName("popularity")
            val popularity: Double,
            @SerialName("poster_path")
            val posterPath: String?,
            @SerialName("softcore")
            val softcore: Boolean,
            @SerialName("vote_average")
            val voteAverage: Double,
            @SerialName("vote_count")
            val voteCount: Int
        )

        @Serializable
        data class Crew(
            @SerialName("adult")
            val adult: Boolean,
            @SerialName("backdrop_path")
            val backdropPath: String?,
            @SerialName("credit_id")
            val creditId: String,
            @SerialName("department")
            val department: String,
            @SerialName("episode_count")
            val episodeCount: Int?,
            @SerialName("first_air_date")
            val firstAirDate: String,
            @SerialName("first_credit_air_date")
            val firstCreditAirDate: String?,
            @SerialName("genre_ids")
            val genreIds: List<Int>,
            @SerialName("id")
            val id: Int,
            @SerialName("job")
            val job: String,
            @SerialName("name")
            val name: String,
            @SerialName("origin_country")
            val originCountry: List<String>,
            @SerialName("original_language")
            val originalLanguage: String,
            @SerialName("original_name")
            val originalName: String,
            @SerialName("overview")
            val overview: String,
            @SerialName("popularity")
            val popularity: Double,
            @SerialName("poster_path")
            val posterPath: String?,
            @SerialName("softcore")
            val softcore: Boolean,
            @SerialName("vote_average")
            val voteAverage: Double,
            @SerialName("vote_count")
            val voteCount: Int
        )
    }
}