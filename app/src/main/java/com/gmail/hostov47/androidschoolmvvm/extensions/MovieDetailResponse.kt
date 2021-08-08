/**
 * Created by Ilia Shelkovenko on 08.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.extensions

import com.gmail.hostov47.androidschoolmvvm.data.network.responses.Cast
import com.gmail.hostov47.androidschoolmvvm.data.network.responses.MovieDetailResponse
import com.gmail.hostov47.androidschoolmvvm.domain.models.MovieDetailsWithCast

fun MovieDetailResponse.toMovieDetailsWithCast(cast: List<Cast>) : MovieDetailsWithCast {
    return MovieDetailsWithCast(
        this.id,
        this.posterPath,
        this.title,
        this.voteAverage.div(2).toFloat(),
        this.overview,
        this.productionCompanies.joinToString(", ") { it.name },
        this.genres.joinToString(", ") { it.name },
        this.releaseDate,
        cast
    )
}