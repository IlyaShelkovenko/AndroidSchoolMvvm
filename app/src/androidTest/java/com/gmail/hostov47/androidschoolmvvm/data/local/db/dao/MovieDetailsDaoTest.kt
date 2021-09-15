/**
 * Created by Ilia Shelkovenko on 15.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.local.db.dao

import androidx.room.*
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gmail.hostov47.androidschoolmvvm.data.local.db.MovieDb
import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.LocalCast
import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.MovieDetailsLocal
import com.gmail.hostov47.androidschoolmvvm.utils.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDetailsDaoTest {
    private lateinit var database: MovieDb
    private lateinit var movieDetailsDao: MovieDetailsDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDb::class.java
        ).allowMainThreadQueries().build()
        movieDetailsDao = database.movieDetailsDao()
    }

    @After
    fun clear() {
        database.close()
    }

    @Test
    fun insertMovieDetailsTest(){
        val expectedMovieDetails = movieDetail

        movieDetailsDao.insert(expectedMovieDetails)

        val actualMovieDetails = movieDetailsDao.getMovieDetails(expectedMovieDetails.movieId)
        Assert.assertTrue(expectedMovieDetails.isTheSameDetails(actualMovieDetails!!))
    }

    @Test
    fun insertDifferentMovieDetailsTest(){
        val movieDetails = movieDetail
        val secondDetails = movieDetailsTest
        val expectedSize = 2
        movieDetailsDao.insert(movieDetails)
        movieDetailsDao.insert(secondDetails)

        val actualLocalCast = movieDetailsDao.getAllMovieDetails()
        Assert.assertTrue(expectedSize == actualLocalCast.size)
    }

    @Test
    fun insertTheSameMovieDetailsTest(){
        val movieDetails = movieDetail
        val expectedSize = 1
        movieDetailsDao.insert(movieDetails)
        movieDetailsDao.insert(movieDetails)

        val actualLocalCast = movieDetailsDao.getAllMovieDetails()
        Assert.assertTrue(expectedSize == actualLocalCast.size)
    }

    @Test
    fun getMovieDetailsTest(){
        val expectedMovieDetails = movieDetail
        movieDetailsDao.insert(expectedMovieDetails)

        val actualMovieDetails = movieDetailsDao.getMovieDetails(expectedMovieDetails.movieId)

        Assert.assertTrue(expectedMovieDetails.isTheSameDetails(actualMovieDetails!!))
    }

    @Test
    fun getMovieDetailsFromEmptyTest(){
        val expectedMovieDetails = movieDetail

        val actualMovieDetails = movieDetailsDao.getMovieDetails(expectedMovieDetails.movieId)

        Assert.assertTrue(actualMovieDetails == null)
    }

    @Test
    fun getMovieDetailsWithWrongIdTest(){
        val expectedMovieDetails = movieDetail
        movieDetailsDao.insert(expectedMovieDetails)
        val wrongId = 4444

        val actualMovieDetails = movieDetailsDao.getMovieDetails(wrongId)

        Assert.assertTrue(actualMovieDetails == null)
    }

    @Test
    fun insertLocalCastTest(){
        val expectedLocalCast = localCast

        movieDetailsDao.insert(expectedLocalCast)

        val actualLocalCast = movieDetailsDao.getMovieCast(expectedLocalCast.movieId)
        Assert.assertTrue(expectedLocalCast.isTheSameLocalCast(actualLocalCast!!))
    }

    @Test
    fun insertDifferentLocalCastTest(){
        val localCast = localCast
        val expectedSize = 2
        movieDetailsDao.insert(localCast)
        movieDetailsDao.insert(LocalCast(333, listOf()))

        val actualLocalCast = movieDetailsDao.getAllMovieCast()
        Assert.assertTrue(expectedSize == actualLocalCast.size)
    }

    @Test
    fun insertTheSameLocalCastTest(){
        val localCast = localCast
        val expectedSize = 1
        movieDetailsDao.insert(localCast)
        movieDetailsDao.insert(localCast)

        val actualLocalCast = movieDetailsDao.getAllMovieCast()
        Assert.assertTrue(expectedSize == actualLocalCast.size)
    }

    @Test
    fun getLocalCastTest(){
        val expectedLocalCast = localCast
        movieDetailsDao.insert(expectedLocalCast)

        val actualLocalCast = movieDetailsDao.getMovieCast(expectedLocalCast.movieId)

        Assert.assertTrue(expectedLocalCast.isTheSameLocalCast(actualLocalCast!!))
    }

    @Test
    fun getLocalCastFromEmptyTest(){
        val expectedLocalCast = localCast

        val actualLocalCast = movieDetailsDao.getMovieCast(expectedLocalCast.movieId)

        Assert.assertTrue(actualLocalCast == null)
    }

    @Test
    fun getLocalCastWithWrongIdTest(){
        val expectedLocalCast = localCast
        movieDetailsDao.insert(expectedLocalCast)
        val wrongId = 4444

        val actualLocalCast = movieDetailsDao.getMovieCast(wrongId)

        Assert.assertTrue(actualLocalCast == null)
    }
}

fun MovieDetailsLocal.isTheSameDetails(details: MovieDetailsLocal): Boolean{
    return this.movieId == details.movieId &&
    this.posterPath == details.posterPath &&
    this.title == details.title &&
    this.voteAverage == details.voteAverage &&
    this.overview == details.overview &&
    this.productionCompanies == details.productionCompanies &&
    this.genres == details.genres &&
    this.releaseDate == details.releaseDate
}

fun LocalCast.isTheSameLocalCast(localCast: LocalCast): Boolean{
    return this.movieId == localCast.movieId &&
            this.cast == localCast.cast
}

/*
@Insert(onConflict = OnConflictStrategy.REPLACE)
fun insert(movieDetails: MovieDetailsLocal)

@Query("SELECT * FROM ${MovieDb.MOVIE_DETAILS_TABLE} WHERE movieId = :movieId")
fun getMovieDetails(movieId: Int): MovieDetailsLocal?

@Insert(onConflict = OnConflictStrategy.REPLACE)
fun insert(localCast: LocalCast)

@Query("SELECT * FROM ${MovieDb.MOVIE_CAST_TABLE} WHERE movieId = :movieId")
fun getMovieCast(movieId: Int): LocalCast?*/
