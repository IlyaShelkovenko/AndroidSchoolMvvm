/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewbinding.ViewBinding
import com.gmail.hostov47.androidschoolmvvm.ImdbApp
import com.gmail.hostov47.androidschoolmvvm.R
import com.gmail.hostov47.androidschoolmvvm.databinding.FragmentHomeBinding
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview
import com.gmail.hostov47.androidschoolmvvm.presentation.base.BindingFragment
import com.gmail.hostov47.androidschoolmvvm.presentation.home.adapters.MoviesAdapter
import com.gmail.hostov47.androidschoolmvvm.presentation.home.adapters.OnMovieItemClick
import javax.inject.Inject


class HomeFragment : BindingFragment<FragmentHomeBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentHomeBinding::inflate

    @Inject
    lateinit var viewModelFactory: HomeViewModelFactory

    private val viewModel: HomeViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as ImdbApp).appComponent.getHomeComponent().inject(this)
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



    private fun openMovieDetails(movie: MoviePreview) {
        val bundle = Bundle()
        bundle.putInt(MOVIE_ID, movie.movieId ?: 0)
        findNavController().navigate(R.id.detailsFragment, bundle)
    }

    private fun showLoading(show: Boolean) {

        if (show) {
            binding.moviesRecyclerView.visibility = View.GONE
            binding.animationView.visibility = View.VISIBLE
            binding.animationView.playAnimation()
        } else {
            binding.animationView.cancelAnimation()
            binding.animationView.visibility = View.GONE
            binding.moviesRecyclerView.visibility = View.VISIBLE
        }
    }

    override fun onStop() {
        super.onStop()
        binding.animationView.cancelAnimation()
    }

    companion object {
        const val MOVIE_ID = "MOVIE_ID"
        const val NUMBER_OF_COLUMNS = 2
    }
}