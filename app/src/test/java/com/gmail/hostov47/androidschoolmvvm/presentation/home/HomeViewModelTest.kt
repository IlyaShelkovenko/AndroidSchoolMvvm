/**
 * Created by Ilia Shelkovenko on 16.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.home

import android.content.SharedPreferences
import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.gmail.hostov47.androidschoolmvvm.R
import com.gmail.hostov47.androidschoolmvvm.domain.interactors.MoviesInteractor
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview
import com.gmail.hostov47.androidschoolmvvm.stubs.movieDomain
import com.gmail.hostov47.androidschoolmvvm.utils.schedulers.SchedulersProviderImplStub
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException


class HomeViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel
    private val interactor: MoviesInteractor = mockk(relaxed = true)
    private val prefs: SharedPreferences = mockk()
    private val resources: Resources = mockk()

    private val popularMoviesObserver: Observer<List<MoviePreview>> = mockk()
    private val upcomingMoviesObserver: Observer<List<MoviePreview>> = mockk()
    private val nowPlayingMoviesObserver: Observer<List<MoviePreview>> = mockk()
    private val showLoadingObserver: Observer<Boolean> = mockk()
    private val errorsObserver: Observer<Throwable> = mockk()

    @Before
    fun setup(){
        every { resources.getString(R.string.pref_key_caching) } returns ""
        every { prefs.getBoolean(resources.getString(R.string.pref_key_caching), true) } returns true

        every { popularMoviesObserver.onChanged(any()) } just Runs
        every { upcomingMoviesObserver.onChanged(any()) } just Runs
        every { nowPlayingMoviesObserver.onChanged(any()) } just Runs
        every { showLoadingObserver.onChanged(any()) } just Runs
        every { errorsObserver.onChanged(any()) } just Runs

        viewModel = HomeViewModel(interactor,SchedulersProviderImplStub(), prefs, resources)

        viewModel.showLoading.observeForever(showLoadingObserver)
        viewModel.popularMovies.observeForever(popularMoviesObserver)
        viewModel.upcomingMovies.observeForever(upcomingMoviesObserver)
        viewModel.nowPlayingMovies.observeForever(nowPlayingMoviesObserver)
        viewModel.errors.observeForever(errorsObserver)
    }

    @After
    fun tearDown(){
        viewModel.popularMovies.removeObserver(popularMoviesObserver)
        viewModel.upcomingMovies.removeObserver(upcomingMoviesObserver)
        viewModel.nowPlayingMovies.removeObserver(nowPlayingMoviesObserver)
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
    fun testPopularMovies(){
        val forceLoad = false
        val caching = true
        every { interactor.getPopularMovies(forceLoad,caching) } returns listOf(movieDomain)

        viewModel.loadPopularMovies(forceLoad, caching)

        verifySequence {
            showLoadingObserver.onChanged(true)
            popularMoviesObserver.onChanged(moviePreview())
            showLoadingObserver.onChanged(false)
        }
        verify { errorsObserver wasNot Called }
    }

    @Test
    fun testPopularMoviesWithoutCash(){
        val forceLoad = false
        val caching = false
        every { interactor.getPopularMovies(forceLoad,caching) } returns listOf(movieDomain)

        viewModel.loadPopularMovies(forceLoad, caching)

        verifySequence {
            showLoadingObserver.onChanged(true)
            popularMoviesObserver.onChanged(moviePreview())
            showLoadingObserver.onChanged(false)
        }
        verify { errorsObserver wasNot Called }
    }

    @Test
    fun testPopularMoviesRefresh(){
        val forceLoad = true
        val caching = true
        every { interactor.getPopularMovies(forceLoad,caching) } returns listOf(movieDomain)

        viewModel.loadPopularMovies(forceLoad, caching)

        verifySequence {
            showLoadingObserver.onChanged(true)
            popularMoviesObserver.onChanged(moviePreview())
            showLoadingObserver.onChanged(false)
        }
        verify { errorsObserver wasNot Called }
    }

    @Test
    fun testPopularMoviesRefreshWithoutCash(){
        val forceLoad = true
        val caching = false
        every { interactor.getPopularMovies(forceLoad,caching) } returns listOf(movieDomain)

        viewModel.loadPopularMovies(forceLoad, caching)

        verifySequence {
            showLoadingObserver.onChanged(true)
            popularMoviesObserver.onChanged(moviePreview())
            showLoadingObserver.onChanged(false)
        }
        verify { errorsObserver wasNot Called }
    }

    @Test
    fun testPopularMoviesEmptyList(){
        val forceLoad = false
        val caching = true
        every { interactor.getPopularMovies(forceLoad,caching) } returns listOf()

        viewModel.loadPopularMovies(forceLoad, caching)

        verifySequence {
            showLoadingObserver.onChanged(true)
            popularMoviesObserver.onChanged(listOf())
            showLoadingObserver.onChanged(false)
        }
        verify { errorsObserver wasNot Called }
    }

    @Test
    fun testPopularError(){
        val forceLoad = false
        val caching = true
        val exception = IOException("Test")
        every { interactor.getPopularMovies(forceLoad,caching ) } throws exception

        viewModel.loadPopularMovies(forceLoad, caching)

        verifySequence {
            showLoadingObserver.onChanged(true)
            errorsObserver.onChanged(exception)
            showLoadingObserver.onChanged(false)
        }
        verify { popularMoviesObserver wasNot Called }
    }

    @Test
    fun testPopularErrorRefresh(){
        val forceLoad = true
        val caching = true
        val exception = IOException("Test")
        every { interactor.getPopularMovies(forceLoad,caching ) } throws exception

        viewModel.loadPopularMovies(forceLoad, caching)

        verifySequence {
            showLoadingObserver.onChanged(true)
            errorsObserver.onChanged(exception)
            showLoadingObserver.onChanged(false)
        }
        verify { popularMoviesObserver wasNot Called }
    }

    @Test
    fun testUpcomingMovies(){
        val forceLoad = false
        val caching = true
        every { interactor.getUpcomingMovies(forceLoad,caching) } returns listOf(movieDomain)

        viewModel.loadUpcomingMovies(forceLoad, caching)

        verifySequence {
            showLoadingObserver.onChanged(true)
            upcomingMoviesObserver.onChanged(moviePreview())
            showLoadingObserver.onChanged(false)
        }
        verify { errorsObserver wasNot Called }
    }

     @Test
     fun testUpcomingMoviesWithoutCash(){
         val forceLoad = false
         val caching = false
         every { interactor.getUpcomingMovies(forceLoad,caching) } returns listOf(movieDomain)

         viewModel.loadUpcomingMovies(forceLoad, caching)

         verifySequence {
             showLoadingObserver.onChanged(true)
             upcomingMoviesObserver.onChanged(moviePreview())
             showLoadingObserver.onChanged(false)
         }
         verify { errorsObserver wasNot Called }
     }

     @Test
     fun testUpcomingMoviesRefresh(){
         val forceLoad = true
         val caching = true
         every { interactor.getUpcomingMovies(forceLoad,caching) } returns listOf(movieDomain)

         viewModel.loadUpcomingMovies(forceLoad, caching)

         verifySequence {
             showLoadingObserver.onChanged(true)
             upcomingMoviesObserver.onChanged(moviePreview())
             showLoadingObserver.onChanged(false)
         }
         verify { errorsObserver wasNot Called }
     }

     @Test
     fun testUpcomingMoviesRefreshWithoutCash(){
         val forceLoad = true
         val caching = false
         every { interactor.getUpcomingMovies(forceLoad,caching) } returns listOf(movieDomain)

         viewModel.loadUpcomingMovies(forceLoad, caching)

         verifySequence {
             showLoadingObserver.onChanged(true)
             upcomingMoviesObserver.onChanged(moviePreview())
             showLoadingObserver.onChanged(false)
         }
         verify { errorsObserver wasNot Called }
     }

     @Test
     fun testUpcomingMoviesEmptyList(){
         val forceLoad = false
         val caching = true
         every { interactor.getUpcomingMovies(forceLoad,caching) } returns listOf()

         viewModel.loadUpcomingMovies(forceLoad, caching)

         verifySequence {
             showLoadingObserver.onChanged(true)
             upcomingMoviesObserver.onChanged(listOf())
             showLoadingObserver.onChanged(false)
         }
         verify { errorsObserver wasNot Called }
     }

     @Test
     fun testUpcomingError(){
         val forceLoad = false
         val caching = true
         val exception = IOException("Test")
         every { interactor.getUpcomingMovies(forceLoad,caching ) } throws exception

         viewModel.loadUpcomingMovies(forceLoad, caching)

         verifySequence {
             showLoadingObserver.onChanged(true)
             errorsObserver.onChanged(exception)
             showLoadingObserver.onChanged(false)
         }
         verify { upcomingMoviesObserver wasNot Called }
     }

     @Test
     fun testUpcomingErrorRefresh(){
         val forceLoad = true
         val caching = true
         val exception = IOException("Test")
         every { interactor.getUpcomingMovies(forceLoad,caching ) } throws exception

         viewModel.loadUpcomingMovies(forceLoad, caching)

         verifySequence {
             showLoadingObserver.onChanged(true)
             errorsObserver.onChanged(exception)
             showLoadingObserver.onChanged(false)
         }
         verify { upcomingMoviesObserver wasNot Called }
     }

    @Test
    fun testNowPlayingMovies(){
        val forceLoad = false
        val caching = true
        every { interactor.getNowPlayingMovies(forceLoad,caching) } returns listOf(movieDomain)

        viewModel.loadNowPlayingMovies(forceLoad, caching)

        verifySequence {
            showLoadingObserver.onChanged(true)
            nowPlayingMoviesObserver.onChanged(moviePreview())
            showLoadingObserver.onChanged(false)
        }
        verify { errorsObserver wasNot Called }
    }

    @Test
    fun testNowPlayingMoviesWithoutCash(){
        val forceLoad = false
        val caching = false
        every { interactor.getNowPlayingMovies(forceLoad,caching) } returns listOf(movieDomain)

        viewModel.loadNowPlayingMovies(forceLoad, caching)

        verifySequence {
            showLoadingObserver.onChanged(true)
            nowPlayingMoviesObserver.onChanged(moviePreview())
            showLoadingObserver.onChanged(false)
        }
        verify { errorsObserver wasNot Called }
    }

    @Test
    fun testNowPlayingMoviesRefresh(){
        val forceLoad = true
        val caching = true
        every { interactor.getNowPlayingMovies(forceLoad,caching) } returns listOf(movieDomain)

        viewModel.loadNowPlayingMovies(forceLoad, caching)

        verifySequence {
            showLoadingObserver.onChanged(true)
            nowPlayingMoviesObserver.onChanged(moviePreview())
            showLoadingObserver.onChanged(false)
        }
        verify { errorsObserver wasNot Called }
    }

    @Test
    fun testNowPlayingMoviesRefreshWithoutCash(){
        val forceLoad = true
        val caching = false
        every { interactor.getNowPlayingMovies(forceLoad,caching) } returns listOf(movieDomain)

        viewModel.loadNowPlayingMovies(forceLoad, caching)

        verifySequence {
            showLoadingObserver.onChanged(true)
            nowPlayingMoviesObserver.onChanged(moviePreview())
            showLoadingObserver.onChanged(false)
        }
        verify { errorsObserver wasNot Called }
    }

    @Test
    fun testNowPlayingMoviesEmptyList(){
        val forceLoad = false
        val caching = true
        every { interactor.getNowPlayingMovies(forceLoad,caching) } returns listOf()

        viewModel.loadNowPlayingMovies(forceLoad, caching)

        verifySequence {
            showLoadingObserver.onChanged(true)
            nowPlayingMoviesObserver.onChanged(listOf())
            showLoadingObserver.onChanged(false)
        }
        verify { errorsObserver wasNot Called }
    }

    @Test
    fun testNowPlayingError(){
        val forceLoad = false
        val caching = true
        val exception = IOException("Test")
        every { interactor.getNowPlayingMovies(forceLoad,caching ) } throws exception

        viewModel.loadNowPlayingMovies(forceLoad, caching)

        verifySequence {
            showLoadingObserver.onChanged(true)
            errorsObserver.onChanged(exception)
            showLoadingObserver.onChanged(false)
        }
        verify { nowPlayingMoviesObserver wasNot Called }
    }

    @Test
    fun testNowPlayingErrorRefresh(){
        val forceLoad = true
        val caching = true
        val exception = IOException("Test")
        every { interactor.getNowPlayingMovies(forceLoad,caching ) } throws exception

        viewModel.loadNowPlayingMovies(forceLoad, caching)

        verifySequence {
            showLoadingObserver.onChanged(true)
            errorsObserver.onChanged(exception)
            showLoadingObserver.onChanged(false)
        }
        verify { nowPlayingMoviesObserver wasNot Called }
    }


}