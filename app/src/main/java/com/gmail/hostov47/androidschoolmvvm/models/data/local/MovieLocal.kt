package com.gmail.hostov47.androidschoolmvvm.models.data.local

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieLocal(
    @SerialName("adult")
    val isAdult: Boolean,
    val overview: String,
    @SerialName("release_date")
    var releaseDate: String,
    @SerialName("genre_ids")
    var genreIds: List<Int>,
    var id: Int,
    @SerialName("original_title")
    var originalTitle: String,
    @SerialName("original_language")
    var originalLanguage: String,
    var title: String,
    @SerialName("backdrop_path")
    var backdropPath: String?,
    var popularity: Double,
    @SerialName("vote_count")
    var voteCount: Int,
    var video: Boolean,
    @SerialName("vote_average")
    var voteAverage: Double,
    @SerialName("poster_path")
    val posterPath: String?
)