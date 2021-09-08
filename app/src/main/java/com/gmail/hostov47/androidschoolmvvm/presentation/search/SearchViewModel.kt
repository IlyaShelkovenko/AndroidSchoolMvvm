/**
 * Created by Ilia Shelkovenko on 08.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gmail.hostov47.androidschoolmvvm.domain.interactors.SearchMoviesInteractor
import com.gmail.hostov47.androidschoolmvvm.extensions.addTo
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview
import com.gmail.hostov47.androidschoolmvvm.presentation.base.BaseViewModel
import com.gmail.hostov47.androidschoolmvvm.utils.SchedulersProvider.SchedulersProvider
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
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

    fun handleSearch(searchObservable: Observable<String>) {
        searchObservable
            .subscribeOn(schedulers.io())
            .debounce(DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS)
            .map { it.trim() }
            .filter { it.length > SearchBar.MIN_LENGTH }
            .doOnNext { Log.d("SearchViewModel","next: $it") }
            .switchMap { searchMovies(it) }
            .observeOn(schedulers.ui())
            .subscribe({}, {})
            .addTo(compositeDisposable)
    }

    private fun searchMovies(query: String): Observable<List<MoviePreview>> {
        return Observable.fromCallable { interactor.searchMovies(query) }.map { list ->
            list.map { movie ->
                MoviePreview(
                    movieId = movie.id,
                    title = movie.title,
                    poster = movie.fullPosterPath,
                    rating = movie.rating
                )
            }
        }
    }

    companion object {
        const val DEBOUNCE_TIMEOUT = 1500L
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