/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.details

import android.os.Bundle
import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.gmail.hostov47.androidschoolmvvm.R
import com.gmail.hostov47.androidschoolmvvm.data.network.api.OkHttpApi
import com.gmail.hostov47.androidschoolmvvm.data.repository.DetailsRepositoryImpl
import com.gmail.hostov47.androidschoolmvvm.databinding.FragmentDetailsBinding
import com.gmail.hostov47.androidschoolmvvm.extensions.load
import com.gmail.hostov47.androidschoolmvvm.presentation.base.BindingFragment
import com.gmail.hostov47.androidschoolmvvm.presentation.details.adapters.ActorItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_details.*
import com.gmail.hostov47.androidschoolmvvm.domain.models.MovieDetailsWithCast
import com.gmail.hostov47.androidschoolmvvm.domain.models.MoviePreview
import com.gmail.hostov47.androidschoolmvvm.presentation.home.HomeFragment

class DetailsFragment : BindingFragment<FragmentDetailsBinding>(){
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentDetailsBinding::inflate

    private val viewModel: DetailsViewModel by viewModels {
        DetailsViewModelFactory(DetailsRepositoryImpl(OkHttpApi()))
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
        viewModel.showLoadingLive.observe(viewLifecycleOwner, Observer {
            showLoading(it)
        })

        viewModel.detailsWithCastLive.observe(viewLifecycleOwner, Observer { result ->
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
            progress_bar.visibility = View.VISIBLE
            content_layout.visibility = View.GONE
        } else {
            progress_bar.visibility = View.GONE
            content_layout.visibility = View.VISIBLE
        }
    }

    private fun bindMovie(movieDetails: MovieDetailsWithCast) {
        iv_movie_poster.load(movieDetails.posterPath!!)
        tv_movie_title.text = movieDetails.title
        rating_bar.rating = movieDetails.rating
        tv_movie_description.text = movieDetails.overview
        tv_studio.text = movieDetails.productionCompanies
        tv_genre.text = movieDetails.genres
        tv_year.text = movieDetails.releaseDate

        val actorsItem = movieDetails.cast.map { ActorItem(it) }.toList()
        rv_cast.adapter = adapter.apply { update(actorsItem) }
    }

}