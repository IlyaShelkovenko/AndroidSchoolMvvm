/**
 * Created by Ilia Shelkovenko on 26.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.profile.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewbinding.ViewBinding
import com.gmail.hostov47.androidschoolmvvm.ImdbApp
import com.gmail.hostov47.androidschoolmvvm.R
import com.gmail.hostov47.androidschoolmvvm.databinding.FragmentFavoriteBinding
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview
import com.gmail.hostov47.androidschoolmvvm.presentation.base.BindingFragment
import com.gmail.hostov47.androidschoolmvvm.presentation.home.HomeFragment
import com.gmail.hostov47.androidschoolmvvm.presentation.profile.ProfileFragment.Companion.MOVIES_SPAN_COUNT
import com.gmail.hostov47.androidschoolmvvm.presentation.profile.adapters.MoviePreviewItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import javax.inject.Inject

class FavoriteMoviesFragment: BindingFragment<FragmentFavoriteBinding>(){

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentFavoriteBinding::inflate

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    @Inject
    lateinit var viewModelFactory: FavoriteViewModelFactory

    private val viewModel: FavoriteViewModel
            by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as ImdbApp).appComponent.getFavoriteMoviesComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.moviesRecyclerView.layoutManager = GridLayoutManager(context, MOVIES_SPAN_COUNT)
        binding.moviesRecyclerView.adapter = adapter.apply { addAll(listOf()) }

        viewModel.favoriteMoviesLive.observe(viewLifecycleOwner, Observer {
            val moviesList = it.map { MoviePreviewItem(it) { movie -> openMovieDetails(movie)} }.toList()
            binding.moviesRecyclerView.adapter = adapter.apply { update(moviesList) }
        })

        viewModel.errorLive.observe(viewLifecycleOwner, Observer {
            showToast(it.message ?: "Unknown error")
        })
    }

    private fun openMovieDetails(movie: MoviePreview) {
        val bundle = Bundle()
        bundle.putInt(HomeFragment.MOVIE_ID, movie.movieId ?: 0)
        findNavController().navigate(R.id.detailsFragment, bundle)
    }
}