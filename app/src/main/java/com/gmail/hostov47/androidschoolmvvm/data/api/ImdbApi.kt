/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */
package com.gmail.hostov47.androidschoolmvvm.data.api

import com.gmail.hostov47.androidschoolmvvm.BuildConfig
import com.gmail.hostov47.androidschoolmvvm.models.data.dto.MovieCreditsResponse
import com.gmail.hostov47.androidschoolmvvm.models.data.dto.MovieDetailResponse
import com.gmail.hostov47.androidschoolmvvm.models.data.dto.MoviesResponse
import java.io.IOException

/**
 * Интерфейс для получения данных из сети
 *
 * @author Shelkovenko Ilya
 */
interface ImdbApi {
    val API_KEY: String get() = BuildConfig.THE_MOVIE_DATABASE_API
    val BASE_URL: String get() = BuildConfig.BASE_URL

    /**
     * Получить популярные фильмы
     * @return ответ со списком фильмов [MoviesResponse]
     */
    @Throws(IOException::class, IllegalStateException::class)
    fun getPopularMovies(): MoviesResponse

    /**
     * Получить детальную информацию о фильме
     *
     * @param movieId идентификатор фильма
     * @return овтет с детальной информацией о фильме [MovieDetailResponse]
     */
    @Throws(IOException::class, IllegalStateException::class)
    fun getMovieDetails(movieId: Int): MovieDetailResponse

    /**
     * Получить информацию о команде фильма
     *
     * @param movieId идентификатор фильма
     * @return ответ со списками каста и команды фильма [MovieCreditsResponse]
     */
    @Throws(IOException::class, IllegalStateException::class)
    fun getMovieCredits(movieId: Int): MovieCreditsResponse
}