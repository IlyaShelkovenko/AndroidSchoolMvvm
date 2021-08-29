/**
 * Created by Ilia Shelkovenko on 28.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.profile.adapters

import com.gmail.hostov47.androidschoolmvvm.R
import com.gmail.hostov47.androidschoolmvvm.extensions.load
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_card_small.view.*

class MoviePreviewItem(
    private val content: MoviePreview,
    private val onClick: (favorite: MoviePreview) -> Unit
) : Item() {

    override fun getLayout() = R.layout.item_card_small

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.image_preview.setOnClickListener {
            onClick.invoke(content)
        }
        viewHolder.itemView.image_preview.load(content.poster)
    }
}