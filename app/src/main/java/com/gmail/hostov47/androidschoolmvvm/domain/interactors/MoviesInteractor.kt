package com.gmail.hostov47.androidschoolmvvm.domain.interactors

import com.gmail.hostov47.androidschoolmvvm.data.repository.home.MoviesRepository
import com.gmail.hostov47.androidschoolmvvm.models.domain.MovieDomain
import io.reactivex.Single
import java.io.IOException
import javax.inject.Inject


/**
 * Интерактор для взаимодействия с фильмами
 *
 * @author Shelkovenko Ilya on 2021-08-04
 */
class MoviesInteractor @Inject constructor(private val moviesRepository: MoviesRepository) {

    @Throws(IOException::class, IllegalStateException::class)
    fun getPopularMovies(forceLoad: Boolean): List<MovieDomain> =
        moviesRepository.getPopularMovies(forceLoad)
            .map { MovieDomain(isAdult = it.isAdult,
                overview = it.overview,
                releaseDate = it.releaseDate,
                genreIds = it.genreIds,
                id = it.id,
                originalTitle = it.originalTitle,
                originalLanguage = it.originalLanguage,
                title = it.title,
                backdropPath = it.backdropPath,
                popularity = it.popularity,
                voteCount = it.voteCount,
                video = it.video,
                voteAverage = it.voteAverage,
                posterPath = it.posterPath)
            }
}