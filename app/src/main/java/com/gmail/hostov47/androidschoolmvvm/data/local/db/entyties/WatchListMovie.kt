/**
 * Created by Ilia Shelkovenko on 26.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.hostov47.androidschoolmvvm.data.local.db.MovieDb.Companion.WATCHLIST_TABLE

@Entity(tableName = WATCHLIST_TABLE)
data class WatchListMovie(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val movieId: Int,
    val title: String,
    var poster: String
)