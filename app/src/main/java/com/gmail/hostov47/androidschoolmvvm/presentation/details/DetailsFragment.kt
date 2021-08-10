/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.gmail.hostov47.androidschoolmvvm.data.api.ImdbApi
import com.gmail.hostov47.androidschoolmvvm.data.api.ImdbApiImpl
import com.gmail.hostov47.androidschoolmvvm.data.local.MovieStore
import com.gmail.hostov47.androidschoolmvvm.data.local.MovieStoreImpl
import com.gmail.hostov47.androidschoolmvvm.data.repository.detail.DetailsRepository
import com.gmail.hostov47.androidschoolmvvm.data.repository.detail.DetailsRepositoryImpl
import com.gmail.hostov47.androidschoolmvvm.databinding.FragmentDetailsBinding
import com.gmail.hostov47.androidschoolmvvm.domain.interactors.MovieDetailsInteractor
import com.gmail.hostov47.androidschoolmvvm.extensions.load
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MovieDetailsWithCast
import com.gmail.hostov47.androidschoolmvvm.presentation.base.BindingFragment
import com.gmail.hostov47.androidschoolmvvm.presentation.details.adapters.ActorItem
import com.gmail.hostov47.androidschoolmvvm.presentation.home.HomeFragment
import com.gmail.hostov47.androidschoolmvvm.utils.SchedulersProvider.SchedulersProvider
import com.gmail.hostov47.androidschoolmvvm.utils.SchedulersProvider.SchedulersProviderImpl
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class DetailsFragment : BindingFragment<FragmentDetailsBinding>(){
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentDetailsBinding::inflate

    private val viewModel: DetailsViewModel by viewModels {
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
        val detailsRepository: DetailsRepository = DetailsRepositoryImpl(movieApi, movieStore)
        val movieDetailsInteractor = MovieDetailsInteractor(detailsRepository)
        val schedulersProvider: SchedulersProvider = SchedulersProviderImpl()
        DetailsViewModelFactory(movieDetailsInteractor, schedulersProvider)
    }
    private var movieId: Int = 0

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt(HomeFragment.MOVIE_ID)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMovieDetails(movieId)
        viewModel.showLoading.observe(viewLifecycleOwner, Observer {
            showLoading(it)
        })

        viewModel.detailsWithCast.observe(viewLifecycleOwner, Observer { result ->
            when(result) {
                Result.Empty -> {}
                is Result.Error -> showToast(result.message)
                is Result.Success -> bindMovie(result.data)
            }
        })

    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(showLoading: Boolean) {
        if (showLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.contentLayout.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.contentLayout.visibility = View.VISIBLE
        }
    }

    private fun bindMovie(movieDetails: MovieDetailsWithCast) {
        binding.ivMoviePoster.load(movieDetails.posterPath!!)
        binding.tvMovieTitle.text = movieDetails.title
        binding.ratingBar.rating = movieDetails.rating
        binding.tvMovieDescription.text = movieDetails.overview
        binding.tvStudio.text = movieDetails.productionCompanies
        binding.tvGenre.text = movieDetails.genres
        binding.tvYear.text = movieDetails.releaseDate

        val actorsItem = movieDetails.cast.map { ActorItem(it) }.toList()
        binding.rvCast.adapter = adapter.apply { update(actorsItem) }
    }

}