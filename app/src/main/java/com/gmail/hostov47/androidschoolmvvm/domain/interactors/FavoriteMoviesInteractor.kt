/**
 * Created by Ilia Shelkovenko on 26.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.domain.interactors

import com.gmail.hostov47.androidschoolmvvm.data.repository.profile.FavoriteMoviesRepository
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MovieDetailsWithCast
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview
import io.reactivex.Single
import javax.inject.Inject

/**
 * Интерактор для взаимодействия со списком понравившихся фильмов
 *
 * @author Shelkovenko Ilya on 2021-08-26
 */
class FavoriteMoviesInteractor @Inject constructor(private val repository: FavoriteMoviesRepository) {

    /**
     * Метод, получающий список понравившихся фильмов.
     *
     * @return [Single] список понравившихся фильмов [MoviePreview]
     */
    fun getFavoriteMovies() : Single<List<MoviePreview>> {
        return repository.getFavoriteMovies()
    }
}