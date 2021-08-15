package com.gmail.hostov47.androidschoolmvvm.data.local

import android.content.SharedPreferences
import com.gmail.hostov47.androidschoolmvvm.models.data.local.MovieCastLocal
import com.gmail.hostov47.androidschoolmvvm.models.data.local.MovieDetailsLocal
import com.gmail.hostov47.androidschoolmvvm.models.data.local.MovieLocal
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

private const val MOVIES_KEY = "MOVIES_KEY"

class MovieStoreImpl @Inject constructor(
    private val preferences: SharedPreferences,
    private val json: Json
) : MovieStore {

    override fun saveMovies(movies: List<MovieLocal>) {
        if (movies.isNotEmpty())
            preferences.edit()
                .putString(MOVIES_KEY, json.encodeToString(movies)).apply()
    }

    override fun getMovies(): List<MovieLocal>? {
        return preferences.getString(MOVIES_KEY, null)?.let(json::decodeFromString)
    }

    override fun getMovieDetails(movieId: Int): MovieDetailsLocal? {
        return preferences.getString("${MOVIES_KEY}_${movieId}", null)?.let(json::decodeFromString)
    }

    override fun saveMovieDetails(details: MovieDetailsLocal?) {
        details?.let {
            preferences.edit()
                .putString("${MOVIES_KEY}_${details.id}", json.encodeToString(details)).apply()
        }
    }

    override fun getMovieCredits(movieId: Int): List<MovieCastLocal>? {
        return preferences.getString("${MOVIES_KEY}_${movieId}_cast", null)?.let(json::decodeFromString)
    }

    override fun saveMovieCredits(movieId: Int, movieCast: List<MovieCastLocal>) {
        if (movieCast.isNotEmpty())
            preferences.edit()
                .putString("${MOVIES_KEY}_${movieId}_cast", json.encodeToString(movieCast)).apply()
    }
}