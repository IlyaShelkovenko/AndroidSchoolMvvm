/**
 * Created by Ilia Shelkovenko on 11.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.utils.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview

class MovieDiffUtil(
    private val oldList: List<MoviePreview>,
    private val newList: List<MoviePreview>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].movieId == newList[newItemPosition].movieId

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}