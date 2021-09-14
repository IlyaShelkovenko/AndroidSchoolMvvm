/**
 * Created by Ilia Shelkovenko on 26.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gmail.hostov47.androidschoolmvvm.BuildConfig
import com.gmail.hostov47.androidschoolmvvm.data.local.db.MovieDb.Companion.DATABASE_VERSION
import com.gmail.hostov47.androidschoolmvvm.data.local.db.dao.FavoriteMoviesDao
import com.gmail.hostov47.androidschoolmvvm.data.local.db.dao.LocalMoviesDao
import com.gmail.hostov47.androidschoolmvvm.data.local.db.dao.MovieDetailsDao
import com.gmail.hostov47.androidschoolmvvm.data.local.db.dao.WatchListDao
import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.*

@Database(
    entities = [FavoriteMovie::class, WatchListMovie::class,
        MovieLocal::class, MovieDetailsLocal::class, LocalCast::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MovieDb : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = BuildConfig.APPLICATION_ID + ".db"
        const val DATABASE_VERSION = 5
        const val FAVORITE_TABLE = "favorite_movies"
        const val WATCHLIST_TABLE = "watchlist_movies"
        const val MOVIE_LOCAL_TABLE = "local_movies"
        const val MOVIE_DETAILS_TABLE = "movie_details"
        const val MOVIE_CAST_TABLE = "movie_cast"
    }

    abstract fun favoriteMoviesDao(): FavoriteMoviesDao
    abstract fun watchlistDao(): WatchListDao
    abstract fun localMovieDao(): LocalMoviesDao
    abstract fun movieDetailsDao(): MovieDetailsDao
}

enum class MoviesCategory(val value: String) {
    Popular("popular"),
    Upcoming("upcoming"),
    NowPlaying("nowPlaying")
}