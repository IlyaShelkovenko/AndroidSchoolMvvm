package com.gmail.hostov47.androidschoolmvvm.presentation

import com.gmail.hostov47.androidschoolmvvm.models.domain.MovieCastDomain
import com.gmail.hostov47.androidschoolmvvm.models.domain.MovieDetailsDomain
import com.gmail.hostov47.androidschoolmvvm.models.presentation.Cast
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MovieDetailsWithCast

val movieDetailsDomain = MovieDetailsDomain(
    id = 123,
    posterPath = "poster_path",
    title = "title",
    rating = 3.4f,
    overview = "overview",
    productCompanies = "product_companies",
    genres = "genres",
    releaseDate = "releaze_date"
)

val moviesCastDomain = listOf(
    MovieCastDomain(
        id = 123,
        name = "name",
        profilePath = "profilePath",
    )
)
val movieCast = moviesCastDomain.map { Cast(it.id, it.name, it.fullPosterPath) }

val moviesDetailWithCast = MovieDetailsWithCast(
    id = 123,
    posterPath = "poster_path",
    title = "title",
    rating = 3.4f,
    overview = "overview",
    productionCompanies = "product_companies",
    genres = "genres",
    releaseDate = "releaze_date",
    cast = movieCast
)
