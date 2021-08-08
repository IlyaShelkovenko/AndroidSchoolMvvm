/**
 * Created by Ilia Shelkovenko on 08.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gmail.hostov47.androidschoolmvvm.data.network.responses.MovieCreditsResponse
import com.gmail.hostov47.androidschoolmvvm.data.network.responses.MovieDetailResponse
import com.gmail.hostov47.androidschoolmvvm.domain.models.MovieDetailsWithCast
import com.gmail.hostov47.androidschoolmvvm.domain.repository.DetailsRepository
import com.gmail.hostov47.androidschoolmvvm.extensions.addTo
import com.gmail.hostov47.androidschoolmvvm.extensions.toMovieDetailsWithCast
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction

/**
 * Конструктор ViewModel.
 *
 * @param repository [DetailsRepository] репозиторий для получения детальных данных о фильме.
 */
class DetailsViewModel(private val repository: DetailsRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _showLoadingLive = MutableLiveData<Boolean>()
    val showLoadingLive: LiveData<Boolean> = _showLoadingLive

    private val _detailsWithCastLive = MutableLiveData<Result>(Result.Empty)
    val detailsWithCastLive: LiveData<Result> = _detailsWithCastLive

    private val movieDetails: (Int) -> Single<MovieDetailResponse> = { movieId ->
        repository.getMovieDetails(movieId)
    }

    private val movieCredits: (Int) -> Single<MovieCreditsResponse> = { movieId ->
        repository.getMovieCredits(movieId)
    }

    /**
     * Метод, получающий детальную информацию о фильме вместе со списком актеров и команды.
     *
     * @param movieId идентификатор фильма.
     */
    fun getMovieDetails(movieId: Int) {
        val zipper =
            BiFunction<MovieDetailResponse, MovieCreditsResponse, MovieDetailsWithCast> { detailRes, castRes ->
                detailRes.toMovieDetailsWithCast(castRes.cast)
            }
        Single.zip(movieDetails(movieId), movieCredits(movieId), zipper)
            .doOnSubscribe { _showLoadingLive.value = true }
            .subscribe(
                {
                    _showLoadingLive.value = false
                    _detailsWithCastLive.value = Result.Success(it)},
                {e -> Result.Error(e.message ?: "Unknown error")}
            ).addTo(compositeDisposable)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
    }
}

class DetailsViewModelFactory(private val repository: DetailsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(repository) as T
    }
}

sealed class Result {
    class Success(val data: MovieDetailsWithCast): Result()
    class Error(val message: String): Result()
    object Empty: Result()
}