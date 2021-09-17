/**
 * Created by Ilia Shelkovenko on 13.09.2021.
 */
package com.gmail.hostov47.androidschoolmvvm.data.local.db.dao

import androidx.room.*
import com.gmail.hostov47.androidschoolmvvm.data.local.db.MovieDb
import com.gmail.hostov47.androidschoolmvvm.data.local.db.MoviesCategory
import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.MovieLocal

@Dao
interface LocalMoviesDao {

    /**
     * Метод, вставляющий фильм в базу данных.
     *
     * @param movie [MovieLocal] фильм который нужно вставить в БД.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie : MovieLocal): Long

    /**
     * Метод, возвращающий список популярных фильмов.
     *
     * @return список популярных фильмов [MovieLocal].
     */
    @Query("SELECT * FROM ${MovieDb.MOVIE_LOCAL_TABLE} WHERE isPopular = 1")
    fun getPopularMovies(): List<MovieLocal>

    /**
     * Метод, возвращающий список новых фильмов.
     *
     * @return список новых фильмов [MovieLocal].
     */
    @Query("SELECT * FROM ${MovieDb.MOVIE_LOCAL_TABLE} WHERE isUpcoming = 1")
    fun getUpcomingMovies(): List<MovieLocal>

    /**
     * Метод, возвращающий список рекомендуемых фильмов.
     *
     * @return список рекомендуемых фильмов [MovieLocal].
     */
    @Query("SELECT * FROM ${MovieDb.MOVIE_LOCAL_TABLE} WHERE isNowPlaying = 1")
    fun getNowPlayingMovies(): List<MovieLocal>

    /**
     * Метод, возвращающий фильм по его идентификатору.
     *
     * @param movieId иденетификатор фильма
     * @return найденный фильм [MovieLocal].
     */
    @Query("SELECT * FROM ${MovieDb.MOVIE_LOCAL_TABLE} WHERE movieId = :movieId")
    fun getMovieById(movieId: Int): MovieLocal

    /**
     * Метод, удаляющий фильм из базы данных по его идентификатору.
     *
     * @param movieId идентификатор фильма который нужно удалить из БД.
     */
    @Query("DELETE FROM ${MovieDb.MOVIE_LOCAL_TABLE} WHERE :movieId = movieId")
    fun delete(movieId: Int)

    /**
     * Метод, для обновления фильма в БД.
     *
     * @param movie [MovieLocal] обновленный фильм для помещения в БД.
     */
    @Update()
    fun update(movie : MovieLocal): Int

    @Transaction
    fun insertOrUpdate(movie : MovieLocal, category: MoviesCategory) {
        val tmpMovie = getMovieById(movie.movieId)
        if (tmpMovie == null) {
            insert(movie)
        } else {
            when(category){
                MoviesCategory.Popular -> { tmpMovie.isPopular = true}
                MoviesCategory.Upcoming -> { tmpMovie.isUpcoming = true }
                MoviesCategory.NowPlaying -> { tmpMovie.isNowPlaying = true }
            }
            update(tmpMovie)
        }
    }
}
