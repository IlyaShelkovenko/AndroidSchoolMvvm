/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.repository

import com.gmail.hostov47.androidschoolmvvm.data.network.responses.MoviesResponse
import com.gmail.hostov47.androidschoolmvvm.domain.api.ImdbApi
import com.gmail.hostov47.androidschoolmvvm.domain.repository.HomeRepository
import io.reactivex.Single

class HomeRepositoryImpl(private val api: ImdbApi) : HomeRepository {
    override fun getUpcomingMovies(): Single<MoviesResponse>  = api.getUpcomingMovies()

    override fun getNowPlayingMovies(): Single<MoviesResponse> = api.getNowPlayingMovies()

    override fun getPopularMovies(): Single<MoviesResponse> = api.getPopularMovies()
}