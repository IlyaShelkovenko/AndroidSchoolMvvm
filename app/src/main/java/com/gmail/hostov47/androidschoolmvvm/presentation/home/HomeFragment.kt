/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.gmail.hostov47.androidschoolmvvm.ImdbApp
import com.gmail.hostov47.androidschoolmvvm.R
import com.gmail.hostov47.androidschoolmvvm.databinding.FragmentHomeBinding
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview
import com.gmail.hostov47.androidschoolmvvm.presentation.base.BindingFragment
import com.gmail.hostov47.androidschoolmvvm.presentation.home.adapters.CategoryAdapter
import com.gmail.hostov47.androidschoolmvvm.presentation.home.adapters.CategoryItem
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
        val adapter = CategoryAdapter(onMovieItemClicked)
        binding.moviesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.moviesRecyclerView.adapter = adapter
        binding.swipeRefresh.setOnRefreshListener {
            adapter.clearList()
            viewModel.onRefreshLayout()
        }

        viewModel.showLoading.observe(viewLifecycleOwner, Observer {
            showLoading(it)
        })

        viewModel.popularMovies.observe(viewLifecycleOwner, Observer { list ->
            adapter.addItem(CategoryItem("Популярные", list))
            binding.swipeRefresh.isRefreshing = false
        })

        viewModel.upcomingMovies.observe(viewLifecycleOwner, Observer { list ->
            adapter.addItem(CategoryItem("Новые", list))
            binding.swipeRefresh.isRefreshing = false
        })

        viewModel.nowPlayingMovies.observe(viewLifecycleOwner, Observer { list ->
            adapter.addItem(CategoryItem("Рекомендуемые", list))
            binding.swipeRefresh.isRefreshing = false
        })

        viewModel.errors.observe(viewLifecycleOwner, Observer {
            showToast(it.message ?: "Unknown error")
            binding.swipeRefresh.isRefreshing = false
        })
    }

    private fun openMovieDetails(movie: MoviePreview) {
        val bundle = Bundle()
        bundle.putInt(MOVIE_ID, movie.movieId ?: 0)
        findNavController().navigate(R.id.detailsFragment, bundle, options)
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