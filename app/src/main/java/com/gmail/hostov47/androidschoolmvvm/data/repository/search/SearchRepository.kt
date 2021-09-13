/**
 * Created by Ilia Shelkovenko on 08.09.2021.
 */
package com.gmail.hostov47.androidschoolmvvm.data.repository.search

import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.MovieLocal


interface SearchRepository {
    /**
     * Метод, получающий список фильмов по поисковому запросу.
     *
     * @param query строка запроса для поиска фильмов.
     * @return список найденных фильмов [MovieLocal]
     */
    fun searchMovies(query: String): List<MovieLocal>
}