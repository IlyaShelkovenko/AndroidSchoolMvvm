package com.gmail.hostov47.androidschoolmvvm.data.mappers

import com.gmail.hostov47.androidschoolmvvm.BuildConfig
import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.MovieDetailsLocal
import com.gmail.hostov47.androidschoolmvvm.models.domain.MovieDetailsDomain

object FromDetailsLocalToDetailsDomain {
    fun map(localDetails: MovieDetailsLocal): MovieDetailsDomain {
        return MovieDetailsDomain(
            id = localDetails.id,
            posterPath = localDetails.posterPath?.let{ "${ BuildConfig.POSTER_PATH}$it" },
            title = localDetails.title,
            rating = (localDetails.voteAverage?.toFloat() ?: 0f) / 2,
            overview = localDetails.overview,
            productCompanies = localDetails.productionCompanies,
            genres = localDetails.genres,
            releaseDate = localDetails.releaseDate,
        )
    }
}