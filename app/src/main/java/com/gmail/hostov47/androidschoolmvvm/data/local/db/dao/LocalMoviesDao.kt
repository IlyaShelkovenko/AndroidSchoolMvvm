/**
 * Created by Ilia Shelkovenko on 13.09.2021.
 */
package com.gmail.hostov47.androidschoolmvvm.data.local.db.dao

import androidx.room.*
import com.gmail.hostov47.androidschoolmvvm.data.local.db.MovieDb
import com.gmail.hostov47.androidschoolmvvm.data.local.db.MoviesCategory
import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.MovieLocal
import io.reactivex.Single

@Dao
interface LocalMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie : MovieLocal): Long

    @Query("SELECT * FROM ${MovieDb.MOVIE_LOCAL_TABLE} WHERE isPopular = 1")
    fun getPopularMovies(): List<MovieLocal>

    @Query("SELECT * FROM ${MovieDb.MOVIE_LOCAL_TABLE} WHERE isUpcoming = 1")
    fun getUpcomingMovies(): List<MovieLocal>

    @Query("SELECT * FROM ${MovieDb.MOVIE_LOCAL_TABLE} WHERE isNowPlaying = 1")
    fun getNowPlayingMovies(): List<MovieLocal>

    @Query("SELECT * FROM ${MovieDb.MOVIE_LOCAL_TABLE} WHERE movieId = :movieId")
    fun getMovieById(movieId: Int): MovieLocal

    @Query("DELETE FROM ${MovieDb.MOVIE_LOCAL_TABLE} WHERE :movieId = movieId")
    fun delete(movieId: Int)

    @Update
    fun update(movie : MovieLocal): Int

    @Transaction
    fun insertOrUpdate(movie : MovieLocal, category: MoviesCategory) {
        val tmpMovie = getMovieById(movie.movieId)
        if (tmpMovie == null) {
            insert(movie)
        } else {
            when(category){
                MoviesCategory.Popular -> { tmpMovie.isPopular = movie.isPopular }
                MoviesCategory.Upcoming -> { tmpMovie.isUpcoming = movie.isUpcoming }
                MoviesCategory.NowPlaying -> { tmpMovie.isNowPlaying = movie.isNowPlaying }
            }
            update(tmpMovie)
        }
    }
}
