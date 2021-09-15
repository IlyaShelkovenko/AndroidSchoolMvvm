/**
 * Created by Ilia Shelkovenko on 13.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.local.db

import androidx.room.TypeConverter
import com.gmail.hostov47.androidschoolmvvm.models.data.local.MovieCastLocal
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject


class Converters{
    private val json = Json {
        ignoreUnknownKeys = true
    }

    @TypeConverter
    fun fromListInts(list: List<Int>): String {
        return list.joinToString(",")
    }
    @TypeConverter
    fun fromIntString(string: String): List<Int> {
        return string.split(",").map { it.toInt() }
    }

    @TypeConverter
    fun fromListCast(movieCast: List<MovieCastLocal>): String {
        return json.encodeToString(movieCast)
    }

    @TypeConverter
    fun fromStringToListCast(jsonString: String): List<MovieCastLocal>{
        return json.decodeFromString(jsonString)
    }

}