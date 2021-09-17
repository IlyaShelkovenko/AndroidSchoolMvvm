/**
 * Created by Ilia Shelkovenko on 26.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gmail.hostov47.androidschoolmvvm.data.local.db.MovieDb.Companion.FAVORITE_TABLE
import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.FavoriteMovie
import io.reactivex.Single

@Dao
interface FavoriteMoviesDao {

    /**
     * Метод, вставляющий фильм в базу данных.
     *
     * @param item [FavoriteMovie] фильм который нужно вставить в БД.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item : FavoriteMovie): Long


    /**
     * Метод, возвращающий список понравившихся фильмов.
     *
     * @return [Single] список фильмов [FavoriteMovie].
     */
    @Query("SELECT * FROM $FAVORITE_TABLE")
    fun getFavoriteMovies(): Single<List<FavoriteMovie>>

    /**
     * Метод, удаляющий фильм из базы данных по его идентификатору.
     *
     * @param movieId идентификатор фильма который нужно удалить из БД.
     */
    @Query("DELETE FROM $FAVORITE_TABLE WHERE movieId = :movieId")
    fun delete(movieId: Int)

    /**
     * Метод, проверяющий, есть ли фильм в хранилище понравившихся фильмов.
     *
     * @return [Single] [FavoriteMovie], если фильм есть в хранилище, exception если нет.
     */
    @Query("SELECT * FROM $FAVORITE_TABLE WHERE movieId = :movieId")
    fun isFavorite(movieId: Int) : Single<FavoriteMovie>
}