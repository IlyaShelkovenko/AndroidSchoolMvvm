/**
 * Created by Ilia Shelkovenko on 26.08.2021.
 */
package com.gmail.hostov47.androidschoolmvvm.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gmail.hostov47.androidschoolmvvm.data.local.db.MovieDb.Companion.WATCHLIST_TABLE
import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.WatchListMovie
import io.reactivex.Single

@Dao
interface WatchListDao {

    /**
     * Метод, вставляющий фильм в базу данных.
     *
     * @param item [WatchListMovie] фильм который нужно вставить в БД.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item : WatchListMovie): Long

    /**
     * Метод, возвращающий список фильмов к просмотру.
     *
     * @return [Single] список фильмов [WatchListMovie].
     */
    @Query("SELECT * FROM $WATCHLIST_TABLE")
    fun getWatchList(): Single<List<WatchListMovie>>

    /**
     * Метод, удаляющий фильм из базы данных по его идентификатору.
     *
     * @param movieId идентификатор фильма который нужно удалить из БД.
     */
    @Query("DELETE FROM $WATCHLIST_TABLE WHERE movieId = :movieId")
    fun delete(movieId: Int)

    /**
     * Метод, проверяющий, есть ли фильм в хранилище фильмов к просмотру.
     *
     * @return [Single] [WatchListMovie], если фильм есть в хранилище, exception если нет.
     */
    @Query("SELECT * FROM $WATCHLIST_TABLE WHERE movieId = :movieId")
    fun isInWatchList(movieId: Int) : Single<WatchListMovie>
}