/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.gmail.hostov47.androidschoolmvvm.ImdbApp
import com.gmail.hostov47.androidschoolmvvm.R
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
    private var movie: MovieDetailsWithCast? = null

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

        binding.cbFavorite.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                movie?.let {
                    viewModel.addToFavorite(it)
                }
            } else {
                movie?.let {
                    viewModel.removeFromFavorite(it)
                }
            }
        }
        binding.btnWatch.setOnClickListener {
            with(binding){
                if (btnWatch.isSelected) {
                    movie?.let {
                        viewModel.removeFromWatchList(it)
                        btnWatch.text = getString(R.string.watch_str)
                        btnWatch.isSelected = false
                    }
                } else {
                    movie?.let {
                        viewModel.addToWatchList(it)
                        btnWatch.text = getString(R.string.will_watch)
                        btnWatch.isSelected = true
                    }
                }
            }
        }

        viewModel.isFavorite.observe(viewLifecycleOwner, Observer { isFavorite ->
            binding.cbFavorite.isChecked = isFavorite
        })

        viewModel.isInWatchList.observe(viewLifecycleOwner, Observer { isInWatchList ->
            with(binding){
                if (isInWatchList) {
                    btnWatch.isSelected = true
                    btnWatch.text = getString(R.string.will_watch)
                } else {
                    btnWatch.isSelected = false
                    btnWatch.text = getString(R.string.watch_str)
                }
            }
        })

        viewModel.detailsWithCast.observe(viewLifecycleOwner, Observer { result ->
            when(result) {
                Result.Empty -> {}
                is Result.Error -> showToast(result.message)
                is Result.Success -> {
                    movie = result.data
                    bindMovie(movie!!)
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        })
    }

    private fun showLoading(showLoading: Boolean) {
        if (showLoading) {
            binding.contentLayout.visibility = View.GONE
            binding.animationView.visibility = View.VISIBLE
            binding.animationView.playAnimation()
        } else {
            binding.animationView.cancelAnimation()
            binding.animationView.visibility = View.GONE
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

    override fun onStop() {
        super.onStop()
        binding.animationView.cancelAnimation()
    }
}