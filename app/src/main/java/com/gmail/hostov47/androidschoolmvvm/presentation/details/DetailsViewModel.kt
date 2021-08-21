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

    private val _showLoading = MutableLiveData<Boolean>(true)
    val showLoading: LiveData<Boolean> = _showLoading

    private val _detailsWithCast = MutableLiveData<Result>(Result.Empty)
    val detailsWithCast: LiveData<Result> = _detailsWithCast

    /**
    * Метод, получающий детальную информацию о фильме вместе со списком актеров и команды.
    *
    * @param movieId идентификатор фильма.
    */
    fun getMovieDetails(movieId: Int, forceLoad: Boolean = false) {
        val movieDetails = Single.fromCallable { interactor.getMovieDetails(movieId, forceLoad) }
        val movieCast = Single.fromCallable { interactor.getMovieCast(movieId, forceLoad)
            .map { Cast(it.id, it.name, it.fullPosterPath) } }
        val zipper =
            BiFunction<MovieDetailsDomain, List<Cast>, MovieDetailsWithCast> { detail, cast ->
                detail.toMovieDetailsWithCast(cast)
            }
        Single.zip(movieDetails, movieCast, zipper)
            .delay(2, TimeUnit.SECONDS)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .doOnSubscribe { _showLoading.value = true }
            .subscribe(
                {
                    _showLoading.value = false
                    _detailsWithCast.value = Result.Success(it)
                },
                { e -> Result.Error(e.message ?: "Unknown error") }
            ).addTo(compositeDisposable)
    }

    fun onRefreshLayout(movieId: Int) {
        getMovieDetails(movieId, true)
    }
}

class DetailsViewModelFactory @Inject constructor(private val interactor: MovieDetailsInteractor,private val schedulers: SchedulersProvider) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(interactor, schedulers) as T
    }
}

sealed class Result {
    class Success(val data: MovieDetailsWithCast) : Result()
    class Error(val message: String) : Result()
    object Empty : Result()
}