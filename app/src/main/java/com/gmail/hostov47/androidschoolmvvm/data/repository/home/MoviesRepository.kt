/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */
package com.gmail.hostov47.androidschoolmvvm.data.repository.home

import com.gmail.hostov47.androidschoolmvvm.models.data.local.MovieLocal


/**
 * Репозиторий со списком фильмов
 *
 * @author Shelkovenko Ilya
 */
interface MoviesRepository {
    /**
     * Метод, получающий список популярных фильмов.
     * @return список популярных фильмов [MovieLocal]
     */
    fun getPopularMovies(): List<MovieLocal>
}