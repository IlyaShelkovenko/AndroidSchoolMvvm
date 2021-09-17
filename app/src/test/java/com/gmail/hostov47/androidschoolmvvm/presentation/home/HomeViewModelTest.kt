/**
 * Created by Ilia Shelkovenko on 16.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.home

import android.content.SharedPreferences
import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.gmail.hostov47.androidschoolmvvm.R
import com.gmail.hostov47.androidschoolmvvm.domain.interactors.MoviesInteractor
import com.gmail.hostov47.androidschoolmvvm.models.domain.MovieDomain
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview
import com.gmail.hostov47.androidschoolmvvm.stubs.movieDomain
import com.gmail.hostov47.androidschoolmvvm.utils.SingleLiveEvent
import com.gmail.hostov47.androidschoolmvvm.utils.schedulers.SchedulersProviderImplStub
import io.mockk.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class HomeViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    lateinit var viewModel: HomeViewModel
    private val interactor: MoviesInteractor = mockk()
    private val prefs: SharedPreferences = mockk()
    private val resources: Resources = mockk()

    private val moviesObserver: Observer<List<MoviePreview>> = mockk()
    private val showLoadingObserver: Observer<Boolean> = mockk()
    private val errorsObserver: Observer<Throwable> = mockk()

    @Before
    fun setup(){
        every { resources.getString(R.string.pref_key_caching) } returns ""
        every { prefs.getBoolean(resources.getString(R.string.pref_key_caching), true) } returns true

        viewModel = HomeViewModel(interactor,SchedulersProviderImplStub(), prefs, resources)
        viewModel.nowPlayingMovies.observeForever(moviesObserver)
        viewModel.showLoading.observeForever(showLoadingObserver)
        viewModel.errors.observeForever(errorsObserver)


        every {
            moviesObserver.onChanged(any())
        } just Runs
        every { showLoadingObserver.onChanged(any()) } just Runs
        every {
            showLoadingObserver.onChanged(any())
        } just Runs

    }

   private fun movieDomain() = listOf(movieDomain)
   private fun moviePreview() = movieDomain().map { movie ->
           MoviePreview(
               movieId = movie.id,
               title = movie.title,
               poster = movie.posterPath ?: "",
               rating = movie.rating
           )
       }


    @Test
    fun testNowPlayingMovies(){
        every {
            interactor.getNowPlayingMovies()
        } returns listOf(movieDomain)
        viewModel.loadNowPlayingMovies()

        verifySequence {
            //showLoadingObserver.onChanged(true)
            showLoadingObserver.onChanged(true)
            moviesObserver.onChanged(moviePreview())
            showLoadingObserver.onChanged(false)
        }
        verify { errorsObserver wasNot Called }
    }
}