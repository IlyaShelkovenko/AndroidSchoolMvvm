/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.home

import android.content.SharedPreferences
import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gmail.hostov47.androidschoolmvvm.R
import com.gmail.hostov47.androidschoolmvvm.domain.interactors.MoviesInteractor
import com.gmail.hostov47.androidschoolmvvm.extensions.addTo
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview
import com.gmail.hostov47.androidschoolmvvm.presentation.base.BaseViewModel
import com.gmail.hostov47.androidschoolmvvm.utils.SchedulersProvider.SchedulersProvider
import com.gmail.hostov47.androidschoolmvvm.utils.SingleLiveEvent
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

/**
 * Конструктор ViewModel.
 *
 * @param interactor [MoviesInteractor] интерактор для получения фильмов.
 * @param schedulers [SchedulersProvider] провайдер шедулеров
 * @param prefs [SharedPreferences] преференсы для кеширования
 * @param resources [Resources] объект для получения ресурсов
 */
class HomeViewModel(
    private val interactor: MoviesInteractor,
    private val schedulers: SchedulersProvider,
    private val prefs: SharedPreferences,
    private val resources: Resources
) : BaseViewModel() {

    private var caching: Boolean = true
        get() {
            field = prefs.getBoolean(resources.getString(R.string.pref_key_caching), true)
            return field
        }
    private val _popularMovies = MutableLiveData<List<MoviePreview>>()
    val popularMovies: LiveData<List<MoviePreview>> = _popularMovies

    private val _upcomingMovies = MutableLiveData<List<MoviePreview>>()
    val upcomingMovies: LiveData<List<MoviePreview>> = _upcomingMovies

    private val _nowPlayingMovies = MutableLiveData<List<MoviePreview>>()
    val nowPlayingMovies: LiveData<List<MoviePreview>> = _nowPlayingMovies

    /*private val _movies = MutableLiveData<List<MoviePreview>>()
    val movies: LiveData<List<MoviePreview>> = _movies*/

    private val _showLoading = MutableLiveData<Boolean>(true)
    val showLoading: LiveData<Boolean> = _showLoading

    private val _errors = SingleLiveEvent<Throwable>()
    val errors: LiveData<Throwable> = _errors

    init {
        loadUpcomingMovies(caching = caching)
        loadPopularMovies(caching = caching)
        loadNowPlayingMovies(caching = caching)
    }

    private fun loadUpcomingMovies(forceLoad: Boolean = false, caching: Boolean = true) {
        Single.fromCallable { interactor.getPopularMovies(forceLoad, caching) }
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
            .delay(2, TimeUnit.SECONDS)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .doFinally { _showLoading.value = false }
            .doOnSubscribe { _showLoading.value = true }
            .subscribe(_upcomingMovies::setValue, _errors::setValue)
            .addTo(compositeDisposable)
    }

    private fun loadPopularMovies(forceLoad: Boolean = false, caching: Boolean = true) {
        Single.fromCallable { interactor.getUpcomingMovies(forceLoad, caching) }
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
            .delay(2, TimeUnit.SECONDS)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .doFinally { _showLoading.value = false }
            .doOnSubscribe { _showLoading.value = true }
            .subscribe(_popularMovies::setValue, _errors::setValue)
            .addTo(compositeDisposable)
    }

    private fun loadNowPlayingMovies(forceLoad: Boolean = false, caching: Boolean = true) {
            Single.fromCallable { interactor.getNowPlayingMovies(forceLoad, caching) }
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
            .delay(2, TimeUnit.SECONDS)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .doFinally { _showLoading.value = false }
            .doOnSubscribe { _showLoading.value = true }
            .subscribe(_nowPlayingMovies::setValue, _errors::setValue)
            .addTo(compositeDisposable)
    }

    fun onRefreshLayout() {
        loadUpcomingMovies(true, caching)
        loadPopularMovies(true, caching)
        loadNowPlayingMovies(true, caching)
    }
}

class HomeViewModelFactory @Inject constructor(
    private val interactor: MoviesInteractor,
    private val schedulers: SchedulersProvider,
    @Named("DefaultPrefs")
    private val prefs: SharedPreferences,
    private val resources: Resources
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(interactor, schedulers, prefs, resources) as T
    }
}