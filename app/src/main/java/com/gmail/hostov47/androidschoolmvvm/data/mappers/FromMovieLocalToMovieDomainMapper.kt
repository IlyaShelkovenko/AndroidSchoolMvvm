package com.gmail.hostov47.androidschoolmvvm.data.mappers

import com.gmail.hostov47.androidschoolmvvm.BuildConfig
import com.gmail.hostov47.androidschoolmvvm.models.data.dto.Movie
import com.gmail.hostov47.androidschoolmvvm.models.data.local.MovieLocal
import com.gmail.hostov47.androidschoolmvvm.models.domain.MovieDomain

object FromMovieLocalToMovieDomainMapper {
    fun map(movie: MovieLocal): MovieDomain {
        return MovieDomain(isAdult = movie.isAdult,
            overview = movie.overview,
            releaseDate = movie.releaseDate,
            genreIds = movie.genreIds,
            id = movie.id,
            originalTitle = movie.originalTitle,
            originalLanguage = movie.originalLanguage,
            title = movie.title,
            backdropPath = movie.backdropPath,
            popularity = movie.popularity,
            voteCount = movie.voteCount,
            video = movie.video,
            rating = (movie.voteAverage?.toFloat() ?: 0f) / 2,
            posterPath = movie.posterPath?.let{ "${ BuildConfig.POSTER_PATH}$it" }
        )
    }

    fun mapList(movies: List<MovieLocal>?): List<MovieDomain> {
        return movies?.map { movie -> map(movie) } ?: emptyList()
    }
}