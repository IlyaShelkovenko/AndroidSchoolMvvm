/**
 * Created by Ilia Shelkovenko on 08.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.repository.search

import com.gmail.hostov47.androidschoolmvvm.data.api.ImdbApi
import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.MovieLocal
import com.gmail.hostov47.androidschoolmvvm.data.mappers.FromMovieToMovieLocalMapper
import javax.inject.Inject


/**
 * Релазизация [SearchRepository]
 */
class SearchRepositoryImpl @Inject constructor(private val moviesApi: ImdbApi) : SearchRepository {

    override fun searchMovies(query: String): List<MovieLocal> {
        val movies =  moviesApi.searchMovies(query).results?.map { movie ->
            FromMovieToMovieLocalMapper.map(movie)
        }
        return movies ?: listOf()
    }

}