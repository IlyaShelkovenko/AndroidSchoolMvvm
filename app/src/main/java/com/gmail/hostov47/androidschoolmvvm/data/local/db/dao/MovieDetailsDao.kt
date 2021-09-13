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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movieDetails: MovieDetailsLocal)

    @Query("SELECT * FROM ${MovieDb.MOVIE_DETAILS_TABLE} WHERE id = :movieId")
    fun getMovieDetails(movieId: Int): MovieDetailsLocal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(localCast: LocalCast)

    @Query("SELECT * FROM ${MovieDb.MOVIE_CAST_TABLE} WHERE movieId = :movieId")
    fun getMovieCast(movieId: Int): LocalCast?
}