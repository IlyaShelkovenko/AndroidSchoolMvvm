/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.repository.home

import com.gmail.hostov47.androidschoolmvvm.data.local.store.MovieStore
import com.gmail.hostov47.androidschoolmvvm.data.api.ImdbApi
import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.MovieLocal
import com.gmail.hostov47.androidschoolmvvm.data.local.store.MovieStoreNew
import com.gmail.hostov47.androidschoolmvvm.data.mappers.FromMovieToMovieLocalMapper
import javax.inject.Inject
import javax.inject.Named


/**
 * Релазизация [MoviesRepository]
 */
class MoviesRepositoryImpl @Inject constructor(
    private val moviesApi: ImdbApi,
    @Named("DbCash")
    private val movieStoreDb: MovieStoreNew
) : MoviesRepository {

    override fun getPopularMovies(forceLoad: Boolean, caching: Boolean): List<MovieLocal> {
        var movies: List<MovieLocal> = emptyList()
        if (!forceLoad)
            movies = movieStoreDb.getPopularMovies()
        if (movies.isEmpty()) {
            movies = moviesApi.getPopularMovies().results?.map { movie ->
                FromMovieToMovieLocalMapper.map(movie)
            }.also {
                if (caching)
                    it?.let {
                        movieStoreDb.savePopularMovies(it)
                    }
            } ?: emptyList()
        }
        return movies
    }

    override fun getUpcomingMovies(forceLoad: Boolean, caching: Boolean): List<MovieLocal> {
        var movies: List<MovieLocal> = emptyList()
        if (!forceLoad)
            movies = movieStoreDb.getUpcomingMovies()
        if (movies.isEmpty()) {
            movies = moviesApi.getUpcomingMovies().results?.map { movie ->
                FromMovieToMovieLocalMapper.map(movie)
            }.also {
                if (caching)
                    it?.let {
                        movieStoreDb.saveUpcomingMovies(it)
                    }
            } ?: emptyList()
        }
        return movies
    }

    override fun getNowPlayingMovies(forceLoad: Boolean, caching: Boolean): List<MovieLocal> {
        var movies: List<MovieLocal> = emptyList()
        if (!forceLoad)
            movies = movieStoreDb.getNowPlayingMovies()
        if (movies.isEmpty()) {
            movies = moviesApi.getNowPlayingMovies().results?.map { movie ->
                val newMovie = FromMovieToMovieLocalMapper.map(movie)
                newMovie
            }.also {
                if (caching)
                    it?.let {
                        movieStoreDb.saveNowPlayingMovies(it)
                    }
            } ?: emptyList()
        }
        return movies
    }
}
