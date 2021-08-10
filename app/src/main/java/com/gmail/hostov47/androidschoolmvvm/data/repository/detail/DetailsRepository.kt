/**
 * Created by Ilia Shelkovenko on 08.08.2021.
 */
package com.gmail.hostov47.androidschoolmvvm.data.repository.detail

import com.gmail.hostov47.androidschoolmvvm.models.data.dto.MovieDetailResponse
import com.gmail.hostov47.androidschoolmvvm.models.data.local.MovieCastLocal
import com.gmail.hostov47.androidschoolmvvm.models.data.local.MovieDetailsLocal


/**
 * Репозиторий c детальной информацией и фильме
 *
 * @author Shelkovenko Ilya
 */
interface DetailsRepository {
    /**
     * Метод, получающий детальную информацию о фильме.
     *
     * @param movieId идентификатор фильма.
     * @return ответ с детальной информацией о фильме [MovieDetailResponse]
     */
    fun getMovieDetails(movieId: Int): MovieDetailsLocal

    /**
     * Метод, получающий информацию о команде фильма.
     *
     * @param movieId идентификатор фильма.
     * @return список каста фильма [MovieCastLocal]
     */
    fun getMovieCredits(movieId: Int): List<MovieCastLocal>
}