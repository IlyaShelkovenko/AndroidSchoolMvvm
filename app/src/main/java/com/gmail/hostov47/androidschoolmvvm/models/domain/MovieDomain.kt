package com.gmail.hostov47.androidschoolmvvm.models.domain

import com.gmail.hostov47.androidschoolmvvm.BuildConfig

data class MovieDomain(
    val isAdult: Boolean,
    val overview: String,
    var releaseDate: String,
    var genreIds: List<Int>,
    var id: Int,
    var originalTitle: String,
    var originalLanguage: String,
    var title: String,
    var backdropPath: String?,
    var popularity: Double,
    var voteCount: Int,
    var video: Boolean,
    var rating: Float?,
    val posterPath: String?
){
    /*var fullPosterPath: String = ""
    get() {
        field = if(posterPath != null)
            "${ BuildConfig.POSTER_PATH}$posterPath"
        else ""
        return field
    }*/
    /*val rating: Float
        get() {
            return (voteAverage?.toFloat() ?: 0f) / 2
        }*/
}

