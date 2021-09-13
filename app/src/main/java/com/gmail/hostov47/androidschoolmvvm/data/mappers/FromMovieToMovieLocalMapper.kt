/**
 * Created by Ilia Shelkovenko on 05.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.mappers

import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.MovieLocal
import com.gmail.hostov47.androidschoolmvvm.models.data.dto.Movie


object FromMovieToMovieLocalMapper {
    fun map(movie: Movie): MovieLocal {
        return MovieLocal(
            isAdult = movie.isAdult,
            overview = movie.overview,
            releaseDate = movie.releaseDate,
            genreIds = movie.genreIds,
            movieId = movie.id,
            originalTitle = movie.originalTitle,
            originalLanguage = movie.originalLanguage,
            title = movie.title,
            backdropPath = movie.backdropPath,
            popularity = movie.popularity,
            voteCount = movie.voteCount,
            video = movie.video,
            voteAverage = movie.voteAverage,
            posterPath = movie.posterPath,
        )
    }

    fun mapList(movies: List<Movie>?): List<MovieLocal> {
        return movies?.map { movie ->
            map(movie)
        } ?: emptyList()
    }
}