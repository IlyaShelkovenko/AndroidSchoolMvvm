/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.repository.home

import com.gmail.hostov47.androidschoolmvvm.data.local.MovieStore
import com.gmail.hostov47.androidschoolmvvm.data.api.ImdbApi
import com.gmail.hostov47.androidschoolmvvm.data.mappers.FromMovieToMovieLocalMapper
import com.gmail.hostov47.androidschoolmvvm.models.data.local.MovieLocal
import javax.inject.Inject


/**
 * Релазизация [MoviesRepository]
 */
class MoviesRepositoryImpl @Inject constructor(
    private val moviesApi: ImdbApi,
    private val movieStore: MovieStore
) : MoviesRepository {

    override fun getPopularMovies(forceLoad: Boolean, caching: Boolean): List<MovieLocal> {
        var movies: List<MovieLocal>? = null
        if (!forceLoad)
            movies = movieStore.getPopularMovies()
        if (movies == null) {
            movies = moviesApi.getPopularMovies().results?.map { movie ->
                FromMovieToMovieLocalMapper.map(movie)
            }.also {
                if (caching)
                    it?.let {
                        movieStore.savePopularMovies(it)
                    }
            }
        }
        return movies ?: emptyList()
    }

    override fun getUpcomingMovies(forceLoad: Boolean, caching: Boolean): List<MovieLocal> {
        var movies: List<MovieLocal>? = null
        if (!forceLoad)
            movies = movieStore.getUpcomingMovies()
        if (movies == null) {
            movies = moviesApi.getUpcomingMovies().results?.map { movie ->
                FromMovieToMovieLocalMapper.map(movie)
            }.also {
                if (caching)
                    it?.let {
                        movieStore.saveUpcomingMovies(it)
                    }
            }
        }
        return movies ?: emptyList()
    }

    override fun getNowPlayingMovies(forceLoad: Boolean, caching: Boolean): List<MovieLocal> {
        var movies: List<MovieLocal>? = null
        if (!forceLoad)
            movies = movieStore.getNowPlayingMovies()
        if (movies == null) {
            movies = moviesApi.getNowPlayingMovies().results?.map { movie ->
                val newMovie = FromMovieToMovieLocalMapper.map(movie)
                newMovie
            }.also {
                if (caching)
                    it?.let {
                        movieStore.saveNowPlayingMovies(it)
                    }
            }
        }
        return movies ?: emptyList()
    }
}
