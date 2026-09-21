package com.makeev.cima.data.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailResponse(
    @SerialName("adult")
    val adult: Boolean = false,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection? = null,
    @SerialName("budget")
    val budget: Long? = null,
    @SerialName("credits")
    val credits: Credits = Credits(),
    @SerialName("genres")
    val genres: List<Genre> = emptyList(),
    @SerialName("homepage")
    val homepage: String? = null,
    @SerialName("id")
    val id: Int,
    @SerialName("images")
    val images: Images = Images(),
    @SerialName("imdb_id")
    val imdbId: String? = null,
    @SerialName("origin_country")
    val originCountry: List<String> = emptyList(),
    @SerialName("original_language")
    val originalLanguage: String? = null,
    @SerialName("original_title")
    val originalTitle: String? = null,
    @SerialName("overview")
    val overview: String? = null,
    @SerialName("popularity")
    val popularity: Double? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompany> = emptyList(),
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry> = emptyList(),
    @SerialName("recommendations")
    val recommendations: Recommendations = Recommendations(),
    @SerialName("release_date")
    val releaseDate: String? = null,
    @SerialName("revenue")
    val revenue: Long? = null,
    @SerialName("runtime")
    val runtime: Int? = null,
    @SerialName("softcore")
    val softcore: Boolean = false,
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage> = emptyList(),
    @SerialName("status")
    val status: String? = null,
    @SerialName("tagline")
    val tagline: String? = null,
    @SerialName("title")
    val title: String,
    @SerialName("video")
    val video: Boolean = false,
    @SerialName("videos")
    val videos: Videos = Videos(),
    @SerialName("vote_average")
    val voteAverage: Double? = null,
    @SerialName("vote_count")
    val voteCount: Int? = null
) {
    @Serializable
    data class BelongsToCollection(
        @SerialName("backdrop_path")
        val backdropPath: String? = null,
        @SerialName("id")
        val id: Int = 0,
        @SerialName("name")
        val name: String = "",
        @SerialName("poster_path")
        val posterPath: String? = null
    )

    @Serializable
    data class Credits(
        @SerialName("cast")
        val cast: List<Cast> = emptyList(),
        @SerialName("crew")
        val crew: List<Crew> = emptyList()
    ) {
        @Serializable
        data class Cast(
            @SerialName("adult")
            val adult: Boolean = false,
            @SerialName("cast_id")
            val castId: Int? = null,
            @SerialName("character")
            val character: String? = null,
            @SerialName("credit_id")
            val creditId: String? = null,
            @SerialName("gender")
            val gender: Int? = null,
            @SerialName("id")
            val id: Int,
            @SerialName("known_for_department")
            val knownForDepartment: String? = null,
            @SerialName("name")
            val name: String? = null,
            @SerialName("order")
            val order: Int? = null,
            @SerialName("original_name")
            val originalName: String? = null,
            @SerialName("popularity")
            val popularity: Double? = null,
            @SerialName("profile_path")
            val profilePath: String? = null
        )

        @Serializable
        data class Crew(
            @SerialName("adult")
            val adult: Boolean = false,
            @SerialName("credit_id")
            val creditId: String? = null,
            @SerialName("department")
            val department: String? = null,
            @SerialName("gender")
            val gender: Int? = null,
            @SerialName("id")
            val id: Int,
            @SerialName("job")
            val job: String? = null,
            @SerialName("known_for_department")
            val knownForDepartment: String? = null,
            @SerialName("name")
            val name: String? = null,
            @SerialName("original_name")
            val originalName: String? = null,
            @SerialName("popularity")
            val popularity: Double? = null,
            @SerialName("profile_path")
            val profilePath: String? = null
        )
    }

    @Serializable
    data class Genre(
        @SerialName("id")
        val id: Int,
        @SerialName("name")
        val name: String
    )

    @Serializable
    data class Images(
        @SerialName("backdrops")
        val backdrops: List<ImageItem> = emptyList(),
        @SerialName("logos")
        val logos: List<ImageItem> = emptyList(),
        @SerialName("posters")
        val posters: List<ImageItem> = emptyList()
    ) {
        @Serializable
        data class ImageItem(
            @SerialName("aspect_ratio")
            val aspectRatio: Double? = null,
            @SerialName("file_path")
            val filePath: String? = null,
            @SerialName("height")
            val height: Int? = null,
            @SerialName("iso_3166_1")
            val iso31661: String? = null,
            @SerialName("iso_639_1")
            val iso6391: String? = null,
            @SerialName("vote_average")
            val voteAverage: Double? = null,
            @SerialName("vote_count")
            val voteCount: Int? = null,
            @SerialName("width")
            val width: Int? = null
        )
    }

    @Serializable
    data class ProductionCompany(
        @SerialName("id")
        val id: Int,
        @SerialName("logo_path")
        val logoPath: String? = null,
        @SerialName("name")
        val name: String,
        @SerialName("origin_country")
        val originCountry: String? = null
    )

    @Serializable
    data class ProductionCountry(
        @SerialName("iso_3166_1")
        val iso31661: String? = null,
        @SerialName("name")
        val name: String? = null
    )

    @Serializable
    data class Recommendations(
        @SerialName("page")
        val page: Int = 1,
        @SerialName("results")
        val movieRecommendations: List<MovieRecommendations> = emptyList(),
        @SerialName("total_pages")
        val totalPages: Int = 0,
        @SerialName("total_results")
        val totalResults: Int = 0
    ) {
        @Serializable
        data class MovieRecommendations(
            @SerialName("adult")
            val adult: Boolean = false,
            @SerialName("backdrop_path")
            val backdropPath: String? = null,
            @SerialName("genre_ids")
            val genreIds: List<Int> = emptyList(),
            @SerialName("id")
            val id: Int,
            @SerialName("media_type")
            val mediaType: String? = null,
            @SerialName("original_language")
            val originalLanguage: String? = null,
            @SerialName("original_title")
            val originalTitle: String? = null,
            @SerialName("overview")
            val overview: String? = null,
            @SerialName("popularity")
            val popularity: Double? = null,
            @SerialName("poster_path")
            val posterPath: String? = null,
            @SerialName("release_date")
            val releaseDate: String? = null,
            @SerialName("softcore")
            val softcore: Boolean = false,
            @SerialName("title")
            val title: String? = null,
            @SerialName("video")
            val video: Boolean = false,
            @SerialName("vote_average")
            val voteAverage: Double? = null,
            @SerialName("vote_count")
            val voteCount: Int? = null
        )
    }

    @Serializable
    data class SpokenLanguage(
        @SerialName("english_name")
        val englishName: String? = null,
        @SerialName("iso_639_1")
        val iso6391: String? = null,
        @SerialName("name")
        val name: String? = null
    )

    @Serializable
    data class Videos(
        @SerialName("results")
        val videoResults: List<VideosResult> = emptyList()
    ) {
        @Serializable
        data class VideosResult(
            @SerialName("id")
            val id: String,
            @SerialName("iso_3166_1")
            val iso31661: String? = null,
            @SerialName("iso_639_1")
            val iso6391: String? = null,
            @SerialName("key")
            val key: String? = null,
            @SerialName("name")
            val name: String? = null,
            @SerialName("official")
            val official: Boolean = false,
            @SerialName("published_at")
            val publishedAt: String? = null,
            @SerialName("site")
            val site: String? = null,
            @SerialName("size")
            val size: Int? = null,
            @SerialName("type")
            val type: String? = null
        )
    }
}
