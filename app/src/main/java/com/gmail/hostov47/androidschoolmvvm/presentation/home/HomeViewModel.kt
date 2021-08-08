/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gmail.hostov47.androidschoolmvvm.R
import com.gmail.hostov47.androidschoolmvvm.data.network.responses.MoviesResponse
import com.gmail.hostov47.androidschoolmvvm.domain.repository.DetailsRepository
import com.gmail.hostov47.androidschoolmvvm.domain.repository.HomeRepository
import com.gmail.hostov47.androidschoolmvvm.extensions.addTo
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers

/**
 * Конструктор ViewModel.
 *
 * @param repository [HomeRepository] репозиторий для получения фильмов разных категорий].
 */
class HomeViewModel(private val repository: HomeRepository) : ViewModel() {
    private val disposables = CompositeDisposable()

    private val nowPlayingMovies: Single<MoviesResponse>
        get() = repository.getNowPlayingMovies()

    private val upcomingMovies: Single<MoviesResponse>
        get() = repository.getUpcomingMovies()

    private val popularMovies: Single<MoviesResponse>
        get() = repository.getPopularMovies()

    private val _moviesMapLive = MutableLiveData<MutableList<Pair<Int, MoviesResponse>>>()
    val moviesMapLive: LiveData<MutableList<Pair<Int, MoviesResponse>>> = _moviesMapLive

    private val _showLoadingLive = MutableLiveData<Boolean>(true)
    val showLoadingLive: LiveData<Boolean> = _showLoadingLive

    init {
        val zipper =
            Function3<MoviesResponse, MoviesResponse, MoviesResponse, MutableList<Pair<Int, MoviesResponse>>>
            { recommended, upcoming, popular ->
                mutableListOf<Pair<Int, MoviesResponse>>().apply {
                    add(R.string.recommended to recommended)
                    add(R.string.upcoming to upcoming)
                    add(R.string.popular to popular)
                }
            }
        Single.zip(upcomingMovies, nowPlayingMovies, popularMovies, zipper)
            .subscribe({ list ->
                _moviesMapLive.value = list
                _showLoadingLive.value = false
            }, { e ->
                Log.d("HomeViewModel", "${e.message}")
            }).addTo(disposables)
    }

    override fun onCleared() {
        if (disposables != null)
            disposables.dispose()
    }

}

class HomeViewModelFactory(private val repository: HomeRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }
}