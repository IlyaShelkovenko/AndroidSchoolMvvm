/**
 * Created by Ilia Shelkovenko on 07.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewbinding.ViewBinding
import com.gmail.hostov47.androidschoolmvvm.ImdbApp
import com.gmail.hostov47.androidschoolmvvm.R
import com.gmail.hostov47.androidschoolmvvm.databinding.FragmentSearchBinding
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview
import com.gmail.hostov47.androidschoolmvvm.presentation.base.BindingFragment
import com.gmail.hostov47.androidschoolmvvm.presentation.home.HomeFragment
import com.gmail.hostov47.androidschoolmvvm.presentation.home.adapters.CategoryItem
import com.gmail.hostov47.androidschoolmvvm.presentation.home.adapters.MoviesAdapter
import com.gmail.hostov47.androidschoolmvvm.presentation.home.adapters.OnMovieItemClick
import com.gmail.hostov47.androidschoolmvvm.presentation.search.adapter.MoviesGridAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.search_toolbar.*
import javax.inject.Inject

class SearchFragment: BindingFragment<FragmentSearchBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentSearchBinding::inflate

    @Inject
    lateinit var viewModelFactory: SearchViewModelFactory

    private val viewModel: SearchViewModel by viewModels {
        viewModelFactory
    }
    private lateinit var searchObservable: Observable<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as ImdbApp).appComponent.getSearchComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val onMovieItemClicked = OnMovieItemClick { movie -> openMovieDetails(movie) }
        val adapter = MoviesGridAdapter(listOf(), onMovieItemClicked)
        binding.searchMoviesRecyclerView.layoutManager = GridLayoutManager(requireContext(),NUMBER_OF_COLUMNS)
        binding.searchMoviesRecyclerView.adapter = adapter

        viewModel.errors.observe(viewLifecycleOwner, Observer {
            showToast(it.message ?: "Unknown error")
        })

        viewModel.searchMovies.observe(viewLifecycleOwner, Observer { list ->
            adapter.submitItems(list)
        })

        searchObservable = Observable.create { emitter ->
            search_edit_text.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}
                override fun onTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    emitter.onNext(query.toString())
                }
            })
        }
        viewModel.handleSearch(searchObservable)
    }

    private fun openMovieDetails(movie: MoviePreview) {
        val bundle = Bundle()
        bundle.putInt(HomeFragment.MOVIE_ID, movie.movieId ?: 0)
        findNavController().navigate(R.id.detailsFragment, bundle, options)
    }

    companion object {
        const val NUMBER_OF_COLUMNS = 2
    }
}