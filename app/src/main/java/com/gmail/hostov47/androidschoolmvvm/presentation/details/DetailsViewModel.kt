/**
 * Created by Ilia Shelkovenko on 08.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gmail.hostov47.androidschoolmvvm.domain.interactors.MovieDetailsInteractor
import com.gmail.hostov47.androidschoolmvvm.domain.interactors.MoviesInteractor
import com.gmail.hostov47.androidschoolmvvm.extensions.addTo
import com.gmail.hostov47.androidschoolmvvm.extensions.toMovieDetailsWithCast
import com.gmail.hostov47.androidschoolmvvm.models.domain.MovieDetailsDomain
import com.gmail.hostov47.androidschoolmvvm.models.presentation.Cast
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MovieDetailsWithCast
import com.gmail.hostov47.androidschoolmvvm.presentation.base.BaseViewModel
import com.gmail.hostov47.androidschoolmvvm.utils.SchedulersProvider.SchedulersProvider
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Конструктор ViewModel.
 *
 * @param interactor [MoviesInteractor] интерактор для получения фильмов.
 * @param schedulers [SchedulersProvider] провайдер шедулеров
 */
class DetailsViewModel(
    private val interactor: MovieDetailsInteractor,
    private val schedulers: SchedulersProvider
) : BaseViewModel() {

    private val _detailsWithCast = MutableLiveData<Result>(Result.Loading)
    val detailsWithCast: LiveData<Result> = _detailsWithCast

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val _isInWatchList = MutableLiveData<Boolean>()
    val isInWatchList: LiveData<Boolean> = _isInWatchList


    /**
     * Метод, получающий детальную информацию о фильме вместе со списком актеров и команды.
     *
     * @param movieId идентификатор фильма.
     */
    fun getMovieDetails(movieId: Int, forceLoad: Boolean = false) {
        val movieDetails = Single.fromCallable { interactor.getMovieDetails(movieId, forceLoad) }

        val movieCast = Single.fromCallable {
            interactor.getMovieCast(movieId, forceLoad)
                .map { Cast(it.id, it.name, it.fullPosterPath) }
        }

        val zipper =
            BiFunction<MovieDetailsDomain, List<Cast>, MovieDetailsWithCast> { detail, cast ->
                detail.toMovieDetailsWithCast(cast)
            }
        Single.zip(movieDetails, movieCast, zipper)
            .delay(1, TimeUnit.SECONDS)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .doOnSubscribe { _detailsWithCast.value = Result.Loading }
            .subscribe(
                { movie ->
                    _detailsWithCast.value = Result.Success(movie)
                    requestMovieIsFavorite(movie)
                    requestMovieIsInWatchList(movie)
                },
                { e -> _detailsWithCast.value = Result.Error(e) }
            ).addTo(compositeDisposable)
    }

    /**
     * Метод, добавляющий фильм в список понравившихся.
     *
     * @param movie [MovieDetailsWithCast] фильм, который нужно добавить в список.
     */
    fun addToFavorite(movie: MovieDetailsWithCast) {
        Completable.fromAction { interactor.addToFavorite(movie) }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .subscribe {
                _isFavorite.value = true
            }.addTo(compositeDisposable)
    }

    /**
     * Метод, удаляющий фильм из списка понравившихся.
     *
     * @param movie [MovieDetailsWithCast] фильм, который нужно удалить из списка.
     */
    fun removeFromFavorite(movie: MovieDetailsWithCast) {
        Completable.fromAction { interactor.removeFromFavorite(movie) }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .subscribe {
                _isFavorite.value = false
            }.addTo(compositeDisposable)
    }

    fun onRefreshLayout(movieId: Int) {
        getMovieDetails(movieId, true)
    }

    /**
     * Метод, удаляющий фильм из списка фильмов к просмотру.
     *
     * @param movie [MovieDetailsWithCast] фильм, который нужно удалить из списка.
     */
    fun removeFromWatchList(movie: MovieDetailsWithCast) {
        Completable.fromAction { interactor.removeFromWatchWist(movie) }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .subscribe {
                _isInWatchList.value = false
            }.addTo(compositeDisposable)
    }

    /**
     * Метод, добавляющий фильм в список фильмов к просмотру.
     *
     * @param movie [MovieDetailsWithCast] фильм, который нужно добавить в список.
     */
    fun addToWatchList(movie: MovieDetailsWithCast) {
        Completable.fromAction { interactor.addToWatchList(movie) }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .subscribe {
                _isInWatchList.value = true
            }.addTo(compositeDisposable)
    }

    private fun requestMovieIsFavorite(movie: MovieDetailsWithCast) {
        interactor.isMovieFavorite(movie)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .subscribe(
                { isFavorite -> _isFavorite.value = isFavorite },
                {  }
            ).addTo(compositeDisposable)
    }

    private fun requestMovieIsInWatchList(movie: MovieDetailsWithCast) {
        interactor.isMovieInWatchList(movie)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .subscribe(
                { isWatched -> _isInWatchList.value = isWatched },
                {  }
            ).addTo(compositeDisposable)
    }
}

class DetailsViewModelFactory @Inject constructor(
    private val interactor: MovieDetailsInteractor,
    private val schedulers: SchedulersProvider
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(interactor, schedulers) as T
    }
}

sealed class Result {
    class Success(val data: MovieDetailsWithCast) : Result()
    class Error(val error: Throwable) : Result()
    object Loading : Result()
}