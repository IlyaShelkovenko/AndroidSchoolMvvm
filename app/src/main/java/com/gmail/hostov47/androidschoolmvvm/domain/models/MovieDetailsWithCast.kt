/**
 * Created by Ilia Shelkovenko on 17.04.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.domain.models

import com.gmail.hostov47.androidschoolmvvm.data.network.responses.Cast


class MovieDetailsWithCast(
    val id: Int,
    val posterPath: String? = null,
    val title: String,
    val rating: Float,
    val overview: String? = null,
    productCompanies: String = "",
    genres: String = "",
    val releaseDate: String,
    val cast: List<Cast>
){
    val productionCompanies: String = if(productCompanies.isEmpty()) "-" else productCompanies
    val genres: String = if(genres.isEmpty()) "-" else genres
}