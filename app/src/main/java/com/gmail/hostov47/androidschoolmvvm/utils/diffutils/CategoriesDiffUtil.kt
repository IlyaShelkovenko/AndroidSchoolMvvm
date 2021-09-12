/**
 * Created by Ilia Shelkovenko on 11.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.utils.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.gmail.hostov47.androidschoolmvvm.presentation.home.adapters.CategoryItem

class CategoriesDiffUtil(
    private val oldList: List<CategoryItem>,
    private val newList: List<CategoryItem>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].title == newList[newItemPosition].title

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}