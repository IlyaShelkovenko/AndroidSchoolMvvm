/**
 * Created by Ilia Shelkovenko on 26.08.2021.
 */
package com.gmail.hostov47.androidschoolmvvm.data.repository.profile

import com.gmail.hostov47.androidschoolmvvm.models.presentation.MovieDetailsWithCast
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview
import io.reactivex.Single

/**
 * Репозиторий со списком понравившихся фильмов
 *
 * @author Shelkovenko Ilya
 */
interface FavoriteMoviesRepository {

    /**
     * Метод, проверяющий, есть ли фильм в хранилище понравившихся фильмов.
     *
     * @return [Single] true, если фильм есть в хранилище, false если его нет.
     */
    fun isFavorite(movieId: Int): Single<Boolean>

    /**
     * Метод, добавляющий фильм в понравившиеся.
     *
     * @param movie [MovieDetailsWithCast] фильм, который нужно добавить в хранилище
     */
    fun addToFavorite(movie: MovieDetailsWithCast)

    /**
     * Метод, удаляющий фильм из понравившихся.
     *
     * @param movie [MovieDetailsWithCast] фильм, который нужно удалить из хранилища
     */
    fun removeFromFavorite(movie: MovieDetailsWithCast)

    /**
     * Метод, получающий список понравившихся фильмов.
     *
     * @return [Single] список понравившихся фильмов [MoviePreview]
     */
    fun getFavoriteMovies(): Single<List<MoviePreview>>
}