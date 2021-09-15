/**
 * Created by Ilia Shelkovenko on 15.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.local.db.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gmail.hostov47.androidschoolmvvm.data.local.db.MovieDb
import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.WatchListMovie
import com.gmail.hostov47.androidschoolmvvm.utils.testWatchListMovie
import com.gmail.hostov47.androidschoolmvvm.utils.testWatchListMovieList
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class WatchListDaoTest {
    private lateinit var database: MovieDb
    private lateinit var watchListDao: WatchListDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDb::class.java
        ).allowMainThreadQueries().build()
        watchListDao = database.watchlistDao()
    }

    @After
    fun clear() {
        database.close()
    }

    @Test
    fun insertMovieTest() {
        val expectedMovie = testWatchListMovie

        watchListDao.insert(expectedMovie)

        watchListDao.getWatchList()
            .test()
            .assertValue { list ->
                val actualMovie = list[0]
                expectedMovie.movieId == actualMovie.movieId &&
                        expectedMovie.title == actualMovie.title &&
                        expectedMovie.poster == actualMovie.poster
            }
    }

    @Test
    fun getWatchListMoviesTest() {
        val expectedMovies = testWatchListMovieList
        for (movie in expectedMovies) {
            watchListDao.insert(movie)
        }

        watchListDao.getWatchList()
            .test()
            .assertValue {
                it.size == expectedMovies.size
            }
            .assertValue { list ->
                expectedMovies.isTheSameList(list)
            }
    }

    @Test
    fun getWatchListMoviesFromEmptyDbTest() {
        watchListDao.getWatchList()
            .test()
            .assertValue {
                it.isEmpty()
            }
    }

    @Test
    fun deleteWatchListMoviesTest() {
        val expectedMovie = testWatchListMovie
        watchListDao.insert(expectedMovie)

        watchListDao.delete(expectedMovie.movieId)

        watchListDao.getWatchList()
            .test()
            .assertValue { list ->
                list.isEmpty()
            }
    }

    @Test
    fun deleteWatchListMoviesFromMultipleMoviesTest() {
        val expectedMovies = testWatchListMovieList.toMutableList()
        val indexToDelete = 0
        for (movie in expectedMovies) {
            watchListDao.insert(movie)
        }

        watchListDao.delete(expectedMovies[indexToDelete].movieId)

        watchListDao.getWatchList()
            .test()
            .assertValue { list ->
                list.size == expectedMovies.size - 1
            }
            .assertValue { list ->
                expectedMovies.removeAt(indexToDelete)
                expectedMovies.isTheSameList(list)
            }
    }

    @Test
    fun isWatchListMovieInEmptyDbTest() {
        val expectedMovie = testWatchListMovie

        watchListDao.isInWatchList(expectedMovie.movieId)
            .test()
            .assertNoValues()
    }

    @Test
    fun isWatchListMovieSingleMatchDbTest() {
        val expectedMovie = testWatchListMovie
        watchListDao.insert(expectedMovie)

        watchListDao.isInWatchList(expectedMovie.movieId)
            .test()
            .assertValue { movie ->
                expectedMovie.isTheSameMovie(movie)
            }
    }

    @Test
    fun isWatchListMovieFullDbTest() {
        val indexToCompare = 2
        val expectedMovies = testWatchListMovieList
        for (movie in expectedMovies) {
            watchListDao.insert(movie)
        }

        watchListDao.isInWatchList(expectedMovies[indexToCompare].movieId)
            .test()
            .assertValue { movie ->
                expectedMovies[indexToCompare].movieId == movie.movieId
            }
    }

    @Test
    fun isWatchListMovieFullDbNoMatchTest() {
        val expectedMovies = testWatchListMovieList
        for (movie in expectedMovies) {
            watchListDao.insert(movie)
        }

        watchListDao.isInWatchList(4444)
            .test()
            .assertNoValues()
    }
}

fun List<WatchListMovie>.isTheSameList(list: List<WatchListMovie>): Boolean {
    if (this.size != list.size) return false
    for (index in this.indices) {
        if (this[index].isTheSameMovie(list[index]))
            continue
        else return false
    }
    return true
}

fun WatchListMovie.isTheSameMovie(movie: WatchListMovie): Boolean {
    return this.movieId == movie.movieId &&
            this.title == movie.title &&
            this.poster == movie.poster
}