/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.repository.home

import com.gmail.hostov47.androidschoolmvvm.data.local.MovieStore
import com.gmail.hostov47.androidschoolmvvm.data.api.ImdbApi
import com.gmail.hostov47.androidschoolmvvm.models.data.local.MovieLocal
import javax.inject.Inject


/**
 * Релазизация [MoviesRepository]
 */
class MoviesRepositoryImpl @Inject constructor(private val moviesApi: ImdbApi, private val movieStore: MovieStore) : MoviesRepository {

    override fun getPopularMovies(forceLoad: Boolean):List<MovieLocal>{
        var movies: List<MovieLocal>? = null
        if (!forceLoad)
            movies = movieStore.getMovies()
        if(movies == null) {
            movies = moviesApi.getPopularMovies().results.map { movie ->
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
        return movies
    }
}
