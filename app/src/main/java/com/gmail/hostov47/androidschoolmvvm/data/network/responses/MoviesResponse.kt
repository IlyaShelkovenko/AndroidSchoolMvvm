/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.network.responses

import com.gmail.hostov47.androidschoolmvvm.BuildConfig
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoviesResponse(
    val page: Int,
    @Json(name = "total_results")var totalResults: Int,
    @Json(name = "total_pages")var totalPages: Int,
    var results: List<Movie>
)

@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name = "adult")val isAdult: Boolean,
    val overview: String,
    @Json(name = "release_date")var releaseDate: String,
    @Json(name = "genre_ids")var genreIds: List<Int>,
    var id: Int,
    @Json(name = "original_title")var originalTitle: String,
    @Json(name = "original_language")var originalLanguage: String,
    var title: String,
    @Json(name = "backdrop_path")var backdropPath: String?,
    var popularity: Double,
    @Json(name = "vote_count")var voteCount: Int,
    var video: Boolean,
    @Json(name = "vote_average")var voteAverage: Double,
    @Json(name = "poster_path")
    val posterPath: String?
)