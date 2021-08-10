/**
 * Created by Ilia Shelkovenko on 10.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.home.adapters

import com.gmail.hostov47.androidschoolmvvm.models.presentation.MoviePreview

fun interface OnMovieItemClick {
    fun onClick(item: MoviePreview)
}