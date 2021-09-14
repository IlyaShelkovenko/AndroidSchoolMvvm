/**
 * Created by Ilia Shelkovenko on 13.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.hostov47.androidschoolmvvm.data.local.db.MovieDb.Companion.MOVIE_LOCAL_TABLE

@Entity(tableName = MOVIE_LOCAL_TABLE)
data class MovieLocal(
    @PrimaryKey
    var movieId: Int,
    @ColumnInfo(name = "is_adult")
    val isAdult: Boolean,
    val overview: String,
    @ColumnInfo(name = "release_date")
    var releaseDate: String?,
    @ColumnInfo(name = "genre_ids")
    var genreIds: List<Int>?,
    @ColumnInfo(name = "original_title")
    var originalTitle: String?,
    @ColumnInfo(name = "original_language")
    var originalLanguage: String?,
    var title: String,
    @ColumnInfo(name = "backdrop_path")
    var backdropPath: String?,
    var popularity: Double,
    @ColumnInfo(name = "vote_count")
    var voteCount: Int,
    var video: Boolean,
    @ColumnInfo(name = "vote_average")
    var voteAverage: Double?,
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,
    var isPopular: Boolean = false,
    var isUpcoming: Boolean = false,
    var isNowPlaying: Boolean = false,
)