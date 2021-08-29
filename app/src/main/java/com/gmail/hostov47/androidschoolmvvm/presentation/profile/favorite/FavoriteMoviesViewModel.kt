/**
 * Created by Ilia Shelkovenko on 26.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.profile.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gmail.hostov47.androidschoolmvvm.domain.interactors.FavoriteMoviesInteractor
import com.gmail.hostov47.androidschoolmvvm.extensions.addTo
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview
import com.gmail.hostov47.androidschoolmvvm.presentation.base.BaseViewModel
import com.gmail.hostov47.androidschoolmvvm.utils.SchedulersProvider.SchedulersProvider
import javax.inject.Inject


/**
 * Конструктор ViewModel.
 *
 * @param interactor [FavoriteMoviesInteractor] интерактор для управления списком понравившихся фильмов.
 * @param schedulers [SchedulersProvider] провайдер шедулеров
 */
class FavoriteViewModel(
    private val interactor: FavoriteMoviesInteractor,
    private val schedulers: SchedulersProvider,
): BaseViewModel() {
    private val _favoriteMoviesLive  = MutableLiveData<List<MoviePreview>>()
    val favoriteMoviesLive : LiveData<List<MoviePreview>> = _favoriteMoviesLive

    private val _errorLive  = MutableLiveData<Throwable>()
    val errorLive : LiveData<Throwable> = _errorLive

    init {
        getFavoriteMovies()
    }

    private fun getFavoriteMovies() {
        interactor.getFavoriteMovies()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .subscribe({
                _favoriteMoviesLive.value = it
            }, { throwable ->
                _errorLive.value = throwable
            })
            .addTo(compositeDisposable)
    }

}

class FavoriteViewModelFactory @Inject constructor(
    private val interactor: FavoriteMoviesInteractor,
    private val schedulers: SchedulersProvider,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoriteViewModel(interactor, schedulers) as T
    }
}