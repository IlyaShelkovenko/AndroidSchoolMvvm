/**
 * Created by Ilia Shelkovenko on 10.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.extensions

import com.gmail.hostov47.androidschoolmvvm.models.domain.MovieDetailsDomain
import com.gmail.hostov47.androidschoolmvvm.models.presentation.Cast
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MovieDetailsWithCast

fun MovieDetailsDomain.toMovieDetailsWithCast(cast: List<Cast>): MovieDetailsWithCast {
    return MovieDetailsWithCast(
        this.id,
        this.posterPath,
        this.title,
        this.rating,
        this.overview,
        this.productCompanies,
        this.genres,
        this.releaseDate,
        cast
    )
}