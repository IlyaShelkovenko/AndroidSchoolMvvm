/**
 * Created by Ilia Shelkovenko on 14.09.2021.
 */
package com.gmail.hostov47.androidschoolmvvm.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gmail.hostov47.androidschoolmvvm.data.local.db.MovieDb
import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.LocalCast
import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.MovieDetailsLocal

@Dao
interface MovieDetailsDao {

    /**
     * Метод, вставляющий фильм в базу данных.
     *
     * @param movieDetails [MovieDetailsLocal] фильм который нужно вставить в БД.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movieDetails: MovieDetailsLocal)

    /**
     * Метод, возвращающий фильм из БД по его идентификатору.
     *
     * @param movieId идентификатор фильма, который нужно пулучить из БД.
     * @return [MovieDetailsLocal] фильм с детальной информацией
     */
    @Query("SELECT * FROM ${MovieDb.MOVIE_DETAILS_TABLE} WHERE movieId = :movieId")
    fun getMovieDetails(movieId: Int): MovieDetailsLocal?


    /**
     * Метод, вставляющий информацию о касте в базу данных.
     *
     * @param localCast [LocalCast] фильм который нужно вставить в БД.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(localCast: LocalCast)

    /**
     * Метод, возвращающий каст фильма из БД по его идентификатору.
     *
     * @param movieId идентификатор фильма, информацию о касте которого нужно пулучить из БД.
     * @return [LocalCast] каст фильма
     */
    @Query("SELECT * FROM ${MovieDb.MOVIE_CAST_TABLE} WHERE movieId = :movieId")
    fun getMovieCast(movieId: Int): LocalCast?

    /**
     * Метод, возвращающий все касты фильмов из БД.
     *
     * @return список [LocalCast] всех кастов из фильмов, хранящихся в БД.
     */
    @Query("SELECT * FROM ${MovieDb.MOVIE_CAST_TABLE}")
    fun getAllMovieCast(): List<LocalCast>

    /**
     * Метод, возвращающий всю детальную информацию о всех фильмах, хранящихся в БД.
     *
     * @return список [MovieDetailsLocal] всей детальной информации о фильмах, хранящихся в БД.
     */
    @Query("SELECT * FROM ${MovieDb.MOVIE_DETAILS_TABLE}")
    fun getAllMovieDetails(): List<MovieDetailsLocal>
}