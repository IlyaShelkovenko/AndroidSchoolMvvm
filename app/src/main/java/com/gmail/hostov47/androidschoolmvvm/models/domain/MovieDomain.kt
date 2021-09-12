package com.gmail.hostov47.androidschoolmvvm.models.domain

data class MovieDomain(
    val isAdult: Boolean,
    val overview: String,
    var releaseDate: String?,
    var genreIds: List<Int>?,
    var id: Int,
    var originalTitle: String?,
    var originalLanguage: String?,
    var title: String,
    var backdropPath: String?,
    var popularity: Double,
    var voteCount: Int,
    var video: Boolean,
    var rating: Float?,
    val posterPath: String?
)


