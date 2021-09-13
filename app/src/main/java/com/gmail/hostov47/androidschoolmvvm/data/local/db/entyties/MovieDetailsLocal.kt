/**
 * Created by Ilia Shelkovenko on 13.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.hostov47.androidschoolmvvm.data.local.db.MovieDb.Companion.MOVIE_DETAILS_TABLE

@Entity(tableName = MOVIE_DETAILS_TABLE)
data class MovieDetailsLocal(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "poster_path")
    val posterPath: String? = null,
    val title: String,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double?,
    val overview: String? = null,
    @ColumnInfo(name = "production_countries")
    val productionCompanies: String,
    val genres: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: String
)
