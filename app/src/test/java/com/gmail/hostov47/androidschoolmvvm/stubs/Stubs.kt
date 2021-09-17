/**
 * Created by Ilia Shelkovenko on 16.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.stubs

import com.gmail.hostov47.androidschoolmvvm.models.domain.MovieCastDomain
import com.gmail.hostov47.androidschoolmvvm.models.domain.MovieDetailsDomain
import com.gmail.hostov47.androidschoolmvvm.models.domain.MovieDomain
import com.gmail.hostov47.androidschoolmvvm.models.presentation.Cast
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MovieDetailsWithCast
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview

val movieDomain = MovieDomain(
    isAdult = false,
    overview = "overview",
    releaseDate = null,
    genreIds = null,
    id = 12345,
    originalTitle = "originalTitle",
    originalLanguage = "eng",
    title = "title",
    backdropPath = null,
    popularity = 0.5,
    voteCount = 10,
    video = false,
    rating = 1.2f,
    posterPath = "posterPath"
)

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

val errorTest = Throwable("test throwable")

val movieDomain2 = MovieDomain(
    isAdult = false,
    overview = "overview2",
    releaseDate = null,
    genreIds = null,
    id = 1234567,
    originalTitle = "originalTitle2",
    originalLanguage = "eng2",
    title = "title",
    backdropPath = null,
    popularity = 0.5,
    voteCount = 10,
    video = false,
    rating = 1.2f,
    posterPath = "posterPath2"
)

val movieDomain3 = MovieDomain(
    isAdult = false,
    overview = "overview3",
    releaseDate = null,
    genreIds = null,
    id = 123456789,
    originalTitle = "originalTitl3",
    originalLanguage = "eng3",
    title = "title",
    backdropPath = null,
    popularity = 0.5,
    voteCount = 10,
    video = false,
    rating = 1.2f,
    posterPath = "posterPath3"
)

val listOfMovieDomain = listOf(movieDomain, movieDomain2, movieDomain3)

val moviesPreviewList = listOfMovieDomain.map { movie ->
    MoviePreview(
        movieId = movie.id,
        title = movie.title,
        poster = movie.posterPath ?: "",
        rating = movie.rating
    )
}