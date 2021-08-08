/**
 * Created by Ilia Shelkovenko on 08.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.repository

import com.gmail.hostov47.androidschoolmvvm.data.network.responses.MovieCreditsResponse
import com.gmail.hostov47.androidschoolmvvm.data.network.responses.MovieDetailResponse
import com.gmail.hostov47.androidschoolmvvm.domain.api.ImdbApi
import com.gmail.hostov47.androidschoolmvvm.domain.repository.DetailsRepository
import io.reactivex.Single

class DetailsRepositoryImpl(private val api: ImdbApi) : DetailsRepository{

    override fun getMovieDetails(movieId: Int): Single<MovieDetailResponse> = api.getMovieDetails(movieId)

    override fun getMovieCredits(movieId: Int): Single<MovieCreditsResponse>
        = api.getMovieCredits(movieId)

}