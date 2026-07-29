package com.makeev.cima.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailDto(
    @SerialName("adult")
    val adult: Boolean = false,
    @SerialName("backdrop_path")
    val backdropPath: String? = null, // Может быть null
    @SerialName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection? = null,
    @SerialName("budget")
    val budget: Long? = null, // Важно: Long, так как бюджет фильма может превысить Int.MAX_VALUE!
    @SerialName("genres")
    val genres: List<Genre> = emptyList(),
    @SerialName("homepage")
    val homepage: String? = null, // Может быть null
    @SerialName("id")
    val id: Int,
    @SerialName("imdb_id")
    val imdbId: String? = null, // Может быть null
    @SerialName("origin_country")
    val originCountry: List<String> = emptyList(),
    @SerialName("original_language")
    val originalLanguage: String? = null,
    @SerialName("original_title")
    val originalTitle: String? = null,
    @SerialName("overview")
    val overview: String? = null, // Может быть null
    @SerialName("popularity")
    val popularity: Double? = null,
    @SerialName("poster_path")
    val posterPath: String? = null, // Может быть null
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompany>? = emptyList(),
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry>? = emptyList(),
    @SerialName("release_date")
    val releaseDate: String? = null, // Может быть null
    @SerialName("revenue")
    val revenue: Long? = null, // Важно: Long для сборов
    @SerialName("runtime")
    val runtime: Int? = null, // Может быть null
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>? = emptyList(),
    @SerialName("status")
    val status: String? = null,
    @SerialName("tagline")
    val tagline: String? = null, // Может быть null
    @SerialName("title")
    val title: String,
    @SerialName("video")
    val video: Boolean = false,
    @SerialName("vote_average")
    val voteAverage: Double? = null,
    @SerialName("vote_count")
    val voteCount: Int? = null
) {
    @Serializable
    data class BelongsToCollection(
        @SerialName("backdrop_path")
        val backdropPath: String? = null, // Может быть null
        @SerialName("id")
        val id: Int,
        @SerialName("name")
        val name: String,
        @SerialName("poster_path")
        val posterPath: String? = null // Может быть null
    )

    @Serializable
    data class Genre(
        @SerialName("id")
        val id: Int,
        @SerialName("name")
        val name: String
    )

    @Serializable
    data class ProductionCompany(
        @SerialName("id")
        val id: Int,
        @SerialName("logo_path")
        val logoPath: String? = null, // <-- ЗДЕСЬ БЫЛА ОШИБКА (исправлено на String?)
        @SerialName("name")
        val name: String,
        @SerialName("origin_country")
        val originCountry: String? = null
    )

    @Serializable
    data class ProductionCountry(
        @SerialName("iso_3166_1")
        val iso31661: String,
        @SerialName("name")
        val name: String
    )

    @Serializable
    data class SpokenLanguage(
        @SerialName("english_name")
        val englishName: String? = null,
        @SerialName("iso_639_1")
        val iso6391: String? = null,
        @SerialName("name")
        val name: String? = null
    )
}
