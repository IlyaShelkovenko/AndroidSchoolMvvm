/**
 * Created by Ilia Shelkovenko on 08.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gmail.hostov47.androidschoolmvvm.domain.interactors.SearchMoviesInteractor
import com.gmail.hostov47.androidschoolmvvm.extensions.addTo
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview
import com.gmail.hostov47.androidschoolmvvm.presentation.base.BaseViewModel
import com.gmail.hostov47.androidschoolmvvm.utils.schedulers.SchedulersProvider
import com.gmail.hostov47.androidschoolmvvm.utils.SingleLiveEvent
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * Конструктор ViewModel.
 *
 * @param interactor [SearchMoviesInteractor] интерактор для поиска фильмов.
 * @param schedulers [SchedulersProvider] провайдер шедулеров
 */
class SearchViewModel(
    private val interactor: SearchMoviesInteractor,
    private val schedulers: SchedulersProvider
) : BaseViewModel() {

    private val _searchMovies = MutableLiveData<List<MoviePreview>>()
    val searchMovies: LiveData<List<MoviePreview>> = _searchMovies

    private val _errors = SingleLiveEvent<Throwable>()
    val errors: LiveData<Throwable> = _errors

    private val _isSubscribed = MutableLiveData<Boolean>(true)
    val isSubscribed: LiveData<Boolean> = _isSubscribed

    /**
     * Метод, обработывающий запросы пользователя по поиску фильмов.
     *
     * @param searchObservable stream, в который поступают поисковые запросы.
     */
    fun handleSearch(searchObservable: Observable<String>) {
        searchObservable
            .subscribeOn(schedulers.io())
            .debounce(DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS)
            .map { it.trim() }
            .filter { it.length > MIN_LENGTH }
            .switchMap { searchMovies(it) }
            .observeOn(schedulers.ui())
            .subscribe(
                { list -> _searchMovies.value = list },
                { error ->
                    _errors.value = error
                    _isSubscribed.value = false
                })
            .addTo(compositeDisposable)
    }

    private fun searchMovies(query: String): Observable<List<MoviePreview>> {
        return Observable.fromCallable { interactor.searchMovies(query) }.map { list ->
            list.map { movie ->
                MoviePreview(
                    movieId = movie.id,
                    title = movie.title,
                    poster = movie.posterPath ?: "",
                    rating = movie.rating
                )
            }
        }
    }

    companion object {
        const val DEBOUNCE_TIMEOUT = 1500L
        const val MIN_LENGTH = 2
    }
}

class SearchViewModelFactory @Inject constructor(
    private val interactor: SearchMoviesInteractor,
    private val schedulers: SchedulersProvider,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(interactor, schedulers) as T
    }
}