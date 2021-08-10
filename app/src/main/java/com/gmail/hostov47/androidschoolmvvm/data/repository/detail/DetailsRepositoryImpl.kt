/**
 * Created by Ilia Shelkovenko on 08.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.repository.detail

import com.gmail.hostov47.androidschoolmvvm.data.local.MovieStore
import com.gmail.hostov47.androidschoolmvvm.data.api.ImdbApi
import com.gmail.hostov47.androidschoolmvvm.models.data.local.MovieCastLocal
import com.gmail.hostov47.androidschoolmvvm.models.data.local.MovieDetailsLocal


/**
 * Релазизация [DetailsRepository]
 */
class DetailsRepositoryImpl(private val api: ImdbApi, private val movieStore: MovieStore) :
    DetailsRepository {

    override fun getMovieDetails(movieId: Int): MovieDetailsLocal {
        var movieDetails = movieStore.getMovieDetails(movieId)
        if (movieDetails == null) {
            val response = api.getMovieDetails(movieId)
            val productionCompanies =
                if(response.productionCompanies == null || response.productionCompanies.isEmpty()) "-"
                else response.productionCompanies.joinToString(", ") { it.name }
            val genres = if(response.genres == null || response.genres.isEmpty()) "-"
            else response.genres.joinToString(", ") { it.name }
            movieDetails = MovieDetailsLocal(
                response.id,
                response.posterPath,
                response.title,
                response.voteAverage,
                response.overview,
                productionCompanies,
                genres,
                response.releaseDate,
            )
            movieStore.saveMovieDetails(movieDetails)
        }
        return movieDetails
    }

    override fun getMovieCredits(movieId: Int): List<MovieCastLocal> {
        var movieCast = movieStore.getMovieCredits(movieId)
        if(movieCast == null){
            val response = api.getMovieCredits(movieId)
            movieCast = response.cast.map {
                MovieCastLocal(it.id, it.name, it.profilePath)
            }
        }
        return movieCast
    }

}