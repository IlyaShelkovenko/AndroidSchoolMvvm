/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.gmail.hostov47.androidschoolmvvm.R
import com.gmail.hostov47.androidschoolmvvm.data.network.api.OkHttpApi
import com.gmail.hostov47.androidschoolmvvm.data.network.responses.Movie
import com.gmail.hostov47.androidschoolmvvm.data.network.responses.MoviesResponse
import com.gmail.hostov47.androidschoolmvvm.data.repository.HomeRepositoryImpl
import com.gmail.hostov47.androidschoolmvvm.databinding.FragmentHomeBinding
import com.gmail.hostov47.androidschoolmvvm.presentation.base.BindingFragment
import com.gmail.hostov47.androidschoolmvvm.presentation.home.adapters.CategoriesContainer
import com.gmail.hostov47.androidschoolmvvm.presentation.home.adapters.MovieItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder

class HomeFragment : BindingFragment<FragmentHomeBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentHomeBinding::inflate

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(HomeRepositoryImpl(OkHttpApi()))
    }

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.moviesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.moviesRecyclerView.adapter = adapter.apply { update(listOf()) }

        viewModel.showLoadingLive.observe(viewLifecycleOwner, Observer {
            showLoading(it)
        })

        viewModel.moviesMapLive.observe(viewLifecycleOwner, Observer {
            for (item in it) {
                addToGroup(item.first, item.second)
            }
        })
    }

    private fun addToGroup(@StringRes title: Int, response: MoviesResponse) {
        val moviesList = listOf(
                CategoriesContainer(
                        title,
                        response.results?.map { movieRes ->
                            MovieItem(movieRes) { movie ->
                                openMovieDetails(
                                    movie
                                )
                            }
                        }?.toList() ?: listOf()
                )
        )
        binding.moviesRecyclerView.adapter = adapter.apply { addAll(moviesList) }
    }

    private fun openMovieDetails(movie: Movie) {
        val bundle = Bundle()
        bundle.putInt(MOVIE_ID, movie.id ?: 0)
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
    }
}