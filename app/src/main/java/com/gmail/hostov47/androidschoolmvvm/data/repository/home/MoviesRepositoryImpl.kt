/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.repository.home

import com.gmail.hostov47.androidschoolmvvm.data.local.MovieStore
import com.gmail.hostov47.androidschoolmvvm.data.api.ImdbApi
import com.gmail.hostov47.androidschoolmvvm.models.data.local.MovieLocal


/**
 * Релазизация [MoviesRepository]
 */
class MoviesRepositoryImpl(private val moviesApi: ImdbApi, private val movieStore: MovieStore) : MoviesRepository {

    override fun getPopularMovies(): List<MovieLocal> =
        movieStore.getMovies() ?: moviesApi.getPopularMovies().results.map { movie ->
            MovieLocal(
                isAdult = movie.isAdult,
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
                voteAverage = movie.voteAverage,
                posterPath = movie.posterPath,
            )
        }.also {
            movieStore.saveMovies(it)
        }
}
