/**
 * Created by Ilia Shelkovenko on 15.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.local.db.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gmail.hostov47.androidschoolmvvm.data.local.db.MovieDb
import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.FavoriteMovie
import com.gmail.hostov47.androidschoolmvvm.utils.testFavoriteMovie
import com.gmail.hostov47.androidschoolmvvm.utils.testFavoriteMovieList
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteMoviesDaoTest {
    private lateinit var database: MovieDb
    private lateinit var favoriteMoviesDao: FavoriteMoviesDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDb::class.java
        ).allowMainThreadQueries().build()
        favoriteMoviesDao = database.favoriteMoviesDao()
    }

    @After
    fun clear() {
        database.close()
    }

    @Test
    fun insertMovieTest() {
        val expectedMovie = testFavoriteMovie

        favoriteMoviesDao.insert(expectedMovie)

        favoriteMoviesDao.getFavoriteMovies()
            .test()
            .assertValue { list ->
                val actualMovie = list[0]
                expectedMovie.movieId == actualMovie.movieId &&
                        expectedMovie.title == actualMovie.title &&
                        expectedMovie.poster == actualMovie.poster
            }
    }

    @Test
    fun getFavoriteMoviesTest() {
        val expectedMovies = testFavoriteMovieList
        for (movie in expectedMovies) {
            favoriteMoviesDao.insert(movie)
        }

        favoriteMoviesDao.getFavoriteMovies()
            .test()
            .assertValue {
                it.size == expectedMovies.size
            }
            .assertValue { list ->
                expectedMovies.isTheSameList(list)
            }
    }

    @Test
    fun getFavoriteMoviesFromEmptyDbTest() {
        favoriteMoviesDao.getFavoriteMovies()
            .test()
            .assertValue {
                it.isEmpty()
            }
    }

    @Test
    fun deleteFavoriteMoviesTest() {
        val expectedMovie = testFavoriteMovie
        favoriteMoviesDao.insert(expectedMovie)

        favoriteMoviesDao.delete(expectedMovie.movieId)

        favoriteMoviesDao.getFavoriteMovies()
            .test()
            .assertValue { list ->
                list.isEmpty()
            }
    }

    @Test
    fun deleteFavoriteMoviesFromMultipleMoviesTest() {
        val expectedMovies = testFavoriteMovieList.toMutableList()
        val indexToDelete = 0
        for (movie in expectedMovies) {
            favoriteMoviesDao.insert(movie)
        }

        favoriteMoviesDao.delete(expectedMovies[indexToDelete].movieId)

        favoriteMoviesDao.getFavoriteMovies()
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
    fun isFavoriteMovieInEmptyDbTest() {
        val expectedMovie = testFavoriteMovie

        favoriteMoviesDao.isFavorite(expectedMovie.movieId)
            .test()
            .assertNoValues()
    }

    @Test
    fun isFavoriteMovieSingleMatchDbTest() {
        val expectedMovie = testFavoriteMovie
        favoriteMoviesDao.insert(expectedMovie)

        favoriteMoviesDao.isFavorite(expectedMovie.movieId)
            .test()
            .assertValue { movie ->
                expectedMovie.isTheSameMovie(movie)
            }
    }

    @Test
    fun isFavoriteMovieFullDbTest() {
        val indexToCompare = 2
        val expectedMovies = testFavoriteMovieList
        for (movie in expectedMovies) {
            favoriteMoviesDao.insert(movie)
        }

        favoriteMoviesDao.isFavorite(expectedMovies[indexToCompare].movieId)
            .test()
            .assertValue { movie ->
                expectedMovies[indexToCompare].movieId == movie.movieId
            }
    }

    @Test
    fun isFavoriteMovieFullDbNoMatchTest() {
        val expectedMovies = testFavoriteMovieList
        for (movie in expectedMovies) {
            favoriteMoviesDao.insert(movie)
        }

        favoriteMoviesDao.isFavorite(4444)
            .test()
            .assertNoValues()
    }
}

fun List<FavoriteMovie>.isTheSameList(list: List<FavoriteMovie>): Boolean {
    if (this.size != list.size) return false
    for (index in this.indices) {
        if (this[index].isTheSameMovie(list[index]))
            continue
        else return false
    }
    return true
}

fun FavoriteMovie.isTheSameMovie(movie: FavoriteMovie): Boolean {
    return this.movieId == movie.movieId &&
            this.title == movie.title &&
            this.poster == movie.poster
}
