/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.gmail.hostov47.androidschoolmvvm.ImdbApp
import com.gmail.hostov47.androidschoolmvvm.databinding.FragmentDetailsBinding
import com.gmail.hostov47.androidschoolmvvm.extensions.load
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MovieDetailsWithCast
import com.gmail.hostov47.androidschoolmvvm.presentation.base.BindingFragment
import com.gmail.hostov47.androidschoolmvvm.presentation.details.adapters.ActorItem
import com.gmail.hostov47.androidschoolmvvm.presentation.home.HomeFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import javax.inject.Inject

class DetailsFragment : BindingFragment<FragmentDetailsBinding>(){
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentDetailsBinding::inflate

    @Inject
    lateinit var viewModelFactory: DetailsViewModelFactory

    private val viewModel: DetailsViewModel by viewModels {
        viewModelFactory
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
        (requireActivity().application as ImdbApp).appComponent.getDetailsComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefresh.setOnRefreshListener { viewModel.onRefreshLayout(movieId) }
        viewModel.getMovieDetails(movieId)
        viewModel.showLoading.observe(viewLifecycleOwner, Observer {
            showLoading(it)
        })

        viewModel.detailsWithCast.observe(viewLifecycleOwner, Observer { result ->
            when(result) {
                Result.Empty -> {}
                is Result.Error -> showToast(result.message)
                is Result.Success -> {
                    bindMovie(result.data)
                    binding.swipeRefresh.isRefreshing = false
                }
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