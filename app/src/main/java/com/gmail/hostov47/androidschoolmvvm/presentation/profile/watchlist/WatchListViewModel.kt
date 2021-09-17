/**
 * Created by Ilia Shelkovenko on 26.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.profile.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gmail.hostov47.androidschoolmvvm.domain.interactors.WatchListInteractor
import com.gmail.hostov47.androidschoolmvvm.extensions.addTo
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview
import com.gmail.hostov47.androidschoolmvvm.presentation.base.BaseViewModel
import com.gmail.hostov47.androidschoolmvvm.utils.schedulers.SchedulersProvider
import javax.inject.Inject

/**
 * Конструктор ViewModel.
 *
 * @param interactor [WatchListInteractor] интерактор для управления списком фильмов к просмотру.
 * @param schedulers [SchedulersProvider] провайдер шедулеров
 */
class WatchListViewModel(
    private val interactor: WatchListInteractor,
    private val schedulers: SchedulersProvider,
) : BaseViewModel() {

    private val _watchListLive = MutableLiveData<List<MoviePreview>>()
    val watchListLive: LiveData<List<MoviePreview>> = _watchListLive

    private val _errorLive = MutableLiveData<Throwable>()
    val errorLive: LiveData<Throwable> = _errorLive

    fun getWatchList() {
        interactor.getWatchList()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .subscribe(
                { list -> _watchListLive.value = list },
                { throwable -> _errorLive.value = throwable }
            ).addTo(compositeDisposable)
    }

}

class WatchListViewModelFactory @Inject constructor(
    private val interactor: WatchListInteractor,
    private val schedulers: SchedulersProvider,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WatchListViewModel(interactor, schedulers) as T
    }
}