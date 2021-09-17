/**
 * Created by Ilia Shelkovenko on 17.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.profile.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.gmail.hostov47.androidschoolmvvm.domain.interactors.FavoriteMoviesInteractor
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview
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

class FavoriteViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: FavoriteViewModel
    private val interactor: FavoriteMoviesInteractor = mockk(relaxed = true)

    private val favoriteMoviesObserver: Observer<List<MoviePreview>> = mockk()
    private val errorsObserver: Observer<Throwable> = mockk()

    @Before
    fun setup() {
        every { favoriteMoviesObserver.onChanged(any()) } just Runs
        every { errorsObserver.onChanged(any()) } just Runs

        viewModel = FavoriteViewModel(interactor, SchedulersProviderImplStub())
        viewModel.errorLive.observeForever(errorsObserver)
        viewModel.favoriteMoviesLive.observeForever(favoriteMoviesObserver)
    }

    @After
    fun tearDown() {
        viewModel.favoriteMoviesLive.removeObserver(favoriteMoviesObserver)
        viewModel.errorLive.removeObserver(errorsObserver)
    }

    private val favoriteMovies = moviesPreviewList

    @Test
    fun getFavoriteMoviesTest(){
        every { interactor.getFavoriteMovies() } returns Single.just(favoriteMovies)

        viewModel.getFavoriteMovies()

        verify { favoriteMoviesObserver.onChanged(favoriteMovies) }
        verify { errorsObserver wasNot Called }

    }

    @Test
    fun getFavoriteMoviesTestWithError(){
        every { interactor.getFavoriteMovies() } returns Single.error(errorTest)

        viewModel.getFavoriteMovies()

        verify { errorsObserver.onChanged(errorTest) }
        verify { favoriteMoviesObserver wasNot Called  }
    }
}