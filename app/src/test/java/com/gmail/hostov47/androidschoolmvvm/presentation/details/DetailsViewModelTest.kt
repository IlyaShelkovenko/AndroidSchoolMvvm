package com.gmail.hostov47.androidschoolmvvm.presentation.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.gmail.hostov47.androidschoolmvvm.domain.interactors.MovieDetailsInteractor
import com.gmail.hostov47.androidschoolmvvm.models.domain.MovieCastDomain
import com.gmail.hostov47.androidschoolmvvm.models.domain.MovieDetailsDomain
import com.gmail.hostov47.androidschoolmvvm.models.domain.MovieDomain
import com.gmail.hostov47.androidschoolmvvm.models.presentation.Cast
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MovieDetailsWithCast
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview
import com.gmail.hostov47.androidschoolmvvm.presentation.home.HomeViewModel
import com.gmail.hostov47.androidschoolmvvm.presentation.movieDetailsDomain
import com.gmail.hostov47.androidschoolmvvm.presentation.moviesCastDomain
import com.gmail.hostov47.androidschoolmvvm.presentation.moviesDetailWithCast
import com.gmail.hostov47.androidschoolmvvm.stubs.movieDomain
import com.gmail.hostov47.androidschoolmvvm.utils.schedulers.SchedulersProviderImplStub
import io.mockk.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetailsViewModel
    private val interactor: MovieDetailsInteractor = mockk(relaxed = true)

    private val moviesDetailsObserver: Observer<Result> = mockk()

    @Before
    fun setup() {
        every { moviesDetailsObserver.onChanged(any()) } just Runs

        viewModel = DetailsViewModel(interactor, SchedulersProviderImplStub())

    }


    @Test
    fun testGetMovieDetails() {
        val movieId = 111
        val forceLoad = true
        every { interactor.getMovieDetails(movieId, forceLoad) } returns movieDetailsDomain
        every { interactor.getMovieCast(movieId, forceLoad) } returns moviesCastDomain

        viewModel.detailsWithCast.observeForever(moviesDetailsObserver)
        viewModel.getMovieDetails(movieId, forceLoad)


        verifySequence {
            moviesDetailsObserver.onChanged(Result.Loading)
            moviesDetailsObserver.onChanged(Result.Success(moviesDetailWithCast))
        }
        verify { moviesDetailsObserver.onChanged(Result.Error(any())) wasNot Called  }
    }
}