/**
 * Created by Ilia Shelkovenko on 08.08.2021.
 */
package com.gmail.hostov47.androidschoolmvvm.domain.repository

import com.gmail.hostov47.androidschoolmvvm.data.network.responses.MovieCreditsResponse
import com.gmail.hostov47.androidschoolmvvm.data.network.responses.MovieDetailResponse
import io.reactivex.Single

interface DetailsRepository {
    /**
     * Метод, получающий детальную информацию о фильме.
     *
     * @param movieId идентификатор фильма.
     */
    fun getMovieDetails(movieId: Int): Single<MovieDetailResponse>

    /**
     * Метод, получающий информацию о команде фильма.
     *
     * @param movieId идентификатор фильма.
     */
    fun getMovieCredits(movieId: Int): Single<MovieCreditsResponse>
}