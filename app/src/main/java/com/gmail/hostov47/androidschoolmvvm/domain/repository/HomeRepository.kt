/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */
package com.gmail.hostov47.androidschoolmvvm.domain.repository

import com.gmail.hostov47.androidschoolmvvm.BuildConfig
import com.gmail.hostov47.androidschoolmvvm.data.network.responses.MoviesResponse
import io.reactivex.Single

interface HomeRepository {
    /**
     * Метод, получающий список рекомандованных фильмов.
     */
    fun getUpcomingMovies() : Single<MoviesResponse>

    /**
     * Метод, получающий список фильмов-новинок.
     */
    fun getNowPlayingMovies(): Single<MoviesResponse>

    /**
     * Метод, получающий список популярных фильмов.
     */
    fun getPopularMovies(): Single<MoviesResponse>
}