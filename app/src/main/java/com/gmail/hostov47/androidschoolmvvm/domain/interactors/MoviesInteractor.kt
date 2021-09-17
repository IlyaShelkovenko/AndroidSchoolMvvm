package com.gmail.hostov47.androidschoolmvvm.domain.interactors

import com.gmail.hostov47.androidschoolmvvm.data.local.db.entyties.MovieLocal
import com.gmail.hostov47.androidschoolmvvm.data.mappers.FromMovieLocalToMovieDomainMapper
import com.gmail.hostov47.androidschoolmvvm.data.repository.home.MoviesRepository
import com.gmail.hostov47.androidschoolmvvm.models.domain.MovieDomain
import java.io.IOException
import javax.inject.Inject


/**
 * Интерактор для взаимодействия с фильмами
 *
 * @author Shelkovenko Ilya on 2021-08-04
 */
class MoviesInteractor @Inject constructor(private val moviesRepository: MoviesRepository) {

    /**
     * Метод, получающий список популярных фильмов.
     *
     * @param forceLoad флаг для получения данных с сети.
     * @param caching флаг для кеширования данных в БД
     * @return список популярных фильмов [MovieDomain]
     * @throws [IOException] или [IllegalStateException] в случае неудачного чтения
     */
    @Throws(IOException::class, IllegalStateException::class)
    fun getPopularMovies(forceLoad: Boolean, caching: Boolean): List<MovieDomain> =
        moviesRepository.getPopularMovies(forceLoad, caching)
            .map { localMovie ->
                FromMovieLocalToMovieDomainMapper.map(localMovie)
            }

    /**
     * Метод, получающий список новых фильмов.
     *
     * @param forceLoad флаг для получения данных с сети.
     * @param caching флаг для кеширования данных в БД.
     * @return список новых фильмов [MovieDomain]
     * @throws [IOException] или [IllegalStateException] в случае неудачного чтения
     */
    @Throws(IOException::class, IllegalStateException::class)
    fun getUpcomingMovies(forceLoad: Boolean, caching: Boolean): List<MovieDomain> =
        moviesRepository.getUpcomingMovies(forceLoad, caching)
            .map { localMovie ->
                FromMovieLocalToMovieDomainMapper.map(localMovie)
            }

    /**
     * Метод, получающий список рекомендуемых фильмов.
     *
     * @param forceLoad флаг для получения данных с сети.
     * @param caching флаг для кеширования данных в БД.
     * @return список рекомендуемых фильмов [MovieLocal]
     * @throws [IOException] или [IllegalStateException] в случае неудачного чтения
     */
    @Throws(IOException::class, IllegalStateException::class)
    fun getNowPlayingMovies(forceLoad: Boolean, caching: Boolean): List<MovieDomain> {
        return moviesRepository.getNowPlayingMovies(forceLoad, caching)
            .map { localMovie ->
                FromMovieLocalToMovieDomainMapper.map(localMovie)
            }
    }

}