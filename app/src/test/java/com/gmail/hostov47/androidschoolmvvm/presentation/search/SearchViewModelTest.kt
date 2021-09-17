/**
 * Created by Ilia Shelkovenko on 17.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.gmail.hostov47.androidschoolmvvm.domain.interactors.SearchMoviesInteractor
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview
import com.gmail.hostov47.androidschoolmvvm.presentation.details.DetailsViewModel
import com.gmail.hostov47.androidschoolmvvm.stubs.listOfMovieDomain
import com.gmail.hostov47.androidschoolmvvm.stubs.moviesDetailWithCast
import com.gmail.hostov47.androidschoolmvvm.stubs.moviesPreviewList
import com.gmail.hostov47.androidschoolmvvm.utils.schedulers.SchedulersProviderImplStub
import io.mockk.*
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class SearchViewModelTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: SearchViewModel
    private val interactor: SearchMoviesInteractor = mockk(relaxed = true)

    private val searchMoviesObserver: Observer<List<MoviePreview>> = mockk()
    private val errorsObserver: Observer<Throwable> = mockk()

    @Before
    fun setup() {
        every { searchMoviesObserver.onChanged(any()) } just Runs
        every { errorsObserver.onChanged(any()) } just Runs

        viewModel = SearchViewModel(interactor, SchedulersProviderImplStub())

        viewModel.searchMovies.observeForever(searchMoviesObserver)
        viewModel.errors.observeForever(errorsObserver)
    }

    @After
    fun tearDown() {
        viewModel.searchMovies.removeObserver(searchMoviesObserver)
        viewModel.errors.removeObserver(errorsObserver)
    }

    private val queryString = "fast"
    private val source = Observable.just(queryString)


    @Test
    fun testHandleSearch() {
        every { interactor.searchMovies(queryString) } returns listOfMovieDomain

        viewModel.handleSearch(source)

        verifySequence {
            searchMoviesObserver.onChanged(moviesPreviewList)
        }
        verify { errorsObserver wasNot Called }
    }

    private val exception = IOException("test exception")
    @Test
    fun testHandleSearchWithError() {
        every { interactor.searchMovies(queryString) } throws exception

        viewModel.handleSearch(source)

        verifySequence {
            errorsObserver.onChanged(exception)
        }
        verify { searchMoviesObserver wasNot Called }
    }

}