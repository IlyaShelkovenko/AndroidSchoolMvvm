/**
 * Created by Ilia Shelkovenko on 08.04.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.network.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailResponse(
    @Json(name = "adult")
    val isAdult: Boolean,
    @Json(name = "backdrop_path")
    val backdrop_path: String?,
    @Json(name = "belongs_to_collection")
    val belongsToCollection: Any?,

    val budget: Long,
    val genres: List<Genre>,
    val homepage: String?,
    val id: Int,

    @Json(name = "imdb_id")
    val imdbID: String?,

    @Json(name = "original_language")
    val originalLanguage: String,

    @Json(name = "original_title")
    val originalTitle: String,

    val overview: String?,
    val popularity: Double,

    @Json(name = "production_companies")
    val productionCompanies: List<ProductionCompany>,

    @Json(name = "production_countries")
    val productionCountries: List<ProductionCountry>,

    @Json(name = "release_date")
    val releaseDate: String,

    val revenue: Long,
    val runtime: Long,

    @Json(name = "spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,

    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,

    @Json(name = "vote_average")
    val voteAverage: Double,

    @Json(name = "vote_count")
    val voteCount: Long,
    @Json(name = "poster_path")
    val posterPath: String?
)

@JsonClass(generateAdapter = true)
data class Genre (
    val id: Long,
    val name: String
)

@JsonClass(generateAdapter = true)
data class ProductionCompany (
    val id: Long,
    @Json(name = "logo_path")
    val logoPath: String? = null,
    val name: String,
    @Json(name = "origin_country")
    val originCountry: String
)

@JsonClass(generateAdapter = true)
data class ProductionCountry (
        @Json(name = "iso_3166_1")
        val iso3166_1: String,
        val name: String
)

@JsonClass(generateAdapter = true)
data class SpokenLanguage (
        @Json(name = "iso_639_1")
        val iso639_1: String,
        val name: String
)