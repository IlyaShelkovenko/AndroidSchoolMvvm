/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gmail.hostov47.androidschoolmvvm.domain.interactors.MoviesInteractor
import com.gmail.hostov47.androidschoolmvvm.extensions.addTo
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview
import com.gmail.hostov47.androidschoolmvvm.presentation.base.BaseViewModel
import com.gmail.hostov47.androidschoolmvvm.utils.SchedulersProvider.SchedulersProvider
import io.reactivex.Single

/**
 * Конструктор ViewModel.
 *
 * @param interactor [MoviesInteractor] интерактор для получения фильмов.
 * @param schedulers [SchedulersProvider] провайдер шедулеров
 */
class HomeViewModel(
    private val interactor: MoviesInteractor,
    private val schedulers: SchedulersProvider
) : BaseViewModel() {

    private val _movies = MutableLiveData<List<MoviePreview>>()
    val movies: LiveData<List<MoviePreview>> = _movies

    private val _showLoading = MutableLiveData<Boolean>(true)
    val showLoading: LiveData<Boolean> = _showLoading

    private val _errors = MutableLiveData<Throwable>()
    val errors: LiveData<Throwable> = _errors

    init {
        loadMovies()
    }

    private fun loadMovies(forceLoad: Boolean = false) {
        Single.fromCallable { interactor.getPopularMovies(forceLoad) }
            .map {
                it.map { movie ->
                    MoviePreview(
                        movieId = movie.id,
                        title = movie.title,
                        poster = movie.fullPosterPath,
                        rating = movie.rating
                    )
                }
            }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .doFinally { _showLoading.value = false }
            .doOnSubscribe { _showLoading.value = true }
            .subscribe(_movies::setValue, _errors::setValue)
            .addTo(compositeDisposable)
    }

    fun onRefreshLayout() {
        loadMovies(true)
    }
}

class HomeViewModelFactory(
    private val interactor: MoviesInteractor,
    private val schedulers: SchedulersProvider
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(interactor, schedulers) as T
    }
}