/**
 * Created by Ilia Shelkovenko on 26.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.hostov47.androidschoolmvvm.data.local.db.MovieDb.Companion.FAVORITE_TABLE

@Entity(tableName = FAVORITE_TABLE)
data class FavoriteMovie(
    @PrimaryKey
    val movieId: Int,
    val title: String,
    var poster: String
)