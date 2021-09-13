/**
 * Created by Ilia Shelkovenko on 13.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.local.store

import com.gmail.hostov47.androidschoolmvvm.data.local.db.MoviesCategory
import com.gmail.hostov47.androidschoolmvvm.data.local.db.dao.LocalMoviesDao
import com.gmail.hostov47.androidschoolmvvm.data.local.db.dao.MovieDetailsDao
import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.LocalCast
import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.MovieDetailsLocal
import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.MovieLocal
import com.gmail.hostov47.androidschoolmvvm.models.data.local.MovieCastLocal
import javax.inject.Inject

class MovieStoreDbImpl @Inject constructor(
    private val localMoviesDao: LocalMoviesDao,
    private val movieDetailsDao: MovieDetailsDao): MovieStoreNew {
    override fun savePopularMovies(movies: List<MovieLocal>) {
        movies.map {
            it.isPopular = true
            it
        }.forEach{
            localMoviesDao.insertOrUpdate(it, MoviesCategory.Popular)
        }
    }

    override fun saveUpcomingMovies(movies: List<MovieLocal>) {
        movies.map {
            it.isUpcoming = true
            it
        }.forEach{
            localMoviesDao.insertOrUpdate(it, MoviesCategory.Upcoming)
        }
    }

    override fun saveNowPlayingMovies(movies: List<MovieLocal>) {
        movies.map {
            it.isNowPlaying = true
            it
        }.forEach{
            localMoviesDao.insertOrUpdate(it, MoviesCategory.NowPlaying)
        }
    }

    override fun getPopularMovies(): List<MovieLocal> {
        return localMoviesDao.getPopularMovies()
    }

    override fun getUpcomingMovies(): List<MovieLocal> {
        return localMoviesDao.getUpcomingMovies()
    }

    override fun getNowPlayingMovies(): List<MovieLocal> {
        return localMoviesDao.getNowPlayingMovies()
    }

    override fun getMovieDetails(movieId: Int): MovieDetailsLocal? {
        return movieDetailsDao.getMovieDetails(movieId)
    }

    override fun saveMovieDetails(details: MovieDetailsLocal) {
        movieDetailsDao.insert(details)
    }

    override fun saveMovieCredits(movieId: Int, movieCast: List<MovieCastLocal>) {
        movieDetailsDao.insert(LocalCast(movieId,movieCast))
    }

    override fun getMovieCredits(movieId: Int): List<MovieCastLocal> {
        return movieDetailsDao.getMovieCast(movieId)?.cast ?: emptyList()
    }
}