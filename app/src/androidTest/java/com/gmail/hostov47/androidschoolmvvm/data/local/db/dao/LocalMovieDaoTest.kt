/**
 * Created by Ilia Shelkovenko on 14.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.local.db.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gmail.hostov47.androidschoolmvvm.data.local.db.MovieDb
import com.gmail.hostov47.androidschoolmvvm.data.local.db.MoviesCategory
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.MovieLocal
import com.gmail.hostov47.androidschoolmvvm.utils.moviesLocal
import com.gmail.hostov47.androidschoolmvvm.utils.moviesLocalNowPlayingCategory
import com.gmail.hostov47.androidschoolmvvm.utils.moviesLocalPopularCategory
import com.gmail.hostov47.androidschoolmvvm.utils.moviesLocalUpcomingCategory
import org.junit.Assert.*


@RunWith(AndroidJUnit4::class)
class LocalMovieDaoTest {
    private lateinit var database: MovieDb
    private lateinit var localMoviesDao: LocalMoviesDao

    /*@Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()*/

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDb::class.java
        ).allowMainThreadQueries().build()
        localMoviesDao = database.localMovieDao()
    }

    @After
    fun clear(){
        database.close()
    }

    @Test
    fun insertMovieTest(){
        val expectedMovie = moviesLocal[0]

        localMoviesDao.insert(expectedMovie)

        val actualMovie = localMoviesDao.getMovieById(expectedMovie.movieId)
        assertTrue(expectedMovie.isTheSameMovie(actualMovie))
    }

    @Test
    fun getMovieByIdTest(){
        val expectedMovie = moviesLocal[0]
        localMoviesDao.insert(expectedMovie)

        val actualMovie = localMoviesDao.getMovieById(expectedMovie.movieId)

        assertTrue(expectedMovie.isTheSameMovie(actualMovie))
    }

    @Test
    fun updateMovieTest(){
        val expectedMovie = moviesLocal[0]
        localMoviesDao.insert(expectedMovie)

        expectedMovie.isPopular = true
        expectedMovie.originalLanguage = "rus"
        expectedMovie.voteAverage = 43.3
        localMoviesDao.update(expectedMovie)

        val actualMovie = localMoviesDao.getMovieById(expectedMovie.movieId)
        assertTrue(expectedMovie.isTheSameMovie(actualMovie))
    }

    @Test
    fun deleteMovieTest(){
        val expectedMovie = moviesLocal[0]
        localMoviesDao.insert(expectedMovie)

        localMoviesDao.delete(expectedMovie.movieId)

        val actualMovie = localMoviesDao.getMovieById(expectedMovie.movieId)
        assertEquals(null, actualMovie)
    }

    @Test
    fun insertOrUpdateEmptyTest(){
        val expectedMovie = moviesLocal[2]
        expectedMovie.isPopular = true

        localMoviesDao.insertOrUpdate(expectedMovie, MoviesCategory.Popular)

        val actualMovie = localMoviesDao.getMovieById(expectedMovie.movieId)
        assertTrue(expectedMovie.isTheSameMovie(actualMovie))
    }

    @Test
    fun insertOrUpdateExistTest(){
        val expectedMovie = moviesLocal[2]
        expectedMovie.isPopular = true
        localMoviesDao.insert(expectedMovie)

        localMoviesDao.insertOrUpdate(expectedMovie, MoviesCategory.Upcoming)
        expectedMovie.isUpcoming = true

        val actualMovie = localMoviesDao.getMovieById(expectedMovie.movieId)
        assertTrue(expectedMovie.isTheSameMovie(actualMovie))
    }

    @Test
    fun getPopularMovies(){
        val movies = moviesLocalPopularCategory
        val popularMovies = movies.filter { it.isPopular }
        val popularMoviesExpectedSize = popularMovies.count()
        movies.forEach { localMoviesDao.insert(it) }

        val actualMovies = localMoviesDao.getPopularMovies()

        assertEquals(popularMoviesExpectedSize, actualMovies.size)
        assertTrue(popularMovies.isTheSameMovieList(actualMovies))
    }

    @Test
    fun getUpcomingMovies(){
        val movies = moviesLocalUpcomingCategory
        val upcomingMovies = movies.filter { it.isUpcoming }
        val upcomingMoviesExpectedSize = upcomingMovies.count()
        movies.forEach { localMoviesDao.insert(it) }

        val actualMovies = localMoviesDao.getUpcomingMovies()

        assertEquals(upcomingMoviesExpectedSize, actualMovies.size)
        assertTrue(upcomingMovies.isTheSameMovieList(actualMovies))
    }

    @Test
    fun getNowPlayingMovies(){
        val movies = moviesLocalNowPlayingCategory
        val nowPlayingMovies = movies.filter { it.isNowPlaying }
        val nowPlayingMoviesExpectedSize = nowPlayingMovies.count()
        movies.forEach { localMoviesDao.insert(it) }

        val actualMovies = localMoviesDao.getNowPlayingMovies()

        assertEquals(nowPlayingMoviesExpectedSize, actualMovies.size)
        assertTrue(nowPlayingMovies.isTheSameMovieList(actualMovies))
    }
}

fun MovieLocal.isTheSameMovie(movie: MovieLocal): Boolean{
    return this.movieId == movie.movieId &&
    this.isAdult == movie.isAdult &&
    this.overview == movie.overview &&
    this.releaseDate == movie.releaseDate &&
    this.genreIds == movie.genreIds &&
    this.originalTitle == movie.originalTitle &&
    this.originalLanguage == movie.originalLanguage &&
    this.title == movie.title &&
    this.backdropPath == movie.backdropPath &&
    this.popularity == movie.popularity &&
    this.voteCount == movie.voteCount &&
    this.video == movie.video &&
    this.voteAverage == movie.voteAverage &&
    this.posterPath == movie.posterPath &&
    this.isPopular == movie.isPopular &&
    this.isUpcoming == movie.isUpcoming &&
    this.isNowPlaying == movie.isNowPlaying
}

fun List<MovieLocal>.isTheSameMovieList(list: List<MovieLocal>): Boolean{
    return this.toSet() == list.toSet()
}