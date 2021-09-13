/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */
package com.gmail.hostov47.androidschoolmvvm.data.repository.home

import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.MovieLocal


/**
 * Репозиторий со списком фильмов
 *
 * @author Shelkovenko Ilya
 */
interface MoviesRepository {
    /**
     * Метод, получающий список популярных фильмов.
     *
     * @param forceLoad флаг для получения данных с сети.
     * @return список популярных фильмов [MovieLocal]
     */
    fun getPopularMovies(forceLoad: Boolean = false, caching: Boolean = true): List<MovieLocal>

    /**
     * Метод, получающий список новых фильмов.
     *
     * @param forceLoad флаг для получения данных с сети.
     * @return список новых фильмов [MovieLocal]
     */
    fun getUpcomingMovies(forceLoad: Boolean = false, caching: Boolean = true): List<MovieLocal>

    /**
     * Метод, получающий список рекомендуемых фильмов.
     *
     * @param forceLoad флаг для получения данных с сети.
     * @return список рекомендуемых фильмов [MovieLocal]
     */
    fun getNowPlayingMovies(forceLoad: Boolean = false, caching: Boolean = true): List<MovieLocal>
}