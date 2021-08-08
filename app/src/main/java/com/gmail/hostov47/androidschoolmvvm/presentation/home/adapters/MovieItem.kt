/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.home.adapters

import com.gmail.hostov47.androidschoolmvvm.R
import com.gmail.hostov47.androidschoolmvvm.data.network.responses.Movie
import com.gmail.hostov47.androidschoolmvvm.extensions.load
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_card.view.*

/**
 * Адаптер для отображения фильмов внутри разных категорий.
 *
 * @author Шелковенко Илья on 2021-08-04
 */
class MovieItem(
    private val content: Movie,
    private val onClick: (movie: Movie) -> Unit) : Item()
{
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.containerView.description.text = content.title
        viewHolder.containerView.movie_rating.rating = (content.voteAverage?.toFloat() ?: 0f) / 2
        viewHolder.containerView.content.setOnClickListener {
            onClick.invoke(content)
        }

        content.posterPath?.let{
            viewHolder.containerView.image_preview
                .load(content.posterPath!!)
        }
    }

    override fun getLayout(): Int = R.layout.item_card
}