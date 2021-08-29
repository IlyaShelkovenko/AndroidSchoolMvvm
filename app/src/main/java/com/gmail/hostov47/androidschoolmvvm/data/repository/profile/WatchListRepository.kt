/**
 * Created by Ilia Shelkovenko on 29.08.2021.
 */
package com.gmail.hostov47.androidschoolmvvm.data.repository.profile

import com.gmail.hostov47.androidschoolmvvm.models.presentation.MovieDetailsWithCast
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview
import io.reactivex.Single

/**
 * Репозиторий со списком фильмов к просмотру
 *
 * @author Shelkovenko Ilya
 */
interface WatchListRepository {
    /**
     * Метод, проверяющий, есть ли фильм в хранилище фильмов к просмотру.
     *
     * @return [Single] true, если фильм есть в хранилище, false если его нет.
     */
    fun isInWatchList(movieId: Int): Single<Boolean>

    /**
     * Метод, добавляющий фильм в список фильмов к просомтру.
     *
     * @param movie [MovieDetailsWithCast] фильм, который нужно добавить в хранилище
     */
    fun addToWatchList(movie: MovieDetailsWithCast)

    /**
     * Метод, удаляющий фильм из фильмов к просмотру.
     *
     * @param movie [MovieDetailsWithCast] фильм, который нужно удалить из хранилища
     */
    fun removeFromWatchList(movie: MovieDetailsWithCast)


    /**
     * Метод, получающий список фильмов к просмотру.
     *
     * @return [Single] список фильмов к просмотру [MoviePreview]
     */
    fun getWatchList(): Single<List<MoviePreview>>
}