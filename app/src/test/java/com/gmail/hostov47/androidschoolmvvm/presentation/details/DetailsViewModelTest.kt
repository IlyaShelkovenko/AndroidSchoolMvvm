/**
 * Created by Ilia Shelkovenko on 17.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.gmail.hostov47.androidschoolmvvm.domain.interactors.MovieDetailsInteractor
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MovieDetailsWithCast
import com.gmail.hostov47.androidschoolmvvm.stubs.errorTest
import com.gmail.hostov47.androidschoolmvvm.stubs.movieDetailsDomain
import com.gmail.hostov47.androidschoolmvvm.stubs.moviesCastDomain
import com.gmail.hostov47.androidschoolmvvm.stubs.moviesDetailWithCast
import com.gmail.hostov47.androidschoolmvvm.utils.schedulers.SchedulersProviderImplStub
import io.mockk.*
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class DetailsViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetailsViewModel
    private val interactor: MovieDetailsInteractor = mockk(relaxed = true)

    private val moviesDetailsObserver: Observer<MovieDetailsWithCast> = mockk()
    private val showLoadingObserver: Observer<Boolean> = mockk()
    private val errorsObserver: Observer<Throwable> = mockk()

    private val addToFavoriteObserver: Observer<Boolean> = mockk()
    private val addToWatchListObserver: Observer<Boolean> = mockk()

    @Before
    fun setup() {
        every { moviesDetailsObserver.onChanged(any()) } just Runs
        every { showLoadingObserver.onChanged(any()) } just Runs
        every { errorsObserver.onChanged(any()) } just Runs
        every { addToFavoriteObserver.onChanged(any()) } just Runs
        every { addToWatchListObserver.onChanged(any()) } just Runs

        viewModel = DetailsViewModel(interactor, SchedulersProviderImplStub())

        viewModel.detailsWithCast.observeForever(moviesDetailsObserver)
        viewModel.showLoading.observeForever(showLoadingObserver)
        viewModel.error.observeForever(errorsObserver)

        viewModel.isFavorite.observeForever(addToFavoriteObserver)
        viewModel.isInWatchList.observeForever(addToWatchListObserver)

    }

    @After
    fun tearDown(){
        viewModel.detailsWithCast.removeObserver(moviesDetailsObserver)
        viewModel.showLoading.removeObserver(showLoadingObserver)
        viewModel.error.removeObserver(errorsObserver)

        viewModel.isFavorite.removeObserver(addToFavoriteObserver)
        viewModel.isInWatchList.removeObserver(addToWatchListObserver)
    }

    @Test
    fun testGetMovieDetails() {
        val movieId = 111
        val forceLoad = true
        every { interactor.getMovieDetails(movieId, forceLoad) } returns movieDetailsDomain
        every { interactor.getMovieCast(movieId, forceLoad) } returns moviesCastDomain

        viewModel.getMovieDetails(movieId, forceLoad)

        verifySequence {
            showLoadingObserver.onChanged(true)
            moviesDetailsObserver.onChanged(moviesDetailWithCast)
            showLoadingObserver.onChanged(false)
        }
        verify { errorsObserver wasNot Called }
    }

    @Test
    fun testGetMovieDetailsError() {
        val movieId = 111
        val forceLoad = true
        every { interactor.getMovieDetails(movieId, forceLoad) } throws errorTest
        every { interactor.getMovieCast(movieId, forceLoad) } returns moviesCastDomain

        viewModel.getMovieDetails(movieId, forceLoad)

        verifySequence {
            showLoadingObserver.onChanged(true)
            errorsObserver.onChanged(errorTest)
            showLoadingObserver.onChanged(false)
        }
        verify { moviesDetailsObserver wasNot Called }
    }

    @Test
    fun testGetMovieDetailsErrorAnother() {
        val movieId = 111
        val forceLoad = true
        every { interactor.getMovieDetails(movieId, forceLoad) } returns movieDetailsDomain
        every { interactor.getMovieCast(movieId, forceLoad) } throws errorTest

        viewModel.getMovieDetails(movieId, forceLoad)

        verifySequence {
            showLoadingObserver.onChanged(true)
            errorsObserver.onChanged(errorTest)
            showLoadingObserver.onChanged(false)
        }
        verify { moviesDetailsObserver wasNot Called }
    }

    @Test
    fun testRequestMovieIsFavoriteTrue() {
        every { interactor.isMovieFavorite(moviesDetailWithCast) } returns Single.just(true)

        viewModel.requestMovieIsFavorite(moviesDetailWithCast)

        verify { addToFavoriteObserver.onChanged(true) }
    }

    @Test
    fun testRequestMovieInWatchListTrue() {
        every { interactor.isMovieInWatchList(moviesDetailWithCast) } returns Single.just(true)

        viewModel.requestMovieIsInWatchList(moviesDetailWithCast)

        verify { addToWatchListObserver.onChanged(true) }
    }

    @Test
    fun testRequestMovieIsFavoriteFalse() {
        every { interactor.isMovieFavorite(moviesDetailWithCast) } returns Single.just(false)

        viewModel.requestMovieIsFavorite(moviesDetailWithCast)

        verify { addToFavoriteObserver.onChanged(false) }
    }

    @Test
    fun testRequestMovieInWatchListFalse() {
        every { interactor.isMovieInWatchList(moviesDetailWithCast) } returns Single.just(false)

        viewModel.requestMovieIsInWatchList(moviesDetailWithCast)

        verify { addToWatchListObserver.onChanged(false) }
    }

    @Test
    fun addToFavoriteMoviesTest(){
        every { interactor.addToFavorite(moviesDetailWithCast) } returns Unit

        viewModel.addToFavorite(moviesDetailWithCast)

        verify { addToFavoriteObserver.onChanged(true) }
        verify { errorsObserver wasNot Called }
    }

    @Test
    fun removeFromFavoriteMoviesTest(){
        every { interactor.removeFromFavorite(moviesDetailWithCast) } returns Unit

        viewModel.removeFromFavorite(moviesDetailWithCast)

        verify { addToFavoriteObserver.onChanged(false) }
        verify { errorsObserver wasNot Called }
    }

    private val exception = IOException("test exception")

    @Test
    fun addToFavoriteMoviesTestWithError(){
        every { interactor.addToFavorite(moviesDetailWithCast) } throws exception

        viewModel.addToFavorite(moviesDetailWithCast)

        verify { errorsObserver.onChanged(exception) }
        verify { addToFavoriteObserver wasNot Called }
    }

    @Test
    fun removeFromFavoriteMoviesTestWithError(){
        every { interactor.removeFromFavorite(moviesDetailWithCast) } throws exception

        viewModel.removeFromFavorite(moviesDetailWithCast)

        verify { errorsObserver.onChanged(exception) }
        verify {addToFavoriteObserver wasNot Called }
    }

    @Test
    fun addToWatchListMoviesTest(){
        every { interactor.addToWatchList(moviesDetailWithCast) } returns Unit

        viewModel.addToWatchList(moviesDetailWithCast)

        verify { addToWatchListObserver.onChanged(true) }
        verify { errorsObserver wasNot Called }
    }

    @Test
    fun removeFromWatchListMoviesTest(){
        every { interactor.removeFromWatchList(moviesDetailWithCast) } returns Unit

        viewModel.removeFromWatchList(moviesDetailWithCast)

        verify { addToWatchListObserver.onChanged(false) }
        verify { errorsObserver wasNot Called }
    }

    @Test
    fun addToWatchListMoviesTestWithError(){
        every { interactor.addToWatchList(moviesDetailWithCast) } throws exception

        viewModel.addToWatchList(moviesDetailWithCast)

        verify { errorsObserver.onChanged(exception) }
        verify { addToWatchListObserver wasNot Called }
    }

    @Test
    fun removeFromWatchListMoviesTestWithError(){
        every { interactor.removeFromWatchList(moviesDetailWithCast) } throws exception

        viewModel.removeFromWatchList(moviesDetailWithCast)

        verify { errorsObserver.onChanged(exception) }
        verify {addToWatchListObserver wasNot Called }
    }

}