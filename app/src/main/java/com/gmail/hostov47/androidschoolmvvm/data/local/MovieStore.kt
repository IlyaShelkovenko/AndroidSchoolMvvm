package com.gmail.hostov47.androidschoolmvvm.data.local

import com.gmail.hostov47.androidschoolmvvm.models.data.local.MovieCastLocal
import com.gmail.hostov47.androidschoolmvvm.models.data.local.MovieDetailsLocal
import com.gmail.hostov47.androidschoolmvvm.models.data.local.MovieLocal

/**
 * Хранилище фильмов и детальной информации о них
 *
 * @author Shelkovenko Ilya
 */
interface MovieStore {
    /**
     * Сохрнить фильмы
     * @param movies список фильмов [MovieLocal]
     */
    fun saveMovies(movies: List<MovieLocal>)

    /**
     * Получить фильмы
     *
     * @return список фильмов [MovieLocal], [null] если фильмов нет
     */
    fun getMovies(): List<MovieLocal>?

    /**
     * Получить детальную информацию о фильме
     *
     * @param movieId идентификатор фильма
     * @return детальная информация [MovieDetailsLocal] о фильме, [null] если информации о фильме нет
     */
    fun getMovieDetails(movieId: Int): MovieDetailsLocal?

    /**
     * Сохрнить детальную информацию о фильме
     * @param details детальная информация о фильме [MovieDetailsLocal]
     */
    fun saveMovieDetails(details: MovieDetailsLocal?)

    /**
     * Сохрнить информацию о команде фильма
     *
     * @param movieId идентификатор фильма
     * @param movieCast список каста фильма [MovieCastLocal]
     */
    fun saveMovieCredits(movieId: Int, movieCast: List<MovieCastLocal>)

    /**
     * Получить информацию о команде фильма.
     *
     * @param movieId идентификатор фильма.
     * @return список каста фильма [MovieCastLocal]
     */
    fun getMovieCredits(movieId: Int): List<MovieCastLocal>?
}