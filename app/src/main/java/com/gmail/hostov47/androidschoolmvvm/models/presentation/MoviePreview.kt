/**
 * Created by Ilia Shelkovenko on 28.04.2021.
 */
package com.gmail.hostov47.androidschoolmvvm.models.presentation


data class MoviePreview(
    val movieId: Int,
    val title: String,
    var poster: String,
    val rating: Float
)