package com.gmail.hostov47.androidschoolmvvm.domain.interactors

import com.gmail.hostov47.androidschoolmvvm.data.mappers.FromMovieLocalToMovieDomainMapper
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
    fun getPopularMovies(forceLoad: Boolean, caching: Boolean): List<MovieDomain> =
        moviesRepository.getPopularMovies(forceLoad, caching)
            .map { localMovie ->
                FromMovieLocalToMovieDomainMapper.map(localMovie)
            }

    @Throws(IOException::class, IllegalStateException::class)
    fun getUpcomingMovies(forceLoad: Boolean, caching: Boolean): List<MovieDomain> =
        moviesRepository.getUpcomingMovies(forceLoad, caching)
            .map { localMovie ->
                FromMovieLocalToMovieDomainMapper.map(localMovie)
            }

    @Throws(IOException::class, IllegalStateException::class)
    fun getNowPlayingMovies(forceLoad: Boolean, caching: Boolean): List<MovieDomain> =
        moviesRepository.getNowPlayingMovies(forceLoad, caching)
            .map { localMovie ->
                FromMovieLocalToMovieDomainMapper.map(localMovie)
            }
}