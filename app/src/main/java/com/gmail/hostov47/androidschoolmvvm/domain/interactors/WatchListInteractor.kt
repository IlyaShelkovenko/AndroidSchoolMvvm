/**
 * Created by Ilia Shelkovenko on 26.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.domain.interactors

import com.gmail.hostov47.androidschoolmvvm.data.repository.profile.WatchListRepository
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview
import io.reactivex.Single
import javax.inject.Inject


/**
 * Интерактор для взаимодействия со списком фильмов к просмотру
 *
 * @author Shelkovenko Ilya on 2021-08-26
 */
class WatchListInteractor@Inject constructor(private val repository: WatchListRepository) {

    /**
     * Метод, получающий список фильмов к просмотру.
     *
     * @return [Single] список фильмов к просмотру [MoviePreview]
     */
    fun getWatchList() : Single<List<MoviePreview>> {
        return repository.getWatchList()
    }
}