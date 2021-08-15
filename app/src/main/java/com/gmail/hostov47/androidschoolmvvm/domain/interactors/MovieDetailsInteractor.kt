package com.gmail.hostov47.androidschoolmvvm.domain.interactors

import com.gmail.hostov47.androidschoolmvvm.data.repository.detail.DetailsRepository
import com.gmail.hostov47.androidschoolmvvm.models.domain.MovieCastDomain
import com.gmail.hostov47.androidschoolmvvm.models.domain.MovieDetailsDomain
import java.io.IOException
import javax.inject.Inject


/**
 * Интерактор для взаимодействия с детальной информацией о фильме
 *
 * @author Shelkovenko Ilya on 2021-08-04
 */
class MovieDetailsInteractor @Inject constructor(private val detailsRepository: DetailsRepository) {

    @Throws(IOException::class, IllegalStateException::class)
    fun getMovieDetails(movieId: Int, forceLoad: Boolean): MovieDetailsDomain {
        val localDetails = detailsRepository.getMovieDetails(movieId, forceLoad)
        return MovieDetailsDomain(
            localDetails.id,
            localDetails.posterPath,
            localDetails.title,
            localDetails.voteAverage,
            localDetails.overview,
            localDetails.productionCompanies,
            localDetails.genres,
            localDetails.releaseDate,
        )
    }

    @Throws(IOException::class, IllegalStateException::class)
    fun getMovieCast(movieId: Int, forceLoad: Boolean): List<MovieCastDomain> {
        return detailsRepository.getMovieCredits(movieId, forceLoad).map {
            MovieCastDomain(
                it.id,
                it.name,
                it.profilePath,
            )
        }
    }
}