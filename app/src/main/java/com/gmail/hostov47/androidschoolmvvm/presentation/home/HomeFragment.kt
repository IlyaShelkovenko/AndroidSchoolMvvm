/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.home

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewbinding.ViewBinding
import com.gmail.hostov47.androidschoolmvvm.R
import com.gmail.hostov47.androidschoolmvvm.data.api.ImdbApi
import com.gmail.hostov47.androidschoolmvvm.data.api.ImdbApiImpl
import com.gmail.hostov47.androidschoolmvvm.data.local.MovieStore
import com.gmail.hostov47.androidschoolmvvm.data.local.MovieStoreImpl
import com.gmail.hostov47.androidschoolmvvm.data.repository.home.MoviesRepository
import com.gmail.hostov47.androidschoolmvvm.data.repository.home.MoviesRepositoryImpl
import com.gmail.hostov47.androidschoolmvvm.databinding.FragmentHomeBinding
import com.gmail.hostov47.androidschoolmvvm.domain.interactors.MoviesInteractor
import com.gmail.hostov47.androidschoolmvvm.models.data.dto.Movie
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview
import com.gmail.hostov47.androidschoolmvvm.presentation.base.BindingFragment
import com.gmail.hostov47.androidschoolmvvm.presentation.home.adapters.MoviesAdapter
import com.gmail.hostov47.androidschoolmvvm.presentation.home.adapters.OnMovieItemClick
import com.gmail.hostov47.androidschoolmvvm.utils.SchedulersProvider.SchedulersProvider
import com.gmail.hostov47.androidschoolmvvm.utils.SchedulersProvider.SchedulersProviderImpl
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


class HomeFragment : BindingFragment<FragmentHomeBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentHomeBinding::inflate

    private val viewModel: HomeViewModel by viewModels {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
        val json = Json {
            ignoreUnknownKeys = true
        }
        val movieApi: ImdbApi = ImdbApiImpl(okHttpClient, json)
        val movieStore: MovieStore = MovieStoreImpl(
            requireContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE),
            json
        )
        val moviesRepository: MoviesRepository = MoviesRepositoryImpl(movieApi, movieStore)
        val moviesInteractor = MoviesInteractor(moviesRepository)
        val schedulersProvider: SchedulersProvider = SchedulersProviderImpl()
        HomeViewModelFactory(moviesInteractor, schedulersProvider)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val onMovieItemClicked = OnMovieItemClick { movie -> openMovieDetails(movie) }
        val adapter = MoviesAdapter(onMovieItemClicked)
        binding.moviesRecyclerView.layoutManager = GridLayoutManager(context, NUMBER_OF_COLUMNS)
        binding.moviesRecyclerView.adapter = adapter
        binding.swipeRefresh.setOnRefreshListener { viewModel.onRefreshLayout() }


        viewModel.showLoading.observe(viewLifecycleOwner, Observer {
            showLoading(it)
        })

        viewModel.movies.observe(viewLifecycleOwner, Observer {
            adapter.submitItems(it)
            binding.swipeRefresh.isRefreshing = false
        })

        viewModel.errors.observe(viewLifecycleOwner, Observer {
            showToast(it.message ?: "Unknown error")
        })
    }

    private fun showToast(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun openMovieDetails(movie: MoviePreview) {
        val bundle = Bundle()
        bundle.putInt(MOVIE_ID, movie.movieId ?: 0)
        findNavController().navigate(R.id.detailsFragment, bundle)
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            binding.progressBar.visibility = View.VISIBLE
            binding.moviesRecyclerView.visibility = View.GONE
        }else {
            binding.progressBar.visibility = View.GONE
            binding.moviesRecyclerView.visibility = View.VISIBLE
        }
    }

    companion object {
        const val MOVIE_ID = "MOVIE_ID"
        const val NUMBER_OF_COLUMNS = 2
    }
}