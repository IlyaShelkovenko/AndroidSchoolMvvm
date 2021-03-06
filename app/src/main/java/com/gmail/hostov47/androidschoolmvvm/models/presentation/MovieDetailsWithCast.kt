/**
 * Created by Ilia Shelkovenko on 08.04.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.models.presentation

import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.FavoriteMovie
import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.WatchListMovie
import com.gmail.hostov47.androidschoolmvvm.models.domain.MovieCastDomain


data class MovieDetailsWithCast(
    val id: Int,
    val posterPath: String? = null,
    val title: String,
    val rating: Float,
    val overview: String? = null,
    val productionCompanies: String ,
    val genres: String = "",
    val releaseDate: String,
    val cast: List<Cast>
)

fun MovieDetailsWithCast.toFavoriteMovie() : FavoriteMovie {
    return FavoriteMovie(
        movieId = id,
        title = title,
        poster = posterPath ?: ""
    )
}

fun MovieDetailsWithCast.toWatchListMovie() : WatchListMovie {
    return WatchListMovie(
        movieId = id,
        title = title,
        poster = posterPath ?: ""
    )
}