/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */
package com.gmail.hostov47.androidschoolmvvm.domain.api

import com.gmail.hostov47.androidschoolmvvm.BuildConfig
import com.gmail.hostov47.androidschoolmvvm.data.network.responses.MovieCreditsResponse
import com.gmail.hostov47.androidschoolmvvm.data.network.responses.MovieDetailResponse
import com.gmail.hostov47.androidschoolmvvm.data.network.responses.MoviesResponse
import io.reactivex.Single

interface ImdbApi {
    val API_KEY: String get() = BuildConfig.THE_MOVIE_DATABASE_API
    val BASE_URL: String get() = BuildConfig.BASE_URL

    fun getUpcomingMovies() : Single<MoviesResponse>
    fun getNowPlayingMovies(): Single<MoviesResponse>
    fun getPopularMovies(): Single<MoviesResponse>
    fun getMovieDetails(movieId: Int): Single<MovieDetailResponse>
    fun getMovieCredits(movieId: Int): Single<MovieCreditsResponse>
}