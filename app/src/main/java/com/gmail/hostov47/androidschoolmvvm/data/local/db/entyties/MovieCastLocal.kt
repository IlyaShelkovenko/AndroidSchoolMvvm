/**
 * Created by Ilia Shelkovenko on 13.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.hostov47.androidschoolmvvm.data.local.db.MovieDb.Companion.MOVIE_CAST_TABLE
import com.gmail.hostov47.androidschoolmvvm.models.data.local.MovieCastLocal

/*@Entity(tableName = MOVIE_CAST_TABLE)
data class MovieCastLocal(
    @PrimaryKey
    val id: Long,
    val name: String,
    @ColumnInfo(name = "profile_path")
    val profilePath: String?
)*/

@Entity(tableName = MOVIE_CAST_TABLE)
data class LocalCast(
    @PrimaryKey
    val movieId: Int,
    val cast: List<MovieCastLocal>
)

