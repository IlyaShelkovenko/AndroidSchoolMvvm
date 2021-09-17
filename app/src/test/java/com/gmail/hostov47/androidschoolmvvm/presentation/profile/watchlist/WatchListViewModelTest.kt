/**
 * Created by Ilia Shelkovenko on 17.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.profile.watchlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.gmail.hostov47.androidschoolmvvm.domain.interactors.FavoriteMoviesInteractor
import com.gmail.hostov47.androidschoolmvvm.domain.interactors.WatchListInteractor
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview
import com.gmail.hostov47.androidschoolmvvm.presentation.profile.favorite.FavoriteViewModel
import com.gmail.hostov47.androidschoolmvvm.stubs.errorTest
import com.gmail.hostov47.androidschoolmvvm.stubs.moviesPreviewList
import com.gmail.hostov47.androidschoolmvvm.utils.schedulers.SchedulersProviderImplStub
import io.mockk.*
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class WatchListViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: WatchListViewModel
    private val interactor: WatchListInteractor = mockk(relaxed = true)

    private val watchListObserver: Observer<List<MoviePreview>> = mockk()
    private val errorsObserver: Observer<Throwable> = mockk()

    @Before
    fun setup() {
        every { watchListObserver.onChanged(any()) } just Runs
        every { errorsObserver.onChanged(any()) } just Runs

        viewModel = WatchListViewModel(interactor, SchedulersProviderImplStub())
        viewModel.errorLive.observeForever(errorsObserver)
        viewModel.watchListLive.observeForever(watchListObserver)
    }

    @After
    fun tearDown() {
        viewModel.watchListLive.removeObserver(watchListObserver)
        viewModel.errorLive.removeObserver(errorsObserver)
    }

    private val watchListMovies = moviesPreviewList

    @Test
    fun getFavoriteMoviesTest(){
        every { interactor.getWatchList() } returns Single.just(watchListMovies)

        viewModel.getWatchList()

        verify { watchListObserver.onChanged(watchListMovies) }
        verify { errorsObserver wasNot Called }

    }

    private val exception = IOException("test exception")

    @Test
    fun getFavoriteMoviesTestWithError(){
        every { interactor.getWatchList() } returns Single.error(errorTest)

        viewModel.getWatchList()

        verify { errorsObserver.onChanged(errorTest) }
        verify { watchListObserver wasNot Called  }
    }
}