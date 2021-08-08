/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.home.adapters

import androidx.annotation.StringRes
import com.gmail.hostov47.androidschoolmvvm.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_category.view.*

/**
 * Адаптер для отображения категорий фильмов.
 *
 * @author Шелковенко Илья on 2021-08-04
 */
class CategoriesContainer(
    @StringRes
    private val title: Int,
    private val items: List<Item>
) : Item() {

    override fun getLayout() = R.layout.item_category

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.containerView.title_text_view.text = viewHolder.containerView.context.getString(title)
        viewHolder.containerView.items_container.adapter =
            GroupAdapter<GroupieViewHolder>().apply { addAll(items) }
    }
}