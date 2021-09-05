package com.gmail.hostov47.androidschoolmvvm.presentation.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.hostov47.androidschoolmvvm.R
import com.gmail.hostov47.androidschoolmvvm.databinding.ItemCardBinding
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview
import com.gmail.hostov47.androidschoolmvvm.extensions.load


/**
 * Адаптер для отображения фильмов.
 *
 * @author Шелковенко Илья on 2021-08-04
 */
class MoviesAdapter(private var movies: List<MoviePreview>, private val onClickListener: OnMovieItemClick) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    fun submitItems(newMovies: List<MoviePreview>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder =
        MoviesViewHolder.create(parent, onClickListener)


    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bindView(movies[position])
    }

    override fun getItemCount(): Int = movies.size


    class MoviesViewHolder private constructor(itemView: View, private val onClickListener: OnMovieItemClick) :
        RecyclerView.ViewHolder(itemView) {

        private val binding: ItemCardBinding = ItemCardBinding.bind(itemView)

        fun bindView(moviePreview: MoviePreview) {
            itemView.setOnClickListener { onClickListener.onClick(moviePreview) }
            binding.apply {
                with(moviePreview) {
                    movieTitle.text = title
                    imagePreview.load(poster)
                    movieRating.rating = rating ?: 0f
                }
            }
        }

        companion object {
            fun create(container: ViewGroup, onClickListener: OnMovieItemClick) = MoviesViewHolder(
                LayoutInflater.from(container.context).inflate(R.layout.item_card, container, false), onClickListener
            )
        }
    }
}