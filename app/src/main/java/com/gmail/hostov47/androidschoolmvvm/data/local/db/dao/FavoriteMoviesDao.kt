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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item : FavoriteMovie): Long

    @Query("SELECT * FROM $FAVORITE_TABLE")
    fun getFavoriteMovies(): Single<List<FavoriteMovie>>

    @Query("DELETE FROM $FAVORITE_TABLE WHERE movieId = :movieId")
    fun delete(movieId: Int)

    @Query("SELECT * FROM $FAVORITE_TABLE WHERE movieId = :movieId")
    fun isFavorite(movieId: Int) : Single<FavoriteMovie>
}