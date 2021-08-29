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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item : WatchListMovie): Long

    @Query("SELECT * FROM $WATCHLIST_TABLE")
    fun getWatchList(): Single<List<WatchListMovie>>

    @Query("DELETE FROM $WATCHLIST_TABLE WHERE movieId = :movieId")
    fun delete(movieId: Int)

    @Query("SELECT * FROM $WATCHLIST_TABLE WHERE movieId = :movieId")
    fun isInWatchList(movieId: Int) : Single<WatchListMovie>
}