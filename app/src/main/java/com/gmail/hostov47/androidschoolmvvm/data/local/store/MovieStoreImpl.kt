package com.gmail.hostov47.androidschoolmvvm.data.local.store

import android.content.SharedPreferences
import com.gmail.hostov47.androidschoolmvvm.data.local.db.dao.LocalMoviesDao
import com.gmail.hostov47.androidschoolmvvm.data.local.store.MovieStore
import com.gmail.hostov47.androidschoolmvvm.models.data.local.MovieCastLocal
import com.gmail.hostov47.androidschoolmvvm.models.data.local.MovieDetailsLocal
import com.gmail.hostov47.androidschoolmvvm.models.data.local.MovieLocal
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Named

private const val MOVIES_KEY = "MOVIES_KEY"
private const val POPULAR_MOVIES_KEY = "POPULAR_MOVIES_KEY"
private const val UPCOMING_MOVIES_KEY = "UPCOMING_MOVIES_KEY"
private const val NOW_PLAYING_MOVIES_KEY = "NOW_PLAYING_MOVIES_KEY"

class MovieStoreImpl @Inject constructor(
    @Named("Caching")
    private val preferences: SharedPreferences,
    private val json: Json,
) : MovieStore {

    override fun savePopularMovies(movies: List<MovieLocal>) {
        if (movies.isNotEmpty())
            preferences.edit()
                .putString(POPULAR_MOVIES_KEY, json.encodeToString(movies)).apply()
    }

    override fun getPopularMovies(): List<MovieLocal>? {
        return preferences.getString(POPULAR_MOVIES_KEY, null)?.let(json::decodeFromString)
    }

    override fun saveUpcomingMovies(movies: List<MovieLocal>) {
        if (movies.isNotEmpty())
            preferences.edit()
                .putString(UPCOMING_MOVIES_KEY, json.encodeToString(movies)).apply()
    }

    override fun getUpcomingMovies(): List<MovieLocal>? {
        return preferences.getString(UPCOMING_MOVIES_KEY, null)?.let(json::decodeFromString)
    }

    override fun saveNowPlayingMovies(movies: List<MovieLocal>) {
        if (movies.isNotEmpty())
            preferences.edit()
                .putString(NOW_PLAYING_MOVIES_KEY, json.encodeToString(movies)).apply()
    }

    override fun getNowPlayingMovies(): List<MovieLocal>? {
        return preferences.getString(NOW_PLAYING_MOVIES_KEY, null)?.let(json::decodeFromString)
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