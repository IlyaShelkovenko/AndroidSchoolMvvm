/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.details

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewbinding.ViewBinding
import com.gmail.hostov47.androidschoolmvvm.ImdbApp
import com.gmail.hostov47.androidschoolmvvm.R
import com.gmail.hostov47.androidschoolmvvm.databinding.FragmentDetailsBinding
import com.gmail.hostov47.androidschoolmvvm.extensions.load
import com.gmail.hostov47.androidschoolmvvm.extensions.loadBlur
import com.gmail.hostov47.androidschoolmvvm.extensions.loadPlaceHolder
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MovieDetailsWithCast
import com.gmail.hostov47.androidschoolmvvm.presentation.base.BindingFragment
import com.gmail.hostov47.androidschoolmvvm.presentation.details.adapters.ActorItem
import com.gmail.hostov47.androidschoolmvvm.presentation.home.HomeFragment
import com.google.android.material.button.MaterialButton
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import javax.inject.Inject

class DetailsFragment : BindingFragment<FragmentDetailsBinding>() {
    private val animationSet = AnimatorSet()

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
        setupActionBar()
        binding.swipeRefresh.setOnRefreshListener { viewModel.onRefreshLayout(movieId) }
        viewModel.getMovieDetails(movieId)

        binding.cbFavorite.setOnClickListener { view ->
            if ((view as AppCompatCheckBox).isChecked) {
                movie?.let {
                    viewModel.addToFavorite(it)
                    snackBarCancelable(getString(R.string.added_to_favorits)) {
                        viewModel.removeFromFavorite(it)
                    }
                }
            } else {
                movie?.let {
                    viewModel.removeFromFavorite(it)
                    snackBarCancelable(getString(R.string.removed_from_favorits)) {
                        viewModel.addToFavorite(it)
                    }
                }
            }
        }
        binding.btnWatch.setOnClickListener {
            with(binding) {
                if (btnWatch.isSelected) {
                    movie?.let {
                        viewModel.removeFromWatchList(it)
                        unselectButtonWatch(btnWatch)
                        snackBarCancelable(getString(R.string.removed_from_watchlist)) {
                            viewModel.addToWatchList(it)
                        }
                    }
                } else {
                    movie?.let {
                        viewModel.addToWatchList(it)
                        selectButtonWatch(btnWatch)
                        snackBarCancelable(getString(R.string.added_to_watchlist)) {
                            viewModel.removeFromWatchList(it)
                        }
                    }
                }
            }
        }

        viewModel.isFavorite.observe(viewLifecycleOwner, Observer { isFavorite ->
            binding.cbFavorite.isChecked = isFavorite
        })

        viewModel.isInWatchList.observe(viewLifecycleOwner, Observer { isInWatchList ->
            with(binding) {
                if (isInWatchList) {
                    selectButtonWatch(btnWatch)
                } else {
                    unselectButtonWatch(btnWatch)
                }
            }
        })

        viewModel.detailsWithCast.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                Result.Loading -> {
                    showLoading()
                }
                is Result.Error -> {
                    showToast(result.error.message ?: "Unknown error")
                    binding.swipeRefresh.isRefreshing = false
                }
                is Result.Success -> {
                    movie = result.data
                    bindMovie(movie!!)
                    hideLoading()
                    binding.swipeRefresh.isRefreshing = false
                    animateRatingBar(movie!!.rating)
                }
            }
        })
    }

    private fun setupActionBar() {
        val navController = findNavController()
        binding.toolbar.setupWithNavController(navController)
        binding.appBarLayout.bringToFront()
        binding.toolbar.title = ""
    }

    private fun selectButtonWatch(btnWatch: MaterialButton) {
        btnWatch.text = getString(R.string.will_watch)
        btnWatch.isSelected = true
        btnWatch.icon = resources.getDrawable(R.drawable.ic_baseline_check_24)
    }

    private fun unselectButtonWatch(btnWatch: MaterialButton) {
        btnWatch.text = getString(R.string.watch_str)
        btnWatch.isSelected = false
        btnWatch.icon = null
    }

    private fun showLoading() {
        binding.contentLayout.visibility = View.GONE
        binding.animationView.visibility = View.VISIBLE
        binding.animationView.playAnimation()
    }

    private fun hideLoading() {
        binding.animationView.cancelAnimation()
        binding.animationView.visibility = View.GONE
        binding.contentLayout.visibility = View.VISIBLE
    }

    private fun bindMovie(movieDetails: MovieDetailsWithCast) {
        if (movieDetails.posterPath?.isNotEmpty() == true) {
            binding.ivMoviePoster.load(movieDetails.posterPath)
            binding.ivMoviePosterLite.loadBlur(movieDetails.posterPath)
        }
        else
            binding.ivMoviePoster.loadPlaceHolder(R.drawable.ic_no_poster)
        binding.tvMovieTitle.text = movieDetails.title
        binding.ratingBar.rating = movieDetails.rating
        binding.tvMovieDescription.text = movieDetails.overview
        binding.tvStudio.text = movieDetails.productionCompanies
        binding.tvGenre.text = movieDetails.genres
        binding.tvYear.text = movieDetails.releaseDate

        val actorsItem = movieDetails.cast.map { ActorItem(it) }.toList()
        binding.rvCast.adapter = adapter.apply { update(actorsItem) }
    }

    private fun animateRatingBar(rating: Float) {
        val ratingAnimation: ObjectAnimator = ObjectAnimator.ofFloat(
            binding.ratingBar,
            "rating",
            0f,
            rating
        ).apply {
            duration = 800
        }
        val scaleAnimation = ValueAnimator.ofFloat(1f, 1.4f)
            .apply {
                duration = 400
                repeatMode = ValueAnimator.REVERSE
                repeatCount = 1
                addUpdateListener { animator: ValueAnimator ->
                    val scale = animator.animatedValue as Float
                    binding.ratingBar.scaleX = scale
                    binding.ratingBar.scaleY = scale
                }
            }
        animationSet.playSequentially(ratingAnimation, scaleAnimation)
        animationSet.start()
    }

    override fun onStop() {
        super.onStop()
        binding.animationView.cancelAnimation()
        animationSet.cancel()
    }
}