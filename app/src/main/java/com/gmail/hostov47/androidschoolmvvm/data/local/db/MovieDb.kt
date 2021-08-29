/**
 * Created by Ilia Shelkovenko on 26.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gmail.hostov47.androidschoolmvvm.BuildConfig
import com.gmail.hostov47.androidschoolmvvm.data.local.db.MovieDb.Companion.DATABASE_VERSION
import com.gmail.hostov47.androidschoolmvvm.data.local.db.dao.FavoriteMoviesDao
import com.gmail.hostov47.androidschoolmvvm.data.local.db.dao.WatchListDao
import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.FavoriteMovie
import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.WatchListMovie

@Database(entities = [FavoriteMovie::class, WatchListMovie::class], version = DATABASE_VERSION, exportSchema = false)
abstract class MovieDb : RoomDatabase(){
    companion object {
        const val DATABASE_NAME = BuildConfig.APPLICATION_ID + ".db"
        const val DATABASE_VERSION = 1
        const val FAVORITE_TABLE = "favorite_movies"
        const val WATCHLIST_TABLE = "watchlist_movies"
    }
    abstract fun favoriteMoviesDao() : FavoriteMoviesDao
    abstract fun watchlistDao() : WatchListDao
}