/**
 * Created by Ilia Shelkovenko on 08.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.repository.detail

import com.gmail.hostov47.androidschoolmvvm.data.local.store.MovieStore
import com.gmail.hostov47.androidschoolmvvm.data.api.ImdbApi
import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.MovieDetailsLocal
import com.gmail.hostov47.androidschoolmvvm.data.local.store.MovieStoreNew
import com.gmail.hostov47.androidschoolmvvm.data.mappers.FromDetailsResponseToDetailsLocal
import com.gmail.hostov47.androidschoolmvvm.models.data.local.MovieCastLocal
import javax.inject.Inject
import javax.inject.Named


/**
 * Релазизация [DetailsRepository]
 */
class DetailsRepositoryImpl @Inject constructor(
    private val api: ImdbApi,
    @Named("PrefsCash") private val movieStore: MovieStore,
    @Named("DbCash") private val movieStoreDb: MovieStoreNew
) :
    DetailsRepository {

    override fun getMovieDetails(movieId: Int, forceLoad: Boolean): MovieDetailsLocal {
        var movieDetails: MovieDetailsLocal? = null
        if (!forceLoad)
            movieDetails = movieStoreDb.getMovieDetails(movieId)
        if (movieDetails == null) {
            val response = api.getMovieDetails(movieId)
            movieDetails = FromDetailsResponseToDetailsLocal.map(response)
            movieStoreDb.saveMovieDetails(movieDetails)
        }
        return movieDetails
    }

    override fun getMovieCredits(movieId: Int, forceLoad: Boolean): List<MovieCastLocal> {
        var movieCast: List<MovieCastLocal> = emptyList()
        if (!forceLoad)
            movieCast = movieStoreDb.getMovieCredits(movieId)
        if (movieCast.isEmpty()) {
            val response = api.getMovieCredits(movieId)
            movieCast = response.cast.map {
                MovieCastLocal(it.id, it.name, it.profilePath)
            }
            movieStoreDb.saveMovieCredits(movieId, movieCast)
        }
        return movieCast
    }
}